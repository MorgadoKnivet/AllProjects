package workay.development.workayparceiros.OS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

public class Diagnostico extends AppCompatActivity {
    Spinner spinner;
    TextView prazoTv;
    ArrayList list = new ArrayList();
    ListView listView;
    String respostaSpinner;
    ListaDiagnostico listaDiagnostico;
    Typeface bold, fontLight, medium, regular;
    String cpf, idOS, email;
    Map  map;
    Set set;
    double soma = 0.0;
    int j = 0;
    OrdemServico os1, os2, os3, os4, os5 ;
    FirebaseAuth mAuth;
    Switch sw;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);

        bold= Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");
        regular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_regular.ttf");

        TextView id = (TextView)findViewById(R.id.textViewIDDiagnostico);
        TextView titulo = (TextView)findViewById(R.id.tituloMateriais);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params!= null){


                idOS = params.getString("idOS").trim();
                id.setText(idOS);
                cpf = params.getString("cpf").trim();
                email = params.getString("email").trim();
            }
        }

        id.setText(idOS);
        titulo.setTypeface(bold);
        id.setTypeface(bold);

        TextView visita = (TextView)findViewById(R.id.textViewVisitaDiagnostico) ;
        TextView diagnostico = (TextView)findViewById(R.id.textViewDiagnosticoDiagnostico) ;
        TextView materiais = (TextView)findViewById(R.id.textViewMateriaisDiagnostico) ;
        TextView revisao = (TextView)findViewById(R.id.textViewRevisaoDiagnostico) ;

        visita.setTypeface(regular);
        diagnostico.setTypeface(regular);
        materiais.setTypeface(regular);
        revisao.setTypeface(regular);


        listView = (ListView)findViewById(R.id.listDiagnostico);

        mAuth = FirebaseAuth.getInstance();

        os1 = new OrdemServico("");
        os2 = new OrdemServico("");
        os3 = new OrdemServico("");
        os4 = new OrdemServico("");
        os5 = new OrdemServico("");
        os1.setStatus("1");
        os2.setStatus("2");
        os3.setStatus("3");
        os4.setStatus("4");
        os5.setStatus("5");


        // Liberar | Bloquear tela

        sw = (Switch)findViewById(R.id.switchDiagnostico);
        final TextView swTv = (TextView)findViewById(R.id.textViewSwitchDiagnostico);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swTv.setText("Liberar Edição");
                    sw.setTrackResource(R.drawable.switch_diagnostico_on);
                }else {
                    swTv.setText("Salvar orçamento");
                    sw.setTrackResource(R.drawable.switch_diagnostico_off);
                }
            }
        });

        // ir para materiais
        materiais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swTv.getText().equals("Liberar Edição")){
                    Intent intent = new Intent(getApplicationContext(),Materiais.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idOS",idOS);
                    bundle.putString("cpf",cpf);
                    bundle.putString("email",email);
                    bundle.putInt("numServicos",list.size()-3 );
                    bundle.putDouble("precoServicos",listaDiagnostico.getSoma());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idOS).child("DadosInternosOS").child("servicos");

        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ListaServicos","Entrou no On Data Change ");

                /*
                if (dataSnapshot == null) {
                    list.add(os3);
                    list.add(os4);
                    list.add(os5);
                    listaDiagnostico = new ListaDiagnostico(list,Diagnostico.this,bold,fontLight,medium);
                    listView.setAdapter(listaDiagnostico);
                    Log.i("AddNaLista","AdicinarStatus3");
                    progressDialog.dismiss();
                    Log.i("Entrou no Return","ENTROU NO RETURN");
                    return;
                }else {*/
                        map = (Map<String, Object>) dataSnapshot.getValue();
                        if (map == null){
                            list.add(os3);
                            list.add(os4);
                            list.add(os5);
                            listaDiagnostico = new ListaDiagnostico(list,Diagnostico.this,bold,fontLight,medium);
                            listView.setAdapter(listaDiagnostico);
                            Log.i("AddNaLista","AdicinarStatus3");
                            progressDialog.dismiss();
                            Log.i("Entrou no Return","ENTROU NO RETURN");
                            return;
                        }else {





                            try {
                                set = map.keySet();
                            } catch (

                                    NullPointerException n) {
                                //       Toast.makeText(Diagnostico.this, "Sem ordens de serviço", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String existPrazo = "";
                            for (Object i : set) {
                                String aux = (String) i;
                                if (!aux.equals("prazo")) {
                                    Log.i("ListaServicos", "ListaServicos  " + aux);
                                    buscaServicos(aux, set.size(),existPrazo);
                                }else{
                                    existPrazo = "sim";
                                }


                            }
                        }
                    }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final OrdemServico ordemServico = (OrdemServico) parent.getAdapter().getItem(position);
                TextView prazo = (TextView)view.findViewById(R.id.textViewPrazoDiagnostico);
                prazoTv = (TextView)view.findViewById(R.id.respostaPrazoDiagnostico);
                TextView novoServico = (TextView)view.findViewById(R.id.novoServicoDiagnostico);
//                Toast.makeText(getApplicationContext(),ordemServico.getPreco()+"teste",Toast.LENGTH_SHORT).show();

                if (ordemServico.getStatus().equals("4")){
                    dialogPrazo();
                }

                if (ordemServico.getStatus().equals("3")){
                    Intent intent = new Intent(getApplicationContext(),NovoServico.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idOS",idOS);
                    bundle.putString("cpf",cpf);
                    bundle.putString("email",email);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }


    private void buscaServicos(String id, final int tamanho, final String existPrazo) {

        final DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idOS).child("DadosInternosOS").child("servicos").child(id);
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(OrdemServico.class) == null ){
                    Log.i("Entrou no Return","ENTROU NO RETURN");
                    progressDialog.dismiss();
                    return;
                }else {
                    OrdemServico ordemServico = dataSnapshot.getValue(OrdemServico.class);

                    if (ordemServico != null) {
                        // Toast.makeText(getApplicationContext(),ordemServico.getDescricao() + "Teste",Toast.LENGTH_SHORT).show();
                        if (ordemServico.getDescricao() == null || ordemServico.getDescricao().equals("")){
                            ordemServico.setStatus("2");
                            list.add(ordemServico);
                            Log.i("AddNaLista","AdicinarStatus2");
                        }else {
                            ordemServico.setStatus("1");
                            list.add(ordemServico);
                            Log.i("AddNaLista","AdicinarStatus1");
                        }

                    }

                 //   for (int i=0;i<list.size();i++){
                        if (existPrazo.equals("sim")){
                            if (list.size() == tamanho-1) {
                                list.add(os3);
                                Log.i("AddNaLista", "AdicinarStatus3");
                                listaDiagnostico = new ListaDiagnostico(list, Diagnostico.this, bold, fontLight, medium);
                                listView.setAdapter(listaDiagnostico);
                            }
                        }else {

                            if (list.size() == tamanho) {
                                list.add(os3);
                                Log.i("AddNaLista", "AdicinarStatus3");
                                listaDiagnostico = new ListaDiagnostico(list, Diagnostico.this, bold, fontLight, medium);
                                listView.setAdapter(listaDiagnostico);
                            }
                        }
                //    }
                    if (j==0) {
                        retornarPrazo();
                         progressDialog.dismiss();
                        j=1;
                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // Log.i("Soma","resultado"+soma); // soma volta a ser zero


    }

    private void dialogPrazo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Diagnostico.this);

        final LayoutInflater inflater = LayoutInflater.from(Diagnostico.this);
        final View inflator = inflater.inflate(R.layout.tela_dialog_tempo_servico, null);

        Button tempo = (Button)inflator.findViewById(R.id.salvarTempoServico);

        final String[] opçoes = new String[]{"dias","semanas","meses"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Diagnostico.this,R.layout.activity_personalizar_spinner,opçoes);
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        final EditText tempoED = (EditText)inflator.findViewById(R.id.tempoTelaDialogTempoServico);
        final Typeface bold= Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        tempo.setTypeface(bold);
        tempoED.setTypeface(bold);


        spinner = (Spinner)inflator.findViewById(R.id.spinnerTelaDialogServico);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                respostaSpinner = opçoes[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(inflator);
        alertDialog.show();



        tempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<list.size();i++){
                    OrdemServico aux = (OrdemServico)list.get(i);
                    if (aux.getStatus().equals("4")){
                        aux.setData(tempoED.getText().toString());
                        aux.setPrazo(tempoED.getText().toString() + " " + respostaSpinner);
                        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idOS).child("DadosInternosOS").child("servicos").child("prazo");
                        OrdemServico prazo = new OrdemServico();
                         prazo.setPrazo(tempoED.getText().toString() + " " + respostaSpinner);
                         regMarkers.setValue(prazo);

                        final Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
                        final Typeface fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
                        final Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");
                        for (int k=0;k<list.size();k++){
                            OrdemServico ordemServico = (OrdemServico)list.get(k);
                            if (ordemServico.getStatus().equals("5")){
                                ordemServico.setPreco(listaDiagnostico.getSoma()+"");
                            }
                        }

                        listaDiagnostico = new ListaDiagnostico(list,Diagnostico.this,font,fontLight,medium);
                        listView.setAdapter(listaDiagnostico);
                        listView.setSelection(listView.getAdapter().getCount()-1);
                    }
                }
                alertDialog.dismiss();
            }
        });
    }

    private void retornarPrazo(){
        final DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idOS).child("DadosInternosOS").child("servicos").child("prazo");
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(OrdemServico.class) == null ){
                    os4.setPrazo("");

              //      list.add(os3);
                    list.add(os4);
                    list.add(os5);
                    os5.setPreco("erro_valor_retornar_prazo_vazio");

                    Log.i("AddNaLista","AdicinarStatus5");
                    listaDiagnostico = new ListaDiagnostico(list,Diagnostico.this,bold,fontLight,medium);
                    listView.setAdapter(listaDiagnostico);
                    progressDialog.dismiss();
                    return;
                }else {

                    OrdemServico ordemServico = dataSnapshot.getValue(OrdemServico.class);
                    Log.i("AddNaLista","AdicinarStatus4");
                    ordemServico.setStatus("4");
                    list.add(ordemServico);

                    OrdemServico ordemServico2 = dataSnapshot.getValue(OrdemServico.class);
                    ordemServico2.setStatus("5");
                    list.add(ordemServico2);

                    Log.i("AddNaLista","AdicinarStatus5");
                    listaDiagnostico = new ListaDiagnostico(list,Diagnostico.this,bold,fontLight,medium);
                    listView.setAdapter(listaDiagnostico);
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed(){
        finish();
    }


}
