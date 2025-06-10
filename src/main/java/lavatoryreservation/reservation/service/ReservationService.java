package lavatoryreservation.reservation.service;

import java.time.LocalDateTime;
import lavatoryreservation.exception.ReservationException;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.service.MemberService;
import lavatoryreservation.reservation.domain.NamingArtist;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.domain.ToiletTime;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.dto.DeleteReservationDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.service.ToiletService;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final MemberService memberService;
    private final ToiletService toiletService;
    private final NamingArtist namingArtist;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberService memberService,
                              ToiletService toiletService, NamingArtist namingArtist) {
        this.reservationRepository = reservationRepository;
        this.memberService = memberService;
        this.toiletService = toiletService;
        this.namingArtist = namingArtist;
    }

    public void addReservation(AddReservationDto addReservationDto) {
        Member member = memberService.getById(addReservationDto.memberId());
        Toilet toilet = toiletService.getById(addReservationDto.toiletId());
        LocalDateTime startTime = addReservationDto.startTime();
        LocalDateTime endTime = addReservationDto.endTime();
        ToiletTime toiletTime = new ToiletTime(startTime, endTime);
        String alias = namingArtist.generateName();

        toilet.getLavatory().validateUseableMember(member);
        reservationRepository.save(new Reservation(member, toilet, toiletTime, alias));
    }

    public void deleteReservation(DeleteReservationDto deleteReservationDto) {
        Member member = memberService.getById(deleteReservationDto.memberId());
        Reservation reservation = getById(deleteReservationDto.reservationId());
        validateSameOwner(reservation, member);
        reservationRepository.deleteById(deleteReservationDto.reservationId());
    }

    private void validateSameOwner(Reservation reservation, Member member) {
        if (!reservation.isSameOwner(member)) {
            throw new ReservationException("예약자가 아닙니다.");
        }
    }

    private Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("존재하지 않는 예약입니다"));
    }
}
