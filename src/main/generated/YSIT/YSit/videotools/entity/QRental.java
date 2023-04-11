package YSIT.YSit.videotools.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRental is a Querydsl query type for Rental
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRental extends EntityPathBase<Rental> {

    private static final long serialVersionUID = -482065375L;

    public static final QRental rental = new QRental("rental");

    public final StringPath headCnt = createString("headCnt");

    public final StringPath id = createString("id");

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath rentalDate = createString("rentalDate");

    public final ListPath<String, StringPath> rentalTools = this.<String, StringPath>createList("rentalTools", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath returnDate = createString("returnDate");

    public final EnumPath<YSIT.YSit.videotools.RentalStatus> status = createEnum("status", YSIT.YSit.videotools.RentalStatus.class);

    public final StringPath uploader = createString("uploader");

    public QRental(String variable) {
        super(Rental.class, forVariable(variable));
    }

    public QRental(Path<? extends Rental> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRental(PathMetadata metadata) {
        super(Rental.class, metadata);
    }

}

