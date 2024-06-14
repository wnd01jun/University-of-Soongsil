package network.freeTopic.repository;

import network.freeTopic.domain.MemberClub;
import network.freeTopic.repository.dynamic.DyMemberClubRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberClubRepository extends JpaRepository<MemberClub, Long>, DyMemberClubRepository {

}
