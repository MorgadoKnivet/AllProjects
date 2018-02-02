package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.BuscaMonitoria;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Universidade.AtualizarMonitoriaBuscaFaculdade;

public class AtualizarMonitoria extends AppCompatActivity {
    public static final int CONSTANTE_ATUALIZAR_MONITORIA = 2;
    EditText nome, sobrenome, materia, ano, turno;
    Button auxBuscar;
    ArrayList auxRegistro;
    Set set = new HashSet();
    Map<String, Object> map;
    private ProgressDialog progressDialog;
    String[] opçoes = new String[]{"Seu ano","1° ano","2° ano","3° ano","4° ano"};
    Spinner sp;
    String resposta;

    AdView adView;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_monitoria);
        MobileAds.initialize(AtualizarMonitoria.this,"ca-app-pub-7164742797981159~2228765994" );


        adView = (AdView)findViewById(R.id.adViewAtualizar);
        // AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AtualizarMonitoria.this,R.layout.activity_personalizar_spinner,opçoes ){};
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        sp = (Spinner)findViewById(R.id.spinnerAtualizar);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resposta = opçoes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        auxBuscar = (Button) findViewById(R.id.procurarMonitoriaAM);

        nome = (EditText) findViewById(R.id.nomeAtt);

        materia = (EditText) findViewById(R.id.MateriaATT);
      //  ano = (EditText) findViewById(R.id.anoAtt);
       // turno = (EditText) findViewById(R.id.turnoAtt);

        progressDialog = new ProgressDialog(this);

        auxBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = (EditText) findViewById(R.id.nomeAtt);
                materia = (EditText) findViewById(R.id.MateriaATT);
         //       ano = (EditText) findViewById(R.id.anoAtt);
          //      turno = (EditText) findViewById(R.id.turnoAtt);

                try {

                    LerMateria();

                  //  atualizarMonitoria();


                }catch (DatabaseException d) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException i) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();

                } catch (NullPointerException n) {
                    Toast.makeText(AtualizarMonitoria.this, "Ops! Sua monitoria não foi encontrado", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void LerMateria(){

        final String nomeL = nome.getText().toString().toUpperCase().replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();

        final String materiaL = materia.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();

        final String ano = resposta
                .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
                .replace("°","")
                .replace("ano","")
                .trim();

        System.out.println(ano);

        if (TextUtils.isEmpty(materiaL)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

        if (resposta.equals("Seu ano")){
            Toast.makeText(this, "Selecione seu ano", Toast.LENGTH_LONG).show();
            return;
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
                    Toast.makeText(AtualizarMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                }
                int cont = 0;


                for (Object i : set) {

                    String palavra = (String) i;
                    if (palavra.contains(materiaL)){
                        Log.i("SCRIPT", palavra);
                        LerDados(palavra,ano, nomeL);
                    }else {
                        cont = cont + 1;
                    }

                    if (cont == set.size()){
                        Toast.makeText(AtualizarMonitoria.this, "Ops! Monitoria não encontrada", Toast.LENGTH_SHORT).show();
                        return;
                    }



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AtualizarMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


                Log.i("SCRIPT", "Entrou no ERRO");
            }


        });
    }

    public void LerDados(final String materiaL, final String anoL, final String nome) {

        adView.setVisibility(View.GONE);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materiaL).child(anoL).child(nome).child("Dados");
        //  DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child("MATEMATICA").child("PRIMEIROANO").child("MANHA").child("GUILHERME_MORGADO");

        auxRegistro = new ArrayList();

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String value = d.getValue(String.class);
                    Log.i("SCRIPT FOR", value);
                    auxRegistro.add(value);
                }
                try {


                    Bundle params = new Bundle();
                    params.putString("Ano", "" + auxRegistro.get(0));
                    params.putString("Materia", "" + auxRegistro.get(1));
                    params.putString("Nome", "" + auxRegistro.get(2));
                    params.putString("dado1", "" + auxRegistro.get(3));
                    params.putString("dado2", "" + auxRegistro.get(4));
                    params.putString("dado3", "" + auxRegistro.get(5));
                    params.putString("dado4", "" + auxRegistro.get(6));
                    params.putString("dado5", "" + auxRegistro.get(7));
                    Intent intent = new Intent(AtualizarMonitoria.this, MenuAtualizarMonitoria.class);
                    intent.putExtras(params);

                    startActivityForResult(intent, CONSTANTE_ATUALIZAR_MONITORIA);
                    finish();
                    return;

                    //   Log.i("Script", "" + nome + " " + auxRegistro.get(1) + " " + auxRegistro.get(2) + " " + auxRegistro.get(3));
                } catch (IndexOutOfBoundsException i) {
                    Log.i("Erro", "Erro do arraylist retornando nada");
                   Toast.makeText(AtualizarMonitoria.this, "Ops! Monitoria não encontrada ", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AtualizarMonitoria.this, "Ops! Ocorreu um erro " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }

        });
    }
}
