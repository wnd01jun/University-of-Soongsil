package sakura.softwareProject.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sakura.softwareProject.domain.Member;

import java.time.LocalDate;

@Setter
@Getter
public class SleepAdviceSearchCondition {
    private Member member;
    private LocalDate dateGoe;
    private LocalDate dateLoe;

    public SleepAdviceSearchCondition(Member member, SleepAdviceRequestDto dto) {
        this.member = member;
        this.dateGoe = dto.getStartDate();
        this.dateLoe = dto.getEndDate();
    }
}
