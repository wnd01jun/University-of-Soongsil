package network.freeTopic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberClub is a Querydsl query type for MemberClub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberClub extends EntityPathBase<MemberClub> {

    private static final long serialVersionUID = 85798703L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberClub memberClub = new QMemberClub("memberClub");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QClub club;

    public final EnumPath<network.freeTopic.domain.enums.ClubRole> clubRole = createEnum("clubRole", network.freeTopic.domain.enums.ClubRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public QMemberClub(String variable) {
        this(MemberClub.class, forVariable(variable), INITS);
    }

    public QMemberClub(Path<? extends MemberClub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberClub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberClub(PathMetadata metadata, PathInits inits) {
        this(MemberClub.class, metadata, inits);
    }

    public QMemberClub(Class<? extends MemberClub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

