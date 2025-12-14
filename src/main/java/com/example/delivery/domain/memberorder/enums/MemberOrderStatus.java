package com.example.delivery.domain.memberorder.enums;

public enum MemberOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDER("주문"),
    ACCEPT("확인"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("완료"),
    ;

    MemberOrderStatus(String description){
        this.description = description;
    }
    private String description;
}