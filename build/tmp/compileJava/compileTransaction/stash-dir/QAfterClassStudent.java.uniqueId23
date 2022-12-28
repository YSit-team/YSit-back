package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAfterClassStudent is a Querydsl query type for AfterClassStudent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAfterClassStudent extends EntityPathBase<AfterClassStudent> {

    private static final long serialVersionUID = -983928715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAfterClassStudent afterClassStudent = new QAfterClassStudent("afterClassStudent");

    public final QAfterClass afterClass;

    public final StringPath end_time = createString("end_time");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath start_time = createString("start_time");

    public final QUser student;

    public QAfterClassStudent(String variable) {
        this(AfterClassStudent.class, forVariable(variable), INITS);
    }

    public QAfterClassStudent(Path<? extends AfterClassStudent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAfterClassStudent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAfterClassStudent(PathMetadata metadata, PathInits inits) {
        this(AfterClassStudent.class, metadata, inits);
    }

    public QAfterClassStudent(Class<? extends AfterClassStudent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.afterClass = inits.isInitialized("afterClass") ? new QAfterClass(forProperty("afterClass"), inits.get("afterClass")) : null;
        this.student = inits.isInitialized("student") ? new QUser(forProperty("student")) : null;
    }

}

