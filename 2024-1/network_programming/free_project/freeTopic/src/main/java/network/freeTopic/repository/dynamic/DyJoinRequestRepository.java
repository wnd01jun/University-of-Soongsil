package network.freeTopic.repository.dynamic;

import network.freeTopic.domain.JoinRequest;
import network.freeTopic.domain.Member;

import java.util.List;

public interface DyJoinRequestRepository {
    List<JoinRequest> findAllRequest(Member member);
}
