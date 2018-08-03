package com.example.hlarbi.app3.MainClasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData( String name, String numbers){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO RUNNING VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, numbers);


        statement.executeInsert();
    }

    public void insertDataAqi( String aqiname, String aqinumbers){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO AQITABLE VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, aqiname);
        statement.bindString(2, aqinumbers);


        statement.executeInsert();
    }

    public void insertDataOauth( String oauthname, String oauthnumber){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO OAUTHTABLE VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, oauthname);
        statement.bindString(2, oauthnumber);


        statement.executeInsert();
    }

    public void insertDataPollu( String fullnamepollu, String polluname, String pollunumber){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO POLLUTABLE VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, fullnamepollu);
        statement.bindString(2, polluname);
        statement.bindString(3, pollunumber);


        statement.executeInsert();
    }




    public void insertDataStat( String statname, String statnumber){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO STATTABLE VALUES (NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, statname);
        statement.bindString(2, statnumber);
        statement.executeInsert();
    }

    public void insertDataGoal( String goalname, String goalnumber){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO GOALTABLE VALUES (NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, goalname);
        statement.bindString(2, goalnumber);
        statement.executeInsert();
    }


  /*  public void updateData(String numbers, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE RUNNING SET numbers = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1,numbers);
        statement.bindDouble(3, (double)id);
        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM RUNNING WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }*/

    public int getProfilesCount() {
        String countQuery = "SELECT * FROM RUNNING";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int getProfilesCountPollu() {
        String countQuery = "SELECT * FROM POLLUTABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
