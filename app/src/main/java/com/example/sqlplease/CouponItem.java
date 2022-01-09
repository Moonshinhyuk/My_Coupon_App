package com.example.sqlplease;

public class CouponItem {
    private int id;           //각 줄의 고유 아이디
    private String name;      //이름
    private String number;    //번호
    private String coupon1;      //카드쿠폰 스탬프 갯수
    private String coupon2;      //현금쿠폰 스탬프 갯수


    public CouponItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCoupon1() {
        return coupon1;
    }

    public void setCoupon1(String coupon1) {
        this.coupon1 = coupon1;
    }

    public String getCoupon2() {
        return coupon2;
    }

    public void setCoupon2(String coupon2) {
        this.coupon2 = coupon2;
    }
}
