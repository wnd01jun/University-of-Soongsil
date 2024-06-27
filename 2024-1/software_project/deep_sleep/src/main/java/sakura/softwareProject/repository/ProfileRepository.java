package sakura.softwareProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sakura.softwareProject.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
