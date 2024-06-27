package sakura.softwareProject.repository.dynamicQuery;

import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.SleepAdvice;

import java.time.YearMonth;
import java.util.List;

public interface DynamicSleepAdviceRepository {


//    List<SleepAdvice> search(SleepAdviceSearchCondition condition);
    public SleepAdvice findOne(Member member, YearMonth date);

    List<SleepAdvice> findAll(Member member);

    List<YearMonth> findAllDate(Member member);

    List<SleepAdvice> selectByDate(Member member, List<YearMonth> dates);
}
