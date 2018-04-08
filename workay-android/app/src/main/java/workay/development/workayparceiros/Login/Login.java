package workay.development.workayparceiros.Login;



import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import workay.development.workayparceiros.BuildConfig;
import workay.development.workayparceiros.Class.Utilitarios;

import workay.development.workayparceiros.OS.MenuOrdemServico;
import workay.development.workayparceiros.R;


public class Login extends AppCompatActivity {

    Typeface font ;
    Typeface fontRegular;
    Button login;
    TextView coloqueLoginSenha, esqueciSenhaLoginSenha,  semSenhaLoginSenha, avisoSemEmail ;
    EditText emailLoginSenha, senhaLoginSenha;
    ImageView imgEmailLoginSenha, imgSenhaLoginSenha, butReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        try {
            Log.i("Entrou","Entrou no diferente de nulo" + " " + mAuth.getCurrentUser().getUid());
            startActivity(new Intent(getApplicationContext(),MenuOrdemServico.class));
        }catch (NullPointerException n) {

        }

        font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");


        // Tela Login emailSenha
        coloqueLoginSenha = (TextView)findViewById(R.id.textViewComentarioTelaDialog);
        emailLoginSenha = (EditText)findViewById(R.id.emailLoginEmailSenha);
        senhaLoginSenha = (EditText)findViewById(R.id.senhaLoginEmailSenha);
        imgEmailLoginSenha = (ImageView)findViewById(R.id.imageEmailLoginSenha) ;
        imgSenhaLoginSenha = (ImageView)findViewById(R.id.imageSenhaLoginSenha) ;
         esqueciSenhaLoginSenha = (TextView)findViewById(R.id.esquecerSenhaLoginEmailSenha);
         semSenhaLoginSenha = (TextView)findViewById(R.id.naoTenhoSenhaLoginSenha);
         avisoSemEmail = (TextView)findViewById(R.id.avisoEmailNaoCadastrado);

        coloqueLoginSenha.setTypeface(font);
        emailLoginSenha.setTypeface(fontRegular);
        senhaLoginSenha.setTypeface(fontRegular);
        esqueciSenhaLoginSenha.setTypeface(fontRegular);
        semSenhaLoginSenha.setTypeface(fontRegular);
        avisoSemEmail.setTypeface(fontRegular);


       // final Button enviar = (Button)findViewById(R.id.butLogin) ;
        login = (Button)findViewById(R.id.butLogin);
        butReturn =(ImageView)findViewById(R.id.butReturnTopLoginEmailSenha);
        login.setTypeface(fontRegular);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params.getString("id").equals("emailSenha")){
                coloqueLoginSenha.setVisibility(View.VISIBLE);
                emailLoginSenha.setVisibility(View.VISIBLE);
                senhaLoginSenha.setVisibility(View.VISIBLE);
                imgEmailLoginSenha.setVisibility(View.VISIBLE);
                imgSenhaLoginSenha.setVisibility(View.VISIBLE);
                esqueciSenhaLoginSenha.setVisibility(View.VISIBLE);
                semSenhaLoginSenha.setVisibility(View.VISIBLE);
                avisoSemEmail.setVisibility(View.GONE);
                login.setText("Entrar");
            }
        }


        final Utilitarios utilitarios = new Utilitarios();

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Versao");

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String  x = d.getValue(String.class);

                    if (!(BuildConfig.VERSION_NAME.equals(x))) {

                        utilitarios.showUpdateAppDialog();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Entrou no entrar","Entrar");

/*
                try {
                   // conectarGet();
                    utilitarios.conectarGet();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

*/

                if (login.getText().equals("Entrar")){


                    if (verificaConexao()){

                        avisoSemEmail.setVisibility(View.GONE);
                        login(((EditText)findViewById(R.id.emailLoginEmailSenha)).getText().toString(),
                            ((EditText)findViewById(R.id.senhaLoginEmailSenha)).getText().toString());

                        }else {
                         Toast.makeText(getApplicationContext(),"Sem conexão com a internet",Toast.LENGTH_SHORT).show();
                        }

                }

                if (login.getText().equals("Próximo")){

                }

                if (login.getText().equals("Enviar")){

                }

            }

        });
/*
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avisoSemEmail.setVisibility(View.VISIBLE);
                semSenhaLoginSenha.setVisibility(View.GONE);
                coloqueLoginSenha.setVisibility(View.VISIBLE);

            }
        });
*/
        esqueciSenhaLoginSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coloqueLoginSenha.setText("Digite seu E-mail cadastrado");
                avisoSemEmail.setVisibility(View.INVISIBLE);
                imgSenhaLoginSenha.setVisibility(View.GONE);
                senhaLoginSenha.setVisibility(View.GONE);
                esqueciSenhaLoginSenha.setVisibility(View.INVISIBLE);
                login.setText("Enviar");


            }
        });

        semSenhaLoginSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coloqueLoginSenha.setText("Digite seu E-mail cadastrado");
                avisoSemEmail.setVisibility(View.VISIBLE);
                imgSenhaLoginSenha.setVisibility(View.GONE);
                senhaLoginSenha.setVisibility(View.GONE);
                semSenhaLoginSenha.setVisibility(View.GONE);
                login.setText("Próximo");
            }
        });

        butReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void login(String email, String senha){

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logando no sistema...");
        progressDialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if the task is successfull
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Log.i("Entrou aqui","entrou aqui2222");
                    startActivity(new Intent(getApplicationContext(),MenuOrdemServico.class));

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();

                }

            }

        });


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

    public void fecharTeclado(View view){
        Utilitarios utilitarios = new Utilitarios();
        utilitarios.fecharTeclado(Login.this,view);
    }

    public void onBackPressed(){
        if (login.getText().equals("Entrar")){
            startActivity(new Intent(getApplicationContext(),TelaInicial.class));
        }

        if (login.getText().equals("Enviar") || login.getText().equals("Próximo") ){
            coloqueLoginSenha.setVisibility(View.VISIBLE);
            emailLoginSenha.setVisibility(View.VISIBLE);
            senhaLoginSenha.setVisibility(View.VISIBLE);
            imgEmailLoginSenha.setVisibility(View.VISIBLE);
            imgSenhaLoginSenha.setVisibility(View.VISIBLE);
            esqueciSenhaLoginSenha.setVisibility(View.VISIBLE);
            semSenhaLoginSenha.setVisibility(View.VISIBLE);
            avisoSemEmail.setVisibility(View.GONE);
            login.setText("Entrar");
        }


    }


    /*

    class conexao extends AsyncTask<String, Void, String > {



    public void conectarGet() throws IOException {
        URL url = new URL("http://demo8993843.mockable.io/");
        HttpURLConnection urlConnection = null;


        try {
            urlConnection = (HttpURLConnection)url.openConnection();

            InputStream in = urlConnection.getInputStream();
/*
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        StringBuffer buffer = new StringBuffer();
        while ((linha = reader.readLine()) != null) {
            buffer.append(linha + "\n");
        }

        Toast.makeText(getApplicationContext(),buffer.toString(),Toast.LENGTH_SHORT).show();

        if (buffer.length() == 0) {
            return;
        }

        if (urlConnection != null) {
            urlConnection.disconnect();
        }

        if (reader != null) {
            reader.close();
        }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
    */
}
