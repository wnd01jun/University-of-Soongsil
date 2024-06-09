package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.enums.JoinStatus;


/*
    회원은 원하는 동아리에 가입신청을 할 수 있다.
    가입 신청을 위해 사용하는 Entity
 */
@Entity
@Getter
@Setter
public class JoinRequest {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;
    private String selfIntroduce;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JoinStatus joinStatus;

}
