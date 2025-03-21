package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users", indexes = @Index(name = "idx_users_nickname", columnList = "nickname"))
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String nickname;
    private String imageName;

    public User(String email, String password, UserRole userRole, String nickname, String imageName) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;
        this.imageName = imageName;
    }

    private User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(),
                UserRole.of(authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseThrow(() ->new InvalidRequestException("권한을 가지고 있지 않습니다.")))
                );
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
