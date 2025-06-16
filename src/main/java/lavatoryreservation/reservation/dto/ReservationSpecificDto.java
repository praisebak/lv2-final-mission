package lavatoryreservation.reservation.dto;

import java.time.LocalDateTime;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.domain.ToiletTime;
import lavatoryreservation.toilet.domain.Toilet;

public record ReservationSpecificDto(String reserverName, String toiletName, String toiletDescription,
                                     LocalDateTime startTime,
                                     LocalDateTime endTime) {
    public static ReservationSpecificDto from(Reservation reservation) {

        Toilet toilet = reservation.getToilet();
        ToiletTime toiletTime = reservation.getToiletTime();
        LocalDateTime toiletTimeStartTime = toiletTime.getStartTime();
        LocalDateTime toiletTimeEndTime = toiletTime.getEndTime();
        String toiletDescription = toilet.getDescription();
        Lavatory lavatory = toilet.getLavatory();
        String lavatoryDescription = lavatory.getDescription();
        return new ReservationSpecificDto(reservation.getAlias(),
                lavatoryDescription,
                toiletDescription,
                toiletTimeStartTime,
                toiletTimeEndTime);
    }
}
