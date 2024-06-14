package network.freeTopic.repository.dynamic;

import network.freeTopic.controller.dto.BoardResponseDto;
import network.freeTopic.domain.Club;
import network.freeTopic.domain.ClubPost;
import network.freeTopic.domain.Member;
import network.freeTopic.domain.Post;
import network.freeTopic.form.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DyPostRepository {
    Page<Post> findByMemberPage(Member member, Pageable pageable);

    Page<Post> findBoardInfo(Pageable pageable);


    Page<ClubPost> findByClub(Club club, Pageable pageable);
}
