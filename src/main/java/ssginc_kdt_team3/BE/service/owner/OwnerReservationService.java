package ssginc_kdt_team3.BE.service.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.OwnerReservationDTO;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.domain.Shop;
import ssginc_kdt_team3.BE.enums.DepositStatus;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.deposit.DepositRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;
import ssginc_kdt_team3.BE.repository.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.repository.shop.JpaDataShopRepository;
import ssginc_kdt_team3.BE.util.TimeUtils;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OwnerReservationService {

    private final JpaDataReservationRepository reservationRepository;
    private final DepositRepository depositRepository;
    private final JpaDataShopRepository shopRepository;
    private final OwnerRepository ownerRepository;

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
    public boolean customerCancel(Long id) {
        Optional<Reservation> byId = reservationRepository.findById(id);

        if (byId.isPresent()) {
            Reservation reservation = byId.get();

            ReservationStatus status = reservation.getStatus();

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

            List<Reservation> reservationList =
                    reservationRepository.findAllByShop_IdAndReservationDateBetweenOrderByReservationDate(shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
        }

        return null;
    }

    public Page<OwnerReservationDTO> showShopReservationReservation(Long ownerId, Pageable pageable, Map<String,String> request) {
        Optional<Shop> shopByOwnerId = shopRepository.findShopByOwner_id(ownerId);

        if (shopByOwnerId.isPresent()) {
            Shop shop = shopByOwnerId.get();

            String start = request.get("start")+" 00:00:00";
            String end = request.get("end")+" 23:59:59";

            LocalDateTime startTime = TimeUtils.stringParseLocalDataTime(start);
            LocalDateTime endTime = TimeUtils.stringParseLocalDataTime(end);

            List<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDate
                            (ReservationStatus.RESERVATION, shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
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

            List<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDate
                            (ReservationStatus.DONE, shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
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

            List<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDate
                            (ReservationStatus.CANCEL, shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
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

            List<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDate
                            (ReservationStatus.IMMINENT, shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
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

            List<Reservation> reservationList =
                    reservationRepository.findAllByStatusAndShop_IdAndReservationDateBetweenOrderByReservationDate
                            (ReservationStatus.NOSHOW, shop.getId(), startTime, endTime);
            return toDtoPage(reservationList, pageable);
        }

        return null;
    }


    /*
    * 이현님 reserveService랑 합침
    * */


    // 활성화된 예약 조회: ReservationStatus가 RESERVATION인 경우
    // List로 Reservation 받아와서 -> dto -> 페이지로
    public Page<OwnerReservationDTO> getActiveReserve(Pageable pageable, Long ownerId) {
        Shop shop = shopRepository.findShopByOwner_id(ownerId).get();
//    List<Reservation> allByShopId = jpaDataReservationRepository.findAllByShop_Id(shop.getId()); 이건 shopId가 같은 예약정보 리스트야

        List<Reservation> byStatus = ownerRepository.findByStatus(shop.getId());

        List<OwnerReservationDTO> dtoList = new ArrayList<>();
        for (Reservation list : byStatus) {
            log.info("없는 아이디는 몇번일까 ============ {}", list.getId());
            Deposit reservationDeposit = depositRepository.findReservationDeposit(list.getId());
            OwnerReservationDTO dto = new OwnerReservationDTO(list, reservationDeposit);
            dtoList.add(dto);
        }
        // dto -> Page로
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), dtoList.size());

        Page<OwnerReservationDTO> activePage = new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
        return activePage;
    }



    // 당일 예약 시간별 조회
    public Page<OwnerReservationDTO> getReserveTime(String type, Long ownerId, Pageable pageable) {
        // shopId 찾아와서 내 매장의 예약시간만 가져오고 싶어 -> shop.getId();
        Shop shop = shopRepository.findShopByOwner_id(ownerId).get();

        LocalDateTime now = LocalDateTime.now();

        if (type.equals("A")) { // 1시간 후
            List<Reservation> after1h = ownerRepository.findDateBetween(now, now.plusHours(1), shop.getId());
            return toDtoPage(after1h, pageable);

        } else if (type.equals("B")) { // 3시간 후
            log.info("service B");
            List<Reservation> after3h = ownerRepository.findDateBetween(now, now.plusHours(3), shop.getId());
            return toDtoPage(after3h, pageable);

        } else if (type.equals("C")) { // 점심시간(11~1)
            LocalDateTime startLunch = now.with(LocalTime.of(11, 0));
            List<Reservation> betweenLunch = ownerRepository.findDateBetween(startLunch, startLunch.plusHours(2), shop.getId());
            return toDtoPage(betweenLunch, pageable);

        } else if (type.equals("D")) { // 저녁시간(17~19)
            LocalDateTime startDinner = now.with(LocalTime.of(17, 0));
            List<Reservation> betweenDinner = ownerRepository.findDateBetween(startDinner, startDinner.plusHours(2), shop.getId());
            return toDtoPage(betweenDinner, pageable);

        } else if (type.equals("E")) { // 전체 시간
            LocalDateTime start = now.with(LocalTime.of(0, 1));
            log.info("기준 시작 시간 = {}", start);
            LocalDateTime end = now.with(LocalTime.of(23, 59));
            log.info("기준 종료 시간 = {}", end);
            List<Reservation> allTime = ownerRepository.findDateBetweenAll(now.with(LocalTime.of(1, 1)), now.with(LocalTime.of(23, 59)), shop.getId());
            return toDtoPage(allTime, pageable);
        }
        return null;
    }

    // List로 Reservation 받아와서 -> dto -> 페이지로
    private Page<OwnerReservationDTO> toDtoPage(List<Reservation> reservationList, Pageable pageable) {
        List<OwnerReservationDTO> dtoList = new ArrayList<>();

        for (Reservation res : reservationList) {
            Deposit reservationDeposit = depositRepository.findReservationDeposit(res.getId());
            OwnerReservationDTO dto = new OwnerReservationDTO(res, reservationDeposit);
            dtoList.add(dto);
        }

        // dto -> Page로
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), dtoList.size());

        Page<OwnerReservationDTO> ownerPage = new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());

        return ownerPage;
    }


}
