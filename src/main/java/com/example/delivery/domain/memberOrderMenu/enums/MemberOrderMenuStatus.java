package com.example.delivery.domain.memberOrderMenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberOrderMenuStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private String description;
}
