package com.example.sqlplease;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Coupon_dialog extends MainActivity{

    private DBHelper mDBHelper;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_dialog);


        //리스트에서 이름과 번호 받아오기
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");


        //텍스트뷰 설정
        TextView tv_manage_name = findViewById(R.id.tv_manage_name);
        TextView tv_manage_number = findViewById(R.id.tv_manage_number);
        TextView tv_manage_coupon1 = findViewById(R.id.tv_manage_coupon1);
        TextView tv_manage_coupon2 = findViewById(R.id.tv_manage_coupon2);
        TextView tv_use_log = findViewById(R.id.tv_use_log);
        TextView tv_use_date = findViewById(R.id.tv_use_date);


        //버튼 설정
        Button btn_coupon1_plus = findViewById(R.id.btn_coupon1_plus);
        Button btn_coupon1_minus = findViewById(R.id.btn_coupon1_minus);
        Button btn_coupon1_use = findViewById(R.id.btn_coupon1_use);
        Button btn_coupon2_plus = findViewById(R.id.btn_coupon2_plus);
        Button btn_coupon2_minus = findViewById(R.id.btn_coupon2_minus);
        Button btn_coupon2_use = findViewById(R.id.btn_coupon2_use);
        Button btn_coupon_save = findViewById(R.id.btn_coupon_save);
        Button btn_reset = findViewById(R.id.btn_reset);
        Button btn_home = findViewById(R.id.btn_home);

        mDBHelper = new DBHelper(this);


//이름 번호 설정
        tv_manage_name.setText(name);
        tv_manage_number.setText(number);


//카드쿠폰 현금쿠폰 불러오고 설정
        List couponNumbers = new ArrayList<>();
        couponNumbers = mDBHelper.getCouponNum(name, number);
        String coupon1 = (String) couponNumbers.get(0);
        String coupon2 = (String) couponNumbers.get(1);


        tv_manage_coupon1.setText(coupon1);
        tv_manage_coupon2.setText(coupon2);




//애니메이션 설정
        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(100);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        if(Integer.parseInt(tv_manage_coupon1.getText().toString()) >= 10) {
            btn_coupon1_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use2));
            btn_coupon1_use.startAnimation(anim);
        }
        if(Integer.parseInt(tv_manage_coupon2.getText().toString()) >= 10) {
            btn_coupon2_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use2));
            btn_coupon2_use.startAnimation(anim);
        }



//현금쿠폰 도장 추가
        btn_coupon1_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon1.getText().toString()) + 1;
                String coupon_ = Integer.toString(coupon);
                tv_manage_coupon1.setText(coupon_);
            }
        });


//현금쿠폰 도장 제거
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


        TextView tv_use1 = findViewById(R.id.tv_use1);

//현금쿠폰 사용
        btn_coupon1_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon1.getText().toString());
                if(coupon >= 10) {
                    coupon = coupon - 10;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon1.setText(coupon_);
                    if(coupon < 10) {
                        btn_coupon1_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use));
                        btn_coupon1_use.clearAnimation();
                    }
                    tv_use1.setText("true");
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "쿠폰을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    tv_use1.setText("false");
                }
            }
        });


//카드쿠폰 도장 추가
        btn_coupon2_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon2.getText().toString()) + 1;
                String coupon_ = Integer.toString(coupon);
                tv_manage_coupon2.setText(coupon_);
            }
        });


//카드쿠폰 도장 제거
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


        TextView tv_use2 = findViewById(R.id.tv_use2);

//카드쿠폰 사용
        btn_coupon2_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coupon = Integer.parseInt(tv_manage_coupon2.getText().toString());
                if(coupon >= 10) {
                    coupon = coupon - 10;
                    String coupon_ = Integer.toString(coupon);
                    tv_manage_coupon2.setText(coupon_);
                    if(coupon < 10) {
                        btn_coupon2_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use));
                        btn_coupon2_use.clearAnimation();
                    }
                    tv_use2.setText("true");
                }
                else {
                    Toast.makeText(Coupon_dialog.this, "쿠폰을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    tv_use2.setText("false");
                }
            }
        });


