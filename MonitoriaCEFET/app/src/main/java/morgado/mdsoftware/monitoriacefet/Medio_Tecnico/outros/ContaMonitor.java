package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.DadosMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.PerfilVisualizar;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.BuscaMonitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MyService;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.menuAluno;
import morgado.mdsoftware.monitoriacefet.R;

public class ContaMonitor extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference regMonitoria, regMonitoria2;
    private FirebaseAuth mAuth;
    EditText nomeMonitor;
    Map<String, Object>  mapL;
    Set setL = new HashSet();
    String x="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_monitor);
        mAuth = FirebaseAuth.getInstance();
         nomeMonitor = (EditText)findViewById(R.id.contaNomeMonitor);
        Button enviarMonitor = (Button) findViewById(R.id.contaEnviarMonitor);


        enviarMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeMonitor = (EditText)findViewById(R.id.contaNomeMonitor);
                ArrayList arrayList = new ArrayList();
                arrayList.add("CEFET Maracanã médio e técnico");
                arrayList.add("CEFET Maracanã universidade");

                for (int i=0;i<arrayList.size();i++){
                    String aux = (String)arrayList.get(i);
                    instituição(aux);
                }




                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(nomeMonitor.getWindowToken(), 0);


            }
        });

    }

    public void enviar(String inst){
        String auxMonitor = nomeMonitor.getText().toString();

        if (TextUtils.isEmpty(auxMonitor)) {
            Toast.makeText(this, "Você esqueceu do seu nome", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println("System funciona");
        database = FirebaseDatabase.getInstance();

        DadosMonitor dadosMonitor = new DadosMonitor();
        dadosMonitor.setNome(auxMonitor);

        // Pedido de conta de monitor


        regMonitoria = database.getReference().child("Pedido de conta de monitor").child(inst).child(""+mAuth.getCurrentUser().getUid());//.child(""+mAuth.getCurrentUser().getEmail());

        regMonitoria.setValue(dadosMonitor);

        // Fim do pedido de conta do monitor

        // Nome do Monitor salvo no BD e tipo //

         regMonitoria = database.getReference().child("Unidades").child("Usuários").child(inst).child(""+mAuth.getCurrentUser().getUid()).child("Nome");

        regMonitoria.setValue(dadosMonitor);
/*
        regMonitoria = database.getReference().child("Unidades").child("Usuários").child(inst).child(""+mAuth.getCurrentUser().getUid()).child("Tipo");

        dadosMonitor = new DadosMonitor();
        dadosMonitor.setTipo("");
        regMonitoria.setValue(dadosMonitor);
*/
        //  Fim Nome do Monitor salvo no BD e tipo //

        regMonitoria = database.getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("NomeMonitor");
        regMonitoria2= database.getReference().child("Perfil").child("Visualizar").child(auxMonitor).child("NomeMonitor");

        DadosMonitor dadosMonitorNome = new DadosMonitor();
        dadosMonitorNome.setNome(auxMonitor);
        regMonitoria.setValue(dadosMonitorNome);
        regMonitoria2.setValue(dadosMonitorNome);


        Intent intent = new Intent(ContaMonitor.this, MyService.class);
        Bundle params = new Bundle();
        params.putString("id","" + mAuth.getCurrentUser().getUid());
        intent.putExtras(params);
        startService(intent);

        irParaMenu(inst);

    }

    public void instituição(final String nomeIns){
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(nomeIns);
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    mapL = (Map<String, Object>) dataSnapshot.getValue();
                }

                try {
                    setL = mapL.keySet();

                }catch (NullPointerException n){
                    //Toast.makeText(BuscaMonitoria.this, "Monitoria não encontrada, procure novamente", Toast.LENGTH_SHORT).show();
                    Log.i("SCRIPT", "Entrou no erro NullPointer");
                }

                for (Object i : setL) {

                    String palavra = (String) i;
                    if (palavra.equals(mAuth.getCurrentUser().getUid())) {
                        enviar(nomeIns);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                                        Toast.makeText(ContaMonitor.this, "Seu pedido foi enviado", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ContaMonitor.this, "Seu pedido foi enviado", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ContaMonitor.this, "Seu pedido foi enviado.", Toast.LENGTH_SHORT).show();
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


    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), menuAluno.class));
    }
}

