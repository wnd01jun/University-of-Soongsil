package network.freeTopic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import network.freeTopic.form.RegisterForm;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity{
    @GeneratedValue
    @Id
    private Long id;

    private String name;
    private String role; // 학생 교직원 교수 ...
    private String major; //  과이름
    private String studentId;
    private String password;

    public Member(RegisterForm form, String password) {
        this.name = form.getName();
        this.role = form.getRole();
        this.major = form.getMajor();
        this.studentId = form.getStudentId();
        this.password = password;
    }

    public Member(String name) {
        this.name = name;
    }
}
