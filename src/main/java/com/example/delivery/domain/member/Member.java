package com.example.delivery.domain.member;

import com.example.delivery.domain.BaseTimeEntity;
import com.example.delivery.domain.member.enums.MemberStatus;
import com.example.delivery.domain.memberorder.MemberOrder;
import com.example.delivery.domain.storemember.StoreMember;
import com.example.delivery.dto.member.RegisterRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"storeMembers", "memberOrders"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member extends BaseTimeEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name", nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status")
    private MemberStatus status;

    @Column(nullable = false, length = 150)
    private String address;

    private String refreshToken;

    @Column(name = "unregistered_at")
    private LocalDateTime unregisteredAt; // 탈퇴 시간

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StoreMember> storeMembers = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberOrder> memberOrders = new ArrayList<>();


    public static Member create(RegisterRequest request){
        return Member.builder()
                .address(request.getAddress())
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .status(MemberStatus.USER)
                .build();
    }

}
