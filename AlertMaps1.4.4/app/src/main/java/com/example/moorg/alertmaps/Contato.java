package com.example.moorg.alertmaps;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Paulo on 11/04/2015.
 */
public class Contato implements Serializable{

    public static String TABELA = "Dados";
    public static String TABELAMARKER = "LatLng";

    public static String ID = "_id";
    public static String NOME = "NOME";
    public static String TELEFONE = "TELEFONE";
    public static String TIPOTELEFONE = "TIPOTELEFONE";
    public static String EMAIL = "EMAIL";
    public static String TIPOEMAIL = "TIPOEMAIL";
    public static String ENDERECO = "ENDERECO";
    public static String TIPOENDERECO = "TIPOENDERECO";
    public static String DATASESPECIAIS = "DATASESPECIAIS";
    public static String TIPODATASESPECIAIS = "TIPODATASESPECIAIS";
    public static String GRUPOS = "GRUPOS";

    private long id;
    private String nome;
    private String telefone;
    private String tipoTelefone;
    private String email;
    private String tipoEmail;
    private String endereco;
    private String tipoEndereco;
    private Date datasEspeciais;
    private String tipoDatasEspeciais;
    private String grupos;

    public Contato()
    {
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return nome + " " + telefone;
    }


}

