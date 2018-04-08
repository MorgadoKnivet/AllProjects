package workay.development.workayparceiros.OS;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

public class CriarListaMateriais extends AppCompatActivity {
    ArrayList list;
    ListView listView;
    String cpf, idOS, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_lista_materiais);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params!= null){


                idOS = params.getString("idOS").trim();
                cpf = params.getString("cpf").trim();
                email = params.getString("email").trim();
            }
        }

        final Typeface bold= Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        final Typeface fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        final Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");

        TextView id = (TextView)findViewById(R.id.textViewIDListaMateriais);
        id.setText(idOS);
        TextView titulo = (TextView)findViewById(R.id.tituloListaMateriais);
        id.setTypeface(bold);
        titulo.setTypeface(bold);

        list = new ArrayList();

        OrdemServico a1 = new OrdemServico("");
        OrdemServico a2 = new OrdemServico("");
        OrdemServico a3 = new OrdemServico("");
        OrdemServico a4 = new OrdemServico("");
        OrdemServico a5 = new OrdemServico("");
        OrdemServico a6 = new OrdemServico("");
        OrdemServico a7 = new OrdemServico("");

        a1.setStatus("1");
        a2.setStatus("2");
        a3.setStatus("3");
        a4.setStatus("4");
        a5.setStatus("5");
        a6.setStatus("6");
        a7.setStatus("7");

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);

        listView = (ListView)findViewById(R.id.listViewListaMateriais);
        ListaListaMateriais lista = new ListaListaMateriais(list,CriarListaMateriais.this,bold,fontLight,medium);
        listView.setAdapter(lista);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final OrdemServico ordemServico = (OrdemServico) parent.getAdapter().getItem(position);
                if (ordemServico.getStatus().equals("5")){
                    Intent intent = new Intent(getApplicationContext(),NovoMaterial.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idOS",idOS);
                    bundle.putString("cpf",cpf);
                    bundle.putString("email",email);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }
}
