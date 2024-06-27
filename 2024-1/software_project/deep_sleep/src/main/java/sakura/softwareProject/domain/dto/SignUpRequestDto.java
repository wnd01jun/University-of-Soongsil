package sakura.softwareProject.domain.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String studentId;
    private String password;
    private String name;
}
