package com.example.sqlplease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Coupon_dialog extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_dialog);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        TextView tv_manage_name = findViewById(R.id.tv_manage_name);
        TextView tv_manage_number = findViewById(R.id.tv_manage_number);
        TextView tv_manage_coupon1 = findViewById(R.id.tv_manage_coupon1);
        TextView tv_manage_coupon2 = findViewById(R.id.tv_manage_coupon2);

        Button btn_coupon1_plus = findViewById(R.id.btn_coupon1_plus);
        Button btn_coupon1_minus = findViewById(R.id.btn_coupon1_minus);
        Button btn_coupon1_use = findViewById(R.id.btn_coupon1_use);
        Button btn_coupon2_plus = findViewById(R.id.btn_coupon2_plus);
        Button btn_coupon2_minus = findViewById(R.id.btn_coupon2_minus);
        Button btn_coupon2_use = findViewById(R.id.btn_coupon2_use);
        Button btn_coupon_save = findViewById(R.id.btn_coupon_save);
        Button btn_reset = findViewById(R.id.btn_reset);


        tv_manage_name.setText(name);
        tv_manage_number.setText(number);

        DBHelper mDBHelper = new DBHelper(this);

        List couponNumbers = new ArrayList<>();
        couponNumbers = mDBHelper.getCouponNum(name, number);
        String coupon1 = (String) couponNumbers.get(0);
        String coupon2 = (String) couponNumbers.get(1);

        tv_manage_coupon1.setText(coupon1);
        tv_manage_coupon2.setText(coupon2);


        btn_coupon1_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon1.getText().toString()) + 1;
                String coupon_ = Integer.toString(coupon);
                tv_manage_coupon1.setText(coupon_);
            }
        });

        btn_coupon1_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon1.getText().toString());
                if(coupon >= 1) {
                    coupon = coupon - 1;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon1.setText(coupon_);
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "도장을 지울 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_coupon1_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon1.getText().toString());
                if(coupon >= 10) {
                    coupon = coupon - 10;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon1.setText(coupon_);
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "쿠폰을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_coupon2_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon2.getText().toString()) + 1;
                String coupon_ = Integer.toString(coupon);
                tv_manage_coupon2.setText(coupon_);
            }
        });

        btn_coupon2_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon2.getText().toString());
                if(coupon >= 1) {
                    coupon = coupon - 1;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon2.setText(coupon_);
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "도장을 지울 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_coupon2_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon2.getText().toString());
                if(coupon >= 10) {
                    coupon = coupon - 10;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon2.setText(coupon_);
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "쿠폰을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_coupon_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coupon1_ = tv_manage_coupon1.getText().toString();
                String coupon2_ = tv_manage_coupon2.getText().toString();

                mDBHelper.UpdateContent(name, number, coupon1_, coupon2_, name, number);
                Toast.makeText(Coupon_dialog.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manage_coupon1.setText(coupon1);
                tv_manage_coupon2.setText(coupon2);
                Toast.makeText(Coupon_dialog.this, "초기화 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
