package workay.development.workayparceiros.OS;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import workay.development.workayparceiros.R;

public class Materiais extends AppCompatActivity {
    String cpf, idOS, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais);


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
        final Typeface regular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_regular.ttf");

        TextView id = (TextView)findViewById(R.id.textViewIDMateriais);
        id.setText(idOS);
        TextView titulo = (TextView)findViewById(R.id.tituloMateriais);

        TextView visita = (TextView)findViewById(R.id.textViewVisitaMateriais) ;
        TextView diagnostico = (TextView)findViewById(R.id.textViewDiagnosticoMateriais) ;
        TextView materiais = (TextView)findViewById(R.id.textViewMateriaisMateriais) ;
        TextView revisao = (TextView)findViewById(R.id.textViewRevisaoMateriais) ;
        TextView ou= (TextView)findViewById(R.id.ouMateirias) ;

        Button concluir = (Button)findViewById(R.id.concluirMateriais);
        Button criarLista = (Button)findViewById(R.id.buttonCriarListaMateriais);

        concluir.setTypeface(medium);
        criarLista.setTypeface(medium);

        id.setTypeface(bold);
        titulo.setTypeface(bold);
        visita.setTypeface(regular);
        diagnostico.setTypeface(regular);
        materiais.setTypeface(regular);
        revisao.setTypeface(regular);

        final RadioButton incluso = (RadioButton)findViewById(R.id.radioButtonIncluso);
        final RadioButton naHora = (RadioButton)findViewById(R.id.radioButtonHoraServico);
        final RadioButton materialCliente = (RadioButton)findViewById(R.id.radioButtonMaterialCliente);
        final RadioButton naoPrecisa = (RadioButton)findViewById(R.id.radioButtonNãoPrecisaMateriais);

        incluso.setTypeface(medium);
        naHora.setTypeface(medium);
        materialCliente.setTypeface(medium);
        naoPrecisa.setTypeface(medium);
        ou.setTypeface(medium);


        incluso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incluso.setChecked(true);
                naoPrecisa.setChecked(false);
            }
        });

        naHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naHora.setChecked(true);
                naoPrecisa.setChecked(false);
            }
        });


        materialCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCliente.setChecked(true);
                naoPrecisa.setChecked(false);
            }
        });

        naoPrecisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incluso.setChecked(false);
                materialCliente.setChecked(false);
                naHora.setChecked(false);
                naoPrecisa.setChecked(true);
            }
        });

        revisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Entrega.class));
            }
        });

        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        criarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CriarListaMateriais.class);
                Bundle bundle = new Bundle();
                bundle.putString("idOS",idOS);
                bundle.putString("cpf",cpf);
                bundle.putString("email",email);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });






    }
}
