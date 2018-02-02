package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar.AtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Atualizar.MenuAtualizarMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilVisualizar;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.NewUserActivity;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.ContaMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;

/**
 * Created by MoorG on 11/07/2017.
 */

public class BuscaMonitoria extends AppCompatActivity {
    public static final int CONSTANTE_PERFIL = 3;
    EditText auxEnviarMateria;
    Button auxEnviarPesquisa;
    ListView lv;
    String[]materias = new String[90];
    Monitoria monitoria = new Monitoria();
    ListaPersonalizada listaPersonalizada, listaPersonalizada2;
    List auxRegistro = new ArrayList();

    ArrayList auxDetalhes;
    Set set = new HashSet();
    Set setL = new HashSet();
    Map<String, Object> map, mapL;
    String x;
    ArrayAdapter<String> adapter2;
    private FirebaseAuth mAuth;

    AdView adView;
    AdRequest adRequest;

    String[] opçoes = new String[]{"Seu ano","1° ano","2° ano","3° ano","4° ano"};
    Spinner sp;

    String resposta;
    int contadorClick = 0;

    ListView lvMaterias;
    ImageButton imageButtonLV;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        MobileAds.initialize(BuscaMonitoria.this,"ca-app-pub-7164742797981159~2228765994" );
        mAuth = FirebaseAuth.getInstance();

        lvMaterias = (ListView)findViewById(R.id.lvMaterias);
        lv = (ListView) findViewById(R.id.lvBuscaMonitoriaMedio);

        lerMateriaSpinner();

        adView = (AdView)findViewById(R.id.adViewBusca);
        // AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        imageButtonLV = (ImageButton)findViewById(R.id.buttonLV);

        auxEnviarPesquisa = (Button) findViewById(R.id.Titulo);

        auxEnviarMateria = (EditText) findViewById(R.id.enviarMateria);


