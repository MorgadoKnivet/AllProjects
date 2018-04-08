package workay.development.workayparceiros.OS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import workay.development.workayparceiros.Class.OrdemServico;

import workay.development.workayparceiros.Class.Utilitarios;
import workay.development.workayparceiros.Login.Login;
import workay.development.workayparceiros.R;

public class NovoServico extends AppCompatActivity {
    Map  map, mapA, mapId;
    Set  set, setA, setId;
    ArrayList os;
    ListaBuscaServico buscaServico;
    ListView list;
    ArrayList listAux;
    String cpf, idOS, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_servico);

         os = new ArrayList();
        final Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        final Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        final Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");

        TextView id = (TextView)findViewById(R.id.textViewIDNovoServico);
        id.setTypeface(bold);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params!= null){


               idOS = params.getString("idOS").trim();
                    cpf = params.getString("cpf").trim();
                email = params.getString("email").trim();

                id.setText(idOS);
            }
        }

        TextView comodo = (TextView)findViewById(R.id.comodoNovoServico);
        TextView infAdd = (TextView)findViewById(R.id.informacoesAdicionaisNovoServico);
        final EditText comodoET = (EditText)findViewById(R.id.editTextComodoNovoServico);
        final EditText infoAddET = (EditText)findViewById(R.id.editTextInformacoesNovoServico);
        final EditText valor = (EditText)findViewById(R.id.precoNovoServico);


        comodoET.setTypeface(font);
        infoAddET.setTypeface(font);

        comodo.setTypeface(bold);
        infAdd.setTypeface(bold);

        list = (ListView)findViewById(R.id.listNovoServico);

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child("17893829102").child("OrdemServico").child("luisccfreitas@bol,com,br");
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
                    Toast.makeText(NovoServico.this, "Sem ordens de serviço", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Object i : setId) {
                    String aux = (String)i;
                    os.add(aux);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mapId = new HashMap();
        setId = new HashSet();



        final EditText buscar = (EditText)findViewById(R.id.buscarServicoNovoServico);
      //  final Button salvar = (Button)findViewById(R.id.adicionarOrcamentoNovoOrcamento);




        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                listAux = new ArrayList();
                for (int i=0;i<os.size();i++){
                    String aux  = (String)os.get(i);
                    if (aux.contains(s)){
                        listAux.add(aux);
                    }
                }

              //  if (!((buscar.getText().equals("")) || TextUtils.isEmpty(buscar.getText().toString()))
               //         && listAux.size()==0){
                    listAux.add("registrar_novo_servico");
               // }

                if ((buscar.getText().equals("")) || TextUtils.isEmpty(buscar.getText().toString())){
                    ArrayList a = new ArrayList();
                    buscaServico = new ListaBuscaServico(a,NovoServico.this,font,bold);
                    list.setAdapter(buscaServico);
                }else{
                    buscaServico = new ListaBuscaServico(listAux,NovoServico.this,font,bold);
                    list.setAdapter(buscaServico);
                }


               // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String auxOs = (String)listAux.get(position);
                buscar.setText(auxOs);

                ArrayList a = new ArrayList();
                buscaServico = new ListaBuscaServico(a,NovoServico.this,font,bold);
                list.setAdapter(buscaServico);
            }
        });

        Button add = (Button)findViewById(R.id.adicionarOrcamentoNovoServico);
        add.setTypeface(medium);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                            OrdemServico servico = new OrdemServico();
                            servico.setComodo(comodoET.getText().toString());

                            servico.setDescricao(infoAddET.getText().toString());

                            if (TextUtils.isEmpty(buscar.getText().toString())) {
                                Toast.makeText(NovoServico.this, "Ei... Você esqueceu do serviço", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                servico.setTitulo(buscar.getText().toString());

                            }

                            if (TextUtils.isEmpty(valor.getText().toString())) {
                                 Toast.makeText(NovoServico.this, "Escreva um valor", Toast.LENGTH_SHORT).show();
                                 return;
                             }else {
                                servico.setPreco(valor.getText().toString());

                            }

                            DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(cpf).child("OrdemServico").child(email).child(idOS).child("DadosInternosOS").child("servicos").child(servico.getTitulo());
                            regMarkers.setValue(servico);
                            Intent intent = new Intent(getApplicationContext(),Diagnostico.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("idOS",idOS);
                            bundle.putString("cpf",cpf);
                            bundle.putString("email",email);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();



            }
        });
    }

    public void fecharTecladoNovoServico(View view){
        Utilitarios utilitarios = new Utilitarios();
        utilitarios.fecharTeclado(NovoServico.this,view);
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),Diagnostico.class);
        Bundle bundle = new Bundle();
        bundle.putString("idOS",idOS);
        bundle.putString("cpf",cpf);
        bundle.putString("email",email);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


}
