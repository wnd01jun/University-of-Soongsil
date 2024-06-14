package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.ClubReview;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.MemberClub;
import network.freeTopic.form.ReviewWriteForm;
import network.freeTopic.repository.ClubReviewRepository;
import network.freeTopic.repository.MemberClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubReviewService {
    private final ClubReviewRepository clubReviewRepository;
    private final MemberClubRepository memberClubRepository;
    public List<ClubReview> findClubReview(Long id){
        return clubReviewRepository.findClubReview(id);
    }

    public ClubReview findById(Long id){
        return clubReviewRepository.findById(id).get();
    }

    public void save(Member member, ReviewWriteForm form){
        MemberClub memberClub = memberClubRepository.findMemberClub(member, form.getClub());
        ClubReview build = ClubReview.builder()
                .score(form.getScore())
                .content(form.getContent())
                .memberClub(memberClub)
                .build();
        clubReviewRepository.save(build);
        return;
    }
}
