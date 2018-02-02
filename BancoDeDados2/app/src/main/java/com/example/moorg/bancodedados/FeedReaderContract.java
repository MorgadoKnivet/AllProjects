package com.example.moorg.bancodedados;

import android.provider.BaseColumns;

/**
 * Created by MoorG on 02/05/2017.
 */
// Uma classe de contrato é o contêiner das constantes que definem nomes para URIs, tabelas e colunas.
// A classe de contrato permite usar as mesmas constantes em outras classes no mesmo pacote. Isso permite
// que você altere o nome da coluna em um local e que a mudança se propague por todo o código.

public final class FeedReaderContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String COLUMN_NAME_TITLE = "title";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    private FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Table_Tetse";
        public static final String COLUMN_NAME_TITLE = "ColunaUm";
        public static final String COLUMN_NAME_SUBTITLE = " ColunaUm_SubTitulo";
    }
}



