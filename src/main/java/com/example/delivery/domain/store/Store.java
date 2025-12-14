package com.example.delivery.domain.store;

import com.example.delivery.domain.memberorder.MemberOrder;
import com.example.delivery.domain.store.enums.StoreCategory;
import com.example.delivery.domain.store.enums.StoreStatus;
import com.example.delivery.domain.storemember.StoreMember;
import com.example.delivery.domain.storemenu.StoreMenu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"storeMembers", "storeMenus", "memberOrders"})
@EqualsAndHashCode(of = "id")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(name = "store_name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status", nullable = false, length = 50)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StoreCategory category;

    @Column(nullable = false)
    private Double star;

    @Column(nullable = false, length = 200)
    private String thumbnailUrl;

    @Column(nullable = false, precision = 11, scale = 4)
    private BigDecimal  minimumAmount;

    @Column(nullable = false, precision = 11, scale = 4)
    private BigDecimal  minimumDeliveryAmount;

    @Column(length = 20)
    private String phoneNumber;

    // 연관관계
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreMember> storeMembers = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreMenu> storeMenus = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<MemberOrder> memberOrders = new ArrayList<>();
}
