package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil.DadosMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.menuAluno;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.R;


/**
 * Created by MoorG on 04/07/2017.
 */
// https://www.simplifiedcoding.net/android-firebase-tutorial-1/ código baseado do site
public class NewUserActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    FirebaseUser user;
    ArrayList unidade = new ArrayList();

    TextView avisoNET, aviso1, aviso2;

    String x;
    EditText email, senha, senhaConfirm;
    Button loguin;

    String[] opçoes = new String[]{"Sua unidade","CEFET Maracanã médio e técnico","CEFET Maracanã universidade"};
    Spinner sp;
    String resposta;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewUserActivity.this,R.layout.activity_personalizar_spinner,opçoes ){};
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        sp = (Spinner)findViewById(R.id.spinner);
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


        aviso1 = (TextView)findViewById(R.id.avisoNumCaracter);
        aviso2 = (TextView)findViewById(R.id.avisoSenha);
        avisoNET = (TextView)findViewById(R.id.avisoNetCadastro);
        mAuth = FirebaseAuth.getInstance();
        loguin = (Button) findViewById(R.id.buttonLoguin);

        progressDialog = new ProgressDialog(this);

        loguin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (!(verificaConexao())){
                    avisoNET.setVisibility(View.VISIBLE);
                    Toast.makeText(NewUserActivity.this, "Ops! Você está desconectado da internet", Toast.LENGTH_SHORT).show();
                    return;

                }else{
                    avisoNET.setVisibility(View.GONE);
                }

                email = (EditText) findViewById(R.id.loguin);
                senha = (EditText) findViewById(R.id.senha);
                senhaConfirm = (EditText) findViewById(R.id.repitirSenha);
                registerUser();
            }
        });



        }

    public void registerUser() {
        String auxSenhaConfirm = senhaConfirm.getText().toString().trim();
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();

            if (!auxSenhaConfirm.equals(auxSenha)) {
                 Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_LONG).show();
                 return;
            }
            //checking if email and passwords are empty
            if (TextUtils.isEmpty(auxEmail)) {
                Toast.makeText(this, "Email não digitado", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(auxSenha)) {
                Toast.makeText(this, "Senha não digitada", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(auxSenhaConfirm)) {
                 Toast.makeText(this, "Confirme sua senha", Toast.LENGTH_LONG).show();
                 return;
            }

           if (resposta.equals("Sua unidade")){
               Toast.makeText(this, "Selecione a sua unidade", Toast.LENGTH_LONG).show();
               return;
            }


            progressDialog.setMessage("Registrando...");
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(auxEmail, auxSenha)
                    .addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                    database = FirebaseDatabase.getInstance();
                                    user =  mAuth.getCurrentUser();
                                    regMonitoria = database.getReference().child("Unidades").child("Usuários").child(resposta).child(user.getUid()).child("Tipo");


                                    DadosMonitor dadosMonitor = new DadosMonitor();
                                    dadosMonitor.setTipo("");
                                    regMonitoria.setValue(dadosMonitor);

                                    aviso1.setVisibility(View.GONE);
                                    aviso2.setVisibility(View.GONE);
                                    userLogin();

                            } else {
                                progressDialog.dismiss();
                                aviso1.setVisibility(View.VISIBLE);
                                aviso2.setVisibility(View.VISIBLE);
                                Toast.makeText(NewUserActivity.this, "Ops! Houve um erro", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
    }

    public void userLogin() {
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(auxEmail)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(auxSenha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Logando no sistema...");
        progressDialog.show();

        //logging in the user
        mAuth.signInWithEmailAndPassword(auxEmail, auxSenha).addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if the task is successfull
                if (task.isSuccessful()) {


                    unidade.add("CEFET Maracanã médio e técnico");
                    unidade.add("CEFET Maracanã universidade");
                    unidade.add(""+mAuth.getCurrentUser().getUid());

                    for (int i = 0; i < unidade.size(); i++) {
                        problemaUnidade("" + unidade.get(i));
                    }

                } else {
                  //  Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }

            }

        });
    }


    public void problemaUnidade( String unidadeL) {

        if (unidadeL.equals("CEFET Maracanã médio e técnico")) {

            DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Unidades").child("Usuários").child(unidadeL).child(mAuth.getCurrentUser().getUid()).child("Tipo");

            regMonitoria.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Log.i("SCRIPT", "ENTROU NO FOR");
                        x = d.getValue(String.class);



                                    if (x.equals("MONITOR")) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));

                                        Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));

                                    }





                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                   // Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
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
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                        Log.i("ENTROU NA UNIVERSIDADE", "" + mAuth.getCurrentUser().getUid());

                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("ENTROU NO USUARIO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));

                                    }




                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
               //     Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
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
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                        Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());

                                    } else {
                                        progressDialog.dismiss();
                                        Log.i("ENTROU NO RESTO", "" + mAuth.getCurrentUser().getUid());
                                        startActivity(new Intent(getApplicationContext(), menuAluno.class));

                                    }



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
               //     Toast.makeText(LoginActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    Log.i("ENTROU NO ERRO 2", "" + mAuth.getCurrentUser());
                }
            });
        }
    }

    public void irParaLogin(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
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

}


