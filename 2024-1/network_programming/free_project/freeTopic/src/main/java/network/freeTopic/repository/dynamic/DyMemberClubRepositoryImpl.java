package network.freeTopic.repository.dynamic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.domain.QMemberClub;
import network.freeTopic.domain.enums.ClubRole;

import java.util.List;

import static network.freeTopic.domain.QMemberClub.*;

@RequiredArgsConstructor
public class DyMemberClubRepositoryImpl implements DyMemberClubRepository{

    private final JPAQueryFactory queryFactory;
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
