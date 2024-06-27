package sakura.softwareProject.domain;

import jakarta.persistence.*;

@Entity
public class MemberLog extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String log;

}
