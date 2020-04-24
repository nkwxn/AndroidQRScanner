package com.example.qrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "qrCodeDatas";
    private static final String TABLE_DATAS = "QRData";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "content";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DATAS_TABLE = "CREATE TABLE " + TABLE_DATAS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_DATAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAS);

        onCreate(sqLiteDatabase);
    }

    //koding untuk menambahkan data kedalam db setelah scam
    void addContent(QRContent qr){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, qr.getId());
        values.put(KEY_NAME, qr.getContents());

        //insert
        db.insert(TABLE_DATAS, null, values);
    }

    //method to delete all datas
    public void deleteContent(QRContent qrc){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATAS, "", null);
    }

    public List<QRContent> getAllContents() {
        List<QRContent> contentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DATAS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                QRContent qrc = new QRContent();
                qrc.setId(c.getString(0));
                qrc.setContents(c.getString(1));
                contentList.add(qrc);
            } while (c.moveToNext());
        }
        return contentList;
    }

    //method to view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATAS;
        Cursor c = db.rawQuery(query, null);

        return c;
    }
}
