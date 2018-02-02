package com.example.moorg.bancodedados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by MoorG on 06/05/2017.
 */

public class DataBase  extends SQLiteOpenHelper{

    public DataBase(Context context) {
        super(context,"NomeDoBanco", null, /* versão do BD */1 );
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("SQL_DELETE_ENTRIES");
        onCreate(db);
    }

}

/* criar na classe do dado uma váriavel do tipo DataBase
SQLiteDataBase comn;
tRY{
    DataBase dataBase = new DataBase(this);


    comn = database.getReadableDataBASE();
    AlertDialog.Builder dlg = new  AlertDialog.Builder(this);
    dlg.setMessage(Conexão criada com sucesso);
    dlg.setNeutralButton("Ok", null):
    dlg.show();
   }CATCH(SQLException ex){
   AlertDialog.Builder dlg = new  AlertDialog.Builder(this);
    dlg.setMessage("Erro ao criar ");
    dlg.setNeutralButton("Ok", null):
    dlg.show();
   }


 */