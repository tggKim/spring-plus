package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class NewUserResponse {
    private final Long id;
    private final String email;
    private final String imageUrl;

    public NewUserResponse(Long id, String email, String imageUrl) {
        this.id = id;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
