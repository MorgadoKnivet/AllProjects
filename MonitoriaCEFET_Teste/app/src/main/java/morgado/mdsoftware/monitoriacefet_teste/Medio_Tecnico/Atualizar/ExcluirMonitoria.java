package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;

public class ExcluirMonitoria extends AppCompatActivity {
    String nome, sobrenome, materia, ano, turno;
    TextView nomeT, sobrenomeT, materiaT, anoT, turnoT;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_monitoria);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                nome =  params.getString("Nome").trim();
                sobrenome = params.getString("Sobrenome").trim();
                materia =params.getString("Materia").trim();
                ano =  params.getString("Ano").trim();
                turno = params.getString("Turno").trim();


            }
        }


        nomeT = (TextView)findViewById(R.id.nomeED);
        materiaT = (TextView)findViewById(R.id.materiaED);
        anoT = (TextView)findViewById(R.id.anoED);
        turnoT = (TextView)findViewById(R.id.turnoED) ;

        nomeT.setText(nome + " "+sobrenome);
        materiaT.setText(materia);
        anoT.setText(ano);
        turnoT.setText(turno);

        progressDialog = new ProgressDialog(this);

        Button excluir = (Button)findViewById(R.id.excluirED);

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
            }
        });
    }

    public void excluir(){

        progressDialog.setMessage("Excluindo...");
        progressDialog.show();

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(turno).child(nome + " "+sobrenome).child("Dados");
        regMonitoria.setValue("");


        Toast.makeText(ExcluirMonitoria.this,"Atualização concluída",Toast.LENGTH_SHORT).show();
        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

    }

}
