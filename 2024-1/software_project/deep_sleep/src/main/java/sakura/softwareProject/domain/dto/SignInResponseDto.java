package sakura.softwareProject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponseDto {
    private String token;
    private String studentId;
    private Long id;
}
