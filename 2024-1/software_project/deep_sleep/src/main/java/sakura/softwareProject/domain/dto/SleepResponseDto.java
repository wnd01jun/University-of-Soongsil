package sakura.softwareProject.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sakura.softwareProject.domain.Sleep;

import java.time.LocalDate;
import java.time.LocalTime;

/*
    임시용 다른거 쓸거임

 */
@Data
public class SleepResponseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime startDate; // 수면 시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime endDate; // 수면 종료 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime totalInBedTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime totalSleepTime; // 총 수면시간 ChronoUnit 활용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate sleepDate; // 기상 날짜 but
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime asleep;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime deepSleep;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime coreSleep;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime remSleep;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime awake;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime inBed;

    private Double sleepQuality;
    public SleepResponseDto(Sleep sleep) {
        startDate = sleep.getStartDate().toLocalTime();
        endDate = sleep.getEndDate().toLocalTime();
        totalSleepTime = sleep.getTotalSleepTime();
        totalInBedTime = sleep.getTotalInBedTime();
        sleepDate = sleep.getSleepDate();
        deepSleep = sleep.getDeepSleep();
        coreSleep = sleep.getCoreSleep();
        remSleep = sleep.getRemSleep();
        awake = sleep.getAwake();
        inBed = sleep.getInBed();
        asleep = sleep.getAsleep();
        sleepQuality = sleep.getSleepQuality();
    }

    public static SleepResponseDto makeDto(Sleep sleep){
        if(sleep == null) return null;
        return new SleepResponseDto(sleep);
    }
}
