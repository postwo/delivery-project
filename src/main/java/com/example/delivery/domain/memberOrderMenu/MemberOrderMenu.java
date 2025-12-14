package com.example.delivery.domain.memberOrderMenu;

import com.example.delivery.domain.memberOrderMenu.enums.MemberOrderMenuStatus;
import com.example.delivery.domain.memberorder.MemberOrder;
import com.example.delivery.domain.storemenu.StoreMenu;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_order_menu")
@ToString(exclude = {"memberOrder", "storeMenu"})
@EqualsAndHashCode(of = "id")
public class MemberOrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MemberOrderMenuStatus status;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 11, scale = 4)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_order_id", nullable = false)
    @JsonBackReference("memberOrder-memberOrderMenus")
    private MemberOrder memberOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_menu_id", nullable = false)
    @JsonBackReference("storeMenu-memberOrderMenus")
    private StoreMenu storeMenu;

}
