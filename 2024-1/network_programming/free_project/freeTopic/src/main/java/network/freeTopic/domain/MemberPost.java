package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("M")
@Getter
@Setter
@Entity
public class MemberPost extends Post{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
