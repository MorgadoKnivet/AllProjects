package mdsoftware.development.calcumath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class matematica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matematica);

    }

    public void voltar(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    public void irParaDetalhes(View view){
        Intent intent = new Intent(this, bhaskaraprincipal.class);
        startActivity(intent);
    }

    public void bhaskara(View view) {
        Intent intent = new Intent(this, bhaskaraprincipal.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), Menu.class));
    }
}



