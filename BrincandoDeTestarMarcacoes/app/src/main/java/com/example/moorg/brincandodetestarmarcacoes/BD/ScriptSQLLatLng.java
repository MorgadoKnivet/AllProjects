package com.example.moorg.brincandodetestarmarcacoes.BD;

/**
 * Created by MoorG on 11/05/2017.
 */

public class ScriptSQLLatLng {
    public static String getCreateDataBase()
    {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS LatLng ( ");
        sqlBuilder.append("_id INTEGER NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Lat             VARCHAR (200) ");
        sqlBuilder.append("Lng             VARCHAR (200) ");
        sqlBuilder.append("); ");





        return sqlBuilder.toString();

    }
}
