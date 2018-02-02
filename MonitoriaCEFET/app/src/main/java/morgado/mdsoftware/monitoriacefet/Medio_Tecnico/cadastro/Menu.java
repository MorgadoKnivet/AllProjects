package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.drakeet.materialdialog.MaterialDialog;
import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.creditos;
import morgado.mdsoftware.monitoriacefet.R;

public class Menu extends AppCompatActivity {
    private MaterialDialog mMaterialDialog;
    private String VERSAO = "2.0";
    TextView creditos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("versao");
        regMonitoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String  x = d.getValue(String.class);

                        if (!(VERSAO.equals(x))) {
                            showUpdateAppDialog();
                        }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        creditos = (TextView)findViewById(R.id.tvCreditosMenu);
        creditos.setText("Desenvolvido por M&D Software");




    }



    public void irParaLogin(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intentAux1);

    }

    public void irParaCadastro(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),NewUserActivity.class);
        startActivity(intentAux1);

    }

    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }
    public void showUpdateAppDialog(){

        mMaterialDialog = new MaterialDialog(this)
                .setTitle( "Atualize o MonitoriaCEFET")
                .setMessage("Nova versão disponível na PlayStore. O aplicativo está chegando a sua perfeição com novas funções, correção de bugs, entre outros detalhes.")
                .setCanceledOnTouchOutside(false)
                .setPositiveButton( "Atualizar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = getPackageName();
                        Intent intent;

                        try {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                            startActivity( intent );
                        }
                        catch (android.content.ActivityNotFoundException e) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                            startActivity( intent );
                        }
                    }
                });
        Log.i("tetse","teste");

        mMaterialDialog.show();
    }
    // TESTEasa sahshahhs
    //anshas
    //asa
}
