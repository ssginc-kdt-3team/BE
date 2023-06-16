package ssginc_kdt_team3.BE.service.owner;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.owner.OwnerReviewListDTO;
import ssginc_kdt_team3.BE.DTOs.reservation.*;
import ssginc_kdt_team3.BE.DTOs.reservation.Alarm.MessageDTO;
import ssginc_kdt_team3.BE.domain.*;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.service.customer.NaverAlarmService;
import ssginc_kdt_team3.BE.service.pointManagement.PointManagementService;
import ssginc_kdt_team3.BE.util.TimeUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;
    private final JpaDataShopRepository shopRepository;
    private final OwnerRepository ownerRepository;
    private final PointManagementService pointManagementService;
    private final NaverAlarmService naverAlarmService;

    //고객 입장 처리
    public boolean customerCome(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.DONE);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
                reservationDeposit.setStatus(DepositStatus.PAYMENT);
                depositRepository.save(reservationDeposit);


                savePoint(id, reservation);
                return true;
            }
            return false;
        }
        return false;
    }

    //고객 노쇼 처리
    public boolean customerNoShow(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.NOSHOW);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
                int originValue = reservationDeposit.getOrigin_value();
                reservationDeposit.setPenaltyValue(originValue);
                reservationDeposit.setStatus(DepositStatus.ALL_PENALTY);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                depositRepository.save(reservationDeposit);

                return true;
            }
            return false;
        }
        return false;
    }

    //고객 취소 처리
    public boolean customerCancel(Long id) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException, InvalidKeyException, JsonProcessingException {
        Optional<Reservation> byId = reservationRepository.findById(id);

        log.info("================== 테스트 ==================");
        log.info("reservation = {}",byId);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

            boolean alarmReceive = reservation.getCustomer().isAlarmBoolean();

            if (alarmReceive){
                MessageDTO messageDTO = new MessageDTO();

                String customerPhone = reservation.getCustomer().getPhoneNumber();
                String customerName = reservation.getCustomer().getName();
                String reservationDate = String.valueOf(reservation.getReservationDate());
                String reservationDateType = reservationDate.substring(0,reservationDate.length() - 5);
                String shopName = reservation.getShop().getName();

                log.info("전화번호 = {}",customerPhone);
                log.info("예약 일시 = {}",reservationDateType);

                String content =  customerName + " 고객님, 해당 예약이 취소되었습니다.\n[예약 일시] : " + reservationDateType + "\n[매장명] : " + shopName + "\n[취소 사유] : " + reservation.getCancelReason();

                messageDTO.setTo(customerPhone);
                messageDTO.setContent(content);

                naverAlarmService.sendSms(messageDTO);
            }
            if (status == ReservationStatus.RESERVATION) {
                reservation.setStatus(ReservationStatus.CANCEL);
                reservation.setChangeTime(TimeUtils.findNow());
                reservationRepository.save(reservation);

                Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
                reservationDeposit.setStatus(DepositStatus.RETURN);

                log.info("reservationDeposit.setStatus========= {}", reservationDeposit.getStatus());
                log.info("reservationDeposit.setPenaltyValue=== {}", reservationDeposit.getPenaltyValue());

                depositRepository.save(reservationDeposit);

                return true;
            }
            return false;
        }
        return false;
    }

    //고객 예약 상세 조회
    public Optional<OwnerReservationDTO> showReservationDetail(Long id) {

        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservation.getId());

            OwnerReservationDTO dto = new OwnerReservationDTO(reservation,reservationDeposit);

            return Optional.ofNullable(dto);
        }

        return Optional.ofNullable(null);
    }

    public Page<OwnerReservationDTO> showShopReservationAll(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {

            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByShop_IdAndReservationDateBetweenOrderByReservationDateDesc(shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    /* Page<Entity> -> Page<Dto> 변환처리 */
    private Page<OwnerReservationDTO> toDtoList(Page<Reservation> reservationList){
        Page<OwnerReservationDTO> dtoPage = reservationList.map(m -> OwnerReservationDTO.builder()
                .id(m.getId())
                .ownerID(m.getShop().getOwner().getId())
                .customerID(m.getCustomer().getId())
                .name(m.getCustomer().getName())
                .phoneNumber(m.getCustomer().getPhoneNumber())
                .email(m.getCustomer().getEmail())
                .people(m.getPeople())
                .child(m.getChild())
                .reservationDate(m.getReservationDate())
                .status(m.getStatus())
                .memo(m.getMemo())
                .originValue(depositRepository.findReservationDeposit(m.getId()).getOrigin_value())
                .penaltyValue(depositRepository.findReservationDeposit(m.getId()).getPenaltyValue())
                .build());

        return dtoPage;
    }


    public Page<OwnerReservationDTO> showShopReservationReservation(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc
                            (ReservationStatus.RESERVATION, shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    public Page<OwnerReservationDTO> showShopReservationDone(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc
                            (ReservationStatus.DONE, shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    public Page<OwnerReservationDTO> showShopReservationCancel(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc
                            (ReservationStatus.CANCEL, shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    public Page<OwnerReservationDTO> showShopReservationImminent(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc
                            (ReservationStatus.IMMINENT, shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    public Page<OwnerReservationDTO> showShopReservationNoShow(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            Page<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc
                            (ReservationStatus.NOSHOW, shop.getId(), startTime, endTime, pageable);

            Page<OwnerReservationDTO> dtoPage = toDtoList(reservationList);

            return dtoPage;
        }

        return null;
    }

    /*
     * 이현님 reserveService랑 합침
     * */

    // 당일 예약 시간별 조회
    public Page<OwnerReservationDTO> getReserveTime(String type, Long ownerId, Pageable pageable) {
        // shopId 찾아와서 내 매장의 예약시간만 가져오고 싶어 -> shop.getId();
        Shop shop = shopRepository.findShopByOwner_id(ownerId).get();
        List<ReservationStatus> statuses = new ArrayList<ReservationStatus>(Arrays.asList(ReservationStatus.RESERVATION, ReservationStatus.DONE, ReservationStatus.NOSHOW));

        LocalDateTime now = LocalDateTime.now();

        if (type.equals("A")) { // 1시간 후
            Page<Reservation> after1h = reservationRepository.findDateBetween(statuses,now, now.plusHours(1), shop.getId(), pageable);

            return convertDto(after1h);

        } else if (type.equals("B")) { // 3시간 후
            log.info("service B");
            Page<Reservation> after3h = reservationRepository.findDateBetween(statuses,now, now.plusHours(3), shop.getId(), pageable);
            return convertDto(after3h);

        } else if (type.equals("C")) { // 점심시간(11~1)
            LocalDateTime startLunch = now.with(LocalTime.of(11, 0));
            Page<Reservation> betweenLunch = reservationRepository.findDateBetween(statuses,startLunch, startLunch.plusHours(2), shop.getId(), pageable);
            return convertDto(betweenLunch);

        } else if (type.equals("D")) { // 저녁시간(17~19)
            LocalDateTime startDinner = now.with(LocalTime.of(17, 0));
            Page<Reservation> betweenDinner = reservationRepository.findDateBetween(statuses, startDinner, startDinner.plusHours(2), shop.getId(), pageable);
            return convertDto(betweenDinner);

        } else if (type.equals("E")) { // 전체 시간
            LocalDateTime start = now.with(LocalTime.of(0, 1));
            log.info("기준 시작 시간 = {}", start);
            LocalDateTime end = now.with(LocalTime.of(23, 59));
            log.info("기준 종료 시간 = {}", end);
            Page<Reservation> allTime = reservationRepository.findDateBetweenAll(now.with(LocalTime.of(1, 1)), now.with(LocalTime.of(23, 59)), shop.getId(), pageable);
            return convertDto(allTime);
        }
        return null;
    }

    // Page로 받은 Review 엔티티를 DTO로 변환
    private Page<OwnerReservationDTO> convertDto(Page<Reservation> reservationList) {
        List<OwnerReservationDTO> reservationDTOList = new ArrayList<>();

        for(Reservation r : reservationList){
            Deposit reservationDeposit = depositRepository.findReservationDeposit(r.getId());
            OwnerReservationDTO reservationDTO = new OwnerReservationDTO(r, reservationDeposit);
            reservationDTOList.add(reservationDTO);
        }
        return new PageImpl<>(reservationDTOList, reservationList.getPageable(), reservationList.getTotalElements());
    }

    public List<OwnerMainMonthlyReservationDTO> showMainMonthlyReservation(Long ownerId) {

        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {

            List<OwnerMainMonthlyReservationDTO> mainMonthlyReservationDTOS = new ArrayList<>();

            Shop shop = shopByOwnerId.get();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                    .with(LocalTime.MIN);
            LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                    .with(LocalTime.MAX);

            LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
            LocalDateTime startOfLastMonth = lastMonth.with(TemporalAdjusters.firstDayOfMonth())
                    .with(LocalTime.MIN);
            LocalDateTime endOfLastMonth = lastMonth.with(TemporalAdjusters.lastDayOfMonth())
                    .with(LocalTime.MAX);

            LocalDateTime lastQuarterFirstDay = getLastQuarterFirstDay(now.minusYears(1));
            LocalDateTime lastQuarterLastDay = getLastQuarterLastDay(now.minusYears(1));

            getMonthlyReservations(mainMonthlyReservationDTOS, shop, startOfMonth, endOfMonth);
            getMonthlyReservations(mainMonthlyReservationDTOS, shop, startOfLastMonth, endOfLastMonth);
            getMonthlyReservations(mainMonthlyReservationDTOS, shop, lastQuarterFirstDay, lastQuarterLastDay);


            return mainMonthlyReservationDTOS;
        }

        return null;
    }

    private void getMonthlyReservations(List<OwnerMainMonthlyReservationDTO> mainMonthlyReservationDTOS, Shop shop, LocalDateTime startOfMonth, LocalDateTime endOfMonth) {
        int whole = reservationRepository.countAllReservation(startOfMonth, endOfMonth, shop.getId());
        int done = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.DONE, shop.getId());
        int noShow = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.NOSHOW, shop.getId());
        int i4 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.IMMINENT, shop.getId());
        int i5 = reservationRepository.countReservation(startOfMonth, endOfMonth, ReservationStatus.CANCEL, shop.getId());
        int cancel = i4 + i5;

        mainMonthlyReservationDTOS.add(new OwnerMainMonthlyReservationDTO(whole,
                noShow,
                (int) Math.round(((double)noShow/(double)whole) * 100),
                done,
                (int) Math.round((((double)done/(double)whole))*100),
                cancel,
                (int) Math.round((((double)cancel/(double)whole))*100))) ;
    }

    public List<OwnerMainDailyReservationDTO> showMainDailyReservationCnt(Long ownerId) {

        Optional<Shop> byId = shopRepository.findShopByOwner_id(ownerId);
        List<OwnerMainDailyReservationDTO> result = new ArrayList<>();

        if (byId.isPresent()) {
            Shop shop = byId.get();
            LocalTime openTime = shop.getOperationInfo().getOpenTime();
            LocalDate getDate = LocalDate.now();
            LocalTime orderCloseTime = shop.getOperationInfo().getOrderCloseTime();

            LocalDateTime minusOneMonth = LocalDateTime.now().minusMonths(3);
            LocalDateTime startOfRecent = minusOneMonth.with(TemporalAdjusters.firstDayOfMonth())
                    .with(LocalTime.MIN);

            LocalDateTime minusThreeMonth = LocalDateTime.now().minusMonths(1);
            LocalDateTime endOfRecent = minusThreeMonth.with(TemporalAdjusters.lastDayOfMonth())
                    .with(LocalTime.MAX);


            for (LocalTime time = openTime; time.isBefore(orderCloseTime.plusMinutes(1)); time = time.plusMinutes(30)) {

                LocalDateTime when = LocalDateTime.of(getDate, time);
                int cnt = reservationRepository.countByReservationDateAndShop_Id(when, shop.getId());

                double recentlyNoShowRate = getRecentlyNoShowRate(startOfRecent, endOfRecent, time);

                int expectNoShow = (int) Math.round((cnt * recentlyNoShowRate));

                int noShowRate = (int) Math.round(recentlyNoShowRate * 100);

                OwnerMainDailyReservationDTO ownerMainDailyReservationDTO = new OwnerMainDailyReservationDTO(time.toString(), cnt, noShowRate, expectNoShow);
                result.add(ownerMainDailyReservationDTO);
            }

            return result;
        }

        return null;
    }

    private double getRecentlyNoShowRate(LocalDateTime startOfRecent, LocalDateTime endOfRecent, LocalTime time) {
        int recentlyNoShow = reservationRepository.cntRecentlyStatus(startOfRecent, endOfRecent, time.getHour(), time.getMinute(), ReservationStatus.NOSHOW);
        int recentlyAll = reservationRepository.cntRecentlyAll(startOfRecent, endOfRecent, time.getHour(), time.getMinute());

        log.info("Noshow Num  = {} ", recentlyNoShow);
        log.info("recentlyAll Num  = {} ", recentlyAll);

        if (recentlyNoShow == 0 || recentlyAll == 0) {
            log.info("zero");
            return 0;
        }

        double noShowRate = (double) recentlyNoShow / recentlyAll;

        log.info("noShowRate  = {} ", noShowRate);

        return noShowRate;
    }

    public List<OwnerReservationCalculateListDTO> showCalculateLists(Long shopId) {
        Optional<Shop> byId = shopRepository.findById(shopId);
        List<OwnerReservationCalculateListDTO> result = new ArrayList<>();

        if (byId.isPresent()) {
            LocalDateTime startTime = LocalDateTime.now().minusHours(2);

            List<Reservation> reservations = reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDateDesc(ReservationStatus.DONE, shopId, startTime, LocalDateTime.now());
            for (Reservation r : reservations) {
                OwnerReservationCalculateListDTO reservationCalculateListDTO = new OwnerReservationCalculateListDTO(r);
                result.add(reservationCalculateListDTO);
            }
        }

        return result;
    }

    public String calculatePayment(Long reservationId) {

        Optional<Reservation> byId = reservationRepository.findById(reservationId);

        if (byId.isPresent() && byId.get().getStatus().equals(ReservationStatus.DONE)) {
            Deposit reservationDeposit = depositRepository.findReservationDeposit(reservationId);

            reservationDeposit.setStatus(DepositStatus.PAYMENT);
            int discount = reservationDeposit.getOrigin_value();
            depositRepository.save(reservationDeposit);

            return discount+"원이 계산되었습니다.";

        }

        return "잘못된 요청입니다.";
    }

    private static LocalDateTime getLastQuarterFirstDay(LocalDateTime dateTime) {
        int currentYear = dateTime.getYear();
        int currentQuarter = (dateTime.getMonthValue() - 1) / 3 + 1;
        int currentQuarterFirstMonth = (currentQuarter - 1) * 3 + 1;

        return LocalDateTime.of(currentYear, Month.of(currentQuarterFirstMonth), 1, 0, 0, 0);
    }

    private static LocalDateTime getLastQuarterLastDay(LocalDateTime dateTime) {
        LocalDateTime currentQuarterFirstDay = getLastQuarterFirstDay(dateTime);
        int currentQuarterLastMonth = currentQuarterFirstDay.getMonthValue() + 2;
        int currentQuarterLastDay = currentQuarterFirstDay.withMonth(currentQuarterLastMonth).withDayOfMonth(1).toLocalDate().lengthOfMonth();

        return LocalDateTime.of(currentQuarterFirstDay.getYear(), currentQuarterLastMonth, currentQuarterLastDay, 23, 59, 59);
    }

    private void savePoint(Long id, Reservation reservation) {
        Deposit reservationDeposit = depositRepository.findReservationDeposit(id);
        Customer customer = reservation.getCustomer();
        Double rate = customer.getGrade().getRate();
        int round =(int) Math.round(reservationDeposit.getOrigin_value() * rate);
        pointManagementService.getPointSave(true, round, "예약 식당 방문", reservation.getCustomer(), reservation.getChangeTime());
    }


}