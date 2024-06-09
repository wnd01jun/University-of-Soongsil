package network.freeTopic.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class RegisterForm {
    @NotBlank
    private String name;
    @NotBlank
    private String role; // 학생 교직원 교수 ...
    @NotBlank
    private String major; //  과이름
    @NotBlank
    @Size(min = 8, max = 8)
    private String studentId;
    @NotBlank
    @Size(min = 6, max = 15)
    private String password;
}
