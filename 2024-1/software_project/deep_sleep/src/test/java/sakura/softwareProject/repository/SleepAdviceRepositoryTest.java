package sakura.softwareProject.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SleepAdviceRepositoryTest {
//    @Autowired
//    SleepAdviceService sleepAdviceService;

    @Autowired
    EntityManager em;


    @Test
    void chronoUnitTest(){
        LocalDateTime time1;
        LocalDateTime time2;

        time1 = LocalDateTime.of(2024, 5, 5, 22, 15);
        time2 = LocalDateTime.of(2024, 5, 6, 10, 15);
        long result = ChronoUnit.MINUTES.between(time1, time2);
        Assertions.assertThat(result).isEqualTo(720);


    }
}