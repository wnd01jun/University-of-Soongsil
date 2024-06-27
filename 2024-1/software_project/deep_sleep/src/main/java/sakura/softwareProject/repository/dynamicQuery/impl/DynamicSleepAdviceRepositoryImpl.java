package sakura.softwareProject.repository.dynamicQuery.impl;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.QMember;
import sakura.softwareProject.domain.SleepAdvice;
import sakura.softwareProject.domain.dto.SleepAdviceSearchCondition;
import sakura.softwareProject.gpt.dto.GptDateRequest;
import sakura.softwareProject.repository.dynamicQuery.DynamicSleepAdviceRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static sakura.softwareProject.domain.QSleepAdvice.*;


public class DynamicSleepAdviceRepositoryImpl implements DynamicSleepAdviceRepository {
    private final JPAQueryFactory queryFactory;

    public DynamicSleepAdviceRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }



//    @Override
//    public List<SleepAdvice> search(SleepAdviceSearchCondition condition) {
//        List<SleepAdvice> result = queryFactory.select(sleepAdvice)
//                .from(sleepAdvice)
//                .join(sleepAdvice.member, QMember.member)
//                .where(dateGoe(condition.getDateGoe()),
//                        dateLoe(condition.getDateLoe()),
//                        QMember.member.eq(condition.getMember()))
//                .fetch();
//        return result;
//    }

    public SleepAdvice findOne(Member member, YearMonth date){
        return queryFactory.select(sleepAdvice)
                .from(sleepAdvice)
                .where(sleepAdvice.member.eq(member),
                        sleepAdvice.date.eq(date))
                .fetchOne();
    }

    @Override
    public List<SleepAdvice> findAll(Member member) {
        return queryFactory.select(sleepAdvice)
                .from(sleepAdvice)
                .where(sleepAdvice.member.eq(member))
                .fetch();
    }

    @Override
    public List<YearMonth> findAllDate(Member member) {
        return queryFactory.select(sleepAdvice.date)
                .from(sleepAdvice)
                .where(sleepAdvice.member.eq(member))
                .fetch();
    }

    @Override
    public List<SleepAdvice> selectByDate(Member member, List<YearMonth> dates) {
        return queryFactory.select(sleepAdvice)
                .from(sleepAdvice)
                .where(sleepAdvice.date.in(dates)
                , sleepAdvice.member.eq(member))
                .fetch();
    }

    //    private BooleanExpression dateGoe(LocalDate date){
//        return (date == null) ? null : sleepAdvice.date.goe(date);
//    }

//    private BooleanExpression dateLoe(LocalDate date){
//        return (date == null) ? null : sleepAdvice.date.loe(date);
//    }




}
