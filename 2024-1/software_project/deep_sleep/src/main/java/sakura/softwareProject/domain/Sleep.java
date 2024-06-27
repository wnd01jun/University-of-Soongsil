package sakura.softwareProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sakura.softwareProject.domain.dto.SleepDto;
import sakura.softwareProject.domain.enums.SleepStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sleep extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate; // 수면 시작 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate; // 수면 종료 시간

    private LocalTime totalInBedTime;
    private LocalTime totalSleepTime; // 총 수면시간 ChronoUnit 활용

    private LocalDate sleepDate; // 기상 날짜 but
    private LocalTime inBed;
    private LocalTime asleep;
    private LocalTime awake;
    private LocalTime remSleep;
    private LocalTime deepSleep;
    private LocalTime coreSleep;
    private Double sleepQuality;


//    public void addUser(User user){
//        this.user = user;
//        user.getSleeps().add(this);
//    }

    public Sleep(Member member, Integer inBed, Integer asleep,Integer awake,Integer remSleep,
                 Integer deepSleep,Integer coreSleep, LocalDate sleepDate,
                 LocalDateTime startDate, LocalDateTime endDate){
        int inBedTime = (int)(asleep + awake + remSleep + deepSleep + coreSleep);
        int totalTime = inBedTime - awake;
        sleepQuality = ((double)totalTime / inBedTime) * 10000;
        System.out.println("sleepQuality = " + sleepQuality);
        sleepQuality = (double)(Math.round(sleepQuality) / 100);
        totalInBedTime = intToTime(inBedTime);
        totalSleepTime = intToTime(totalTime);
        this.inBed = intToTime(inBedTime);
        this.asleep = intToTime(asleep);
        this.awake = intToTime(awake);
        this.remSleep = intToTime(remSleep);
        this.deepSleep = intToTime(deepSleep);
        this.coreSleep = intToTime(coreSleep);
        this.sleepDate = sleepDate;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private LocalTime intToTime(int i){
        return LocalTime.of(i / 60, i % 60);
    }
}
