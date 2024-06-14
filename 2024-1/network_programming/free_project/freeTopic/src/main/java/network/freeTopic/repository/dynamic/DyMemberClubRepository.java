package network.freeTopic.repository.dynamic;

import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.domain.enums.ClubRole;

import java.util.List;

public interface DyMemberClubRepository {
    List<MemberClub> findByMember(Member member);

    List<MemberClub> findByRole(ClubRole role);
    List<MemberClub> findByClub(Club club);

    MemberClub findMemberClub(Member member, Club club);
}
