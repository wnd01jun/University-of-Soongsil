package network.freeTopic.repository;

import network.freeTopic.domain.Member;
import network.freeTopic.domain.Post;
import network.freeTopic.repository.dynamic.DyPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, DyPostRepository {
}