package network.freeTopic.repository;

import network.freeTopic.domain.JoinRequest;
import network.freeTopic.repository.dynamic.DyJoinRequestRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long>, DyJoinRequestRepository {
}
