package com.example.sqlplease;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Search_result extends AppCompatActivity {

    private RecyclerView mRv_result;
    private ArrayList<ResultItem> mResultItems;
    private DBHelper mDBHelper;
    private CustomAdapter2 mAdapter;

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
    }

    private void setInit2(String name) {
        mDBHelper = new DBHelper(this);

        mRv_result = findViewById(R.id.rv_result);
        mResultItems = new ArrayList<>();

        LoadRecentDB2(name);
    }

    private void setInit1(String number) {
        mDBHelper = new DBHelper(this);

        mRv_result = findViewById(R.id.rv_result);
        mResultItems = new ArrayList<>();

        LoadRecentDB1(number);
    }

    private void LoadRecentDB1(String number) {
        // 저장 되어있던 데이터를 가져옴
        mResultItems = mDBHelper.getResultList1(number);
        mAdapter = new CustomAdapter2(mResultItems, this);
        mRv_result.setHasFixedSize(true);
        mRv_result.setAdapter(mAdapter);
    }

    private void LoadRecentDB2(String name) {
        mResultItems = mDBHelper.getResultList2(name);
        mAdapter = new CustomAdapter2(mResultItems, this);
        mRv_result.setHasFixedSize(true);
        mRv_result.setAdapter(mAdapter);
    }

//    private void LoadRecentDB3(String name, String number) {
//        mResultItems = mDBHelper.
//    }
}
