package sakura.softwareProject.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import sakura.softwareProject.domain.SleepAdvice;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

@Data
@Slf4j
public class SleepAdviceResponseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
    private YearMonth date;

    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime avgTotalSleepTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime avgAwakeTime;

    public SleepAdviceResponseDto(SleepAdvice sleepAdvice) {
        this.date = sleepAdvice.getDate();
        this.description = sleepAdvice.getDescription();
        avgTotalSleepTime = sleepAdvice.getAvgTotalSleepTime();
        avgAwakeTime = sleepAdvice.getAvgAwakeTime();
    }
}
