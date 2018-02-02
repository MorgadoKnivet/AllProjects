package morgado.mdsoftware.monitoriacefet.Universidade;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilVisualizar;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.BuscaMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.ListaPersonalizada;
import morgado.mdsoftware.monitoriacefet.R;

public class BuscaUniversidade extends AppCompatActivity {

    EditText materia;
    public static final int CONSTANTE_PERFIL = 3;
    ListView lv;
    Set set = new HashSet();
    Set setL = new HashSet();
    Map<String, Object> map, mapL;
    String x;
    Monitoria monitoria = new Monitoria();
    List auxRegistro = new ArrayList();
    ArrayList auxDetalhes;
    ListaPersonalizada listaPersonalizada;
    AdView adView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_universidade);

        lv = (ListView) findViewById(R.id.lvProcurarMonitoriaFaculdade);
        materia = (EditText)findViewById(R.id.materiaProcurarMonitoriaFacul);
        Button buscar = (Button)findViewById(R.id.buscarMonitoriaFaculdade);


        adView = (AdView) findViewById(R.id.adViewBuscarFaculdade);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monitoria monitoria = (Monitoria) parent.getAdapter().getItem(position);
                final String monitor = monitoria.getMonitor().toUpperCase().trim();
                final String materia = monitoria.getMateria().trim();
                Log.i("Info","aa"+monitoria.getMonitor());
                //   Perfil, Visualizar, nome monitor , avisos ou contatos
                // Então temos que pegar aqui o nome do monitor e passar junto do intent


                DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Visualizar");

                regMonitoria.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator
                            mapL = (Map<String, Object>) dataSnapshot.getValue();
                            Log.i("SCRIPT", "Entrou no for");

                        }
                        try {
                            setL = mapL.keySet();

                        }catch (NullPointerException n){
                            //Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                            Log.i("SCRIPT", "Entrou no erro NullPointer");
                        }

                        int cont = 0;
                        for (Object i : setL) {

                            String palavra = (String) i;
                            String nomeNormal = (String) i;
                            palavra = palavra.toUpperCase();
                            Log.i("SCRIPT", "Palavra encontrada ao clicar no LV foi SEM ENTRAR NO IF: " + palavra + "  " +monitor);
                            if (palavra.contains(monitor)) {
                                Log.i("SCRIPT", "Palavra encontrada ao clicar no LV foi: " + palavra);

                                Bundle params = new Bundle();
                                params.putString("Nome", nomeNormal);
                                params.putString("Materia",materia);
                                Intent intent = new Intent(BuscaUniversidade.this, PerfilVisualizar.class);
                                intent.putExtras(params);

                                startActivityForResult(intent, CONSTANTE_PERFIL);
                                return;

                            }
                            else {
                                cont = cont + 1;
                            }

                            if (cont == set.size()){
                                Toast.makeText(BuscaUniversidade.this, "Perfil não cadastrado", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(BuscaUniversidade.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


                        Log.i("SCRIPT", "Entrou no ERRO");
                    }


                });



            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auxRegistro.clear();
                materia = (EditText) findViewById(R.id.materiaProcurarMonitoriaFacul);

                try {


                    LerMateria();
                }  catch (NullPointerException n) {
                    Toast.makeText(BuscaUniversidade.this, "Ops! Sua monitoria não foi encontrada", Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                }
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(materia.getWindowToken(), 0);


            }



        });


    }

    public void LerMateria(){

        final String materiaL = materia.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .trim();

        if (TextUtils.isEmpty(materiaL)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade");

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
                    Toast.makeText(BuscaUniversidade.this, "Ops! Sua monitoria não foi encontrada" , Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);

                }
                int cont = 0;


                for (Object i : set) {

                    String palavra = (String) i;
                    if (palavra.contains(materiaL)){
                        Log.i("SCRIPT_Contains", palavra);
                        LerDados(palavra);
                    }else {
                        cont = cont + 1;
                    }

                    if (cont == set.size()){
                        Toast.makeText(BuscaUniversidade.this, "Ops! Monitoria não encontrada", Toast.LENGTH_SHORT).show();
                          auxRegistro.clear();
                          listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaUniversidade.this);
                          lv = (ListView) findViewById(R.id.lvProcurarMonitoriaFaculdade);
                          lv.setAdapter(listaPersonalizada);

                        adView.setVisibility(View.VISIBLE);
                        return;
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                Log.i("SCRIPT", "Entrou no ERRO");
            }


        });


    }

    public void LerDados(final String materiaL) {

        adView.setVisibility(View.GONE);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(materiaL);



        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    map = (Map<String, Object>) dataSnapshot.getValue();

                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                //    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                //    adView.setVisibility(View.VISIBLE);
                }

                for (Object i : set) {
                    String palavra = (String) i;
                    Log.i("SCRIPT_NomeMonitores", palavra );

                        LerDetalhes(materiaL,palavra);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              //  Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                Log.i("SCRIPT", "Entrou no ERRO");
            }

        });



    }



    public void LerDetalhes(final String materia, final String monitor){

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(materia).child(monitor).child("Dados");




        auxDetalhes = new ArrayList();

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    Log.i("Detalhes", aux);
                    auxDetalhes.add(aux);

                }

                try {
                    monitoria.setMonitor("" + auxDetalhes.get(1));
                    monitoria.setMateria(""+auxDetalhes.get(0));
                    monitoria.setZdado1(""+auxDetalhes.get(2));
                    monitoria.setZdado2(""+auxDetalhes.get(3));
                    monitoria.setZdado3(""+auxDetalhes.get(4));
                    monitoria.setZdado4(""+auxDetalhes.get(5));
                    monitoria.setZdado5(""+auxDetalhes.get(6));





                    auxRegistro.add(monitoria);

                }catch (IndexOutOfBoundsException i){
                    //    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    //  adView.setVisibility(View.VISIBLE);
                }

                listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaUniversidade.this);

                lv = (ListView) findViewById(R.id.lvProcurarMonitoriaFaculdade);
                lv.setAdapter(listaPersonalizada);

                monitoria = new Monitoria();
                auxDetalhes = new ArrayList();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });


    }

    public void onBackPressed(){
       finish();
    }
}
