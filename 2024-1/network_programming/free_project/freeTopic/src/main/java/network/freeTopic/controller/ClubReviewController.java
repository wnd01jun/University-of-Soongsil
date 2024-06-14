package network.freeTopic.controller;

import lombok.RequiredArgsConstructor;
import network.freeTopic.domain.ClubReview;
import network.freeTopic.domain.Member;
import network.freeTopic.form.ReviewWriteForm;
import network.freeTopic.security.Login;
import network.freeTopic.service.ClubReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ClubReviewController {
    private final ClubReviewService clubReviewService;
    @GetMapping("/list")
    public String getList(@RequestParam("id")Long id, Model model){
        List<ClubReview> clubReviews = clubReviewService.findClubReview(id);
        model.addAttribute("reviews", clubReviews);
        return "/review/list";
    }

    @GetMapping("/get")
    public String getReview(@RequestParam("id")Long id, Model model){
        ClubReview clubReview = clubReviewService.findById(id);
        ReviewWriteForm form = new ReviewWriteForm(clubReview.getContent(), clubReview.getScore(), clubReview.getMemberClub().getClub());
        model.addAttribute("form", form);
        return "/review/review";

    }

    @PostMapping("/write")
    public String postReview(@Login Member member,
                             @ModelAttribute("form") ReviewWriteForm form){
        clubReviewService.save(member, form);
        return "redirect:/clubs/menu";
    }
}
