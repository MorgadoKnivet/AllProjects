package development.rodrigo.fastbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void irParaLogin(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));

    }

    public void irParaCadastro(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrarUsuario.class));

    }

}