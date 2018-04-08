package workay.development.workayparceiros.OS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.Login.Cadastro;
import workay.development.workayparceiros.Login.Login;
import workay.development.workayparceiros.Login.TelaInicial;
import workay.development.workayparceiros.R;

public class MenuOrdemServico extends AppCompatActivity implements Serializable {

    Map map, mapId, mapEmail;
    Set set, setId, setEmail;
    ProgressDialog progressDialog;
    ListView listView;
    List total = new ArrayList();

    int contMarcada=0, contAguardando=0, contRevisao=0,contAceito=0,contRejeitado=0,contFinalizado=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ordem_servico);

        // progressDialog.setMessage("");
        //  progressDialog.show();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando Dados");
        progressDialog.show();




        TextView topoOS = (TextView)findViewById(R.id.textViewTopOrdensServico);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        topoOS.setTypeface(font);

        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();


        ImageView imageView = (ImageView)findViewById(R.id.logoWorkayMenuOS);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), TelaInicial.class));

            }
        });



        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("ID_Usuários").child(mAuth.getCurrentUser().getUid());
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
//                    String aux = (String) dataSnapshot.getValue();
                    String aux = d.getValue(String.class);

                    BuscarOrdem(aux);

                    Log.i("ID", "ID usuário " + aux);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.listOrdemDeServiço);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrdemServico ordemServico = (OrdemServico) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), DetalhesOrdemServico.class);
                Bundle bundle = new Bundle();
                bundle.putString("client", ordemServico.getNomeCliente());
                bundle.putString("titulo", ordemServico.getTitulo());
                bundle.putString("endereco", ordemServico.getEndereco());
                bundle.putString("desc", ordemServico.getDescricao());
                bundle.putString("status", ordemServico.getStatus());
                bundle.putString("tipo", ordemServico.getTipoServico());
                bundle.putString("id",ordemServico.getIdOS());
                bundle.putString("envio", ordemServico.getDataEnvio());
                bundle.putString("marcacao", ordemServico.getDataMarcacao());
                bundle.putString("realizacao", ordemServico.getDataRealizacao());
                bundle.putString("prazo", ordemServico.getPrazo());
                bundle.putString("cpf",ordemServico.getCpf());
                bundle.putString("email",ordemServico.getEmail());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

/*

*/

    }

    private void BuscarOrdem(final String cpf) {
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico");

        map = new HashMap();
        set = new HashSet();
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }


                try {
                    set = map.keySet();
                } catch (

                        NullPointerException n) {
                    Toast.makeText(MenuOrdemServico.this, "Sem ordens de serviço", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Object i : set) {
                    String email = (String) i;
                    Log.i("CPF", cpf + " - " + email);

                    idOrdemServico(cpf, email);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void idOrdemServico(final String cpf, final String email) {
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email);
        // DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference();
        // regMarkers.push();

        mapId = new HashMap();
        setId = new HashSet();
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    mapId = (Map<String, Object>) dataSnapshot.getValue();
                }


                try {
                    setId = mapId.keySet();
                } catch (

                        NullPointerException n) {
                    Toast.makeText(MenuOrdemServico.this, "Sem ordens de serviço", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Object i : setId) {
                    String idServico = (String) i;
                    Log.i("CPF", cpf + " - " + " - " + email + idServico);
                    detalhesServico(cpf, email, idServico);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void detalhesServico(final String cpf, final String email, String idServico) {
       // Log.i("CPFaaaaa", cpf + " - " + " - " + email + idServico);
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idServico);
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                OrdemServico ordemServico = dataSnapshot.getValue(OrdemServico.class);
                ordemServico.setCpf(cpf);
                ordemServico.setEmail(email);



                if (ordemServico.getStatus().equals("Visita Marcada")) {
                    total.add(contMarcada, ordemServico);
                    contMarcada++;
                }else {
                    if (ordemServico.getStatus().equals("Aguardando Orçamento")){
                        total.add(contMarcada + contAguardando, ordemServico);
                        contAguardando++;
                    }else{
                        if ((ordemServico.getStatus().equals("Em revisão"))){
                            total.add(contMarcada + contAguardando + contRevisao, ordemServico);
                            contRevisao++;
                        }else{
                            if ((ordemServico.getStatus().equals("Aceito"))){
                                total.add(contMarcada + contAguardando + contRevisao + contAceito, ordemServico);
                                contAceito++;
                            }
                            else{
                                if ((ordemServico.getStatus().equals("Rejeitado"))){
                                    total.add(contMarcada + contAguardando + contRevisao + contAceito + contRejeitado, ordemServico);
                                    contRejeitado++;
                                }else{
                                    if ((ordemServico.getStatus().equals("Finalizado"))) {
                                        total.add(contMarcada + contAguardando + contRevisao + contAceito + contRejeitado, ordemServico);
                                        contFinalizado++;
                                    }
                                }

                            }
                        }
                    }


                }

                Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
                Typeface fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");

                ListaPersonalizada listaPersonalizada = new ListaPersonalizada(total, MenuOrdemServico.this,font,fontLight);

                listView.setAdapter(listaPersonalizada);
                progressDialog.dismiss();





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), TelaInicial.class));
    }
}