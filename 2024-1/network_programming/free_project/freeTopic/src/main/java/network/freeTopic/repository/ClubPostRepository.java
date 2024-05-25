package network.freeTopic.repository;

import network.freeTopic.domain.ClubPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPostRepository extends JpaRepository<ClubPost, Long> {
}
