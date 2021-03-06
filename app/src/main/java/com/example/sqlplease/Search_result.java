package com.example.sqlplease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Search_result extends AppCompatActivity {

    private RecyclerView mRv_result;
    private ArrayList<ResultItem> mResultItems;
    private DBHelper mDBHelper;
    private CustomAdapter2 mAdapter;
    Button btn_home_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        if(number.length() != 0 && name.length() == 0){
            setInit1(number);
        }
        else if(name.length() != 0 && number.length() == 0){
            setInit2(name);
        }
        else {
            setInit3(name, number);
        }

        btn_home_result = findViewById(R.id.btn_home_result);

        btn_home_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public static Comparator<ResultItem> Compare = new Comparator<ResultItem>() {
        @Override
        public int compare(ResultItem t1, ResultItem t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };


    private void setInit1(String number) {
        mDBHelper = new DBHelper(this);

        mRv_result = findViewById(R.id.rv_result);
        mResultItems = new ArrayList<>();

        LoadRecentDB1(number);
    }

    private void setInit2(String name) {
        mDBHelper = new DBHelper(this);

        mRv_result = findViewById(R.id.rv_result);
        mResultItems = new ArrayList<>();

        LoadRecentDB2(name);
    }

    private void setInit3(String name, String number) {
        mDBHelper = new DBHelper(this);

        mRv_result = findViewById(R.id.rv_result);
        mResultItems = new ArrayList<>();

        LoadRecentDB3(name, number);
    }

    private void LoadRecentDB1(String number) {
        // ?????? ???????????? ???????????? ?????????
        mResultItems = mDBHelper.getResultList1(number);
        Collections.sort(mResultItems, Compare);
        mAdapter = new CustomAdapter2(mResultItems, this);
        mRv_result.setHasFixedSize(true);
        mRv_result.setAdapter(mAdapter);
    }

    private void LoadRecentDB2(String name) {
        mResultItems = mDBHelper.getResultList2(name);
        Collections.sort(mResultItems, Compare);
        mAdapter = new CustomAdapter2(mResultItems, this);
        mRv_result.setHasFixedSize(true);
        mRv_result.setAdapter(mAdapter);
    }

    private void LoadRecentDB3(String name, String number) {
        mResultItems = mDBHelper.getResultList3(name, number);
        Collections.sort(mResultItems, Compare);
        mAdapter = new CustomAdapter2(mResultItems, this);
        mRv_result.setHasFixedSize(true);
        mRv_result.setAdapter(mAdapter);
    }
}
