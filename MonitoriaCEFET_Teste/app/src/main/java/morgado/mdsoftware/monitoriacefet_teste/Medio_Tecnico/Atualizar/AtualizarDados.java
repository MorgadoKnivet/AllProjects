package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;

public class AtualizarDados extends AppCompatActivity {
    EditText auxLocal, auxHorario, auxMonitor, auxAno, auxMateria, auxDias, auxTurno, auxMonitorSobrenome, auxObs;
    String nome, sobrenome, materia, ano, turno;
    TextView nomeT, sobrenomeT, materiaT, anoT, turnoT;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);
        MobileAds.initialize(AtualizarDados.this, "ca-app-pub-7164742797981159~2228765994");

        AdView adView = (AdView) findViewById(R.id.adViewDados);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
     //   auxMonitor = (EditText)findViewById(R.id.nomeAD);
      //  auxMonitorSobrenome = (EditText)findViewById(R.id.sobrenomeAD);
        auxLocal = (EditText)findViewById(R.id.localAD);
        auxHorario = (EditText)findViewById(R.id.horarioAD);
       // auxAno = (EditText)findViewById(R.id.anoAD);
       // auxMateria = (EditText)findViewById(R.id.materiaAD);
        auxDias = (EditText)findViewById(R.id.diaAD);
        auxObs = (EditText)findViewById(R.id.observacaoAD);
        // auxTurno = (EditText)findViewById(R.id.turnoAD);


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



/*        auxAno.setText(ano);
        auxMonitor.setText(nome);
        auxMonitorSobrenome.setText(sobrenome);
        auxMateria.setText(materia);
        auxTurno.setText(turno);
*/
        progressDialog = new ProgressDialog(this);

        final Button atualizar = (Button)findViewById(R.id.buttonAtualizarDados);


        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxLocal = (EditText)findViewById(R.id.localAD);
                auxHorario = (EditText)findViewById(R.id.horarioAD);
                auxDias = (EditText)findViewById(R.id.diaAD);
                auxObs = (EditText)findViewById(R.id.observacaoAD);

                AtualizarDados();

            }
        });



    }

    public void AtualizarDados(){
       String auxObservacao = auxObs.getText().toString().trim();
        String auxDiasR = auxDias.getText().toString().trim();
        String auxLocalR = auxLocal.getText().toString().trim();
       String auxHorarioR = auxHorario.getText().toString().trim();


        if (TextUtils.isEmpty(auxDiasR)) {
            Toast.makeText(this, "Escreva o(s) dia(s) da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxHorarioR)) {
            Toast.makeText(this, "Escreva o horário", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxLocalR)) {
            Toast.makeText(this, "Escreva o local", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Atualizando...");
        progressDialog.show();
        Monitoria monitoria = new Monitoria();



        monitoria.setMonitor(nome);
        monitoria.setSobrenome(sobrenome);
        monitoria.setMateria(materia);
        monitoria.setAno(ano);
        monitoria.setTurno(turno);
        monitoria.setDia(auxDiasR);
        monitoria.setHorario(auxHorarioR);
        monitoria.setLocal(auxLocalR);
        monitoria.setObservação(auxObservacao);



        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia).child(ano).child(turno).child(nome +" "+ sobrenome).child("Dados");
        regMonitoria.setValue(monitoria);

        Toast.makeText(AtualizarDados.this,"Atualização concluída",Toast.LENGTH_SHORT).show();
        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
    }
}
