package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalVideoTool is a Querydsl query type for RentalVideoTool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalVideoTool extends EntityPathBase<RentalVideoTool> {

    private static final long serialVersionUID = -394146747L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalVideoTool rentalVideoTool = new QRentalVideoTool("rentalVideoTool");

    public final EnumPath<AppStatus> appStatus = createEnum("appStatus", AppStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reason = createString("reason");

    public final NumberPath<Long> ref = createNumber("ref", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalDate = createDateTime("rentalDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> returnDate = createDateTime("returnDate", java.time.LocalDateTime.class);

    public final QUser student;

    public final QVideoTool videoTool;

    public QRentalVideoTool(String variable) {
        this(RentalVideoTool.class, forVariable(variable), INITS);
    }

    public QRentalVideoTool(Path<? extends RentalVideoTool> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalVideoTool(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalVideoTool(PathMetadata metadata, PathInits inits) {
        this(RentalVideoTool.class, metadata, inits);
    }

    public QRentalVideoTool(Class<? extends RentalVideoTool> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new QUser(forProperty("student")) : null;
        this.videoTool = inits.isInitialized("videoTool") ? new QVideoTool(forProperty("videoTool"), inits.get("videoTool")) : null;
    }

}

