package network.freeTopic.repository.dynamic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import network.freeTopic.domain.JoinRequest;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.QClub;
import network.freeTopic.domain.QJoinRequest;
import network.freeTopic.domain.enums.JoinStatus;

import java.util.ArrayList;
import java.util.List;

import static network.freeTopic.domain.QClub.club;
import static network.freeTopic.domain.QJoinRequest.*;

public class DyJoinRequestRepositoryImpl implements DyJoinRequestRepository{
    private final JPAQueryFactory queryFactory;

    public DyJoinRequestRepositoryImpl(EntityManager em) {

        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<JoinRequest> findAllRequest(Member member) {
        List<JoinRequest> requests = queryFactory.select(joinRequest)
                .from(joinRequest)
                .where(joinRequest.club.eq(
                        queryFactory.select(club)
                                .from(club)
                                .where(club.leader.eq(member))
                ))
                .where(joinRequest.joinStatus.eq(JoinStatus.REQUEST))
                .fetch();
        return requests;
    }
}
