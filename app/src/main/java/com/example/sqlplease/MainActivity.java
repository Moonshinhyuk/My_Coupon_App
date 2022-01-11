package com.example.sqlplease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv_coupon;
    private Button mBtn_write;
    private Button mBtn_delete;
    private Button mBtn_reload;
    private Button mBtn_search;
    private ArrayList<CouponItem> mCouponItems;
    private DBHelper mDBHelper;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);

        mRv_coupon = findViewById(R.id.rv_coupon);
        mBtn_write = findViewById(R.id.btn_write);
        mBtn_delete = findViewById(R.id.btn_delete);
        mBtn_reload = findViewById(R.id.btn_reload);
        mBtn_search = findViewById(R.id.btn_search);
        mCouponItems = new ArrayList<>();

        // load recent db
        LoadRecentDB();

        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_search_name = findViewById(R.id.et_search_name);
                EditText et_search_number = findViewById(R.id.et_search_number);

                String name = et_search_name.getText().toString();
                String number = et_search_number.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Search_result.class);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                et_search_name.setText("");
                et_search_number.setText("");
            }
        });

        mBtn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadRecentDB();

                Toast.makeText(MainActivity.this, "새로고침이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        mBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit2);

                EditText et_del_name = dialog.findViewById(R.id.et_del_name);
                EditText et_del_number = dialog.findViewById(R.id.et_del_number);
                Button btn_del = dialog.findViewById(R.id.btn_del);

                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_del_name.getText().toString();
                        String number = et_del_number.getText().toString();

                        mDBHelper.DeleteContent(name, number);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "목록 삭제가 완료 되었습니다", Toast.LENGTH_SHORT).show();
                        LoadRecentDB();
                    }
                });

                dialog.show();
            }
        });

        mBtn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 팝업 창 띄우기
                Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_edit);

                EditText et_name = dialog.findViewById(R.id.et_name);
                EditText et_number = dialog.findViewById(R.id.et_number);
                EditText et_coupon1 = dialog.findViewById(R.id.et_coupon1);
                EditText et_coupon2 = dialog.findViewById(R.id.et_coupon2);
                Button btn_ok = dialog.findViewById(R.id.btn_ok);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = et_name.getText().toString();
                        String number = et_number.getText().toString();
                        String coupon1 = et_coupon1.getText().toString();
                        String coupon2 = et_coupon2.getText().toString();

                        if(coupon1.length() == 0) {
                            coupon1 = "0";
                        }
                        else if(coupon2.length() == 0) {
                            coupon2 = "0";
                        }

                        //Insert Database
                        mDBHelper.InsertContent(name,
                                number,
                                coupon1,
                                coupon2);

                        //Insert UI
                        CouponItem item = new CouponItem();
                        item.setName(name);
                        item.setNumber(number);
                        item.setCoupon1(coupon1);
                        item.setCoupon2(coupon2);

                        mAdapter.addItem(item);

                        mRv_coupon.smoothScrollToPosition(0);
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "목록에 추가 되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.show();
            }
        });
    }

    private void LoadRecentDB() {
        // 저장 되어있던 데이터를 가져옴
        mCouponItems = mDBHelper.getCouponList();
        mAdapter = new CustomAdapter(mCouponItems, this);
        mRv_coupon.setHasFixedSize(true);
        mRv_coupon.setAdapter(mAdapter);
    }
}