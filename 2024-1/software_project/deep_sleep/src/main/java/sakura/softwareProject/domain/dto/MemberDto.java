package sakura.softwareProject.domain.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class MemberDto {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false) // 이메일 주소를 아이디 대신 사용
    private String studentNumber;
    @Column(nullable = false)
    private String password;
}
