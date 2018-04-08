package workay.development.workayparceiros.OS;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


public class NovoMaterial extends AppCompatActivity {

    Map mapId;
    Set setId;
    ArrayList os;
    ListaBuscaServico buscaServico;
    ListaNovaLoja listaNovaLoja;

    ListView list;
    ListView listLoja;
    ArrayList listAux;
    ArrayList novaLoja = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_material);

        final Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        final Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        final Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");

        TextView id = (TextView)findViewById(R.id.textViewIDNovoMaterial);
        id.setTypeface(bold);

        Button adicionar = (Button)findViewById(R.id.adicionarOrcamentoNovoOrcamento);
        adicionar.setTypeface(medium);

        list = (ListView)findViewById(R.id.listNovoMaterial);




        os = new ArrayList();

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
                    Toast.makeText(NovoMaterial.this, "Sem Material", Toast.LENGTH_SHORT).show();
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

        final EditText buscar = (EditText)findViewById(R.id.buscarMaterialNovoMaterial);
        buscar.setTypeface(font);

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
                listAux.add("registrar_novo_material");
                // }

                if ((buscar.getText().equals("")) || TextUtils.isEmpty(buscar.getText().toString())){
                    ArrayList a = new ArrayList();
                    buscaServico = new ListaBuscaServico(a,NovoMaterial.this,font,bold);
                    list.setAdapter(buscaServico);
                }else{
                    buscaServico = new ListaBuscaServico(listAux,NovoMaterial.this,font,bold);
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
                buscaServico = new ListaBuscaServico(a,NovoMaterial.this,font,bold);
                list.setAdapter(buscaServico);
            }
        });


    }

    public void fecharTecladoNovoMaterial(View view){
        Utilitarios utilitarios = new Utilitarios();
        utilitarios.fecharTeclado(NovoMaterial.this,view);
    }

}
