package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.R;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;

public class RegistrarMonitoria extends AppCompatActivity {
    EditText auxNome, auxAno, auxMateria, auxObs, auxDado1, auxDado2, auxDado3, auxDado4, auxDado5;
    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    FirebaseAuth mAuth;
    String x = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_monitoria);
        MobileAds.initialize(RegistrarMonitoria.this, "ca-app-pub-7164742797981159~2228765994");
        AdView adView ;
        adView = (AdView) findViewById(R.id.adViewRegistrarMedio);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        mAuth = FirebaseAuth.getInstance();

        Button registrar = (Button)findViewById(R.id.butRegistro);

        auxNome = (EditText)findViewById(R.id.nomeRegistro);

        auxDado1 = (EditText)findViewById(R.id.dadosRegistro1);
        auxDado2 = (EditText)findViewById(R.id.dadosRegistro2);
        auxDado3 = (EditText)findViewById(R.id.dadosRegistro3);
        auxDado4 = (EditText)findViewById(R.id.dadosRegistro4);
        auxDado5 = (EditText)findViewById(R.id.dadosRegistro5);

        auxAno = (EditText)findViewById(R.id.anoRegistro);
        auxMateria = (EditText)findViewById(R.id.materiaRegistro);

       // auxObs = (EditText)findViewById(R.id.obsRegistro);
      //  auxTurno = (EditText)findViewById(R.id.turnoRegistro);


        nomeMonitor();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auxNome = (EditText)findViewById(R.id.nomeRegistro);
                auxDado1 = (EditText)findViewById(R.id.dadosRegistro1);
                auxDado2 = (EditText)findViewById(R.id.dadosRegistro2);
                auxDado3 = (EditText)findViewById(R.id.dadosRegistro3);
                auxDado4 = (EditText)findViewById(R.id.dadosRegistro4);
                auxDado5 = (EditText)findViewById(R.id.dadosRegistro5);

                auxAno = (EditText)findViewById(R.id.anoRegistro);
                auxMateria = (EditText)findViewById(R.id.materiaRegistro);

         //       auxObs = (EditText)findViewById(R.id.obsRegistro);
             //   auxTurno = (EditText)findViewById(R.id.turnoRegistro);


                    registrar();



            }
        });

    }

    public void registrar()  {

        String nome, auxAnoR, auxMateriaR, auxTurnoR, zdado1, zdado2, zdado3, zdado4, zdado5;

        nome = auxNome.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                //      .replace("1","PRIMEIRO").replace("2","SEGUNDO").replace("3","TERCEIRO").replace("4","QUARTO")
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
                .replace("ANO","").replace("°","")
                .replace("/","")
                .trim();
/*
        auxTurnoR = auxTurno.getText().toString().toUpperCase().replace("Ã","A")
                .replace("/","")
                .trim();
*/

        zdado1 = auxDado1.getText().toString()
                .trim();

        zdado2 = auxDado2.getText().toString()
                .trim();
        zdado3 = auxDado3.getText().toString()
                .trim();
        zdado4 = auxDado4.getText().toString()
                .trim();
        zdado5 = auxDado5.getText().toString()
                .trim();




        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Escreva o nome do Monitor.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(auxMateriaR)) {
            Toast.makeText(this, "Ops! Qual a matéria da monitoria?", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(auxAnoR)) {
            Toast.makeText(this, "Escreva o ano da monitoria.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(zdado1)) {
            Toast.makeText(this, "Ops! Monitoria sem dados.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(   (auxAnoR.contains("PRIMEIRO")) || (auxAnoR.contains("SEGUNDO")) ||(auxAnoR.contains("TERCEIRO")) ||(auxAnoR.contains("QUARTO")) )          ){
            Toast.makeText(this, "Ops! Ano escrito incorretamente.", Toast.LENGTH_SHORT).show();
            return;
        }



        if (auxAnoR.contains("PRIMEIRO")){
            RegistrarPrimeiroAno(nome, auxMateriaR,"PRIMEIRO", zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("SEGUNDO")){
            RegistrarSegundoAno(nome, auxMateriaR,"SEGUNDO" , zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("TERCEIRO")){
            RegistrarTerceiroAno(nome, auxMateriaR,"TERCEIRO", zdado1, zdado2, zdado3, zdado4, zdado5);
        }

        if (auxAnoR.contains("QUARTO")){
            RegistrarQuartoAno(nome, auxMateriaR,"QUARTO", zdado1, zdado2, zdado3, zdado4, zdado5);
        }


        Toast.makeText(RegistrarMonitoria.this,"Registro concluído",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));


    }

    public void RegistrarPrimeiroAno(String nome, String materia, String ano, String dado1, String dado2, String dado3, String dado4, String dado5){
       /*
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }
        */
        Registrar(nome, materia, ano, dado1, dado2, dado3, dado4, dado5);
    }

    public void RegistrarSegundoAno(String nome, String materia, String ano, String dado1, String dado2, String dado3, String dado4, String dado5){
      /*
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }
        */
        Registrar(nome, materia, ano, dado1, dado2, dado3, dado4, dado5);
    }

    public void RegistrarTerceiroAno(String nome, String materia, String ano, String dado1, String dado2, String dado3, String dado4, String dado5){
       /*
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }
        */
        Registrar(nome, materia, ano, dado1, dado2, dado3, dado4, dado5);
    }

    public void RegistrarQuartoAno(String nome, String materia, String ano, String dado1, String dado2, String dado3, String dado4, String dado5){
      /*
        if (turno.contains("MANHA")){
            RegistrarManha(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }

        if (turno.contains("TARDE")){
            RegistrarTarde(nome, materia, ano, obs, dado1, dado2, dado3, dado4, dado5);
        }*/
        Registrar(nome, materia, ano, dado1, dado2, dado3, dado4, dado5);

    }

    public void Registrar(String nome, String materia, String ano, String dado1, String dado2, String dado3, String dado4, String dado5){


        Monitoria monitoria = new Monitoria();

        monitoria.setMonitor(nome);
        monitoria.setMateria(materia);
        monitoria.setAno(ano);
        monitoria.setObservação("");
     //   monitoria.setTurno("MANHA");
        monitoria.setZdado1(dado1);
        monitoria.setZdado2(dado2);
        monitoria.setZdado3(dado3);
        monitoria.setZdado4(dado4);
        monitoria.setZdado5(dado5);

        database = FirebaseDatabase.getInstance();

        regMonitoria = database.getReference().child("Monitorias").child(materia)
                .child(ano).child(nome).child("Dados");

        regMonitoria.setValue(monitoria);
    }

    public void nomeMonitor(){
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child("CEFET Maracanã médio e técnico").child(mAuth.getCurrentUser().getUid()).child("Nome");

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String x = d.getValue(String.class);
                    auxNome.setText(x);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

}
