package YSIT.YSit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideotool is a Querydsl query type for Videotool
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideotool extends EntityPathBase<Videotool> {

    private static final long serialVersionUID = -1281841815L;

    public static final QVideotool videotool = new QVideotool("videotool");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> max_quantity = createNumber("max_quantity", Integer.class);

    public final StringPath vt_name = createString("vt_name");

    public QVideotool(String variable) {
        super(Videotool.class, forVariable(variable));
    }

    public QVideotool(Path<? extends Videotool> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideotool(PathMetadata metadata) {
        super(Videotool.class, metadata);
    }

}

