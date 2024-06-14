package network.freeTopic.form;

import lombok.*;
import network.freeTopic.domain.Club;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubJoinForm {
    private String contact;
    private String introduction;
    private Club club;
}
