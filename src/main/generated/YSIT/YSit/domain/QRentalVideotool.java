package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRentalVideotool is a Querydsl query type for RentalVideotool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRentalVideotool extends EntityPathBase<RentalVideotool> {

    private static final long serialVersionUID = -393193435L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRentalVideotool rentalVideotool = new QRentalVideotool("rentalVideotool");

    public final EnumPath<AppStatus> appStatus = createEnum("appStatus", AppStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rentalDate = createDateTime("rentalDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> returnDate = createDateTime("returnDate", java.time.LocalDateTime.class);

    public final QUser student;

    public final QVideotool videotool;

    public QRentalVideotool(String variable) {
        this(RentalVideotool.class, forVariable(variable), INITS);
    }

    public QRentalVideotool(Path<? extends RentalVideotool> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRentalVideotool(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRentalVideotool(PathMetadata metadata, PathInits inits) {
        this(RentalVideotool.class, metadata, inits);
    }

    public QRentalVideotool(Class<? extends RentalVideotool> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new QUser(forProperty("student")) : null;
        this.videotool = inits.isInitialized("videotool") ? new QVideotool(forProperty("videotool")) : null;
    }

}

