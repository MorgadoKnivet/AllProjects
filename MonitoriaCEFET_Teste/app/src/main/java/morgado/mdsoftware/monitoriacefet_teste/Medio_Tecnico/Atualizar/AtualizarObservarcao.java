package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.Atualizar;

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

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;

public class AtualizarObservarcao extends AppCompatActivity {
    String nome, sobrenome, materia, ano, turno, horario, local, obs, dia;
    EditText auxObs;
    ArrayList auxDetalhes;
    Monitoria monitoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_observarcao);
        MobileAds.initialize(AtualizarObservarcao.this, "ca-app-pub-7164742797981159~2228765994");
        AdView adView = (AdView) findViewById(R.id.adViewObs);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        /*   params.putString("Nome", ""+auxRegistro.get(5) );
                    params.putString("Sobrenome",""+auxRegistro.get(6));
                    params.putString("Dias", "" + auxRegistro.get(1));
                    params.putString("Horario", "" + auxRegistro.get(2));
                    params.putString("Local", "" + auxRegistro.get(3));
                    params.putString("Materia", "" + auxRegistro.get(4));
                    params.putString("Ano", "" + auxRegistro.get(0));
                    params.putString("Obs",""+auxRegistro.get(7));
                    params.putString("Turno", "" + auxRegistro.get(8));
        */



        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {

                nome =  params.getString("Nome").trim();
                sobrenome = params.getString("Sobrenome").trim();
                materia = params.getString("Materia").trim();
                ano =  params.getString("Ano").trim();
                turno = params.getString("Turno").trim();
                horario = params.getString("Horario").trim();
                local = params.getString("Local").trim();
                obs = params.getString("Obs").trim();
                dia = params.getString("Dias").trim();

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

        final DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(turno).child(nome+" "+sobrenome).child("Dados");

        auxDetalhes = new ArrayList();

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    auxDetalhes.add(aux);

                }
                try {

                    monitoria.setMonitor(nome);
                    monitoria.setSobrenome(sobrenome);
                    monitoria.setMateria(materia);
                    monitoria.setAno(ano);
                    monitoria.setTurno(turno);
                    monitoria.setDia(""+ auxDetalhes.get(1));
                    monitoria.setHorario( ""+ auxDetalhes.get(2));
                    monitoria.setLocal( ""+ auxDetalhes.get(3));
                    monitoria.setObservação(obs);

                    regDetalhes.setValue(monitoria);


                }catch (IndexOutOfBoundsException i){
                    Toast.makeText(AtualizarObservarcao.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AtualizarObservarcao.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Toast.makeText(AtualizarObservarcao.this,"Atualização concluída",Toast.LENGTH_SHORT).show();
        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
    }
}
