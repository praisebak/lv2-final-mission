package lavatoryreservation.reservation.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.toilet.domain.Toilet;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Toilet toilet;
    @Embedded
    private ToiletTime toiletTime;

    public Reservation(Member member, Toilet toilet, ToiletTime toiletTime) {
        this.member = member;
        this.toilet = toilet;
        this.toiletTime = toiletTime;
    }

    protected Reservation() {

    }

    public Member getMember() {
        return member;
    }

    public Toilet getToilet() {
        return toilet;
    }

    public ToiletTime getToiletTime() {
        return toiletTime;
    }
}
