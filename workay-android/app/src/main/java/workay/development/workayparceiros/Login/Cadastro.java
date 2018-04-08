package workay.development.workayparceiros.Login;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;


import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Date;

import workay.development.workayparceiros.BuildConfig;
import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.Class.Utilitarios;
import workay.development.workayparceiros.OS.MenuOrdemServico;
import workay.development.workayparceiros.R;

public class Cadastro extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

/*
            try {
                Log.i("Entrou","Entrou no diferente de nulo" + " " + mAuth.getCurrentUser().getUid());
                startActivity(new Intent(getApplicationContext(),MenuOrdemServico.class));
            }catch (NullPointerException n) {

            }

*/
        DatabaseReference regMarkers;

        /*
        regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child("17893829102").child("Perfil");
        Perfil perfil = new Perfil();
        perfil.setNome(" ");
        regMarkers.setValue(perfil);
        */

       /*
        Date date = new Date();
        String idOS = ("Hidraulica"+ date.getTime()).replace(".","");


        regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child("17893829102").child("OrdemServico").child("luisccfreitas@bol,com,br").child(idOS);

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setNomeCliente("Marcia Freitas");
        ordemServico.setTitulo("Hidráulica");
        ordemServico.setStatus("Aguardando Orçamento");
        ordemServico.setPrazo("04/02/2018");
        ordemServico.setTipoServico("Hidraulico");
        regMarkers.setValue(ordemServico);
        */




        Date date = new Date();
        String idOS = ("Hidraulica"+ date.getTime()).replace(".","");


        regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child("17893829102").child("OrdemServico").child("luisccfreitas@bol,com,br").child(idOS);

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setIdOS(idOS);
        ordemServico.setNomeCliente("Marcia Freitas");
        ordemServico.setTitulo("Hidráulica");
        ordemServico.setStatus("Visita Marcada");
        ordemServico.setPrazo("04/02/2018");
        ordemServico.setDataRealizacao("04/02/2018");
        ordemServico.setDataEnvio("04/02/2018");
        ordemServico.setDataMarcacao("05/02/2018");
        ordemServico.setEndereco("Rua Igarapava, 50/50");
        ordemServico.setDescricao("Possível Infiltração");
        ordemServico.setTipoServico("Hidraulico");
        regMarkers.setValue(ordemServico);




        /*
        perfil = new Perfil();
        regMarkers = FirebaseDatabase.getInstance().getReference().child("ID_Usuários").child(mAuth.getCurrentUser().getUid());
        perfil.setCpf("17893829102");
        regMarkers.setValue(perfil);
        */

       // DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Perfil");
/*
        if (mAuth != null){
            startActivity(new Intent(getApplication(),MenuOrdemServico.class));
        }
*/
        Button cadastro = (Button)findViewById(R.id.buttonCadastro);
        TextView loginExist = (TextView)findViewById(R.id.loginExistCadastro);
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


        loginExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaConexao()){
                    cadastro(((EditText)findViewById(R.id.emailCadastro)).getText().toString(),
                            ((EditText)findViewById(R.id.senhaCadastro)).getText().toString(),
                            ((EditText)findViewById(R.id.confirmSenhaCadastro)).getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),"Sem conexão com a internet",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void cadastro(String email, String senha, String confirmarSenha){

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Escreva seu email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Escreva sua senha de confirmação", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(senha.equals(confirmarSenha))){
            Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_LONG).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logando no sistema...");
        progressDialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),MenuOrdemServico.class));

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Cadastro.this, "Ops! Houve um erro", Toast.LENGTH_LONG).show();
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


}
