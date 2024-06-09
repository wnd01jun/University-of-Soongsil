package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.Post;
import network.freeTopic.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Member member, Post post){
        post.setMember(member);
        postRepository.save(post);
        return post;
    }

    public Page<Post> findByMember(Member member, Pageable pageable){
        return postRepository.findByMemberPage(member, pageable);
    }


}
