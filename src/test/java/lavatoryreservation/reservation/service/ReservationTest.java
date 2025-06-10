package lavatoryreservation.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lavatoryreservation.lavatory.domain.Lavatory;
import lavatoryreservation.lavatory.domain.Sex;
import lavatoryreservation.lavatory.service.LavatoryService;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.service.MemberService;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.service.ToiletService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {

    @Autowired
    LavatoryService lavatoryService;
    @Autowired
    ToiletService toiletService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    MemberService memberService;
    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void 화장실과_화장실칸에_대해서_예약을할_수_있다() {
        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
        Long toiletId = toiletService.addToilet(new AddToiletDto(null, false, lavatory.getId()));
        Long memberId = memberService.addMember(new Member(null, "투다", "praisebak@naver.com", Sex.MEN));
        LocalDate date = LocalDate.now().plusDays(1L);
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(8, 0);

        reservationService.addReservation(new AddReservationDto(memberId, toiletId, LocalDateTime.of(date, startTime),
                LocalDateTime.of(date, endTime)));
        assertThat(reservationRepository.count()).isEqualTo(1L);
    }

    void 화장실은_같은_성별이아니면_예약이_불가능하다() {
//        Lavatory lavatory = lavatoryService.addLavatory(new Lavatory(null, Sex.MEN, "잠실굿샷남자화장실"));
//        Toilet toilet = toiletService.addToilet(new Toilet(null, "1번칸", true),lavatory.getId()));
//
//        Member member = memberService.addMember(new Member(null, "투다", "praisebak@naver.com", Sex.WOMEN));
//
//        assertThatThrownBy(() -> reservationService.addReservation())
//                .isInstanceOf(IllegalArgumentException.class);
    }

    void 자신의_화장실_예약을_제거할_수_있다() {

    }
}
