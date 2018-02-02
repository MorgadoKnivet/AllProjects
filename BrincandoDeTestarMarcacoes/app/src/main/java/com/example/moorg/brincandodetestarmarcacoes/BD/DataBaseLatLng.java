package com.example.moorg.brincandodetestarmarcacoes.BD;

/**
 * Created by MoorG on 11/05/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Paulo on 28/03/2015.
 */

import android.content.Context;
import android.database.sqlite.*;

public class DataBaseLatLng extends SQLiteOpenHelper {

    public DataBaseLatLng(Context context)
    {
        super(context, "DBLatLng", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptSQLLatLng.getCreateDataBase());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ;
    }

}
