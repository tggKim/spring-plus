package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QTodo todo = QTodo.todo;
    QUser user = QUser.user;

    @Override
    public Page<Todo> findAllByOrders(Pageable pageable, String weather, String startDate, String endDate) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        if(startDate != null && endDate != null){
            start = LocalDateTime.parse(startDate + "T00:00:00");
            end = LocalDateTime.parse(endDate + "T23:59:59");
        }

        List<Todo> todos = queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(weather != null ? todo.weather.eq(weather) : null)
                .where(start != null && end != null ? todo.modifiedAt.between(start, end) : null)
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(todo.count())
                .from(todo)
                .fetchOne();

        return new PageImpl<>(todos, pageable, total);
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }
}
