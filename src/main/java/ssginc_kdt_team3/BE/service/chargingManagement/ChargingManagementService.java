package ssginc_kdt_team3.BE.service.chargingManagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssginc_kdt_team3.BE.domain.ChargingDetail;
import ssginc_kdt_team3.BE.domain.ChargingManagement;
import ssginc_kdt_team3.BE.domain.Deposit;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingDetailRepository;
import ssginc_kdt_team3.BE.repository.charging.JpaDataChargingManagementRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChargingManagementService {

    private final JpaDataChargingManagementRepository chargingManagementRepository;
    private final JpaDataChargingDetailRepository chargingDetailRepository;

    public boolean saveReservationPayment(Deposit deposit) {

        ChargingManagement reservationChargingManagement = ChargingManagement.builder()
                .changeDate(deposit.getReservation().getApplyTime())
                .changeReason("예약금 결제")
                .status(false)
                .value(deposit.getPayValue())
                .customer(deposit.getReservation().getCustomer())
                .deposit(deposit).build();

        ChargingManagement saveManagement = chargingManagementRepository.save(reservationChargingManagement);
        saveChargingDetail(saveManagement);

        return true;
    }

    public boolean saveReservationUpdatePayment(Deposit deposit, int needPayValue) {

        ChargingManagement reservationChargingManagement = ChargingManagement.builder()
                .changeDate(deposit.getReservation().getChangeTime())
                .changeReason("예약금 추가 결제")
                .status(false)
                .value(needPayValue)
                .customer(deposit.getReservation().getCustomer())
                .deposit(deposit).build();

        ChargingManagement saveManagement = chargingManagementRepository.save(reservationChargingManagement);
        saveChargingDetail(saveManagement);

        return true;
    }

    public boolean saveRefundPayment(Deposit deposit) {

        ChargingManagement reservationChargingManagement = ChargingManagement.builder()
                .changeDate(deposit.getReservation().getChangeTime())
                .changeReason("예약금 환불")
                .status(true)
                .value(deposit.getPayValue()-deposit.getPenaltyValue())
                .customer(deposit.getReservation().getCustomer())
                .deposit(deposit).build();

        ChargingManagement saveManagement = chargingManagementRepository.save(reservationChargingManagement);
        saveRefundChargingDetail(saveManagement);

        return true;
    }

    public boolean savePartRefundPayment(Deposit deposit, int returnMoney) {

        ChargingManagement reservationChargingManagement = ChargingManagement.builder()
                .changeDate(deposit.getReservation().getChangeTime())
                .changeReason("예약금 환불")
                .status(true)
                .value(returnMoney)
                .customer(deposit.getReservation().getCustomer())
                .deposit(deposit).build();

        ChargingManagement saveManagement = chargingManagementRepository.save(reservationChargingManagement);
        saveRefundChargingDetail(saveManagement);

        return true;
    }


    private void saveChargingDetail(ChargingManagement chargingManagement) {

        int payment = chargingManagement.getValue();

        List<Object[]> details = chargingDetailRepository.balanceInquiry(chargingManagement.getCustomer().getId());
        int i = 0;

        while (payment > 0) {
            Object[] objects = details.get(i);

            Long detailUseId = (Long) objects[0];
            Long balance = (Long) objects[1];

            if (payment > balance) {
                payment -= balance;

                ChargingDetail reservationChargingDetail = ChargingDetail.builder()
                        .detailUseId(detailUseId)
                        .operateDate(chargingManagement.getChangeDate())
                        .status(false)
                        .value(Integer.parseInt(Long.toString(balance)))
                        .chargingManagement(chargingManagement).build();

                chargingDetailRepository.save(reservationChargingDetail);

            } else if (payment <= balance) {

                ChargingDetail reservationChargingDetail = ChargingDetail.builder()
                        .detailUseId(detailUseId)
                        .operateDate(chargingManagement.getChangeDate())
                        .status(false)
                        .value(Integer.parseInt(Long.toString(payment)))
                        .chargingManagement(chargingManagement).build();
                chargingDetailRepository.save(reservationChargingDetail);

                payment = 0;
            }

            i += 1;
        }
    }

    private void saveRefundChargingDetail(ChargingManagement chargingManagement) {
        ChargingDetail refundChargingDetail = ChargingDetail.builder()
                .operateDate(chargingManagement.getChangeDate())
                .status(true)
                .value(chargingManagement.getValue())
                .chargingManagement(chargingManagement).build();

        ChargingDetail detail = chargingDetailRepository.save(refundChargingDetail);
        detail.setDetailUseId();
        chargingDetailRepository.save(detail);
    }
}
