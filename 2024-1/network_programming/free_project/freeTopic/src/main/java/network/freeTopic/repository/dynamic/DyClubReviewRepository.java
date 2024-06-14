package network.freeTopic.repository.dynamic;

import network.freeTopic.domain.ClubReview;

import java.util.List;

public interface DyClubReviewRepository {
    List<ClubReview> findClubReview(Long id);
}
