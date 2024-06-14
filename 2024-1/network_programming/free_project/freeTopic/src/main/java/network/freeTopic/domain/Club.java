package network.freeTopic.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY) // 한 사람이 다양한 동아리의 회장직을 겸임할 수 있다.
    @JoinColumn(name = "lead_id")
    private Member leader;

    @OneToOne(fetch = FetchType.LAZY) // 동아리랑 카테고리는 하나로 제한한다.
    @JoinColumn(name = "category_id")
    private Category category;

    public Club(String name) {
        this.name = name;
    }
}
