package network.freeTopic.repository.dynamic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.domain.QMemberClub;
import network.freeTopic.domain.enums.ClubRole;

import java.util.List;

import static network.freeTopic.domain.QMemberClub.*;

public class DyMemberClubRepositoryImpl implements DyMemberClubRepository{
    private final JPAQueryFactory queryFactory;
    public DyMemberClubRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberClub> findByMember(Member member) {
        return queryFactory.select(memberClub)
                .from(memberClub)
                .where(memberClub.member.eq(member))
                .fetch();
    }

    @Override
    public List<MemberClub> findByRole(ClubRole role) {
        return queryFactory.select(memberClub)
                .from(memberClub)
                .where(memberClub.clubRole.eq(role))
                .fetch();
    }

    @Override
    public List<MemberClub> findByClub(Club club) {
        return queryFactory.select(memberClub)
                .from(memberClub)
                .where(memberClub.club.eq(club))
                .fetch();
    }
}
