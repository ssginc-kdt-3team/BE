package ssginc_kdt_team3.BE.service.owner.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.DTOs.reservation.ReserveDTO;
import ssginc_kdt_team3.BE.domain.Reservation;
import ssginc_kdt_team3.BE.enums.ReservationStatus;
import ssginc_kdt_team3.BE.repository.owner.reservation.OwnerRepository;
import ssginc_kdt_team3.BE.repository.reservation.JpaDataReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerReserveService {
  private final OwnerRepository ownerRepository;
  private final JpaDataReservationRepository reservationRepository;

  // 매장 모든 예약내역 조회
//  public List<ReserveDTO> getAllReserve(){
//    List<Reservation> allReserve = ownerRepository.findAllReserve();
//
//    reservationRepository.findAll();
//
//    List<ReserveDTO> ownerReserveDTOList = new ArrayList<>();
//    for (Reservation list : allReserve) {
//      ReserveDTO ownerReserveDTO = new ReserveDTO(list);
//      ownerReserveDTOList.add(ownerReserveDTO);
//    }
//    return ownerReserveDTOList;
//  }

  // 페이징처리: Controller 선언 -> Reservation 리스트 가져와서 -> DTO 리스트로 바꾸고 -> 다시 페이지로 바꾼다.
  public Page<ReserveDTO> getAllReserve(Pageable pageable){
//    List<Reservation> allReserve = ownerRepository.findAllReserve();

    Page<Reservation> all = reservationRepository.findAll(pageable);
    System.out.println("all = " + all);
    return toDtoPage(all);
  }

  private Page<ReserveDTO> toDtoPage(Page<Reservation> reservationList) {
    Page<ReserveDTO> reserveDtoList = reservationList.map( //빌더패턴
        m -> ReserveDTO.builder()
            .id(m.getId())
            .name(m.getCustomer().getName())
            .phoneNumber(m.getCustomer().getPhoneNumber())
            .build());
    return reserveDtoList;
  }

  // 활성화된 예약 조회: ReservationStatus가 RESERVATION인 경우
  public List<ReserveDTO> getActiveReserve(){
    List<Reservation> byStatus = ownerRepository.findByStatus(ReservationStatus.RESERVATION);

    List<ReserveDTO> reserveList = new ArrayList<>();
    for(Reservation list : byStatus) {
      ReserveDTO dto = new ReserveDTO(list);
      reserveList.add(dto);
    }
    return reserveList;
  }

  // 당일 예약 시간별 조회 : 서비스도 여러개로 나누지 말고 if else로 열거하기
  // 리턴 결과는 같으니까 ~~~
  public List<ReserveDTO> getReserveTime(){
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime nowPlus1 = now.plusHours(1); //1시간 뒤
    LocalDateTime nowPlus3 = now.plusHours(3); //3시간 뒤

    // 형식 지정
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // 문자열로 변환
    nowPlus1.format(formatter);
    nowPlus3.format(formatter);
    return null;
  }

  public List<ReserveDTO> getTimeAfterOne(){

    //예약정보 다 들고와서, if로 조건 걸어줘야..?

    List<Reservation> allReserve = ownerRepository.findAllReserve();

    List<Reservation> nowPlus1 = ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    List<Reservation> nowPlus3 = ownerRepository.findDateBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(3));

    return null;

  }
//  public List<ReserveDTO> getTimeAfterThree(){
//
//  }




}