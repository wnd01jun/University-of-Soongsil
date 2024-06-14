package network.freeTopic.form;

import lombok.*;
import network.freeTopic.domain.Category;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostSearchCondition {
    private String title;
    private String clubName;
    private Category category;
}
