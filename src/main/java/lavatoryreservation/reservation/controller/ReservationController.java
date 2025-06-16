package lavatoryreservation.reservation.controller;

import lavatoryreservation.external.auth.MemberAuthentication;
import lavatoryreservation.external.auth.MemberInfo;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.dto.DeleteReservationDto;
import lavatoryreservation.reservation.dto.ReservationSpecificDto;
import lavatoryreservation.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation/")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Long> addReservation(AddReservationDto addReservationDto,
                                               @MemberInfo MemberAuthentication memberAuthentication) {
        AddReservationDto memberAddReservationDto = new AddReservationDto(memberAuthentication.id(),
                addReservationDto.toiletId(), addReservationDto.startTime(), addReservationDto.endTime());

        Long reservationId = reservationService.addReservation(memberAddReservationDto);
        return ResponseEntity.ok(reservationId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(DeleteReservationDto deleteReservationDto,
                                                  @MemberInfo MemberAuthentication memberAuthentication) {
        DeleteReservationDto memberDeleteReservationDto = new DeleteReservationDto(memberAuthentication.id(),
                deleteReservationDto.reservationId());
        reservationService.deleteReservation(memberDeleteReservationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<ReservationSpecificDto> myReservation(@MemberInfo MemberAuthentication memberAuthentication) {
        Reservation reservation = reservationService.myReservation(memberAuthentication.id());
        return ResponseEntity.ok(ReservationSpecificDto.from(reservation));
    }
}
