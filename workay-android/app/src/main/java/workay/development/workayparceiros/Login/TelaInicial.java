package workay.development.workayparceiros.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import me.drakeet.materialdialog.MaterialDialog;
import workay.development.workayparceiros.Class.Utilitarios;
import workay.development.workayparceiros.Login.Cadastro;
import workay.development.workayparceiros.OS.DetalhesOrdemServico;
import workay.development.workayparceiros.OS.MenuOrdemServico;
import workay.development.workayparceiros.R;

public class TelaInicial extends AppCompatActivity {



    private MaterialDialog mMaterialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        try {
            Log.i("Entrou","Entrou no diferente de nulo" + " " + mAuth.getCurrentUser().getUid());
            startActivity(new Intent(getApplicationContext(),MenuOrdemServico.class));
        }catch (NullPointerException n) {

        }

        Typeface bold = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        Typeface regular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_regular.ttf");
        Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");
        Typeface light = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");

        Utilitarios utilitarios = new Utilitarios();


        TextView coment = (TextView)findViewById(R.id.textViewFormaDeAcesso);
        coment.setTypeface(bold);

        Button butEmail = (Button)findViewById(R.id.butEmailSenha);
        butEmail.setTypeface(light);

        Button butSMS = (Button)findViewById(R.id.butSMS);
        butSMS.setTypeface(light);
        Button butGoogle = (Button)findViewById(R.id.butGoogle);
        butGoogle.setTypeface(light);


        TextView creditos = (TextView)findViewById(R.id.creditos);
       creditos.setTypeface(light);

        butGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Função não implementada",Toast.LENGTH_SHORT).show();
            }
        });


        butSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final AlertDialog.Builder builder = new AlertDialog.Builder(TelaInicial.this);

                final LayoutInflater inflater = LayoutInflater.from(TelaInicial.this);
                final View inflator = inflater.inflate(R.layout.tela_dialog, null);

                TextView positive = (TextView) inflator.findViewById(R.id.buttonPositiveDialog);

                TextView negative = (TextView) inflator.findViewById(R.id.buttonNegativeDialog);


                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(inflator);
                alertDialog.show();

                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),LoginSMS.class));
                    }
                });

                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }


        });

        butEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("id","emailSenha");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    public void dialogPositive(View view){

        startActivity(new Intent(getApplicationContext(),LoginSMS.class));


    }

    public void dialogNegative(View view){
        mMaterialDialog.dismiss();
    }



}
