package sakura.softwareProject.repository.dynamicQuery.impl;

import com.mysema.commons.lang.Pair;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.QSleep;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.domain.dto.DateRequestDto;
import sakura.softwareProject.domain.dto.SleepDateRequestDto;
import sakura.softwareProject.domain.dto.SleepDto;
import sakura.softwareProject.repository.dynamicQuery.DynamicSleepRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static sakura.softwareProject.domain.QSleep.*;


public class DynamicSleepRepositoryImpl implements DynamicSleepRepository {
    private final JPAQueryFactory queryFactory;
    private final DateTimeFormatter formatter;

    public DynamicSleepRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy-MM")
                .toFormatter(Locale.KOREAN);
    }

    @Override
    public List<Sleep> findAll(Member member) {
        return queryFactory.select(sleep)
                .from(sleep)
                .where(sleep.member.eq(member))
                .orderBy(sleep.sleepDate.asc().nullsLast())
                .fetch();
    }

    @Override
    public List<Sleep> findByDate(Member member, SleepDateRequestDto dto) {
        return queryFactory.select(sleep)
                .from(sleep)
                .where(sleep.member.eq(member),
                        dateGoe(dto.getStartDate()),
                        dateLoe(dto.getEndDate()))
                .orderBy(sleep.sleepDate.asc().nullsLast())
                .fetch();
    }

    @Override
    public Sleep findByDateOne(Member member, LocalDate date) {
        return queryFactory.select(sleep)
                .from(sleep)
                .where(sleep.member.eq(member),
                        dateEq(date))
                .fetchFirst();
    }

    @Override
    public Sleep findRecent(Member member) {
        return queryFactory.select(sleep)
                .from(sleep)
                .where(sleep.member.eq(member))
                .orderBy(sleep.sleepDate.desc().nullsLast())
                .fetchFirst();
    }

    @Override
    public boolean checkExist(Member member, SleepDto dto) {
        Sleep sleep = queryFactory.select(QSleep.sleep)
                .from(QSleep.sleep)
                .where(QSleep.sleep.member.eq(member),
                        QSleep.sleep.sleepDate.eq(dto.getEndDate().toLocalDate()))
                .fetchFirst();
        return sleep == null;
    }

    private BooleanExpression dateGoe(LocalDate startDate){
        return startDate != null ? sleep.sleepDate.goe(startDate) : null;
    }

    private BooleanExpression dateLoe(LocalDate endDate){
        return endDate != null ? sleep.sleepDate.loe(endDate) : null;
    }

    private BooleanExpression dateEq(LocalDate date){
        return date != null ? sleep.sleepDate.eq(date) : null;
    }

    @Override
    public List<YearMonth> findMonthOver5(Member member) {

//        StringTemplate formattedDate = Expressions.stringTemplate(
//                "DATE_FORMAT({0}, {1})",
//                sleep.sleepDate,
//                ConstantImpl.create("%Y-%m"));
//        List<String> result = queryFactory.select(formattedDate.as("good"))
//                .from(sleep)
//                .where(sleep.member.eq(member))
//                .groupBy(formattedDate)
//                .having(formattedDate.count().goe(5))
//                .orderBy(formattedDate.asc())
//                .fetch();
//        List<YearMonth> list = result.stream()
//                .map(s -> YearMonth.parse(s, formatter))
//                .sorted(YearMonth::compareTo)
//                .toList();
//        return list;

        return queryFactory.select(sleep.sleepDate)
                .from(sleep)
                .where(sleep.member.eq(member))
                .orderBy(sleep.sleepDate.asc().nullsLast())
                .fetch()
                .stream()
                .map(YearMonth::from)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }
}
