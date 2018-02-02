package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;

/**
 * Created by MoorG on 07/07/2017.
 */

public class InserirDadosMonitoria extends AppCompatActivity {

    EditText auxLocal, auxHorario, auxMonitor, auxAno, auxMateria, auxDias, auxTurno, auxMonitorSobrenome, auxObs;
    Button auxRegistrar;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dados_monitoria);
        MobileAds.initialize(InserirDadosMonitoria.this,"ca-app-pub-7164742797981159~2228765994" );


      //  mInterstitialAd = new InterstitialAd(this);
      //  mInterstitialAd.setAdUnitId("ca-app-pub-7164742797981159/2809814954");
     //   mInterstitialAd.loadAd(new AdRequest.Builder().build());




        AdView adView = (AdView)findViewById(R.id.adViewCadastro);
       // AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
          AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        progressDialog = new ProgressDialog(this);
        auxRegistrar = (Button)findViewById(R.id.Registrar);


        auxMonitor = (EditText)findViewById(R.id.Monitor);
        auxMonitorSobrenome = (EditText)findViewById(R.id.sobrenomeMonitor);
        auxLocal = (EditText)findViewById(R.id.Local);
        auxHorario = (EditText)findViewById(R.id.Horario);
        auxAno = (EditText)findViewById(R.id.ano);
        auxMateria = (EditText)findViewById(R.id.Materia);
        auxDias = (EditText)findViewById(R.id.dias);
        auxObs = (EditText)findViewById(R.id.observ);


        auxRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auxMonitor = (EditText)findViewById(R.id.Monitor);
                auxMonitorSobrenome = (EditText)findViewById(R.id.sobrenomeMonitor);
                auxLocal = (EditText)findViewById(R.id.Local);
                auxHorario = (EditText)findViewById(R.id.Horario);
                auxAno = (EditText)findViewById(R.id.ano);
                auxMateria = (EditText)findViewById(R.id.Materia);
                auxDias = (EditText)findViewById(R.id.dias);
                auxTurno = (EditText)findViewById(R.id.TurnoP);
                auxObs = (EditText)findViewById(R.id.observ);
                try {
                    registrar();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });



    }


    public void registrar() throws InterruptedException {


        String auxLocalR, auxHorarioR, auxMonitorR, auxAnoR, auxMateriaR, auxDiasR, auxTurnoR, auxObservacao;


        auxMonitorR = auxMonitor.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                //      .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .trim();

        String sobrenomeL = auxMonitorSobrenome.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                .trim();

        auxMateriaR = auxMateria.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                .trim();

        auxAnoR = auxAno.getText().toString().toUpperCase()
                .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .replace("ANO","")
                .replace("/","")
                .trim();

        auxTurnoR = auxTurno.getText().toString().toUpperCase().replace("Ã","A")
                .trim();

        auxDiasR = auxDias.getText().toString()
                .trim();

        auxHorarioR = auxHorario.getText().toString()
                .trim();

        auxLocalR = auxLocal.getText().toString()
                .trim();

        auxObservacao = auxObs.getText().toString()
                .trim();



        if (TextUtils.isEmpty(auxMonitorR)) {
            Toast.makeText(this, "Escreva o nome do Monitor.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(sobrenomeL)) {
            Toast.makeText(this, "Esqueceu o sobrenome do monitor.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxMateriaR)) {
            Toast.makeText(this, "Escreva a matéria da monitoria.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxAnoR)) {
            Toast.makeText(this, "Escreva o ano da monitoria.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxDiasR)) {
            Toast.makeText(this, "Escreva o(s) dia(s) da monitoria.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxHorarioR)) {
            Toast.makeText(this, "Escreva o horário.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxLocalR)) {
            Toast.makeText(this, "Escreva o local.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxTurnoR)){
            Toast.makeText(this, "Escreva o turno.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(auxAnoR.equals("PRIMEIRO") || auxAnoR.equals("SEGUNDO") || auxAnoR.equals("TERCEIRO") || auxAnoR.equals("QUARTO"))){
            Toast.makeText(this, "Escreva somente um ano.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(auxTurnoR.equals("MANHA") || auxTurnoR.equals("TARDE"))) {
            Toast.makeText(this, "Turno: Manhã ou tarde.", Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();



        progressDialog.setMessage("Cadastrando monitoria...");
        progressDialog.show();

        /*          TESTE OBJETO */


        Monitoria monitoria = new Monitoria();


        monitoria.setMonitor(auxMonitorR);
        monitoria.setSobrenome(sobrenomeL);
        monitoria.setMateria(auxMateriaR);
        monitoria.setAno(auxAnoR);
        monitoria.setTurno(auxTurnoR);
        monitoria.setDia(auxDiasR);
        monitoria.setHorario(auxHorarioR);
        monitoria.setLocal(auxLocalR);
        monitoria.setObservação(auxObservacao);



         /*          TESTE OBJETO  fim */


        database = FirebaseDatabase.getInstance();

       regMonitoria = database.getReference().child("Monitorias").child(auxMateriaR)
             .child(auxAnoR).child(auxTurnoR).child(auxMonitorR +" "+ sobrenomeL).child("Dados");

        regMonitoria.setValue(monitoria);

        Toast.makeText(InserirDadosMonitoria.this,"Registro concluído",Toast.LENGTH_SHORT).show();

        finish();

        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

    }




    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }





}