//쿠폰 사용 및 추가 내역 저장
        btn_coupon_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coupon1_ = tv_manage_coupon1.getText().toString();
                String coupon2_ = tv_manage_coupon2.getText().toString();

                mDBHelper.UpdateContent(name, number, coupon1_, coupon2_, name, number);
                Toast.makeText(Coupon_dialog.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();

                String use1 = null;
                String use2 = null;
                String plus1 = null;
                String plus2 = null;
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                int plus1Count = Integer.parseInt(tv_manage_coupon1.getText().toString()) - Integer.parseInt(coupon1);
                int plus2Count = Integer.parseInt(tv_manage_coupon2.getText().toString()) - Integer.parseInt(coupon2);


                //현금쿠폰 도장 추가 갯수 구하기
                if(plus1Count <= 0){
                }
                else {
                    plus1 = Integer.toString(plus1Count) + "개 추가";
                }


                //카드쿠폰 도장 추가 갯수 구하기
                if(plus2Count <= 0){
                }
                else {
                    plus2 = Integer.toString(plus2Count) + "개 추가";
                }


                //현금쿠폰 사용 여부 구하기
                if(tv_use1.getText().toString() == "false"){
                }
                else if(tv_use1.getText().toString() == "true"){
                    use1 = "사용함";
                    if(Integer.parseInt(tv_manage_coupon1.getText().toString()) > Integer.parseInt(coupon1) - 10) {
                        int instant = Integer.parseInt(tv_manage_coupon1.getText().toString()) - (Integer.parseInt(coupon1) - 10);
                        plus1 = Integer.toString(instant) + "개 추가";
                    }
                }


                //카드쿠폰 사용 여부 구하기
                if(tv_use2.getText().toString() == "false"){
                }
                else if(tv_use2.getText().toString() == "true"){
                    use2 = "사용함";
                    if(Integer.parseInt(tv_manage_coupon2.getText().toString()) > Integer.parseInt(coupon2) - 10) {
                        int instant = Integer.parseInt(tv_manage_coupon2.getText().toString()) - (Integer.parseInt(coupon2) - 10);
                        plus2 = Integer.toString(instant) + "개 추가";
                    }
                }


                //변경점이 없다면 그냥 지나가고, 있다면 사용, 추가 내역을 저장
                if(use1 == null && use2 == null && plus1 == null && plus2 == null) {
                }
                else {
                    mDBHelper.InsertLogTable(name, number, use1, use2, plus1, plus2, date);
                }
            }
        });


//쿠폰 추가 및 사용하기 전 상태로 돌림
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_manage_coupon1.setText(coupon1);
                tv_manage_coupon2.setText(coupon2);
                if(Integer.parseInt(tv_manage_coupon1.getText().toString()) >= 10) {
                    btn_coupon1_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use2));
                    btn_coupon1_use.startAnimation(anim);
                }
                if(Integer.parseInt(tv_manage_coupon2.getText().toString()) >= 10) {
                    btn_coupon2_use.setBackground(ContextCompat.getDrawable(Coupon_dialog.this, R.drawable.button_use2));
                    btn_coupon2_use.startAnimation(anim);
                }

                Toast.makeText(Coupon_dialog.this, "초기화 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


//홈 화면으로 나가기
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


//이름 및 번호 수정
        tv_manage_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv_manage_name = findViewById(R.id.tv_manage_name);
                TextView tv_manage_number = findViewById(R.id.tv_manage_number);
                TextView tv_manage_coupon2 = findViewById(R.id.tv_manage_coupon2);
                TextView tv_manage_coupon1 = findViewById(R.id.tv_manage_coupon1);
                String name_ = tv_manage_name.getText().toString();
                String number_ = tv_manage_number.getText().toString();
                String coupon2 = tv_manage_coupon2.getText().toString();
                String coupon1 = tv_manage_coupon1.getText().toString();

                Dialog dialog = new Dialog(Coupon_dialog.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit2);

                EditText et_change_name = dialog.findViewById(R.id.et_del_name);
                EditText et_change_number = dialog.findViewById(R.id.et_del_number);
                Button btn_change = dialog.findViewById(R.id.btn_del);

                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_change_name.getText().toString();
                        String number = et_change_number.getText().toString();

                        Boolean bool = mDBHelper.CheckContent(name, number);

                        if(bool == false) {
                            if(name.length() == 0) {
                                name = name_;
                            }
                            if(number.length() == 0) {
                                number = number_;
                            }

                            mDBHelper.UpdateContent(name, number, coupon1, coupon2, name_, number_);

                            dialog.dismiss();

                            tv_manage_name.setText(name);
                            tv_manage_number.setText(number);

                            Toast.makeText(Coupon_dialog.this, "변경 완료.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(Coupon_dialog.this, "이미 있는 이름, 번호 입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

    }
}
