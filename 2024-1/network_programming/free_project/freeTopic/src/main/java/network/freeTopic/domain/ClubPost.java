package network.freeTopic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.enums.PostOpenPermission;

@Getter
@Setter
public class ClubPost{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated
    @Column(name = "permission")
    private PostOpenPermission permission;
}
