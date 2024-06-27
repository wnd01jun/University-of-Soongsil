package sakura.softwareProject.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sakura.softwareProject.domain.enums.SleepStatus;

import java.time.LocalDateTime;

// sleep 저장시에 쓰이는 dto
@Data
public class SleepDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDate;
    private SleepStatus sleepStatus;
}
