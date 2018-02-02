package morgado.mdsoftware.megafone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GriteAqui extends AppCompatActivity {
    private EditText titulo, texto;
    private String resposta;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grite_aqui);

        titulo = (EditText)findViewById(R.id.tituloGriteAqui);
        texto = (EditText)findViewById(R.id.textoGriteAqui);

        Button but = (Button)findViewById(R.id.butGriteAqui);


        final String opçoes[] = new String[]{"Você quer?","Dizer um Relato?","Deixar uma Opinião?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GriteAqui.this,R.layout.activity_personalizar_spinner,opçoes ){};
        adapter.setDropDownViewResource(R.layout.activity_personalizar_item_spinner);

        Spinner spinner = (Spinner)findViewById(R.id.spinnerGriteAqui);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resposta = opçoes[position];

                if (resposta.equals("Dizer um Relato?")){
                    texto.setHint("Escreva aqui sobre algo que aconteceu com você ou algo que viu ");

                }

                if (resposta.equals("Deixar uma Opinião?")){
                    texto.setHint("Deixe sugestões para outras mulheres");
                }

                if(resposta.equals("Você quer?")){
                    texto.setHint("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarRelato();
            }
        });

    }

    private void EnviarRelato() {
        String titulo = this.titulo.getText().toString();
        String texto = this.texto.getText().toString();

        if (TextUtils.isEmpty(titulo)) {
            Toast.makeText(this, "Escreva um título", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(texto)) {
            Toast.makeText(this, "Escreva sua senha", Toast.LENGTH_LONG).show();
            return;
        }

        if (resposta.equals("Você quer?")){
            Toast.makeText(this, "Selecione uma opção", Toast.LENGTH_LONG).show();
            return;
        }
        auth = FirebaseAuth.getInstance();
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("GriteAqui").child(resposta).child(auth.getCurrentUser().getUid());
        GriteAquiClass a = new GriteAquiClass(titulo,texto);
        regMonitoria.setValue(a);




    }


}
