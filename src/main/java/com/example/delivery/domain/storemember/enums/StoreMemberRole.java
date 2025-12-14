package com.example.delivery.domain.storemember.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreMemberRole {
    MASTER("마스터"),
    ADMIN("관리자"),
    USER("일반유저"),
    ;

    private String description;
}
