package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;

public class AtualizarObservarcao extends AppCompatActivity {
    String nome, sobrenome, materia, ano, turno, horario, local, obs, dia,  auxDado1, auxDado2, auxDado3, auxDado4, auxDado5;;
    EditText auxObs;
    ArrayList auxDetalhes;
    Monitoria monitoria;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    Set set = new HashSet();
    Map<String, Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_observarcao);
        MobileAds.initialize(AtualizarObservarcao.this, "ca-app-pub-7164742797981159~2228765994");
        AdView adView = (AdView) findViewById(R.id.adViewObs);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {

                nome =  params.getString("Nome").trim();
                materia = params.getString("Materia").trim();
                ano =  params.getString("Ano").trim();
                obs = params.getString("Obs").trim();
                auxDado1  = params.getString("dado1").trim();
                auxDado2  = params.getString("dado2").trim();
                auxDado3  = params.getString("dado3").trim();
                auxDado4  = params.getString("dado4").trim();
                auxDado5 = params.getString("dado5").trim();

            }
        }

        auxObs = (EditText)findViewById(R.id.observacaoATT);
        auxObs.setText(obs);

        Button atualizar = (Button)findViewById(R.id.atualizarBut);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxObs = (EditText)findViewById(R.id.observacaoATT);
                atualizarObs();
            }
        });
    }

    public void atualizarObs(){
        obs = auxObs.getText().toString();
       monitoria = new Monitoria();

        final DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(nome).child("Dados");

        progressDialog.setMessage("Atualizando...");
        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia);

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    map = (Map<String, Object>) dataSnapshot.getValue();

                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                 //   Toast.makeText(AtualizarDados.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                }

                for (Object i : set) {

                    String palavra = (String) i;
                    attAno(palavra, auxDado1, auxDado2, auxDado3, auxDado4, auxDado5, obs);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();
        Toast.makeText(AtualizarObservarcao.this,"Atualização concluída",Toast.LENGTH_SHORT).show();
        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
    }

    public void attAno(final String anoL, final String dado1L, final String dado2L, final String dado3L, final String dado4L, final String dado5L, final String obsL) {
        regMonitoria = database.getReference().child("Monitorias").child(materia).child(anoL).child(nome).child("Dados");

        monitoria.setAno(anoL);
        monitoria.setMonitor(nome);
        monitoria.setMateria(materia);
        monitoria.setZdado1(dado1L);
        monitoria.setZdado2(dado2L);
        monitoria.setZdado3(dado3L);
        monitoria.setZdado4(dado4L);
        monitoria.setZdado5(dado5L);
        monitoria.setObservação(obsL);

        regMonitoria.setValue(monitoria);

    }
}
