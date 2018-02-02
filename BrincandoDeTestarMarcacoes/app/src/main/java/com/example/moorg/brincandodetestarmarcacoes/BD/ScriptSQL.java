package com.example.moorg.brincandodetestarmarcacoes.BD;

/**
 * Created by Paulo on 28/03/2015.
 */
public class ScriptSQL {


       public static String getCreateDataBase()
       {

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" CREATE TABLE IF NOT EXISTS Dados ( ");
            sqlBuilder.append("_id                INTEGER       NOT NULL ");
            sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
           sqlBuilder.append("Nome               VARCHAR (200) ");
           sqlBuilder.append("); ");
       /*    sqlBuilder.append(" CREATE TABLE IF NOT EXISTS LatLng ( ");
            sqlBuilder.append("_id INTEGER NOT NULL");
            sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
            sqlBuilder.append("Lat             DOUBLE ");
            sqlBuilder.append("Lng             DOUBLE "); */
         //   sqlBuilder.append("); ");





            return sqlBuilder.toString();

       }





}
