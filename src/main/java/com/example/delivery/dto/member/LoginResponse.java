package com.example.delivery.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    public static LoginResponse login(String jwt){
        return LoginResponse.builder()
                .accessToken(jwt)
                .build();
    }
}
