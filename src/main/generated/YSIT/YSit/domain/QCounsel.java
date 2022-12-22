package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCounsel is a Querydsl query type for Counsel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCounsel extends EntityPathBase<Counsel> {

    private static final long serialVersionUID = -104108917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCounsel counsel = new QCounsel("counsel");

    public final DateTimePath<java.time.LocalDateTime> counselDate = createDateTime("counselDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser student;

    public QCounsel(String variable) {
        this(Counsel.class, forVariable(variable), INITS);
    }

    public QCounsel(Path<? extends Counsel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCounsel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCounsel(PathMetadata metadata, PathInits inits) {
        this(Counsel.class, metadata, inits);
    }

    public QCounsel(Class<? extends Counsel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new QUser(forProperty("student")) : null;
    }

}

