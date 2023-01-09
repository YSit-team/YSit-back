package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAfterClass is a Querydsl query type for AfterClass
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAfterClass extends EntityPathBase<AfterClass> {

    private static final long serialVersionUID = 2032515814L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAfterClass afterClass = new QAfterClass("afterClass");

    public final ListPath<AfterClassStudent, QAfterClassStudent> afterClassStudents = this.<AfterClassStudent, QAfterClassStudent>createList("afterClassStudents", AfterClassStudent.class, QAfterClassStudent.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final EnumPath<AppStatus> status = createEnum("status", AppStatus.class);

    public final QUser user;

    public QAfterClass(String variable) {
        this(AfterClass.class, forVariable(variable), INITS);
    }

    public QAfterClass(Path<? extends AfterClass> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAfterClass(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAfterClass(PathMetadata metadata, PathInits inits) {
        this(AfterClass.class, metadata, inits);
    }

    public QAfterClass(Class<? extends AfterClass> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

