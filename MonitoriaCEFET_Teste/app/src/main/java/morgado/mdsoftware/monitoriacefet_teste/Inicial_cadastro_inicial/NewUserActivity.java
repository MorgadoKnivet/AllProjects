package morgado.mdsoftware.monitoriacefet_teste.Inicial_cadastro_inicial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import morgado.mdsoftware.monitoriacefet_teste.R;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet_teste.Medio_Tecnico.utilidades.menuAluno;


/**
 * Created by MoorG on 04/07/2017.
 */
// https://www.simplifiedcoding.net/android-firebase-tutorial-1/ código baseado do site
public class NewUserActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference regMonitoria;
    FirebaseUser user;


    String x;
    EditText email, senha, senhaConfirm;
    Button loguin;


    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;
     String[] opçoes = new String[]{"Sua unidade","CEFET Maracanã médio e técnico","CEFET Maracanã universidade"};
      Spinner sp;
    String resposta;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        MobileAds.initialize(NewUserActivity.this,"ca-app-pub-7164742797981159~2228765994" );

        AdView adView = (AdView)findViewById(R.id.adViewNewUser);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewUserActivity.this,R.layout.personalizar_spinner,opçoes );
        adapter.setDropDownViewResource(R.layout.personalizar_item_spinner);

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



        mAuth = FirebaseAuth.getInstance();
        loguin = (Button) findViewById(R.id.buttonLoguin);

        progressDialog = new ProgressDialog(this);

        loguin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

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
        //    String auxSenhaConfirm = senhaConfirm.getText().toString().trim();

            if (!auxSenhaConfirm.equals(auxSenha)) {
                 Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_LONG).show();
                 return;
            }
            //checking if email and passwords are empty
            if (TextUtils.isEmpty(auxEmail)) {
                Toast.makeText(this, "Email não digitado", Toast.LENGTH_LONG).show();
                return;
            }

            if (resposta.equals("Sua unidade")){
                Toast.makeText(this, "Selecione a sua unidade", Toast.LENGTH_LONG).show();
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


            progressDialog.setMessage("Registrando...");
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(auxEmail, auxSenha)
                    .addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                    database = FirebaseDatabase.getInstance();
                                    user =  mAuth.getCurrentUser();
                                    regMonitoria = database.getReference().child("Usuarios").child(resposta).child(user.getUid()).child("Tipo");
                                    regMonitoria.setValue("");
                                    //userLogin();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(NewUserActivity.this, "Ops! Houve um erro", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
    }


    public void irParaLogin(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

    public void userLogin() {
        String auxEmail = email.getText().toString().trim();
        String auxSenha = senha.getText().toString().trim();


        //logging in the user
        mAuth.signInWithEmailAndPassword(auxEmail, auxSenha).addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //if the task is successfull
                if (task.isSuccessful()) {
                    DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());

                    regMonitoria.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {


                                x = d.getValue(String.class);
                                // Map z = d.getValue(Map.class);
                                // z = z + x;
                                if (x.equals("MONITOR")) {
                                    Toast.makeText(NewUserActivity.this, "Cadastro e logado com sucesso", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
                                } else {
                                    Toast.makeText(NewUserActivity.this, "Cadastrado e logado com sucesso", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), menuAluno.class));
                                }

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(NewUserActivity.this, "Login Error: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {
                    Toast.makeText(NewUserActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                //  progressDialog.dismiss();
            }

        });

        // Código a ser executado quando o anúncio intersticial for fechado.
        //   mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());


        //start the profile activity}
    }

}


