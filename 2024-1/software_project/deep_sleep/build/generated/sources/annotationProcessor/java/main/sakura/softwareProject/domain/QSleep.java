package sakura.softwareProject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSleep is a Querydsl query type for Sleep
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSleep extends EntityPathBase<Sleep> {

    private static final long serialVersionUID = -188053968L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSleep sleep = new QSleep("sleep");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final TimePath<java.time.LocalTime> asleep = createTime("asleep", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> awake = createTime("awake", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> coreSleep = createTime("coreSleep", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final TimePath<java.time.LocalTime> deepSleep = createTime("deepSleep", java.time.LocalTime.class);

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final TimePath<java.time.LocalTime> inBed = createTime("inBed", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final TimePath<java.time.LocalTime> remSleep = createTime("remSleep", java.time.LocalTime.class);

    public final DatePath<java.time.LocalDate> sleepDate = createDate("sleepDate", java.time.LocalDate.class);

    public final NumberPath<Double> sleepQuality = createNumber("sleepQuality", Double.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final TimePath<java.time.LocalTime> totalInBedTime = createTime("totalInBedTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> totalSleepTime = createTime("totalSleepTime", java.time.LocalTime.class);

    public QSleep(String variable) {
        this(Sleep.class, forVariable(variable), INITS);
    }

    public QSleep(Path<? extends Sleep> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSleep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSleep(PathMetadata metadata, PathInits inits) {
        this(Sleep.class, metadata, inits);
    }

    public QSleep(Class<? extends Sleep> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

