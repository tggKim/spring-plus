package org.example.expert.domain.manager.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManagerLog is a Querydsl query type for ManagerLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManagerLog extends EntityPathBase<ManagerLog> {

    private static final long serialVersionUID = -1906659731L;

    public static final QManagerLog managerLog = new QManagerLog("managerLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> requestAt = createDateTime("requestAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> requestManagerId = createNumber("requestManagerId", Long.class);

    public final NumberPath<Long> requestTodoId = createNumber("requestTodoId", Long.class);

    public final NumberPath<Long> requestUserId = createNumber("requestUserId", Long.class);

    public QManagerLog(String variable) {
        super(ManagerLog.class, forVariable(variable));
    }

    public QManagerLog(Path<? extends ManagerLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManagerLog(PathMetadata metadata) {
        super(ManagerLog.class, metadata);
    }

}

