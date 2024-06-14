package network.freeTopic.repository.dynamic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import network.freeTopic.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static network.freeTopic.domain.QClub.*;
import static network.freeTopic.domain.QClubReview.*;
import static network.freeTopic.domain.QMemberClub.*;

public class DyClubReviewRepositoryImpl implements DyClubReviewRepository{
    private final JPAQueryFactory queryFactory;

    public DyClubReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ClubReview> findClubReview(Long id) {
        List<ClubReview> result = queryFactory.select(clubReview)
                .from(clubReview)
                .innerJoin(clubReview.memberClub,memberClub)
                .where(memberClub.club.id.eq(id))
                .fetch();
        return result;
    }
}
