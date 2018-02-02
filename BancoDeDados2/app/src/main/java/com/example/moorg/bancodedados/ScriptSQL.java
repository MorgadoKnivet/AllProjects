package com.example.moorg.bancodedados;

/**
 * Created by MoorG on 06/05/2017.
 */

public class ScriptSQL {

        public static String getCreateTabela(){
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONTATO ( ");
            sqlBuilder.append("_id                INTEGER       NOT NULL ");
            sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("Nome VARCHAR (255), ");
            sqlBuilder.append(" ); ");

            return sqlBuilder.toString();

        }

}
