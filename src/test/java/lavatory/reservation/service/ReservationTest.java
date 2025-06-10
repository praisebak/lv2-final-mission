package lavatory.reservation.service;

import lavatory.reservation.domain.SEX;
import lavatory.reservation.domain.Toilet;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ReservationTest {

    @Autowired
    private lavatory.reservation.service.ReservationService reservationService;

    void 화장실과_화장실칸에_대해서_예약을할_수_있다(){
        new Toilet(null,"1번칸","비데");
        Lavatory lavatory = new Lavatory(SEX.MEN,"잠실남자화장실");


    }

    void 화장실은_같은_성별이아니면_예약이_불가능하다(){
        Lavatory lavatory = new Lavatory(SEX.MEN);


    }
}
