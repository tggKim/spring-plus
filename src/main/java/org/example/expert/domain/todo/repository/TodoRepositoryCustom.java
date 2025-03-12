package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepositoryCustom {
    Page<Todo> findAllByOrders(Pageable pageable, String weather, String startDate, String endDate);

    Optional<Todo> findByIdWithUser(Long todoId);

    Page<Todo> findAllMyTodos(String title, String startDate, String endDate, String nickname, Pageable pageable);
}
