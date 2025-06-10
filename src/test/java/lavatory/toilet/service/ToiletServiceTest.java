package lavatory.toilet.service;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ToiletServiceTest {

    void 화장실칸을_추가할_수_있다(){

    }

    void 존재하지_않는_화장실이면_화장실칸을_추가할_수_없다(){
    }
}
