package sakura.softwareProject.repository.dynamicQuery;

import sakura.softwareProject.domain.Member;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.domain.dto.DateRequestDto;
import sakura.softwareProject.domain.dto.SleepDateRequestDto;
import sakura.softwareProject.domain.dto.SleepDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface DynamicSleepRepository {

    List<Sleep> findAll(Member member);

    List<Sleep> findByDate(Member member, SleepDateRequestDto dto);

    Sleep findByDateOne(Member member, LocalDate date);

    Sleep findRecent(Member member);

    boolean checkExist(Member member, SleepDto dto);

    List<YearMonth> findMonthOver5(Member member);
}
