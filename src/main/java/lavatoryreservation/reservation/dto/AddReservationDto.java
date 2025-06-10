package lavatoryreservation.reservation.dto;

import java.time.LocalDateTime;

public record AddReservationDto(Long memberId, Long toiletId, LocalDateTime startTime, LocalDateTime endTime) {

}
