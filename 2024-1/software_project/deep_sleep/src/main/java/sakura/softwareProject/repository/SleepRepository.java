package sakura.softwareProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sakura.softwareProject.domain.Sleep;
import sakura.softwareProject.repository.dynamicQuery.DynamicSleepRepository;

@Transactional
public interface SleepRepository extends JpaRepository<Sleep, Long>, DynamicSleepRepository {
}
