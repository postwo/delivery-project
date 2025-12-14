package com.example.delivery.domain.storemenu;

import com.example.delivery.domain.store.Store;
import com.example.delivery.domain.memberOrderMenu.MemberOrderMenu;
import com.example.delivery.domain.storemenu.enums.StoreMenuStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "store_menu")
@ToString(exclude = {"store", "memberOrderMenus"})
@EqualsAndHashCode(of = "id")
public class StoreMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name", nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 11, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_status", nullable = false, length = 45)
    private StoreMenuStatus status;

    @Column(nullable = false, length = 200)
    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Integer sequence;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "storeMenu", cascade = CascadeType.ALL)
    private List<MemberOrderMenu> memberOrderMenus = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 비즈니스 로직
    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
