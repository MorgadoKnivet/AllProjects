package moorg.monitoriacefet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by MoorG on 07/07/2017.
 */

public class PerfilActivity extends Activity {
    ArrayList auxRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_perfil);


       // LerDados();

        ScrollView sv = new ScrollView(this);
        FrameLayout.LayoutParams lpsv = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        sv.setLayoutParams(lpsv);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lpll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lpll);
        sv.addView(ll);

        for (int i=0;i<50;i++){
            TextView tv = new TextView(this);
            EditText editText = new EditText(this);

            ll.addView(editText);
        }

        setContentView(sv);


    }

    public void LerDados()  {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMonitoria = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid());
        auxRegistro = new ArrayList();

        regMonitoria.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Log.i("Script","Entrou no for");
                    String x = d.getValue(String.class);
                    // Map z = d.getValue(Map.class);
                    // z = z + x;

                    if(x.equals("MONITOR")){
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                    }




                }
                Log.i("Script","Terminou o Loop");
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
              // Toast.makeText(BuscaMonitoria.this, "Registration Error: "+databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });







    }
    public void irParaCreditos(View view){
        Intent intentAux1 = new Intent(getApplicationContext(),creditos.class);
        startActivity(intentAux1);
    }

}
