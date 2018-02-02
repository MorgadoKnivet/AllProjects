package morgado.mdsoftware.megafone;

import android.widget.EditText;

/**
 * Created by MoorG on 30/11/2017.
 */

public class Usuario {

    private String nome, identidade, email, idade;


    public Usuario(String nome, String identidade, String email, String idade) {
        this.nome = nome;
        this.identidade = identidade;
        this.email = email;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }
}
