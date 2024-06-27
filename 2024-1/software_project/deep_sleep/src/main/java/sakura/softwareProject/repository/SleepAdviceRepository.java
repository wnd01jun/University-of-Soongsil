package sakura.softwareProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.domain.SleepAdvice;
import sakura.softwareProject.repository.dynamicQuery.DynamicSleepAdviceRepository;

public interface SleepAdviceRepository extends JpaRepository<SleepAdvice, Long>, DynamicSleepAdviceRepository {

}
