package network.freeTopic.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import network.freeTopic.domain.Category;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostSearchCondition {
    private String title;
    private String clubName;
    private Category category;
}
