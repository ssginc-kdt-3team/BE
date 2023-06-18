package ssginc_kdt_team3.BE.service.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ssginc_kdt_team3.BE.DTOs.reservation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.ResponseSmsDTO;
import ssginc_kdt_team3.BE.domain.*;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.review.JpaDataReviewRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.service.chargingManagement.ChargingManagementService;
import ssginc_kdt_team3.BE.service.coupon.CouponProvideService;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;
import ssginc_kdt_team3.BE.util.TimeUtils;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final JpaDataShopRepository shopRepository;
    private final JpaCustomerRepository customerRepository;
    private final DepositRepository depositRepository;
    private final JpaDataReviewRepository reviewRepository;
    private final CustomerChargingService chargingService;
    private final ChargingManagementService chargingManagementService;
    private final NaverAlarmService naverAlarmService;
    private final PointManagementService pointManagementService;
    private final CouponProvideService couponProvideService;

    @Value("${reservation.depositPerPerson}")
    private int depositPerPerson;

    MessageDTO customerMessageDTO = new MessageDTO();
    MessageDTO ownerMessageDTO = new MessageDTO();

    @Transactional(readOnly = false)
    public Long makeReservation(@NotNull CustomerReservationAddDTO dto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        Shop shop = shopRepository.findById(dto.getShopId()).get();

        Customer customer = customerRepository.findCustomer(dto.getUserId()).get();
        //DTO 검증
        Reservation reservation = setReservationInfo(dto, shop, customer);

        try {
            int pointValue = Integer.parseInt(dto.getPointValue());
            int originValue = calculatingDeposit(dto.getPeople(), dto.getChild());
            //쿠폰 할인
            int couponDiscount = 0;
            if (dto.getCouponId() > 0) {
                couponDiscount = couponProvideService.getCouponDiscount(dto, originValue);
            }

            //포인트 할인
            int pointDiscount = 0;
            if (isEnoughPoint(dto.getUserId(), pointValue)) {
                pointDiscount += pointValue;
            } else {
                return null;
            }
            log.info("=====================================");
            //총 할인 금액
            int discount = couponDiscount + pointDiscount;
            //실제 계산 금액
            int needValue = originValue - discount;
            log.info("필요한 금액 : {}",needValue);

            //충전금 충분한지 확인
            if (isEnoughMoney(dto.getUserId(), needValue)) {

                Reservation saveReservation = reservationRepository.save(reservation);
                log.info("================================================================can");

                //예약금 정보 생성
                Deposit deposit = new Deposit();
                deposit.setReservation(saveReservation);
                deposit.setStatus(DepositStatus.RECEIVE);
                deposit.setOrigin_value(originValue);
                deposit.setPayValue(needValue);
                deposit.setPointDiscount(pointDiscount);
                deposit.setCouponDiscount(couponDiscount);
                Deposit saveDeposit = depositRepository.save(deposit);

                //예약금 결제 정보  생성
                boolean b = chargingManagementService.saveReservationPayment(saveDeposit);

                if (pointDiscount != 0) {
                    boolean c = pointManagementService.savePointPayment(saveDeposit);
                }

                boolean d = couponProvideService.saveReservationCouponUseStatus(saveDeposit, dto.getCouponId());

                //알림 메시지 추가
                sendMessage(dto, shop, customer);

                return saveReservation.getId();
            }

            return null;

        } catch (Exception e) {
            log.info("================================================================bomb");
            return null;
        }
    }

    public Page<CustomerReservationListDTO> showMyActiveReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllActive(id, ReservationStatus.RESERVATION);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }

    public Page<CustomerReservationListDTO> showMyAllReservation(Pageable pageable, Long id) {
        List<Reservation> allBy = reservationRepository.findAllMy(id);
        Page<CustomerReservationListDTO> customerReservations = listToDTOList(pageable, allBy);

        return customerReservations;
    }

    public Optional<Reservation> showOneReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public CustomerReservationUpdateDTO showUpdateReservation(Long id) {

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            CustomerReservationUpdateDTO dto = new CustomerReservationUpdateDTO(byId.get());
            return dto;
        }

        return null;
    }


    public Map<String, String> updateReservation(Long id, @Validated CustomerReservationUpdateDTO dto) {

        Map<String, String> result = new HashMap<>();

        if (dto.getPeople() <= dto.getChild()) {
            result.put("result", "false");
            result.put("error", "too many baby");
            return result;
        }

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            if (reservation.getStatus() == ReservationStatus.RESERVATION) {
                Deposit oldDeposit = depositRepository.findReservationDeposit(reservation.getId());
                int originDeposit = oldDeposit.getOrigin_value();
                int afterDeposit = calculatingDeposit(dto.getPeople(), dto.getChild());
                // 방문 하루 전에는 변경 불가능
                if (TimeUtils.findNow().isBefore(reservation.getReservationDate().minusHours(24))) {
                    if (originDeposit == afterDeposit) {
                        update(reservation, dto);
                        try {
                            reservationRepository.save(reservation);
                            result.put("result", "true");
                            return result;
                        } catch (Exception e) {
                            result.put("result", "false");
                            result.put("error", "reservation save error");
                            return result;
                        }
                    } else if (originDeposit > afterDeposit) {

                        if (oldDeposit.getPayValue() >= afterDeposit) {

                            update(reservation, dto);
                            reservationRepository.save(reservation);

                            int afterNeed = afterDeposit - (oldDeposit.getPointDiscount() + oldDeposit.getCouponDiscount());
                            int returnMoney = 0;
                            int returnPoint = 0;

                            if (oldDeposit.getPayValue() - afterNeed >= 0) {
                                returnMoney = oldDeposit.getPayValue() - afterNeed;
                            } else {
                                returnPoint = afterNeed - oldDeposit.getPayValue();
                            }

                            oldDeposit.setStatus(DepositStatus.PART_RETURN);
                            oldDeposit.setOrigin_value(afterDeposit);
                            oldDeposit.setPayValue(afterDeposit);
                            oldDeposit.setPointDiscount(0);
                            oldDeposit.setCouponDiscount(0);

                            Deposit newDeposit = depositRepository.save(oldDeposit);

                            if (returnMoney > 0) {
                                boolean b = chargingManagementService.savePartRefundPayment(newDeposit,returnMoney);
                                log.info("================== {} 만큼 충전금 환불 ========================", returnMoney);
                            }

                            if (returnPoint > 0) {
                                pointManagementService.getPointSave(true, returnPoint, "예약 수정 차액 환불", reservation.getCustomer(), LocalDateTime.now());
                                log.info("================== {} 만큼 포인트 충전 ========================", returnPoint);
                            }


                            result.put("result", "true");
                            return result;
                        } else {

                            int returnPoint = oldDeposit.getOrigin_value() - afterDeposit;

                            update(reservation, dto);
                            reservationRepository.save(reservation);

                            oldDeposit.setStatus(DepositStatus.PART_RETURN);
                            oldDeposit.setOrigin_value(afterDeposit);

                            Deposit newDeposit = depositRepository.save(oldDeposit);

                            //returnPoint만큼 포인트 충전
                            log.info("================== {} 만큼 포인트 충전 ========================", returnPoint);

                            result.put("result", "true");
                            return result;
                        }
                    } else {
                        int needPayValue = afterDeposit - originDeposit;
                        int customerChargingValue = chargingService.showCustomerChargingValue(reservation.getCustomer().getId());

                        // 차액이 얼만지 확인하고 그 차액보다 많이 가지고 있는지 확인한다
                        if (customerChargingValue >= needPayValue) {

                            update(reservation, dto);
                            reservationRepository.save(reservation);

                            oldDeposit.setOrigin_value(afterDeposit);
                            oldDeposit.setPayValue(oldDeposit.getPayValue() + needPayValue);
                            log.info("old id = {}", oldDeposit.getId());

                            Deposit newDeposit = depositRepository.save(oldDeposit);

                            boolean b = chargingManagementService.saveReservationUpdatePayment(newDeposit, needPayValue);

                            result.put("result", "true");
                            return result;
                        }
                        result.put("result", "false");
                        result.put("error", "not enough charging value");
                        return result;
                    }
                }
                result.put("result", "false");
                result.put("error", "over limit day");
                return result;
            }
            result.put("result", "false");
            result.put("error", "not active reservation");
            return result;
        }
        result.put("result", "false");
        result.put("error", "can't find reservation");
        return result;
    }

    public boolean customerCancel(Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {

            Reservation reservation = byId.get();

            LocalDateTime expectedTime = reservation.getReservationDate();

            if (reservation.getStatus() == ReservationStatus.RESERVATION) {

                if (TimeUtils.findNow().isBefore(expectedTime.minusHours(24))) {
                    //방문 에정일자보다 24시간 이상 여유 있는 경우

                    reservation.setStatus(ReservationStatus.CANCEL);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    String customerName = reservation.getCustomer().getName();
                    String customerPhone = reservation.getCustomer().getPhoneNumber();
                    String reservationDateTime = String.valueOf(reservation.getReservationDate());
                    String reservationDate = reservationDateTime.substring(0, reservationDateTime.length() - 5);
                    String shopName = reservation.getShop().getName();

                    String ownerName = reservation.getShop().getOwner().getName();
                    String ownerPhone = reservation.getShop().getOwner().getPhoneNumber();
                    String reservationCancelMessage = "[매장명]: " + shopName + "\n[예약일시]: " + reservationDate+ "\n" + ownerName + "점주님 매장 예약이 취소되었습니다." ;
                    //점주용 메시지

                    ownerMessageDTO.setTo(ownerPhone);
                    ownerMessageDTO.setContent(reservationCancelMessage);

                    ResponseSmsDTO ownerResponse = naverAlarmService.sendSms(ownerMessageDTO);

                    if (reservation.getCustomer().isAlarmBoolean()){
                        String customerContent = "[매장명]: " + shopName + "\n[예약일시]: " + reservationDate+ "\n" + customerName + "고객님 예약 취소가 완료되었습니다!";
                        //고객용 메시지

                        customerMessageDTO.setTo(customerPhone);
                        customerMessageDTO.setContent(customerContent);

                        ResponseSmsDTO customerResponse = naverAlarmService.sendSms(customerMessageDTO);

                        log.info("customerRequestTime = {}", customerResponse.getRequestTime());
                        log.info("customerStatusCode = {}", customerResponse.getStatusCode());
                        //요청에 대한 응답 출력 -> 고객꺼만
                    }
                    log.info("ownerRequestTime = {}", ownerResponse.getRequestTime());
                    log.info("ownerStatusCode = {}", ownerResponse.getStatusCode());
                    //요청에 대한 응답 출력 -> 점주꺼만

                    //전액 환불 구현하기
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.RETURN);
                    Deposit saveDeposit = depositRepository.save(reservationDeposit);

                    boolean b = chargingManagementService.saveRefundPayment(saveDeposit);

                    //쿠폰, 포인트 환불 구현하기

                } else if (TimeUtils.findNow().isBefore(expectedTime.minusHours(8))) {
                    //방문 예정일자보다 8시간 ~ 24시간 여유 있는 경우
                    reservation.setStatus(ReservationStatus.IMMINENT);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    //위약금 50% 환불 50% 구현하기
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.HALF_PENALTY);
                    int originValue = reservationDeposit.getOrigin_value();
                    reservationDeposit.setPenaltyValue(originValue / 2);
                    Deposit saveDeposit = depositRepository.save(reservationDeposit);

                    boolean b = chargingManagementService.saveRefundPayment(saveDeposit);

                    //쿠폰, 포인트 환불 구현하기

                } else {
                    //방문 예정일자보다 8시간 미만으로 여유있는 경우
                    reservation.setStatus(ReservationStatus.IMMINENT);
                    reservation.setChangeTime(TimeUtils.findNow());
                    reservationRepository.save(reservation);

                    //위약금 100%
                    Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());
                    reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);
                    int originValue = reservationDeposit.getOrigin_value();
                    reservationDeposit.setPenaltyValue(originValue);
                    depositRepository.save(reservationDeposit);

                    //쿠폰, 포인트 환불 X
                }
                return true;
            }
            return true;
        }
        return false;
    }

    public Optional<CustomerReservationDetailDTO> findOne(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            Optional<Review> byReservationId = reviewRepository.findByReservationId(reservation.getId());

            boolean canReview = false;

            log.info("예약 가능 일자 {}",LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7)) );
            log.info("예약 여부 {}", reservation.getStatus().equals(ReservationStatus.DONE));
            if (!byReservationId.isPresent() && reservation.getStatus().equals(ReservationStatus.DONE) && LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7))) {
                log.info("예약 가능 일자 {}",LocalDateTime.now().isBefore(reservation.getReservationDate().plusDays(7)) );
                canReview = true;
            }

            //리뷰가 존재하면 작성 불가 false, 존재하지 않으면 작성가능 ture
            CustomerReservationDetailDTO dto = new CustomerReservationDetailDTO(reservation, reservationDeposit, canReview);

            return Optional.ofNullable(dto);
        }

        return Optional.ofNullable(null);
    }

    public List<reservationPossibleDTO> canReservation(Long shopId, String date) {
        Optional<Shop> byId = shopRepository.findById(shopId);
        List<reservationPossibleDTO> result = new ArrayList<>();

        LocalDateTime today = TimeUtils.findNow();
        LocalDate getDate = LocalDate.parse(date);


        LocalDate dayLimit = null;
        if (today.getDayOfMonth() <= 15) {
            dayLimit = LocalDate.of(today.getYear(), today.getMonth().plus(1), 15);

        } else {
            dayLimit = LocalDate.of(today.getYear(), today.getMonth().plus(1), today.getMonth().plus(1).maxLength());
        }

        if (getDate.isAfter(dayLimit) || getDate.isBefore(today.toLocalDate())) {
            return result;
        }


        if (byId.isPresent()) {
            Shop shop = byId.get();
            int limit = shop.getOperationInfo().getSeats();
            LocalTime openTime = shop.getOperationInfo().getOpenTime();
            LocalTime nowTime = LocalTime.now();
            LocalTime orderCloseTime = shop.getOperationInfo().getOrderCloseTime();

            long temp = 1L;
            for (LocalTime time = openTime; time.isBefore(orderCloseTime.plusMinutes(1)); time = time.plusMinutes(30)) {

                LocalDateTime when = LocalDateTime.of(getDate, time);

                int cnt = reservationRepository.countByReservationDateAndShop_Id(when, shopId);

                if (today.toLocalDate().isEqual(getDate) && nowTime.plusMinutes(30).isAfter(time)) {
                    result.add(new reservationPossibleDTO(temp, time, false));
                } else if (cnt < limit) {
                    result.add(new reservationPossibleDTO(temp, time, true));
                } else {
                    result.add(new reservationPossibleDTO(temp, time, false));
                }

                temp += 1;
            }
            return result;
        }

        return result;
    }

    private void sendMessage(CustomerReservationAddDTO dto, Shop shop, Customer customer) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, URISyntaxException {
        String shopName = shop.getName();
        String ownerName = shop.getOwner().getName();
        String ownerPhone = shop.getOwner().getPhoneNumber();
        String customerName = customer.getName();
        String customerPhone = customer.getPhoneNumber();
        String reservationDate = dto.getReservationDate();
        String reservationFormatter = reservationDate.substring(0,reservationDate.length() - 5);


        //고객용 메시지
        String customerReservationMessage = customerName + " 고객님! 예약이 완료되었습니다!\n[예약일시]: " + reservationFormatter + "\n[예약매장]: " + shopName;
        customerMessageDTO.setTo(customerPhone);
        customerMessageDTO.setContent(customerReservationMessage);
        naverAlarmService.sendSms(customerMessageDTO);
        //점주용 메시지
        String ownerReservationMessage = ownerName + " 점주님! 새로운 예약내역입니다!\n[예약 일시]: " + reservationFormatter;
        ownerMessageDTO.setTo(ownerPhone);
        ownerMessageDTO.setContent(ownerReservationMessage);
        naverAlarmService.sendSms(ownerMessageDTO);
    }

    private int calculatingDeposit(int people, int child) {
        return depositPerPerson * (people - child);
    }

    private boolean isEnoughMoney(Long userId, int value) {
        return chargingService.showCustomerChargingValue(userId) >= value;
    }
    private boolean isEnoughPoint(Long userId, int value) {
        return pointManagementService.showCustomerPointValue(userId) >= value;
    }

    //예약 정보를 dto로 받아온 새로운 정보로 변경
    private void update(Reservation before, CustomerReservationUpdateDTO after) {
        LocalDateTime updateTime = TimeUtils.findNow();
        LocalDateTime reservationTime = TimeUtils.stringParseLocalDataTime(after.getReservationDateTime());
        String memo = after.getMemo();
        int people = after.getPeople();
        int child = after.getChild();

        before.setPeople(people);
        before.setChild(child);
        before.setMemo(memo);
        before.setReservationDate(reservationTime);
        before.setChangeTime(updateTime);
    }

    private Page<CustomerReservationListDTO> listToDTOList(Pageable pageable, List<Reservation> allBy) {
        List<CustomerReservationListDTO> customerReservationList = new ArrayList<>();

        for (Reservation r : allBy) {
            CustomerReservationListDTO dto = new CustomerReservationListDTO(r);
            customerReservationList.add(dto);
        }

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), customerReservationList.size());

        Page<CustomerReservationListDTO> customerReservations = new PageImpl<>(customerReservationList.subList(start, end), pageable, customerReservationList.size());
        return customerReservations;
    }

    private Reservation setReservationInfo(CustomerReservationAddDTO dto, Shop shop, Customer customer) {

        Reservation reservation = new Reservation();

        LocalDateTime reservationTime = TimeUtils.stringParseLocalDataTime(dto.getReservationDate());
        reservation.setReservationDate(reservationTime);
        reservation.setPeople(dto.getPeople());
        reservation.setChild(dto.getChild());
        reservation.setMemo(dto.getMemo());
        reservation.setStatus(ReservationStatus.RESERVATION);

        LocalDateTime parse = TimeUtils.findNow();
        reservation.setApplyTime(parse);
        reservation.setChangeTime(parse);
        reservation.setShop(shop);
        reservation.setCustomer(customer);

        return reservation;
    }


}