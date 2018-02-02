package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.BuscaMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.ListaPersonalizada;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;

public class AtualizarDados extends AppCompatActivity {
    EditText auxObs, auxDado1, auxDado2, auxDado3, auxDado4, auxDado5;
    String nome, sobrenome, materia, ano, turno;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    Set set = new HashSet();
    Map<String, Object> map;
    Monitoria monitoria = new Monitoria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);
        MobileAds.initialize(AtualizarDados.this, "ca-app-pub-7164742797981159~2228765994");

        AdView adView = (AdView) findViewById(R.id.adViewDados);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        auxDado1 = (EditText)findViewById(R.id.dado1ATT);
        auxDado2 = (EditText)findViewById(R.id.dado2ATT);
        auxDado3 = (EditText)findViewById(R.id.dado3ATT);
        auxDado4 = (EditText)findViewById(R.id.dado4ATT);
        auxDado5 = (EditText)findViewById(R.id.dado5ATT);


        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                 nome =  params.getString("Nome").trim();
                materia =params.getString("Materia").trim();
                ano =  params.getString("Ano").trim();
             //   turno = params.getString("Turno").trim();


            }
        }

        progressDialog = new ProgressDialog(this);

        final Button atualizar = (Button)findViewById(R.id.buttonAtualizarDados);


        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxDado1 = (EditText)findViewById(R.id.dado1ATT);
                auxDado2 = (EditText)findViewById(R.id.dado2ATT);
                auxDado3 = (EditText)findViewById(R.id.dado3ATT);
                auxDado4 = (EditText)findViewById(R.id.dado4ATT);
                auxDado5 = (EditText)findViewById(R.id.dado5ATT);


                AtualizarDados();

            }
        });



    }

    public void AtualizarDados(){

     final   String dado1 = auxDado1.getText().toString().trim();
      final   String dado2 = auxDado2.getText().toString().trim();
     final   String dado3 = auxDado3.getText().toString().trim();
     final    String dado4 = auxDado4.getText().toString().trim();

     final   String dado5 = auxDado5.getText().toString().trim();



        if (TextUtils.isEmpty(dado1)) {
            Toast.makeText(this, "Escreva os dados da monitoria", Toast.LENGTH_LONG).show();
            return;
        }


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
               //     Toast.makeText(AtualizarDados.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                }

                for (Object i : set) {

                    String palavra = (String) i;
                    attAno(palavra, dado1, dado2, dado3, dado4, dado5);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();
        Toast.makeText(AtualizarDados.this,"Atualização concluída",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), MenuAtualizarMonitoria.class));
    }

    public void attAno(final String anoL, final String dado1L, final String dado2L, final String dado3L, final String dado4L, final String dado5L) {
        regMonitoria = database.getReference().child("Monitorias").child(materia).child(anoL).child(nome).child("Dados");

        monitoria.setAno(anoL);
        monitoria.setMonitor(nome);
        monitoria.setMateria(materia);
        monitoria.setZdado1(dado1L);
        monitoria.setZdado2(dado2L);
        monitoria.setZdado3(dado3L);
        monitoria.setZdado4(dado4L);
        monitoria.setZdado5(dado5L);


        regMonitoria.setValue(monitoria);

    }




}
