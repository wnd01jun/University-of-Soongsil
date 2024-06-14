package network.freeTopic.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.Club;

import java.util.List;

@Setter
@Getter
public class PostForm {
    @NotNull
    private String title;
    @NotNull
    private String content;

    private Club club;

}
