package YSIT.YSit.videotools.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoTool is a Querydsl query type for VideoTool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoTool extends EntityPathBase<VideoTool> {

    private static final long serialVersionUID = 200259734L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideoTool videoTool = new QVideoTool("videoTool");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> maxQuantity = createNumber("maxQuantity", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final YSIT.YSit.user.entity.QUser user;

    public final StringPath writer = createString("writer");

    public QVideoTool(String variable) {
        this(VideoTool.class, forVariable(variable), INITS);
    }

    public QVideoTool(Path<? extends VideoTool> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideoTool(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideoTool(PathMetadata metadata, PathInits inits) {
        this(VideoTool.class, metadata, inits);
    }

    public QVideoTool(Class<? extends VideoTool> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new YSIT.YSit.user.entity.QUser(forProperty("user")) : null;
    }

}

