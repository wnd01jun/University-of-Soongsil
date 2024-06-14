package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.JoinRequest;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.enums.JoinStatus;
import network.freeTopic.form.ClubJoinForm;
import network.freeTopic.repository.JoinRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    public void join(Member member, ClubJoinForm clubJoinForm){
        JoinRequest joinRequest = JoinRequest.builder()
                .member(member)
                .club(clubJoinForm.getClub())
                .Introduction(clubJoinForm.getIntroduction())
                .contact(clubJoinForm.getContact())
                .joinStatus(JoinStatus.REQUEST)
                .build();
        joinRequestRepository.save(joinRequest);

    }

    public List<JoinRequest> findAllRequest(Member member){
        return joinRequestRepository.findAllRequest(member);
    }

    public void deny(Long id){
        JoinRequest joinRequest = joinRequestRepository.findById(id).get();
        joinRequest.setJoinStatus(JoinStatus.DENY);
        return;
    }

    public JoinRequest accept(Long id){
        JoinRequest joinRequest = joinRequestRepository.findById(id).get();
        joinRequest.setJoinStatus(JoinStatus.ACCEPT);
        return joinRequest;
    }

}
