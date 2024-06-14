package network.freeTopic.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import network.freeTopic.domain.Club;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
public class ReviewWriteForm {
    private String content;
    private Double score;
    private Club club;
}
