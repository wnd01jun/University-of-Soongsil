package sakura.softwareProject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sakura.softwareProject.domain.dto.MemberDto;

import java.time.LocalDate;

@Entity
@Getter
@Setter // setter는 테스트용으로 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // DB에 createdAt가 null 로 저장되는 현상 디버깅
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true) // 이메일 주소를 아이디 대신 사용
    private String studentId;
    @Column(nullable = false)
    private String password;
    @OneToOne(mappedBy = "member")
    private Profile profile;

    private LocalDate lastUpdated;

//    @OneToMany(mappedBy = "user")
//    private List<Sleep> Sleeps = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<HeartRate> heartRates = new ArrayList<>();


    public Member(String name, String studentId, String password) {
        this.name = name;
        this.studentId = studentId;
        this.password = password;
    }

    public Member(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.studentId = memberDto.getStudentNumber();
        this.password = memberDto.getPassword();
    }

    public static Member emptyMember(){
        Member member = new Member();
        member.setName("empty");
        return member;
    }

    public Member(String name) {
        this.name = name;
    }
}
