package com.example.offlinedatabase.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {     //1 Time Alt+Enter  //2 Time Alt+Enter But Not All Select Only One Select

    //In SqliteHelper (Class):-extends SQLiteOpenHelper {...}

    public SqliteHelper(@Nullable Context context) {

        super(context, "mydb", null, 1);   //"mydb", null, 1
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String str = "create table contactbook (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,contact,TXET)";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String name, String contact) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String qry = "insert into contactbook (name,contact) values ('"+name+"','"+contact+"')";

        sqLiteDatabase.execSQL(qry);
    }


    public Cursor getAllData() {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String qry = "select * from contactbook";

        Cursor cursor = sqLiteDatabase.rawQuery(qry,null);

        return cursor;

    }

    public void deleteData(int userid) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String qry = "delete from contactbook where id = '"+userid+"'";

        sqLiteDatabase.execSQL(qry);
    }

    public void updateData(int userid,String newname, String newcontact) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String qry = "update contactbook set name='"+newname+"', contact='"+newcontact+"' where id='"+userid+"'";

        sqLiteDatabase.execSQL(qry);

    }
}
