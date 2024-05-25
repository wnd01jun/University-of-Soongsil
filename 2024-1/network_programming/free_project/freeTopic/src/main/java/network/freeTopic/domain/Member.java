package network.freeTopic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends BaseEntity{
    @GeneratedValue
    @Id
    private Long id;

    private String name;
    private String role; // 학생 교직원 교수 ...
    private String major; //  과이름
    private String studentId;
    private String password;

}
