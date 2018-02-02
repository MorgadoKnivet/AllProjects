package morgado.mdsoftware.monitoriacefet.Universidade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.BuscaMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.RegistrarMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.menuAluno;
import morgado.mdsoftware.monitoriacefet.R;

public class RegistrarMonitoriaFaculdade extends AppCompatActivity {
    EditText nomeG,materiaG,info1,info2,info3,info4,info5;
    FirebaseAuth mAuth;
    String x = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_monitoria_faculdade);
        MobileAds.initialize(RegistrarMonitoriaFaculdade.this, "ca-app-pub-7164742797981159~2228765994");
        AdView adView;
        adView = (AdView) findViewById(R.id.adViewRegistrarFacul);
        //  AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        mAuth = FirebaseAuth.getInstance();
        nomeG = (EditText)findViewById(R.id.nomeRegistrarFacul);
        nomeMonitor();


        Button registrar = (Button)findViewById(R.id.buttonRegistrarFacul);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeG = (EditText)findViewById(R.id.nomeRegistrarFacul);
                materiaG = (EditText)findViewById(R.id.materiaRegistrarFacul);
                info1 = (EditText)findViewById(R.id.info1RegistrarFacul);
                info2 = (EditText)findViewById(R.id.info2RegistrarFacul);
                info3 = (EditText)findViewById(R.id.info3RegistrarFacul);
                info4 = (EditText)findViewById(R.id.info4RegistrarFacul);
                info5 = (EditText)findViewById(R.id.info5RegistrarFacul);

                registrar();

            }
        });
    }

    public void registrar(){

        String auxNome = nomeG.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                .trim();

        String auxMateria = materiaG.getText().toString().toUpperCase()
                .replace("Á", "A").replace("Ã", "A").replace("Â", "A").replace("À", "A")
                .replace("É","E").replace("Ê","E")
                .replace("Í","I").replace("Î","I")
                .replace("Ó","O").replace("Ô","O").replace("Õ","O")
                .replace("Ú","U") .replace("Û","U")
                .replace("/","")
                .trim();

        String zdado1 = info1.getText().toString()
                .trim();

        String zdado2 = info2.getText().toString()
                .trim();
        String zdado3 =info3.getText().toString()
                .trim();
        String zdado4 = info4.getText().toString()
                .trim();
        String zdado5 = info5.getText().toString()
                .trim();

        if (TextUtils.isEmpty(auxNome)) {
            Toast.makeText(this, "Escreva seu nome.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(auxMateria)) {
            Toast.makeText(this, "Escreva a matéria da monitoria.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(zdado1)) {
            Toast.makeText(this, "Ops! Monitoria sem dados.", Toast.LENGTH_SHORT).show();
            return;
        }

        registrarMonitoria(auxMateria, auxNome, zdado1,zdado2,zdado3,zdado4,zdado5);

    }

    public void registrarMonitoria(String materia, String nome, String dado1, String dado2, String dado3, String dado4, String dado5){
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Monitorias_Faculdade").child(materia).child(nome).child("Dados");
        Monitoria monitoria = new Monitoria();
        monitoria.setMonitor(nomeG.getText().toString());
        monitoria.setMateria(materiaG.getText().toString());
        monitoria.setZdado1(dado1);
        monitoria.setZdado2(dado2);
        monitoria.setZdado3(dado3);
        monitoria.setZdado4(dado4);
        monitoria.setZdado5(dado5);
        monitoria.setZmMonitorID(""+mAuth.getCurrentUser().getUid());

        regMonitoria.setValue(monitoria);

        Toast.makeText(RegistrarMonitoriaFaculdade.this,"Registro concluído",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

    }

    public void nomeMonitor(){
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child("CEFET Maracanã universidade").child(mAuth.getCurrentUser().getUid()).child("Nome");

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                   String x = d.getValue(String.class);
                    nomeG.setText(x);
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
