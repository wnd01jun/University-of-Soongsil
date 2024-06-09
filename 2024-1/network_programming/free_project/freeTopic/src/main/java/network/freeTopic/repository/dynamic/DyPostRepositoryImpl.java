package network.freeTopic.repository.dynamic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static network.freeTopic.domain.QClub.*;
import static network.freeTopic.domain.QClubPost.clubPost;
import static network.freeTopic.domain.QPost.*;

@Slf4j
public class DyPostRepositoryImpl implements DyPostRepository{
    private final JPAQueryFactory queryFactory;

    public DyPostRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public Page<Post> findByMemberPage(Member member, Pageable pageable){
        List<Post> list = queryFactory.select(post)
                .from(post)
                .where(post.member.eq(member))
                .orderBy(post.createdTime.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long cnt = queryFactory.select(post.count())
                .from(post)
                .where(post.member.eq(member))
                .fetchOne();
        if(cnt == null) cnt = 0L;


        return new PageImpl<>(list, pageable, cnt);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        List<Post> list = queryFactory.select(post)
                .from(post)
                .orderBy(post.createdTime.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long cnt = queryFactory.select(post.count())
                .from(post)
                .fetchOne();
        if(cnt == null) cnt = 0L;


        return new PageImpl<>(list, pageable, cnt);
    }

    @Override
    public Page<ClubPost> findByClub(Club club, Pageable pageable) {
        List<ClubPost> list = queryFactory.select(clubPost)
                .from(clubPost, QClub.club)
                .where(clubPost.club.eq(club))
                .orderBy(clubPost.createdTime.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long cnt = queryFactory.select(clubPost.count())
                .from(clubPost, QClub.club)
                .where(clubPost.club.eq(club))
                .fetchOne();
        if(cnt == null) cnt = 0L;


        return new PageImpl<>(list, pageable, cnt);
    }
}
