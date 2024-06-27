package sakura.softwareProject.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SleepAdviceRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer minHeartRate;
    private Integer maxHeartRate;

}
