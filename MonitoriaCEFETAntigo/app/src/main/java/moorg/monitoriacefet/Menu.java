package moorg.monitoriacefet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        }


    public void irParaLogin(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),LoginActivity.class);
        // aux.setClass(this,Calculadora.class);
        startActivity(intentAux1);

    }

    public void irParaCadastro(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),NewUserActivity.class);
        startActivity(intentAux1);

    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

}
