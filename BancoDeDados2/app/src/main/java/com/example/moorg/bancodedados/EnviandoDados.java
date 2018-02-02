package com.example.moorg.bancodedados;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/**
 * Created by MoorG on 06/05/2017.
 */

public class EnviandoDados extends Activity {
    Context x = null;
    FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(x);
    SQLiteDatabase db = mDbHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "teste_titulo");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "Teste_subTitulo");
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,null, values);

    }


}
