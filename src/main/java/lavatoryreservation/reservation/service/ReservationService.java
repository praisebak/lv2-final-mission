package lavatoryreservation.reservation.service;

import java.time.LocalDateTime;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.service.MemberService;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.domain.ToiletTime;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.service.ToiletService;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final MemberService memberService;
    private final ToiletService toiletService;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberService memberService,
                              ToiletService toiletService) {
        this.reservationRepository = reservationRepository;
        this.memberService = memberService;
        this.toiletService = toiletService;
    }

    public void addReservation(AddReservationDto addReservationDto) {
        Member member = memberService.getById(addReservationDto.memberId());
        Toilet toilet = toiletService.getById(addReservationDto.toiletId());
        LocalDateTime startTime = addReservationDto.startTime();
        LocalDateTime endTime = addReservationDto.endTime();
        ToiletTime toiletTime = new ToiletTime(startTime, endTime);

        reservationRepository.save(new Reservation(member, toilet, toiletTime));
    }
}
