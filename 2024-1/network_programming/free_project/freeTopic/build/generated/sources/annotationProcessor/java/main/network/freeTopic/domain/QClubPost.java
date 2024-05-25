package network.freeTopic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubPost is a Querydsl query type for ClubPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubPost extends EntityPathBase<ClubPost> {

    private static final long serialVersionUID = -1494355211L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubPost clubPost = new QClubPost("clubPost");

    public final QPost _super;

    public final QClub club;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime;

    public final EnumPath<network.freeTopic.domain.enums.PostOpenPermission> permission = createEnum("permission", network.freeTopic.domain.enums.PostOpenPermission.class);

    //inherited
    public final StringPath textDetail;

    //inherited
    public final StringPath title;

    public QClubPost(String variable) {
        this(ClubPost.class, forVariable(variable), INITS);
    }

    public QClubPost(Path<? extends ClubPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubPost(PathMetadata metadata, PathInits inits) {
        this(ClubPost.class, metadata, inits);
    }

    public QClubPost(Class<? extends ClubPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPost(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.createdTime = _super.createdTime;
        this.id = _super.id;
        this.member = _super.member;
        this.modifiedTime = _super.modifiedTime;
        this.textDetail = _super.textDetail;
        this.title = _super.title;
    }

}

