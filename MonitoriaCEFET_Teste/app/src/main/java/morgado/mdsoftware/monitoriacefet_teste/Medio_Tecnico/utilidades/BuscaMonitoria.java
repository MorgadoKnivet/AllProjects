package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;

/**
 * Created by MoorG on 11/07/2017.
 */

public class BuscaMonitoria extends AppCompatActivity {

    EditText auxEnviarMateria, auxEnviarAno, auxEnviarTurno;
    Button auxEnviarPesquisa;
    ListView lv;

    Monitoria monitoria = new Monitoria();
    ListaPersonalizada listaPersonalizada;
    List auxRegistro = new ArrayList();

    ArrayList auxDetalhes;
    Set set = new HashSet();
    Map<String, Object> map;
    String x;
    private FirebaseAuth mAuth;
    private InterstitialAd mInterstitialAd;
    AdView adView;
    AdRequest adRequest;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        MobileAds.initialize(BuscaMonitoria.this,"ca-app-pub-7164742797981159~2228765994" );


        adView = (AdView)findViewById(R.id.adViewBusca);
       // AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
         adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        auxEnviarPesquisa = (Button) findViewById(R.id.Titulo);



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7869198683351919/7714393949");
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAd.loadAd(request);

        /*
        auxResultOne = (TextView) findViewById(R.id.ResultOne);
        auxResultTwo = (TextView) findViewById(R.id.ResultTwo);
        auxResultThree = (TextView) findViewById(R.id.ResultThree);
        auxResultFour = (TextView) findViewById(R.id.ResultFour);
*/

        // auxResultThree.setText("ahhhhh");


        auxEnviarPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          //    lv.setVisibility(View.);
                auxRegistro.clear();
                auxEnviarMateria = (EditText) findViewById(R.id.enviarMateria);
                auxEnviarAno = (EditText) findViewById(R.id.enviarAno);
                auxEnviarTurno = (EditText) findViewById(R.id.TurnoP);


                try {

                    LerMateria();


                    //  LerDetalhes();
                } catch (DatabaseException d) {
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    adView.setVisibility(View.VISIBLE);
                } catch (IndexOutOfBoundsException i) {
                    Log.i("SCRIPTERRO", "Erro do arrayList");
                    adView.setVisibility(View.VISIBLE);
                } catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    adView.setVisibility(View.VISIBLE);
                }




                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarMateria.getWindowToken(), 0);

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarAno.getWindowToken(), 0);

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarTurno.getWindowToken(), 0);

            }
        });


    }



    public void LerMateria(){

        final String materiaL = auxEnviarMateria.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();
        final String anoL = auxEnviarAno.getText().toString().toUpperCase()
                .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .replace("ANO","")
                .trim();
        final String turnoL = auxEnviarTurno.getText().toString().toUpperCase()
                .replace("Ã","A")
                .trim();

        if (TextUtils.isEmpty(materiaL)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(anoL)) {
            Toast.makeText(this, "Escreva seu ano", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(turnoL)) {
            Toast.makeText(this, "Escreva o turno da monitoria", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(anoL.equals("PRIMEIRO") || anoL.equals("SEGUNDO") || anoL.equals("TERCEIRO") || anoL.equals("QUARTO"))){
            Toast.makeText(this, "Escreva somente um ano", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(turnoL.equals("MANHA") || turnoL.equals("TARDE"))) {
            Toast.makeText(this, "Turno: Manhã ou tarde", Toast.LENGTH_LONG).show();
        }


        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias");

        //    Log.i("SCRIPT_TURNO",turnoL);

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator
                    map = (Map<String, Object>) dataSnapshot.getValue();


                }
                try {
                    set = map.keySet();

                }catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    adView.setVisibility(View.VISIBLE);
                }
                int cont = 0;


                for (Object i : set) {

                    String palavra = (String) i;
                    if (palavra.contains(materiaL)){
                        LerDados(palavra,anoL,turnoL);
                    }else {
                        cont = cont + 1;
                    }

                    if (cont == set.size()){
                        Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


                Log.i("SCRIPT", "Entrou no ERRO");
            }


        });
    }

    public void LerDados(final String materiaL, final String anoL, final String turnoL) {

         // materia, ano, turno


    //    String texto = "Hoje o Sol está forte";
     //   String procurarPor = "sol";
     //   System.out.println(texto.toLowerCase().contains(procurarPor.toLowerCase()));

 /* correção de erro ao eviar nenhum valor para a tela */


        adView.setVisibility(View.GONE);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materiaL).child(anoL).child(turnoL);

    //    Log.i("SCRIPT_TURNO",turnoL);

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator
                    map = (Map<String, Object>) dataSnapshot.getValue();


                }
            try {
             set = map.keySet();
                }catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                }



                for (Object i : set) {

                    String palavra = (String) i;


                    //Log.i("SCRIPT_NomeMonitores", "" + monitoria.getMonitor() );

                    LerDetalhes(palavra,materiaL,anoL,turnoL);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                Log.i("SCRIPT", "Entrou no ERRO");
            }

        });



    }

    public void LerDetalhes(final String nome, String materia, String ano, String turno){


        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(turno).child(nome).child("Dados");

            auxDetalhes = new ArrayList();

            regDetalhes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        String aux = d.getValue(String.class);


                        auxDetalhes.add(aux);

                    }

                    try {
                         monitoria.setMonitor("Monitor: " + nome);
                         monitoria.setZdado1(""+auxDetalhes.get(5));
                         monitoria.setZdado2(""+auxDetalhes.get(6));
                         monitoria.setZdado3(""+auxDetalhes.get(7));
                         monitoria.setZdado4(""+auxDetalhes.get(8));
                         monitoria.setZdado5(""+auxDetalhes.get(9));
                         monitoria.setObservação(""+auxDetalhes.get(3));




                        auxRegistro.add(monitoria);

                    }catch (IndexOutOfBoundsException i){
                        Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                        adView.setVisibility(View.VISIBLE);
                    }

                    listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaMonitoria.this);


                    // list = getGambiarra("erroabcde");

                    lv = (ListView) findViewById(R.id.lv);
                    lv.setAdapter(listaPersonalizada);

                    monitoria = new Monitoria();
                    auxDetalhes = new ArrayList();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("SCRIPT", "Entrou no ERRO");
                }


            });
     //   }

    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

    @Override
    public void onBackPressed()
    {
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

        regMonitoria.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    x = d.getValue(String.class);
                }
                if (x.equals("MONITOR")) {

                    finish();
                    startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                } else {

                    finish();
                    startActivity(new Intent(getApplicationContext(), menuAluno.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              //  Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}





