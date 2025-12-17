package com.example.delivery.controller;

import com.example.delivery.common.api.Api;
import com.example.delivery.dto.member.LoginRequest;
import com.example.delivery.dto.member.LoginResponse;
import com.example.delivery.dto.member.MemberResponse;
import com.example.delivery.dto.member.RegisterRequest;
import com.example.delivery.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public Api<MemberResponse> register(@Valid @RequestBody RegisterRequest request){
        var response = memberService.register(request);
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        var response = memberService.login(request);
        return Api.OK(response);
    }

    @PostMapping("/refresh")
    public Api<LoginResponse> refresh(@RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");
        var response = memberService.refreshToken(token);
        return Api.OK(response);
    }

}
