package morgado.mdsoftware.monitoriacefet_teste.Inicial_cadastro_inicial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        }


    public void irParaLogin(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),LoginActivity.class);
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
