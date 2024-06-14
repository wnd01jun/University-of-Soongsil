package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.repository.MemberClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberClubRepository memberClubRepository;

    public List<Club> findClubName(Member member){

        return memberClubRepository.findByMember(member).stream()
                .map(MemberClub::getClub)
                .toList();
    }


}
