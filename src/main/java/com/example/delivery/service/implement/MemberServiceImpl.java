package com.example.delivery.service.implement;

import com.example.delivery.common.error.MemberErrorCode;
import com.example.delivery.common.exception.ApiException;
import com.example.delivery.domain.member.Member;
import com.example.delivery.dto.member.LoginRequest;
import com.example.delivery.dto.member.LoginResponse;
import com.example.delivery.dto.member.MemberResponse;
import com.example.delivery.dto.member.RegisterRequest;
import com.example.delivery.repository.MemberRepository;
import com.example.delivery.service.MemberService;
import com.example.delivery.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public MemberResponse register(RegisterRequest request) {
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(member -> {
                    throw new ApiException(MemberErrorCode.MEMBER_ALREADY_REGISTERED, "이미 가입된 회원");
                });
         String encodedPassword = passwordEncoder.encode(request.getPassword());
         request.setPassword(encodedPassword);
         Member member = Member.create(request);

         memberRepository.save(member);

         return MemberResponse.create(member);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
       Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(MemberErrorCode.MEMBER_NOT_FOUND, "사용자를 찾을 수 없음."));

       if (!passwordEncoder.matches(request.getPassword(),member.getPassword())){
           throw new ApiException(MemberErrorCode.PASSWORD_NOT_MATCH);
       }

        String jwt = jwtTokenProvider.generateToken(member.getEmail());

       return LoginResponse.login(jwt);
    }


}
