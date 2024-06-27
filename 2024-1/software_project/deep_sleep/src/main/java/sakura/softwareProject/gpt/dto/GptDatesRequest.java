package sakura.softwareProject.gpt.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;
import java.util.List;

@Data
public class GptDatesRequest {
    @DateTimeFormat(pattern = "yyyy-MM")
    private List<YearMonth> dates;
}
