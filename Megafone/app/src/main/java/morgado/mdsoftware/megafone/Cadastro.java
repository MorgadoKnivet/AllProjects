package morgado.mdsoftware.megafone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {

    private EditText nome, idade, identidade, email, senha, confirmarSenha;
    private FirebaseAuth auth;
    private String resposta;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        auth = FirebaseAuth.getInstance();

        Log.i("Script", "Entrou");

        final String opçoes[] = new String[]{"Gênero","Feminino","Masculino"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Cadastro.this,R.layout.activity_personalizar_spinner,opçoes ){};
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        Spinner spinner = (Spinner)findViewById(R.id.spinnerCadastro);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resposta = opçoes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressDialog = new ProgressDialog(Cadastro.this);
        Button but = (Button)findViewById(R.id.butCadastro);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = (EditText)findViewById(R.id.nomeCadastro);
                idade = (EditText)findViewById(R.id.idadeCadastro);
                email = (EditText)findViewById(R.id.emailCadastro);
                identidade = (EditText)findViewById(R.id.identidadeCadastro);
                senha = (EditText)findViewById(R.id.senhaCadastro);
                confirmarSenha = (EditText)findViewById(R.id.confirmarCadastro);

                cadastro(resposta);
            }
        });



    }


    public void cadastro(String resposta){

       final String nome = this.nome.getText().toString().trim();
       final String idade = this.idade.getText().toString().trim();
        final String identidade = this.identidade.getText().toString().trim();
       final  String email = this.email.getText().toString().trim();
       final String senha = this.senha.getText().toString().trim();
        String cSenha = this.confirmarSenha.getText().toString().trim();

        if (!(senha.equals(cSenha))){
            Toast.makeText(this, "Suas senhas estão diferentes", Toast.LENGTH_LONG).show();
            return;
        }

        if (resposta.equals("Gênero")){
            Toast.makeText(this, "Selecione seu gênero", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Criando conta");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuários").child(auth.getCurrentUser().getUid());

                    Usuario usuario = new Usuario(nome,identidade,email,idade);
                    regMonitoria.setValue(usuario);

                    progressDialog.setMessage("Logando no sistema");

                    auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //ir para menu
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MenuIntarface.class));

                            }else{
                                Toast.makeText(Cadastro.this,"E-mail ou senha incorretos",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // fazer progresso de dialogo

                } else {
                    Toast.makeText(Cadastro.this,"Ocorreu uma falha no cadastro",Toast.LENGTH_SHORT).show();
                      progressDialog.dismiss();
                }
            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), Principal.class));

    }

}
