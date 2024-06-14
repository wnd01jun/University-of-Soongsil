package network.freeTopic.form;

import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.Category;

@Getter
@Setter
public class ClubCreateForm {
    private String name;
    private String description;
    private Category category;
}
