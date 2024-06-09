package network.freeTopic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.domain.Post;
import network.freeTopic.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

//    @GetMapping("/boards")
//    public Page<Post> findAll(@PageableDefault(size = 15)Pageable pageable){
//
//    }

}
