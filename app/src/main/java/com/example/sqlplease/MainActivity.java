package com.example.sqlplease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv_coupon;
    private Button mBtn_write;
    private Button mBtn_delete;
    private Button mBtn_reload;
    private Button mBtn_search;
    private List<String> list;
    private TextView mTv_count;
    private ArrayList<CouponItem> mCouponItems;
    private DBHelper mDBHelper;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
    }

    public static Comparator<CouponItem> Compare = new Comparator<CouponItem>() {
        @Override
        public int compare(CouponItem t1, CouponItem t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };

    private void setInit() {
        mDBHelper = new DBHelper(this);

        mRv_coupon = findViewById(R.id.rv_coupon);
        mBtn_write = findViewById(R.id.btn_write);
        mBtn_delete = findViewById(R.id.btn_delete);
        mBtn_reload = findViewById(R.id.btn_reload);
        mBtn_search = findViewById(R.id.btn_search);
        mTv_count = findViewById(R.id.tv_count);
        mCouponItems = new ArrayList<>();

        // load recent db
        LoadRecentDB();
        setCount();

        list = new ArrayList<String>();

        settingList();

        final AutoCompleteTextView autocompleteTextview = (AutoCompleteTextView) findViewById(R.id.et_search_name);

        autocompleteTextview.setAdapter(new ArrayAdapter<String>(this, R.layout.search_suggest, list));


        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView et_search_name = findViewById(R.id.et_search_name);
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
                setCount();

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
                        setCount();
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

                        Boolean bool = mDBHelper.CheckContent(name, number);

                        if(bool == false) {
                            if(coupon1.length() == 0) {
                                coupon1 = "0";
                            }
                            if(coupon2.length() == 0) {
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
                        else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "이미 있는 이름, 번호 입니다.", Toast.LENGTH_SHORT).show();
                        }
                        setCount();

                    }
                });
                dialog.show();
            }
        });
    }

    private void LoadRecentDB() {
        // 저장 되어있던 데이터를 가져옴
        mCouponItems = mDBHelper.getCouponList();
        Collections.sort(mCouponItems, Compare);
        mAdapter = new CustomAdapter(mCouponItems, this);
        mRv_coupon.setHasFixedSize(true);
        mRv_coupon.setAdapter(mAdapter);
    }

    private void setCount() {
        int count = mDBHelper.getCouponCount();
        String count_Text = Integer.toString(count);
        mTv_count.setText(count_Text);
    }

    private void settingList() {
        list = mDBHelper.getCouponName();
        Collections.sort(list);
    }
}