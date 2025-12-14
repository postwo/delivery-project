package com.example.delivery.service;

import com.example.delivery.dto.member.LoginRequest;
import com.example.delivery.dto.member.LoginResponse;
import com.example.delivery.dto.member.MemberResponse;
import com.example.delivery.dto.member.RegisterRequest;

public interface MemberService {
    MemberResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
