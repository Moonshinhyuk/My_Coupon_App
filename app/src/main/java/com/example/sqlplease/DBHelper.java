package com.example.sqlplease;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성이 될 때 호출
        // 데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS CouponList (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, number TEXT NOT NULL, coupon1 TEXT NOT NULL, coupon2 TEXT NOT NULL);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public int getCouponCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList ORDER BY id DESC", null);
        int count = cursor.getCount();

        return count;
    }

    // SELECT 문 (쿠폰 목록 조회)
    public ArrayList<CouponItem> getCouponList() {
        ArrayList<CouponItem> couponItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList ORDER BY id DESC", null);
        if(cursor.getCount() != 0) {
            //조회 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
                String coupon1 = cursor.getString(cursor.getColumnIndexOrThrow("coupon1"));
                String coupon2 = cursor.getString(cursor.getColumnIndexOrThrow("coupon2"));

                CouponItem couponItem = new CouponItem();
                couponItem.setId(id);
                couponItem.setName(name);
                couponItem.setNumber(number);
                couponItem.setCoupon1(coupon1);
                couponItem.setCoupon2(coupon2);
                couponItems.add(couponItem);
            }
        }
        cursor.close();

        return couponItems;
    }

    public ArrayList<ResultItem> getResultList1(String Number) {
        ArrayList<ResultItem> resultItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList WHERE number='"+ Number +"'ORDER BY id DESC;", null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString((cursor.getColumnIndexOrThrow("name")));
                String number = cursor.getString((cursor.getColumnIndexOrThrow("number")));
                String coupon1 = cursor.getString((cursor.getColumnIndexOrThrow("coupon1")));
                String coupon2 = cursor.getString((cursor.getColumnIndexOrThrow("coupon2")));

                ResultItem resultItem = new ResultItem();
                resultItem.setId(id);
                resultItem.setName(name);
                resultItem.setNumber(number);
                resultItem.setCoupon1(coupon1);
                resultItem.setCoupon2(coupon2);
                resultItems.add(resultItem);
            }
        }
        cursor.close();

        return resultItems;
    }

    public ArrayList<ResultItem> getResultList2(String Name) {
        ArrayList<ResultItem> resultItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList WHERE name LIKE '%"+ Name +"%' ORDER BY id DESC;", null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString((cursor.getColumnIndexOrThrow("name")));
                String number = cursor.getString((cursor.getColumnIndexOrThrow("number")));
                String coupon1 = cursor.getString((cursor.getColumnIndexOrThrow("coupon1")));
                String coupon2 = cursor.getString((cursor.getColumnIndexOrThrow("coupon2")));

                ResultItem resultItem = new ResultItem();
                resultItem.setId(id);
                resultItem.setName(name);
                resultItem.setNumber(number);
                resultItem.setCoupon1(coupon1);
                resultItem.setCoupon2(coupon2);
                resultItems.add(resultItem);
            }
        }
        cursor.close();

        return resultItems;
    }

    public ArrayList<ResultItem> getResultList3(String Name, String Number) {
        ArrayList<ResultItem> resultItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList WHERE name='"+ Name +"' AND number='"+ Number +"'ORDER BY id DESC;", null);
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString((cursor.getColumnIndexOrThrow("name")));
                String number = cursor.getString((cursor.getColumnIndexOrThrow("number")));
                String coupon1 = cursor.getString((cursor.getColumnIndexOrThrow("coupon1")));
                String coupon2 = cursor.getString((cursor.getColumnIndexOrThrow("coupon2")));

                ResultItem resultItem = new ResultItem();
                resultItem.setId(id);
                resultItem.setName(name);
                resultItem.setNumber(number);
                resultItem.setCoupon1(coupon1);
                resultItem.setCoupon2(coupon2);
                resultItems.add(resultItem);
            }
        }
        cursor.close();

        return resultItems;
    }

    public List getCouponNum(String _name, String _number) {
        List items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT coupon1, coupon2 FROM CouponList WHERE name='"+ _name +"' AND number='"+ _number +"';", null);
        if(cursor.getCount() != 0) {
            //조회 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()) {
                String item1 = cursor.getString(cursor.getColumnIndexOrThrow("coupon1"));
                String item2 = cursor.getString(cursor.getColumnIndexOrThrow("coupon2"));

                items.add(item1);
                items.add(item2);
            }
        }
        cursor.close();

        return items;
    }

    public List<String> getCouponName() {
        List<String> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList ORDER BY id DESC", null);
        if(cursor.getCount() != 0) {
            //조회 데이터가 있을 때 내부 수행
            while(cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                names.add(name);
            }
        }
        cursor.close();

        return names;
    }


    // INSERT 문 (내용을 데이터베이스 테이블에 넣는다)
    public void InsertContent(String _name, String _number, String _coupon1, String _coupon2) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO CouponList (name, number, coupon1, coupon2) VALUES('" + _name + "', '" + _number + "', '" + _coupon1 + "', '" + _coupon2 + "');");
    }


    // UPDATE 문 (데이터베이스 테이블 내용을 수정한다)
    // 입력된 이름과 번호의 줄 내용을 수정가능 (WHERE 을 통해 입력 이름과 입력 번호를 찾음)
    public void UpdateContent(String _name, String _number, String _coupon1, String _coupon2, String insertName, String insertNumber) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CouponList SET name='" + _name + "', number='" + _number + "', coupon1='" + _coupon1 + "', coupon2='" + _coupon2 + "' WHERE name='" + insertName + "' AND number='" + insertNumber + "';");
    }

    public void UpdateCoupon(String _name, String _number, String _coupon1, String _coupon2, String insertName, String insertNumber) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CouponList SET name='" + _name + "', number='" + _number + "', coupon1='" + _coupon1 + "', coupon2='" + _coupon2 + "' WHERE name='" + insertName + "' AND number='" + insertNumber + "';");
    }


    // DELETE 문 (데이터베이스 테이블 내용을 삭제한다)
    // 입력된 이름과 번호의 줄 삭제
    public void DeleteContent(String insertName, String insertNumber) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CouponList " +
                "WHERE name='" + insertName + "' AND number='" + insertNumber + "';");
    }

    public boolean CheckContent(String _name, String _number) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CouponList WHERE name='"+ _name +"' AND number='"+ _number +"';", null);

        Boolean bool;

        if(cursor.getCount() != 0) {
            bool = true;
        }
        else {
            bool = false;
        }

        return bool;
    }

}