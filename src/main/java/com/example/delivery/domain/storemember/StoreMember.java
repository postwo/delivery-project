package com.example.delivery.domain.storemember;

import com.example.delivery.domain.BaseTimeEntity;
import com.example.delivery.domain.member.Member;
import com.example.delivery.domain.member.enums.MemberStatus;
import com.example.delivery.domain.store.Store;
import com.example.delivery.domain.storemember.enums.StoreMemberRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_member")
@ToString(exclude = {"member", "store"})
@EqualsAndHashCode(of = "id", callSuper = false) //// callSuper = false 추가 (BaseTimeEntity를 상속하는 경우)
public class StoreMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_member_status", nullable = false, length = 50)
    private StoreMemberRole status;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false, length = 50)
    private MemberStatus role;

    @Column(name = "unregistered_at")
    private LocalDateTime unregisteredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonBackReference("store-storeMembers")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference("member-storeMembers")
    private Member member;
}
