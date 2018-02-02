package morgado.mdsoftware.megafone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        TextView creditos = (TextView)findViewById(R.id.tvCreditosMenu);
        creditos.setText("Desenvolvido por M&D Software");
        Button cadastro = (Button)findViewById(R.id.butCadastroPrincipal);
        Button  login = (Button)findViewById(R.id.butLogPrincipal);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cadastro.class));
                finish();
            }
        });

    }

}
