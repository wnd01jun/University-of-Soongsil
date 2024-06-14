package network.freeTopic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.controller.dto.BoardResponseDto;
import network.freeTopic.controller.dto.PostResponseDto;
import network.freeTopic.domain.Category;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.Member;
import network.freeTopic.form.PostForm;
import network.freeTopic.form.PostSearchCondition;
import network.freeTopic.security.Login;
import network.freeTopic.service.CategoryService;
import network.freeTopic.service.MemberService;
import network.freeTopic.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final CategoryService categoryService;


//    @GetMapping("/boards")
//    public Page<Post> findAll(@PageableDefault(size = 15)Pageable pageable){
//
//    }


    @GetMapping
    public String posts(@PageableDefault(size = 10, page = 0)Pageable pageable, Model model,
                        @ModelAttribute("postSearchCondition")PostSearchCondition postSearchCondition){
        log.info("condition = {}", postSearchCondition);
        Page<BoardResponseDto> pages = postService.findAllByPage(pageable, postSearchCondition);
        if(pages == null){
            log.info("page is empty");
            pages = Page.empty();
        }
        log.info("pages = {}", pages);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", pages);
        return "/post/board";
    }

    @GetMapping("/myPost")
    public String myPosts(@Login Member member, @PageableDefault(size = 10, page = 0)Pageable pageable,
                          Model model){
        Page<BoardResponseDto> pages = postService.findBoardByMember(member, pageable);
        if(pages == null){
            log.info("page is empty");
            pages = Page.empty();
        }
        log.info("pages = {}", pages);
        model.addAttribute("posts", pages);
        return "/post/myBoard";
    }

    @GetMapping("/new")
    public String newPost(@Login Member member,@ModelAttribute("postForm")PostForm postForm
    , Model model){
        List<Club> clubNames = memberService.findClubName(member);
        if(clubNames == null){
            clubNames = new ArrayList<>();
        }
        model.addAttribute("clubs", clubNames);
        return "/post/form";
    }

    @PostMapping("/new")
    public String postPost(@Login Member member, @ModelAttribute("postForm")PostForm postForm){
        postService.save(member, postForm);
        return "redirect:/posts";
    }

    @GetMapping("/get")
    public String getPost(@Login Member member, @RequestParam(value = "id", required = true) Long id,
                          Model model){
        PostResponseDto post = postService.findPost(id);
        model.addAttribute("post", post);
        return "/post/post";
    }


    /*
        게시판 html 작성
        게시글 id | 제목 | 작성자 | 작성자 ID | 작성일자 의 format
        하단에는 다음 페이지로 갈 수 있는 버튼
     */

}
