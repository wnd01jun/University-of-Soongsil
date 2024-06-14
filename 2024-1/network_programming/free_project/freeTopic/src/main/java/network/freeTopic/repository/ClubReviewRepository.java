package network.freeTopic.repository;

import network.freeTopic.domain.ClubReview;
import network.freeTopic.repository.dynamic.DyClubReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubReviewRepository extends JpaRepository<ClubReview, Long>, DyClubReviewRepository {

}
