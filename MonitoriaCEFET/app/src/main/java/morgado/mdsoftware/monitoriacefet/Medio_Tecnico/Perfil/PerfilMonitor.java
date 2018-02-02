package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Perfil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.Avaliar.AvaliarMonitorBusca;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro.LoginActivity;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.MenuMonitor;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades.menuAluno;
import morgado.mdsoftware.monitoriacefet.R;

public class PerfilMonitor extends AppCompatActivity {

    String celular, email, facebook, outros, info1,info2, info3, info4, aviso1, aviso2, aviso3, aviso4, nome;
    EditText auxInfo1, auxInfo2, auxInfo3, auxInfo4, auxCelular, auxEmail, auxFB, auxOutros;
    TextView tvCelular, tvEmail, tvFb, tvOutros, tvCarregando;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    ArrayList auxDetalhesContatos, auxDetalhesAvisos;
    ProgressDialog progressDialog;




    Date date = new Date();
    SimpleDateFormat sdf;
    String str, returnNome;
    private String aux;
   public static boolean primeiraVez = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_monitor);
        MobileAds.initialize(PerfilMonitor.this, "ca-app-pub-7164742797981159~2228765994");
/*
    Estrutura do banco de dados

    Raiz - Perfil - UserID - Contatos
    Raiz - Perfil - UserID - Avisos

    Raiz - Perfil - Visualizar

 */
        final AdView adView ;
        adView = (AdView) findViewById(R.id.adViewPerfilMonitor);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        final Button atualizar = (Button)findViewById(R.id.atualizarPerfilDados);
        final TextView titulo = (TextView)findViewById(R.id.tituloPerfil);
        final TextView aviso = (TextView)findViewById(R.id.avisoMonitorPerfil);

        final TextView tituloCadastro = (TextView)findViewById(R.id.titulPerfilCadastro);
        final EditText nomeMontitor = (EditText)findViewById(R.id.nomeCadastroPerfil);
        final Button enviar = (Button)findViewById(R.id.enviarCadastroPerfil);

        // Progress Bar
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        tvCarregando = (TextView)findViewById(R.id.tvCarregando);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE,android.graphics.PorterDuff.Mode.MULTIPLY);
        // EditText aviso //

        auxInfo1 = (EditText)findViewById(R.id.perfilAvisoEdit1) ;
        auxInfo2 = (EditText)findViewById(R.id.perfilAvisoEdit2) ;
        auxInfo3 = (EditText)findViewById(R.id.perfilAvisoEdit3) ;
        auxInfo4 = (EditText)findViewById(R.id.perfilAvisoEdit4) ;

        // Fim EditText aviso //

        // TextView e EditText relacionados a contato //

        auxCelular = (EditText)findViewById(R.id.perfilContatoEditCelular) ;
        auxFB = (EditText)findViewById(R.id.perfilContatoEditFB) ;
        auxEmail = (EditText)findViewById(R.id.perfilContatoEditEmail) ;
        auxOutros = (EditText)findViewById(R.id.perfilContatoEditOutros) ;

        tvCelular = (TextView)findViewById(R.id.textViewCelular) ;
        tvEmail = (TextView)findViewById(R.id.textViewEmail) ;
        tvFb = (TextView)findViewById(R.id.textViewFB) ;
        tvOutros = (TextView)findViewById(R.id.textViewOutros) ;


        // FIM TextView e EditText relacionados a contato //



        mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid());

