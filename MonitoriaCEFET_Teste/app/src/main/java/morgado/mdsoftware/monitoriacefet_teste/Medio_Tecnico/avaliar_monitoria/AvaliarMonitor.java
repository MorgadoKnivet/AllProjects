package morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.avaliar_monitoria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.menuAluno;

public class AvaliarMonitor extends AppCompatActivity {

    TextView nomeTV,diasTV, horarioTV, localTV;
    EditText auxPresença, auxNota, auxOpiniao;
    Button enviar;
    String nome, dias, horario, local, materia,ano, turno;
    private ProgressDialog progressDialog;
    ArrayList auxDetalhes;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    private FirebaseAuth mAuth;
    Map<String, Object> map;
    Map<String, Object> mapDetalhes;
    Set set = new HashSet();
    Set setDetalhes = new HashSet();
    ArrayList presença = new ArrayList();
    double media = 0;
    int cont = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliar_monitor);


        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                nome = params.getString("Nome");
                 dias = params.getString("Dias");
                horario = params.getString("Horario");
                 local = params.getString("Local");
                materia= params.getString("Materia");
                ano= params.getString("Ano");
                turno= params.getString("Turno");

              // Log.i("Script", nome + dias + horario + local + materia + ano +turno);
                nomeTV = (TextView)findViewById(R.id.nomeMonitorATV);
                diasTV = (TextView)findViewById(R.id.diaAVTV);
                horarioTV = (TextView)findViewById(R.id.horarioATV);
                localTV = (TextView)findViewById(R.id.localATV);

                nomeTV.setText("Monitor: "+nome);
                diasTV.setText("Dia(s) "+dias);
                horarioTV.setText("Horário: "+horario);
                localTV.setText("Local: "+local);

            }
        }

        mAuth = FirebaseAuth.getInstance();
        atualizacao();
        enviar = (Button)findViewById(R.id.enviarAM);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxPresença = (EditText)findViewById(R.id.presenca);
                auxNota = (EditText)findViewById(R.id.nota);
                auxOpiniao = (EditText)findViewById(R.id.opiniaoMonitor);
                enviarDados();

            }
        });
    }


    public void enviarDados(){
        String presenca = auxPresença.getText().toString().toLowerCase().replace("ã","a");
        String notaAux = auxNota.getText().toString();
        String opiniao = auxOpiniao.getText().toString();



        if (TextUtils.isEmpty(presenca)) {
            Toast.makeText(this, "Acho Esqueceu da presença", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(presenca.equals("sim") || presenca.equals("nao"))){
            Toast.makeText(this, "Hey, coloque sim/não na presença", Toast.LENGTH_LONG).show();
            return;
        }

        if (presenca.equals("nao")){

        }

        if (TextUtils.isEmpty(notaAux)) {
            Toast.makeText(this, "Acho que esquceu da nota", Toast.LENGTH_LONG).show();
            return;
        }



        double nota = Double.parseDouble(notaAux);

        if (nota > 10){
            Toast.makeText(this, "Sua nota foi maior que 10", Toast.LENGTH_LONG).show();
            auxNota.setText("");
            return;
        }

        if (nota < 0){
            Toast.makeText(this, "Sua nota foi menor que 0", Toast.LENGTH_LONG).show();
            auxNota.setText("");
            return;
        }

        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia).child(ano.replace(" ","")).child(turno).child(nome).child("Avaliacão").child(mAuth.getCurrentUser().getUid());

        Map map = new HashMap();

       map.put("Presença",presenca);
        map.put("Nota",nota);
        map.put("Opiniao",opiniao);

        regMonitoria.setValue(map);



        database = FirebaseDatabase.getInstance();

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());
        regMonitoria.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {


                    String x = d.getValue(String.class);
                    // Map z = d.getValue(Map.class);
                    // z = z + x;

                    if(x.equals("MONITOR")){

                        Toast.makeText(AvaliarMonitor.this,"Obrigado por sua avaliação. ",Toast.LENGTH_LONG).show();

                        finish();
                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                    }else {

                        Toast.makeText(AvaliarMonitor.this,"Obrigado por sua avaliação. ",Toast.LENGTH_LONG).show();

                        finish();
                        startActivity(new Intent(getApplicationContext(), menuAluno.class));
                    }




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void atualizacao(){
        // Aqui ficara a parte de contar as presençar, x presença negativa, excluir a monitoria do sistema.


       DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano.replace(" ","")).child(turno).child(nome).child("Avaliacão");


        //    Log.i("SCRIPT_TURNO",turnoL);



        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                Log.i("Script_FOR","Entrou no for");
                    //        try {
//https://stackoverflow.com/questions/37688031/class-java-util-map-has-generic-type-parameters-please-use-generictypeindicator
                    map = (Map<String, Object>) dataSnapshot.getValue();


                }
                try {
                    set = map.keySet();
                    Log.i("Script_set","Entrou no set");
                }catch (NullPointerException n){
                    Toast.makeText(AvaliarMonitor.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    Log.i("Script_Erro","Entrou no erro");
                }



                for (Object i : set) {
                    cont = cont +1;
                    String palavra = (String) i;


                    Log.i("SCRIPT","UserID"+ " " + palavra + " " + cont );
                    LerDetalhes(palavra);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AvaliarMonitor.this, "Registration Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                Log.i("SCRIPT", "Entrou no ERRO");
            }


        });



    }

    public void LerDetalhes(String userId){
        Log.i("SCRIPT","LER DETALHES "+userId);
        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano.replace(" ","")).child(turno).child(nome).child("Avaliacão").child(userId);
     //   DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias").child(materia).child(ano.replace(" ","")).child(turno).child(nome).child("Avaliacão");

        auxDetalhes = new ArrayList();
        cont = 0;
        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                  //  String aux = d.getValue(String.class);
                   mapDetalhes = (Map<String, Object>) dataSnapshot.getValue();
                    cont = cont +1;






                }
                try {
                    setDetalhes = mapDetalhes.keySet();
                    Log.i("Script_set_Detalhes","Entrou no set");
                }catch (NullPointerException n){
                    Toast.makeText(AvaliarMonitor.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_LONG).show();
                    Log.i("Script_Erro","Entrou no erro");
                }



                for (Object i : setDetalhes) {

                    String palavra = (String) i;

                    if (palavra.equals("Presença")){

                        presença.add(mapDetalhes.get("Presença"));
                        if (presença.size()==10){

                        }

                    }

                    if (palavra.equals("Nota")){
                        int nota= Integer.parseInt(""+mapDetalhes.get("Nota"));
                        media = media + nota;

                    }


                    Log.i("SCRIPTaaaaa",""+ mapDetalhes.get(palavra) + " "+cont );



                }
                media = media / cont;


         //       DatabaseReference inserirSoma = database.getReference().child("Monitorias").child(materia).child(ano.replace(" ","")).child(turno).child(nome).child("Dados");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("SCRIPT_Detalhes","Entrou no erro");
            }


        });
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }
    }
