package sakura.softwareProject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    private Integer weight;
    private Integer height;

    @OneToOne
    @JoinColumn(name = "memebr_id")
    private Member member;
}
