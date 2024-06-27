package sakura.softwareProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Slf4j
public class SleepAdvice extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "sa_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
    private YearMonth date;

    @Column(length = 2000)
    private String description;

    private LocalTime avgTotalSleepTime;
    private LocalTime avgAwakeTime;

    public SleepAdvice(Member member, YearMonth date, String description
            , LocalTime avgTotalSleepTime, LocalTime avgAwakeTime) {
        this.member = member;
        this.date = date;
        this.description = description;
        this.avgTotalSleepTime = avgTotalSleepTime;
        this.avgAwakeTime = avgAwakeTime;
    }
}
