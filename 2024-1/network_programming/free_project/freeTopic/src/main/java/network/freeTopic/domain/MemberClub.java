package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.*;
import network.freeTopic.domain.enums.ClubRole;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberClub extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated
    @Column(name = "role")
    private ClubRole clubRole;

}
