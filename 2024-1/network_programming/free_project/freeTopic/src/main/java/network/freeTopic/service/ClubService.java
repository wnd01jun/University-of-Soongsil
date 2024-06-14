package network.freeTopic.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.form.ClubCreateForm;
import network.freeTopic.form.ClubJoinForm;
import network.freeTopic.repository.ClubRepository;
import network.freeTopic.repository.JoinRequestRepository;
import network.freeTopic.repository.MemberClubRepository;
import network.freeTopic.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        Club club = new Club();
        club.setName("개인");
        club.setLeader(Member.builder()
                .name("Admin")
                        .studentId("00000000")
                .build());
        club.setDescription("모든 유저에게 부여되는 클럽");
        memberRepository.save(club.getLeader());
        clubRepository.save(club);

    }

    public List<Club> findAll(){
        return clubRepository.findAll();
    }

    public Club create(Member member, ClubCreateForm form){
        Club club = Club.builder()
                .name(form.getName())
                .description(form.getDescription())
                .leader(member)
                .category(form.getCategory())
                .build();
        clubRepository.save(club);
        return club;

    }




}
