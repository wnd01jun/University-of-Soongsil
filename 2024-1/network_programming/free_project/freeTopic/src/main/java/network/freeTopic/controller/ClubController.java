package network.freeTopic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import network.freeTopic.domain.*;
import network.freeTopic.form.ClubCreateForm;
import network.freeTopic.form.ClubJoinForm;
import network.freeTopic.form.ReviewWriteForm;
import network.freeTopic.security.Login;
import network.freeTopic.service.*;
import org.hibernate.validator.constraints.ModCheck;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;
    private final JoinRequestService joinRequestService;
    private final MemberClubService memberClubService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String create(@ModelAttribute("clubCreateForm")ClubCreateForm form,
                         Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "club/create";
    }

    @PostMapping("/create")
    public String postCreate(@Login Member member,
                             @ModelAttribute("clubCreateForm")ClubCreateForm form){
        Club club = clubService.create(member, form);
        memberClubService.save(member, club);
        return "redirect:/";
    }
    @GetMapping("/join")
    public String join(@ModelAttribute("clubJoinForm")ClubJoinForm clubJoinForm
                       ,Model model){
        List<Club> all = clubService.findAll();
        model.addAttribute("clubs", all);
        return "/club/join";
    }

    @PostMapping("/join")
    public String postJoin(@Login Member member
            , @ModelAttribute("clubJoinForm")ClubJoinForm clubJoinForm){
        joinRequestService.join(member, clubJoinForm);
        return "redirect:/";
    }

    @GetMapping("/join/check")
    public String check(@Login Member member, Model model){
        List<JoinRequest> allRequest = joinRequestService.findAllRequest(member);
        if(allRequest == null){
            return "redirect:/";
        }
        model.addAttribute("applicants", allRequest);
        return "/club/check";
    }

    @PostMapping("/{id}/deny")
    public String joinDeny(@PathVariable("id")Long id){
        joinRequestService.deny(id);
        return "redirect:/clubs/check";
    }

    @PostMapping("/{id}/accept")
    public String joinAccept(@PathVariable("id")Long id){
        JoinRequest accept = joinRequestService.accept(id);
        memberClubService.save(accept.getMember(), accept.getClub());
        return "redirect:/clubs/check";
    }

    @GetMapping("/menu")
    public String menu(@Login Member member, Model model){
        model.addAttribute("member", member);
        return "/club/menu";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Club> all = clubService.findAll();
        model.addAttribute("clubs", all);
        return "/club/list";
    }

    @GetMapping("/review/write")
    public String writeReview(@Login Member member,
            @ModelAttribute("form") ReviewWriteForm form,
                              Model model){
        List<Club> clubs = memberService.findClubName(member);
        model.addAttribute("clubs", clubs);

        List<Double> sequence = new ArrayList<>();
        for(double i = 0.5; i <= 5; i += 0.5){
            sequence.add(i);
        }
        model.addAttribute("sequence", sequence);
        return "/review/create";
    }






}
