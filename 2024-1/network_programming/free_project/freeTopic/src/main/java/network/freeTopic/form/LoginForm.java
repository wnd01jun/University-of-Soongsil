package network.freeTopic.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginForm {
    @NotBlank
    @Size(max = 8, min = 8)
    private String loginId;
    @NotBlank
    private String password;
}
