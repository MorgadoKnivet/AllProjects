package com.example.moorg.brincandodetestarmarcacoes.BD;

/**
 * Created by Paulo on 28/03/2015.
 */

import android.content.Context;
import android.database.sqlite.*;

import static android.os.FileObserver.CREATE;

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context)
    {
        super(context, "AGENDA", null, 19);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Dados (id INTEGER PRIMARY KEY, nome VARCHAR(250), lat VARCHAR(20), lng VARCHAR(20))");// db.execSQL(ScriptSQLLatLng.getCreateDataBase());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
      // db.execSQL("DROP TABLE Dados");

        db.execSQL("CREATE TABLE IF NOT EXISTS Dados (id INTEGER PRIMARY KEY, nome VARCHAR(250), lat VARCHAR(20), lng VARCHAR(20))");
       //     db.execSQL("ALTER TABLE Dados ADD COLUMN Lat_d REAL");
         //   db.execSQL("ALTER TABLE Dados ADD COLUMN Lng_d REAL");

        // Create tables again
        // onCreate(db);
    }

}
