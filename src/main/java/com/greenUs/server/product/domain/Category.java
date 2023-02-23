package com.greenUs.server.product.domain;

public enum Category {
    식품("food"),주방("kitchen"),욕실("bath"),생활("life"),취미("hobby"),선물("gift"),여성용품("woman"),반려동물("pet"),문구("stationery");
    private String title;

    Category(String title) {
        this.title = title;
    }
}
