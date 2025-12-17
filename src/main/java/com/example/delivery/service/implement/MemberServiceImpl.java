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
                    throw new ApiException(MemberErrorCode.MEMBER_ALREADY_REGISTERED, "이미 가입된 회원 입니다");
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
           throw new ApiException(MemberErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 일치하지 않습니다.");
       }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getEmail());

        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

       return LoginResponse.login(accessToken,refreshToken);
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        String email = jwtTokenProvider.getUsernameFromToken(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new RuntimeException("서버에 저장된 Refresh Token과 다릅니다.");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(email);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(email);

        member.setRefreshToken(newRefreshToken);
        memberRepository.save(member);

        return LoginResponse.login(newAccessToken, newRefreshToken);
    }


}
