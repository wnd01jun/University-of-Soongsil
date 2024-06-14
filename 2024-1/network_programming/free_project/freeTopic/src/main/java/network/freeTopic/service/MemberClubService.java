package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.repository.ClubRepository;
import network.freeTopic.repository.MemberClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberClubService {
    private final MemberClubRepository memberClubRepository;
    private final ClubRepository clubRepository;
    public void createInit(Member member){
        Club club = clubRepository.findById(1L).get();
        MemberClub memberClub = MemberClub.builder()
                .member(member)
                .club(club)
                .build();
        memberClubRepository.save(memberClub);

    }

    public void save(Member member, Club club){
        memberClubRepository.save(
                MemberClub.builder()
                        .member(member)
                        .club(club)
                        .build()
        );
        return;
    }
}
