package sakura.softwareProject.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class SleepSaveDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private List<SleepDto> sleeps;



    /*
    "sleeps":[
            {
                "startDate":"2024-5-11 19:30",
                "endDate":"2024-5-11 19:40",
                "sleepStatus":"IN_BED"
            },
            {
                "startDate":"2024-5-11 19:40",
                "endDate":"2024-5-11 19:59",
                "sleepStatus":"REM_SLEEP"
            },
            {
                "startDate":"2024-5-11 19:59",
                "endDate":"2024-5-11 21:40",
                "sleepStatus":"DEEP_SLEEP"
            }
     */

}
