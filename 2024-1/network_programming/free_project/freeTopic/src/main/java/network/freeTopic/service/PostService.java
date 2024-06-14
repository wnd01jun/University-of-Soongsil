package network.freeTopic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.controller.dto.BoardResponseDto;
import network.freeTopic.controller.dto.PostResponseDto;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.Post;
import network.freeTopic.form.PostForm;
import network.freeTopic.form.PostSearchCondition;
import network.freeTopic.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(Member member, PostForm form){
        Post post = Post.builder()
                .member(member)
                .title(form.getTitle())
                .content(form.getContent())
                .club(form.getClub())
                .build();
        postRepository.save(post);
        return post;
    }

    public Page<Post> findByMember(Member member, Pageable pageable){
        return postRepository.findByMemberPage(member, pageable);
    }

    public Page<BoardResponseDto> findAllByPage(Pageable pageable){
        Page<Post> posts = postRepository.findBoardInfo(pageable);
        List<BoardResponseDto> boards = posts.stream()
                .map(post -> BoardResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .name(post.getMember().getName())
                        .createdDate(post.getCreatedTime())
                        .studentId(post.getMember().getStudentId())
                        .clubName(post.getClub().getName())
                        .build())
                .toList();
        return new PageImpl<>(boards, pageable, posts.getNumber());
    }

    public Page<BoardResponseDto> findBoardByMember(Member member, Pageable pageable){
        Page<Post> posts = postRepository.findByMemberPage(member, pageable);
        List<BoardResponseDto> boards = posts.stream()
                .map(post -> BoardResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .name(post.getMember().getName())
                        .createdDate(post.getCreatedTime())
                        .studentId(post.getMember().getStudentId())
                        .clubName(post.getClub().getName())
                        .build())
                .toList();
        return new PageImpl<>(boards, pageable, posts.getNumber());
    }

    public PostResponseDto findPost(Long id){
        Post post = postRepository.findById(id).get();
        String name = post.getMember().getName();
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(name)
                .createdDate(post.getCreatedTime())
                .build();
    }


}
