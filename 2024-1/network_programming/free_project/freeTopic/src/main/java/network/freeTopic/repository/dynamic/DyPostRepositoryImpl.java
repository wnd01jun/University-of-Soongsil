package network.freeTopic.repository.dynamic;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.freeTopic.controller.dto.BoardResponseDto;
import network.freeTopic.domain.*;
import network.freeTopic.form.PostSearchCondition;
import network.freeTopic.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static network.freeTopic.domain.QMember.*;
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
                .join(post.member, QMember.member)
                .fetchJoin()
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
    public Page<Post> findBoardInfo(Pageable pageable, PostSearchCondition condition) {


        List<Post> list = queryFactory
                .select(post)
                .from(post)
                .join(post.member, member)
                .where(searchTitle(condition.getTitle())
                , searchClubName(condition.getClubName())
                , searchCategories(condition.getCategory()))
                .fetchJoin()
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
//        List<ClubPost> list = queryFactory.select(clubPost)
//                .from(clubPost, QClub.club)
//                .where(clubPost.club.eq(club))
//                .orderBy(clubPost.createdTime.asc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        Long cnt = queryFactory.select(clubPost.count())
//                .from(clubPost, QClub.club)
//                .where(clubPost.club.eq(club))
//                .fetchOne();
//        if(cnt == null) cnt = 0L;


        return new PageImpl<>(new ArrayList<>(), pageable, 0L);
    }

    private BooleanExpression searchTitle(String title){
        if(title == null || title.isEmpty()){
            return null;
        }
        return (post.title.contains(title));
    }

    private BooleanExpression searchClubName(String clubName){
        if(clubName == null || clubName.isEmpty()){
            return null;
        }
        return (post.club.name.eq(clubName));
    }
    private BooleanExpression searchCategories(Category category){
        return (category == null) ? null : (post.club.category.eq(category));
    }

}
