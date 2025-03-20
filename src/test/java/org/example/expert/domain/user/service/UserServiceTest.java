package org.example.expert.domain.user.service;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserBulkRepository;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserBulkRepository userBulkRepository;

    @Test
    void saveTest(){
        for(int i=0;i<1000000;i++){
            String email = "scie" + (i + 2) + "@gmail.com";
            String password = UUID.randomUUID().toString() + "password";
            UserRole userRole = UserRole.ROLE_USER;
            String nickname = "tgg" + i;
            String imageName = UUID.randomUUID().toString() + ".png";
            User user = new User(email, password, userRole, nickname, imageName);

            userRepository.save(user);
        }
    }

    @Test
    void saveAllTest(){
        List<User> users = new ArrayList<>();
        for(int i=0;i<1000000;i++){
            String email = "scie" + (i + 2) + "@gmail.com";
            String password = UUID.randomUUID().toString() + "password";
            UserRole userRole = UserRole.ROLE_USER;
            String nickname = "tgg" + i;
            String imageName = UUID.randomUUID().toString() + ".png";
            User user = new User(email, password, userRole, nickname, imageName);

            users.add(user);
        }
        userRepository.saveAll(users);
    }

    @Test
    void saveAllBulkTest(){
        List<User> users = new ArrayList<>();
        for(int i=0;i<1000000;i++){
            String email = "scie" + (i + 2) + "@gmail.com";
            String password = UUID.randomUUID().toString() + "password";
            UserRole userRole = UserRole.ROLE_USER;
            String nickname = "tgg" + i;
            String imageName = UUID.randomUUID().toString() + ".png";
            User user = new User(email, password, userRole, nickname, imageName);

            users.add(user);
        }

        userBulkRepository.saveAll(users);
    }
}