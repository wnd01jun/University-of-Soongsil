package sakura.softwareProject.gpt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;

@Data
@AllArgsConstructor
public class GptDateRequest {
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth date;
}
