package network.freeTopic.repository;

import network.freeTopic.domain.Club;
import network.freeTopic.domain.ClubPost;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.Post;
import network.freeTopic.domain.enums.ClubRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ClubRepository clubRepository;

    @Test
    public void find(){
        Member member = new Member("lee");
        memberRepository.save(member);

        Member member2 = new Member("ki");
        memberRepository.save(member2);

        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        Post post4 = new Post();
        Post post5 = new Post();
        Post post6 = new Post();


        post1.setMember(member);
        post2.setMember(member);
        post4.setMember(member);

        post3.setMember(member2);
        post5.setMember(member2);
        post6.setMember(member2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        postRepository.save(post5);
        postRepository.save(post6);


        Pageable pageable = PageRequest.of(0, 5);

        Member findMember = memberRepository.findById(member2.getId()).get();
        Page<Post> byMember = postRepository.findByMemberPage(findMember, pageable);
        Page<Post> all = postRepository.findAll(pageable);


//        assertThat(byMember.stream().toList().size()).isEqualTo(0);
        assertThat(all.stream().toList().size()).isEqualTo(5);


    }

    @Test
    void clubTest(){
        Member member = new Member("lee");
        memberRepository.save(member);

        Member member2 = new Member("ki");
        memberRepository.save(member2);

        Club clubA = new Club("clubA");
        Club clubB = new Club("clubB");
        clubRepository.save(clubA);
        clubRepository.save(clubB);


        ClubPost post1 = new ClubPost();
        ClubPost post2 = new ClubPost();
        ClubPost post3 = new ClubPost();
        post1.setClub(clubA);
        post2.setClub(clubA);
        post3.setClub(clubA);
        post1.setTitle("post1");
        post2.setTitle("post2");
        post3.setTitle("post3");

        ClubPost post4 = new ClubPost();
        ClubPost post5 = new ClubPost();
        ClubPost post6 = new ClubPost();
        post4.setClub(clubB);
        post5.setClub(clubB);
        post6.setClub(clubB);
        post4.setTitle("post4");
        post5.setTitle("post5");
        post6.setTitle("post6");


        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);

        Pageable pageable = PageRequest.of(1, 3);

        Page<ClubPost> a = postRepository.findByClub(clubA, pageable);
        Page<ClubPost> b = postRepository.findByClub(clubB, pageable);

        Page<Post> all = postRepository.findAll(pageable);

        List<ClubPost> aList = a.stream().toList();
        for (Post clubPost : all.stream().toList()) {
            System.out.println("clubPost.getTitle() = " + clubPost.getTitle());
        }
        System.out.println("a.getSize() = " + a.getSize());
//        assertThat(all.stream().toList().size()).isEqualTo(3);

        assertThat(a.stream().toList().size()).isEqualTo(3);
//        assertThat(b.stream().toList().size()).isEqualTo(3);


    }
}