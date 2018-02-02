package morgado.mdsoftware.monitoriacefet.Universidade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.RegistrarMonitoria;
import morgado.mdsoftware.monitoriacefet.R;

public class AtualizarMonitoriaFaculdade extends AppCompatActivity {

    EditText materia, dado1, dado2, dado3, dado4, dado5;
    String auxNome;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_monitoria_faculdade);
        mAuth = FirebaseAuth.getInstance();
        materia = (EditText)findViewById(R.id.materiaATTFaculdade);
        dado1 = (EditText)findViewById(R.id.dado1ATTFaculdade);
        dado2 = (EditText)findViewById(R.id.dado2ATTFaculdade);
        dado3 = (EditText)findViewById(R.id.dado3ATTFaculdade);
        dado4 = (EditText)findViewById(R.id.dado4ATTFaculdade);
        dado5 = (EditText)findViewById(R.id.dado5ATTFaculdade);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                auxNome =  params.getString("Nome").trim();
                String auxMateria =params.getString("Materia").trim();
                String auxDado1 = params.getString("dado1").trim();
                String auxDado2 = params.getString("dado2").trim();
                String auxDado3 = params.getString("dado3").trim();
                String auxDado4 = params.getString("dado4").trim();
                String auxDado5 = params.getString("dado5").trim();

                materia.setText(auxMateria);
                dado1.setText(auxDado1);
                dado2.setText(auxDado2);
                dado3.setText(auxDado3);
                dado4.setText(auxDado4);
                dado5.setText(auxDado5);

            }
        }





        Button atualizar = (Button)findViewById(R.id.butAtualizarATTFaculdade);
        Button excluir = (Button)findViewById(R.id.butExcluirATTFaculdade);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materia = (EditText)findViewById(R.id.materiaATTFaculdade);
                dado1 = (EditText)findViewById(R.id.dado1ATTFaculdade);
                dado2 = (EditText)findViewById(R.id.dado2ATTFaculdade);
                dado3 = (EditText)findViewById(R.id.dado3ATTFaculdade);
                dado4 = (EditText)findViewById(R.id.dado4ATTFaculdade);
                dado5 = (EditText)findViewById(R.id.dado5ATTFaculdade);

                atualizar();
                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                finish();
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
                startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                finish();
            }
        });

    }

    public void atualizar(){

        String auxMateria = materia.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();


        if (TextUtils.isEmpty(auxMateria)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }
/*
        if (!((dado1.getText().toString().equals(""))&&(dado2.getText().toString().equals(""))
                &&(dado3.getText().toString().equals(""))&&(dado4.getText().toString().equals(""))
                && (dado5.getText().toString().equals("")))){

            Toast.makeText(this, "Ops! Você esqueceu das informações", Toast.LENGTH_SHORT).show();
        }
        */

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(auxMateria).child(auxNome.toUpperCase()).child("Dados");

        Monitoria monitoria = new Monitoria();

        monitoria.setMateria(auxMateria);
        monitoria.setMonitor(auxNome);
        monitoria.setZdado1(dado1.getText().toString());
        monitoria.setZdado2(dado2.getText().toString());
        monitoria.setZdado3(dado3.getText().toString());
        monitoria.setZdado4(dado4.getText().toString());
        monitoria.setZdado5(dado5.getText().toString());
        monitoria.setZmMonitorID(mAuth.getCurrentUser().getUid());


        regMonitoria.setValue(monitoria);
        Toast.makeText(this, "Monitoria atualizada com sucesso", Toast.LENGTH_SHORT).show();



    }

    public void excluir(){
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(materia.getText().toString()).child(auxNome.toUpperCase()).child("Dados");
        Monitoria monitoria = new Monitoria();
        regMonitoria.setValue(monitoria);
        Toast.makeText(this, "Monitoria excluida com sucesso", Toast.LENGTH_SHORT).show();


    }

}
