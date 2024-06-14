package network.freeTopic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJoinRequest is a Querydsl query type for JoinRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJoinRequest extends EntityPathBase<JoinRequest> {

    private static final long serialVersionUID = -142862042L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJoinRequest joinRequest = new QJoinRequest("joinRequest");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QClub club;

    public final StringPath contact = createString("contact");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath Introduction = createString("Introduction");

    public final EnumPath<network.freeTopic.domain.enums.JoinStatus> joinStatus = createEnum("joinStatus", network.freeTopic.domain.enums.JoinStatus.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedTime = _super.modifiedTime;

    public QJoinRequest(String variable) {
        this(JoinRequest.class, forVariable(variable), INITS);
    }

    public QJoinRequest(Path<? extends JoinRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJoinRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJoinRequest(PathMetadata metadata, PathInits inits) {
        this(JoinRequest.class, metadata, inits);
    }

    public QJoinRequest(Class<? extends JoinRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

