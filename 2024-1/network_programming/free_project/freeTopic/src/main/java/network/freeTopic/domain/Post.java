package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
@Getter
@Setter
public class Post extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String textDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster")
    private Member member;
}