// verifica se é um usuário antigo que não tem conta cadastrada
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Log.i("Usuário antigo",""+primeiraVez);
                    primeiraVez = true;
                    Log.i("Usuário antigo depois",""+primeiraVez);
                    if (primeiraVez){
                        Log.i("Usuário antigo dentro ",""+primeiraVez);

                        progressBar.setVisibility(View.GONE);
                        tvCarregando.setVisibility(View.GONE);
                        tvCelular.setVisibility(View.VISIBLE);
                        tvEmail.setVisibility(View.VISIBLE);
                        tvFb.setVisibility(View.VISIBLE);
                        tvOutros.setVisibility(View.VISIBLE);

                        auxInfo1.setVisibility(View.VISIBLE);
                        auxInfo2.setVisibility(View.VISIBLE);
                        auxInfo3.setVisibility(View.VISIBLE);
                        auxInfo4.setVisibility(View.VISIBLE);
                        auxCelular.setVisibility(View.VISIBLE);
                        auxEmail.setVisibility(View.VISIBLE);
                        auxFB.setVisibility(View.VISIBLE);
                        auxOutros.setVisibility(View.VISIBLE);
                        adView.setVisibility(View.VISIBLE);

                        titulo.setVisibility(View.VISIBLE);

                        aviso.setVisibility(View.VISIBLE);

                        atualizar.setVisibility(View.VISIBLE);

                        tituloCadastro.setVisibility(View.GONE);
                        nomeMontitor.setVisibility(View.GONE);
                        enviar.setVisibility(View.GONE);

                        retornarDadosAviso();
                        retornarDadosContato();
                        primeiraVez = false;
                        return;


                    }
                }
                Log.i("CRL ENTROU AQUI ","que tedio");
                if(!(primeiraVez)){
                    progressBar.setVisibility(View.GONE);
                    tvCarregando.setVisibility(View.GONE);
                    tituloCadastro.setVisibility(View.VISIBLE);
                    nomeMontitor.setVisibility(View.VISIBLE);
                    enviar.setVisibility(View.VISIBLE);

                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





// termina aqui

         // variáveis para criar a parte da data



        // termina aqui
        /*

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                nome =  params.getString("Nome").trim();
            }
        }

        */

        // abaixo serve para o touch do ListView funcionar junto do Scroll View

        // Quando o botão de atualizar for criado
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info4 = ((EditText)findViewById(R.id.perfilAvisoEdit4)).getText().toString();
                info3 = ((EditText)findViewById(R.id.perfilAvisoEdit3)).getText().toString();
                info2 = ((EditText)findViewById(R.id.perfilAvisoEdit2)).getText().toString();
                info1 = ((EditText)findViewById(R.id.perfilAvisoEdit1)).getText().toString();
                outros = ((EditText)findViewById(R.id.perfilContatoEditOutros)).getText().toString();
                facebook = ((EditText)findViewById(R.id.perfilContatoEditFB)).getText().toString();
                email = ((EditText)findViewById(R.id.perfilContatoEditEmail)).getText().toString();
                celular = ((EditText)findViewById(R.id.perfilContatoEditCelular)).getText().toString();

                retornaNome();



            }
        });
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(auxFB.getWindowToken(), 0);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(auxCelular.getWindowToken(), 0);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(auxOutros.getWindowToken(), 0);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(auxEmail.getWindowToken(), 0);

    }
    // regMonitoriaVisualizar = database.getReference().child("Perfil").child("Contatos");
    public void atualizarDados(String nome){

        DatabaseReference regMonitoria,  regMonitoriaVisualizar, regMonitoriaData;
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();


        sdf = new SimpleDateFormat("dd-MM-yy");

        try {
            date = sdf.parse(""+date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        str = sdf.format(date);

        Log.i("Data atualizada",str);

        // ---------------------------------- //

        DadosMonitor dadosMonitorContatos = new DadosMonitor();

        regMonitoria = database.getReference().child("Perfil").child("Editar").child(""+mAuth.getCurrentUser().getUid()).child("Contatos");

        dadosMonitorContatos.setCelular(celular);
        dadosMonitorContatos.setEmail(email);
        dadosMonitorContatos.setFacebook(facebook);
        dadosMonitorContatos.setOutros(outros);

        regMonitoria.setValue(dadosMonitorContatos);

        // ------------------------------ //

        DadosMonitor dadosMonitorAvisos = new DadosMonitor();

        regMonitoria = database.getReference().child("Perfil").child("Editar").child(""+mAuth.getCurrentUser().getUid()).child("Avisos");

        dadosMonitorAvisos.setInfo1(info1);
        dadosMonitorAvisos.setInfo2(info2);
        dadosMonitorAvisos.setInfo3(info3);
        dadosMonitorAvisos.setInfo4(info4);
        regMonitoria.setValue(dadosMonitorAvisos);

        //  -------------------------------- //

        regMonitoria = database.getReference().child("Perfil").child("Visualizar").child(nome).child("Contatos");
        regMonitoriaVisualizar =  database.getReference().child("Perfil").child("Visualizar").child(nome).child("Avisos");
        regMonitoriaVisualizar.setValue(dadosMonitorAvisos);
        regMonitoria.setValue(dadosMonitorContatos);


        // DATA atualizada //

        regMonitoriaData = database.getReference().child("Perfil").child("Visualizar").child(nome).child("UltimaData");
        Monitoria data = new Monitoria();;
        data.setDia(str);
        regMonitoriaData.setValue(data);


        Toast.makeText(this, "Seu perfil foi atualizado", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
        finish();


    }

    public void retornaNome(){

    //    regMonitoria = database.getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("NomeMonitor");
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("NomeMonitor");

        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);
                    Log.i("Detalhes_Nome do Return","Entrou no onDaTACHANGE" + aux);
                    atualizarDados(aux);
                    return;
                }
                Log.i("Detalhes_Nome do Return","Entrou no onDaTACHANGE" + aux);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void retornarDadosContato(){

        auxDetalhesContatos = new ArrayList();

        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("Contatos");

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);


                    auxDetalhesContatos.add(aux);

                }

                try {

                   auxCelular.setText(""+auxDetalhesContatos.get(0));
                    auxEmail.setText(""+auxDetalhesContatos.get(1));
                    auxFB.setText(""+auxDetalhesContatos.get(2));
                    auxOutros.setText(""+auxDetalhesContatos.get(3));


                }catch (IndexOutOfBoundsException i){
                    // Tratando erro
                }catch (NullPointerException n){
                    // Tratando erro
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void retornarDadosAviso(){

        auxDetalhesAvisos = new ArrayList();


        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("Avisos");

        regDetalhes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    String aux = d.getValue(String.class);

                    Log.i("Detalhes_Aviso", aux);
                    auxDetalhesAvisos.add(aux);

                }

                try {
                    auxInfo1.setText(""+auxDetalhesAvisos.get(0));
                    auxInfo2.setText(""+auxDetalhesAvisos.get(1));
                    auxInfo3.setText(""+auxDetalhesAvisos.get(2));
                    auxInfo4.setText(""+auxDetalhesAvisos.get(3));

                    aviso1 = ""+auxDetalhesAvisos.get(0);
                    aviso2 = ""+auxDetalhesAvisos.get(1);
                    aviso3 = ""+auxDetalhesAvisos.get(2);
                    aviso4 = ""+auxDetalhesAvisos.get(3);


                }catch (IndexOutOfBoundsException i){
                    // aa
                }catch (NullPointerException n){
                    //Erro tratado
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void enviar(){
        DatabaseReference regMonitoria, regMonitoria2;
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        String nomeMontitor = ((EditText)findViewById(R.id.nomeCadastroPerfil)).getText().toString().toUpperCase();

        regMonitoria = database.getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("NomeMonitor");
        regMonitoria2= database.getReference().child("Perfil").child("Visualizar").child(nomeMontitor).child("NomeMonitor");

        DadosMonitor dadosMonitorNome = new DadosMonitor();
        dadosMonitorNome.setNome(nomeMontitor);

        regMonitoria.setValue(dadosMonitorNome);
        regMonitoria2.setValue(dadosMonitorNome);
        startActivity(new Intent(getApplicationContext(), PerfilMonitor.class));
        return;



    }

    public void retornarObsAntiga(){
        auxDetalhesAvisos = new ArrayList();

        DatabaseReference regDetalhes = FirebaseDatabase.getInstance().getReference().child("Perfil").child("Editar").child(mAuth.getCurrentUser().getUid()).child("Avisos");

    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MenuMonitor.class));
    }
}
