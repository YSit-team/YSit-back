package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdmins is a Querydsl query type for Admins
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdmins extends EntityPathBase<Admins> {

    private static final long serialVersionUID = 621717998L;

    public static final QAdmins admins = new QAdmins("admins");

    public final StringPath ad_name = createString("ad_name");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logincode = createString("logincode");

    public QAdmins(String variable) {
        super(Admins.class, forVariable(variable));
    }

    public QAdmins(Path<? extends Admins> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdmins(PathMetadata metadata) {
        super(Admins.class, metadata);
    }

}