        imageButtonLV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvMaterias.setVisibility(View.VISIBLE);
                auxEnviarPesquisa.setVisibility(View.GONE);
                contadorClick = contadorClick + 1;
                if (contadorClick == 2){
                    contadorClick = 0;
                    lvMaterias.setVisibility(View.GONE);
                    auxEnviarPesquisa.setVisibility(View.VISIBLE);
                }
            }
        });

        lvMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monitoria monitoria = (Monitoria) parent.getAdapter().getItem(position);
                auxEnviarMateria.setText(monitoria.getZdado1());
                contadorClick = contadorClick + 1;
                lvMaterias.setVisibility(View.GONE);
                auxEnviarPesquisa.setVisibility(View.VISIBLE);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BuscaMonitoria.this,R.layout.activity_personalizar_spinner,opçoes ){};
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        sp = (Spinner)findViewById(R.id.spinnerBusca);
        sp.setAdapter(adapter);

        if (!(verificaConexao())){

            Toast.makeText(BuscaMonitoria.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
            return;

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monitoria monitoria = (Monitoria) parent.getAdapter().getItem(position);
                final String monitor = monitoria.getMonitor().toUpperCase().trim();
                final String materia = monitoria.getMateria().trim();
                Log.i("Info","aa"+monitoria.getMonitor());
                //   Perfil, Visualizar, nome monitor , avisos ou contatos
                // Então temos que pegar aqui o nome do monitor e passar junto do intent
                DatabaseReference regMonitoria2 = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Visualizar");

                regMonitoria2.addValueEventListener(new ValueEventListener() {
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
                                Intent intent = new Intent(BuscaMonitoria.this, PerfilVisualizar.class);
                                intent.putExtras(params);

                                startActivityForResult(intent, CONSTANTE_PERFIL);
                                return;

                            }else {
                                cont = cont + 1;
                            }

                            if (cont == setL.size()){
                                Toast.makeText(BuscaMonitoria.this, "Perfil não cadastrado", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            /*
                            else{
                                Toast.makeText(BuscaMonitoria.this, "Perfil ainda não configurado", Toast.LENGTH_SHORT).show();
                            }
                             */
                            // Para o erro funcionar corretamente, tem que fazer a gambiarra contado a quantidade de números no SET, quantidade do contador dentro e etc

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


                        Log.i("SCRIPT", "Entrou no ERRO");
                    }


                });

            }
        });


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resposta = opçoes[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        auxEnviarPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxRegistro.clear();
                auxEnviarMateria = (EditText) findViewById(R.id.enviarMateria);
              //  auxEnviarAno = (EditText) findViewById(R.id.enviarAno);
               // auxEnviarTurno = (EditText) findViewById(R.id.TurnoP);

                try {
                    if (!(verificaConexao())){

                        Toast.makeText(BuscaMonitoria.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
                        return;

                    }else {
                        LerMateria();
                    }
                    //  LerDetalhes();
                } catch (DatabaseException d) {
                    Toast.makeText(BuscaMonitoria.this, "Ops! Sua monitoria não foi encontrada", Toast.LENGTH_LONG).show();
                    adView.setVisibility(View.VISIBLE);
                } catch (IndexOutOfBoundsException i) {
                    Log.i("SCRIPTERRO", "Erro do arrayList");
                    adView.setVisibility(View.VISIBLE);
                } catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Ops! Sua monitoria não foi encontrada", Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                } catch (IllegalStateException i){
                    Toast.makeText(BuscaMonitoria.this, "Ops! Sua monitoria não foi encontrada", Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                }


                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarMateria.getWindowToken(), 0);

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
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Ops! Sua monitoria não foi encontrada" , Toast.LENGTH_SHORT).show();
                    adView.setVisibility(View.VISIBLE);
                }
                int cont = 0;
                for (Object i : set) {

                    String palavra = (String) i;
                    if (palavra.contains(materiaL)){
                        Log.i("SCRIPT", palavra);
                        LerDados(palavra,ano);
                    }else {
                        cont = cont + 1;
                    }
                    if (cont == set.size()){
                        Toast.makeText(BuscaMonitoria.this, "Ops! Monitoria não encontrada", Toast.LENGTH_SHORT).show();
                        auxRegistro.clear();
                        listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaMonitoria.this);
                        lv = (ListView) findViewById(R.id.lvBuscaMonitoriaMedio);
                        lv.setAdapter(listaPersonalizada);

                        adView.setVisibility(View.VISIBLE);
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

    public void LerDados(final String materiaL, final String anoL) {

        adView.setVisibility(View.GONE);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materiaL).child(anoL);



        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

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
                    Log.i("SCRIPT_NomeMonitores", palavra );
                    if (!((palavra.equals("MANHA") || palavra.equals("TARDE")))){

                        LerDetalhes(palavra,materiaL,anoL);
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

    public void LerDetalhes(final String nome, String materia, String ano){


        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano).child(nome).child("Dados");

        auxDetalhes = new ArrayList();

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    Log.i("Detalhes", aux);
                    auxDetalhes.add(aux);

                }

                try {
                    monitoria.setMonitor("" + auxDetalhes.get(2));
                    monitoria.setMateria(""+auxDetalhes.get(1));
                    monitoria.setZdado1(""+auxDetalhes.get(4));
                    monitoria.setZdado2(""+auxDetalhes.get(5));
                    monitoria.setZdado3(""+auxDetalhes.get(6));
                    monitoria.setZdado4(""+auxDetalhes.get(7));
                    monitoria.setZdado5(""+auxDetalhes.get(8));
                    monitoria.setObservação(""/*+auxDetalhes.get(3)*/);




                    auxRegistro.add(monitoria);

                }catch (IndexOutOfBoundsException i){
                //    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                  //  adView.setVisibility(View.VISIBLE);
                }

                listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaMonitoria.this);

                lv = (ListView) findViewById(R.id.lvBuscaMonitoriaMedio);
                lv.setAdapter(listaPersonalizada);

                monitoria = new Monitoria();
                auxDetalhes = new ArrayList();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });


    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

    @Override
    public void onBackPressed()
    {
        ArrayList arrayList = new ArrayList();
        arrayList.add("CEFET Maracanã médio e técnico");
        arrayList.add("CEFET Maracanã universidade");
        arrayList.add(""+mAuth.getCurrentUser().getUid());

        for (int i=0;i<arrayList.size();i++){
            String aux = (String)arrayList.get(i);
            irParaMenu(aux);
        }

    }

    public void irParaMenu(String unidadeL){

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            final DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);



                        if (x.equals("MONITOR")) {

                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                            //     Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
                        } else {
                            //    progressDialog.dismiss();

                            Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }


        if (unidadeL.equals("CEFET Maracanã universidade")) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");


            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                        if (x.equals("MONITOR")) {

                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                            Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());
                        } else {

                            Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                        }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }


        if (!(unidadeL.equals("CEFET Maracanã universidade") && unidadeL.equals("CEFET Maracanã médio e técnico")) ){

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);


                        if (x.equals("MONITOR")) {
                            startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                            Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());
                        } else {

                            Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());
                            startActivity(new Intent(getApplicationContext(), menuAluno.class));
                        }

                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public void lerMateriaSpinner(){

    DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias");
    regMonitoria.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot d : dataSnapshot.getChildren()) {
                map = (Map<String, Object>) dataSnapshot.getValue();
            }
            try {
                set = map.keySet();
            }catch (NullPointerException n){
             //   Toast.makeText(BuscaMonitoria.this, "Ops! Sua monitoria não foi encontrada" , Toast.LENGTH_SHORT).show();
             //   adView.setVisibility(View.VISIBLE);
            }
            ArrayList materias = new ArrayList();

            for (Object i : set) {
                String palavra = (String) i;
                Monitoria monitoria = new Monitoria();
                monitoria.setZdado1(palavra);
                materias.add(monitoria);

            }

             listaPersonalizada2 = new ListaPersonalizada(materias,BuscaMonitoria.this);

             lvMaterias = (ListView) findViewById(R.id.lvMaterias);
             lvMaterias.setAdapter(listaPersonalizada2);

            }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(BuscaMonitoria.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
            Log.i("SCRIPT", "Entrou no ERRO");
        }
    });
}


}







