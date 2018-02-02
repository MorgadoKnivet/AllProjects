package moorg.monitoriacefet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    List list = new ArrayList();
    ArrayList auxDetalhes;
    Set set = new HashSet();
    Map<String, Object> map;

    private InterstitialAd mInterstitialAd;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);




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

                auxRegistro.clear();
                auxEnviarMateria = (EditText) findViewById(R.id.enviarMateria);
                auxEnviarAno = (EditText) findViewById(R.id.enviarAno);
                auxEnviarTurno = (EditText) findViewById(R.id.TurnoP);

                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                try {

                    LerDados();


                    //  LerDetalhes();
                } catch (DatabaseException d) {
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException i) {
                    Log.i("SCRIPTERRO", "Erro do arrayList");
                } catch (NullPointerException n){
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                }




                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarMateria.getWindowToken(), 0);

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarAno.getWindowToken(), 0);

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(auxEnviarTurno.getWindowToken(), 0);




            }
        });






      //  ListaPersonalizada listaPersonalizada = new ListaPersonalizada(LerDados(),this);
//        lv.setAdapter(listaPersonalizada);

        // dia, hora, local, monitor,


    }

    public void LerDados() {
        final String materiaL = auxEnviarMateria.getText().toString().toUpperCase().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O");
        final String anoL = auxEnviarAno.getText().toString().toUpperCase().trim().replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("Ã", "A").replace("Õ", "O").replace("Ô", "O").replace("1", "PRIMEIRO").replace("2", "SEGUNDO").replace("3", "TERCEIRO").replace("4", "QUARTO").replace("Ê", "E").replace("°","").replace(" ","");
        final String turnoL = auxEnviarTurno.getText().toString().toUpperCase().replace("Ã", "A").replace("ã", "A");
        if (TextUtils.isEmpty(materiaL)) {
            Toast.makeText(this, "Escreva a matéria", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(anoL)) {
            Toast.makeText(this, "Escreva o ano foco da monitoria", Toast.LENGTH_LONG).show();
           return;
        }

        if (TextUtils.isEmpty(turnoL)) {
            Toast.makeText(this, "Escreva o turno da monitoria", Toast.LENGTH_LONG).show();
           return;
        }
 /* correção de erro ao eviar nenhum valor para a tela */




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
                    Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
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


     //   for (int i = 0; i <= list.size(); i++) {
            auxDetalhes = new ArrayList();

            regDetalhes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        String aux = d.getValue(String.class);

                        auxDetalhes.add(aux);


                        Log.i("SCRIPT",aux);


                    }
                    try {


                        monitoria.setMonitor("Monitor: " + nome);
                        monitoria.setAno("Ano: " + auxDetalhes.get(0));
                        monitoria.setDia("Dia(s): " + auxDetalhes.get(1));
                        monitoria.setHorario("Horário: " + auxDetalhes.get(2));
                        monitoria.setLocal("Local: " + auxDetalhes.get(3));
                        auxRegistro.add(monitoria);
                    }catch (IndexOutOfBoundsException i){
                        Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    }
                    listaPersonalizada = new ListaPersonalizada(auxRegistro,BuscaMonitoria.this);

                    lv = (ListView) findViewById(R.id.lv);
                    // list = getGambiarra("erroabcde");

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
}
        /*








                        //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator





                    for (Object i : set) {


                    }


                }




        }

        */


























           //     String x = d.getValue(String.class);
              //  Map teste = new HashMap();
               // String teste = (String) d.getKey(Map.class);
              //  z = z + x;
              //  auxRegistro.add(x);





     //       }catch (DatabaseException a){
         //       Log.i("SCRIPT","ERRO DO String to MAP");

       //     }
                   // Map z = d.getValue(Map.class);



                    // Anotações de erros em sequencias.

                    // 1 - Erro de database, DatabaseException, só da quando vc coloca coisas surreais, tipo, procurar pontos.
                    // 2 - Quando vc procura algo que não existe na database, ele entra em onDataChande, mas não entrega nenhum valor, assim, ele não envia nenhum valor para o arraylist, que não da nenhum
                            // valor para setar na tela, assim dando o erro IndexOutOfBoundsException
                    // 3 - Existe um terceiro erro, quando vc procura alguma coisa faltando child, tipo procura a matéria certa, mas não procura o ano encontrado, ele retorna um mapa, ai eu quero retornar um mapa em uma string.
                            // dando o erro assim IndexOutOfBoundsException i.

                    // Agora temos um erro 4, onde a ideia de usar um novo child não esta retornando nenhum valor, não sabemos PQ ainda, temos que repensar nas coisas. Ele não está retornando nada, não está entrando no for

        /*

                set = map.keySet();


                for (Object i : set){

                    String palavra = (String)i;
                    auxRegistro.add(palavra);
                 //   aux[j] = map.get(palavra).toString();
                 //   j = j+1;

                    Log.i("SCRIPT","" + palavra +" ..." + map.get(palavra));
                }
                */


          //    Map<String, Object> auxM = (Map<String, Object>) map.values();

              //  Map<String,String> x = (HashMap<String, String>) auxC.g;





                /* gambiarra abaixo


                String aux[] = new String[10];

                int j = 0;


               String auxVet[] = aux[0].split(",");
                auxVet[0] = auxVet[0].replace("monitor=","");
                Log.i("SCRIPT",auxVet[0]);




                try {
               //     auxResultFour.setText("Horário: " + auxRegistro.get(1));
                //    auxResultThree.setText("Dia: " + auxRegistro.get(0));
                 //   auxResultTwo.setText("Local: " + auxRegistro.get(2));
                  //  auxResultOne.setText("Monitor: " + auxRegistro.get(3));
                }catch (IndexOutOfBoundsException i){
                    Log.i("SCRIPT","Erro do for vazio");

                }

*/







/*
        //  String []aux = new String[5];
/*
        for (int i=0;i<=auxRegistro.size();i++){
            String aux = (String)auxRegistro.get(i);
            auxResultFour.setText(aux);

        }
*/


        //  return aux;






