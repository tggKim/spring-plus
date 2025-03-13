package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ManagerLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestTodoId;

    private Long requestUserId;

    private Long requestManagerId;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime requestAt;

    public ManagerLog(Long requestTodoId, Long requestUserId, Long requestManagerId){
        this.requestTodoId = requestTodoId;
        this.requestUserId = requestUserId;
        this.requestManagerId = requestManagerId;
    }
}
