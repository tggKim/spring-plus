package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    QTodo todo = QTodo.todo;
    QUser user = QUser.user;
    QManager manager = QManager.manager;
    QComment comment = QComment.comment;

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
                .where(searchByWeather(weather), modifiedDateBetweenStartAndEnd(start, end))
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(todo.count())
                .from(todo)
                .where(searchByWeather(weather), modifiedDateBetweenStartAndEnd(start, end))
                .fetchOne();

        return new PageImpl<>(todos, pageable, total);
    }

    private BooleanExpression searchByWeather(String weather){
        if(weather == null){
            return null;
        }
        return todo.weather.eq(weather);
    }

    private BooleanExpression modifiedDateBetweenStartAndEnd(LocalDateTime start, LocalDateTime end){
        if(start == null || end == null){
            return null;
        }
        return todo.modifiedAt.between(start, end);
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<Todo> findAllMyTodos(String title, String startDate, String endDate, String nickname, Pageable pageable) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        if(startDate != null && endDate != null){
            start = LocalDateTime.parse(startDate + "T00:00:00");
            end = LocalDateTime.parse(endDate + "T23:59:59");
        }


        List<Todo> todos = queryFactory.selectFrom(todo)
                .where(searchManagerByNickname(nickname), searchByTitle(title), createdDateBetweenStartAndEnd(start, end))
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(todo.count())
                .from(todo)
                .where(searchManagerByNickname(nickname), searchByTitle(title), createdDateBetweenStartAndEnd(start, end))
                .fetchOne();

        return new PageImpl<>(todos, pageable, total);
    }

    private BooleanExpression searchManagerByNickname(String nickname){
        if(nickname == null){
            return null;
        }
        JPQLQuery<Long> subquery = JPAExpressions.select(manager.id).from(manager).join(manager.user, user).where(user.nickname.contains(nickname));
        return todo.managers.any().id.in(subquery);
    }

    private BooleanExpression searchByTitle(String title){
        if(title == null){
            return null;
        }
        return todo.title.contains(title);
    }

    private BooleanExpression createdDateBetweenStartAndEnd(LocalDateTime start, LocalDateTime end){
        if(start == null || end == null){
            return null;
        }
        return todo.createdAt.between(start, end);
    }
}
