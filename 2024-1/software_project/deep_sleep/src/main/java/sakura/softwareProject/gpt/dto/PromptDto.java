package sakura.softwareProject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class PromptDto {
    private String prompt;
    private LocalTime avgTotalSleep;
    private LocalTime avgAwakeSleep;


}
