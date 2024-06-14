package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubReview extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_club_id")
    private MemberClub memberClub;

    private Double score;

    private String content;
}
