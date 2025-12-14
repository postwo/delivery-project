package com.example.delivery.domain.memberorder;

import com.example.delivery.domain.member.Member;
import com.example.delivery.domain.memberorder.enums.MemberOrderStatus;
import com.example.delivery.domain.store.Store;
import com.example.delivery.domain.memberOrderMenu.MemberOrderMenu;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_order")
@ToString(exclude = {"member", "store", "memberOrderMenus"})
@EqualsAndHashCode(of = "id")
public class MemberOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 50)
    private MemberOrderStatus status;

    @Column(nullable = false, precision = 11, scale = 4)
    private BigDecimal amount;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "cooking_started_at")
    private LocalDateTime cookingStartedAt;

    @Column(name = "delivery_started_at")
    private LocalDateTime deliveryStartedAt;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "memberOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberOrderMenu> memberOrderMenus = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

}
