package morgado.mdsoftware.alertmaps;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CentralClasse extends AppCompatActivity {
    Set set = new HashSet();
    Map<String, Object> map;
    FirebaseAuth mAuth;
    boolean help = false;
    TextView t1, t2, t3, t4, t5;
    private MediaPlayer mp;
    private Button btnSom;
    int music = R.raw.toque_hotline_bling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_classe);


        t1 = (TextView)findViewById(R.id.tvEndereçoCentral);
        t2 = (TextView)findViewById(R.id.tvBairroCentral);
        t3 = (TextView)findViewById(R.id.tvCidadeCentral);
        t4 = (TextView)findViewById(R.id.tvEstadoCentral);
        t5 = (TextView)findViewById(R.id.tvPaisCentral);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                t1.setText( params.getString("endereco").trim());
               t2.setText(params.getString("bairro").trim());
                t3.setText(  params.getString("localidade").trim());
               t4.setText( params.getString("areaAdm").trim());
                t5.setText( params.getString("pais").trim());



            }
        }

        mAuth =FirebaseAuth.getInstance();
        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!mp.isPlaying()){
                    mp.start();
                }
            }
        });
        //     btnSom = (Button) findViewById(R.id.butStop);

        try {
            mp.reset();
            AssetFileDescriptor afd = null;
            if (!mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();

            afd = getResources().openRawResourceFd(music);



            if (afd != null) {
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepareAsync();
            }
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }

        TextView vc = (TextView)findViewById(R.id.voceChegou);
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                mp.stop();
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                finish();
            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.iconeGrandeTopo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                mp.stop();

                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                finish();
            }
        });









    }

    public void lerLatLng(String id){
        final ArrayList arrayList = new ArrayList();
        final DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);

                    arrayList.add(aux);

                }

                try {



                    Log.i("Dados Makers LerLatLng",""+ arrayList.get(0) + "   " + ""+ arrayList.get(1));
                    contruirListaTarefas(Double.parseDouble(""+ arrayList.get(0)) ,  Double.parseDouble(""+ arrayList.get(1)));
                }catch (IndexOutOfBoundsException i){
                    // erro tratado
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void contruirListaTarefas(double lat, double lng){

        final Tarefa tarefa = new Tarefa();
        Geocoder gc = new Geocoder(CentralClasse.this);
        List<Address> addressList;
        String endereço,bairro,localidade,areaAdministrativa,pais;
        Log.i("construirLista Dados",""+lat + " " + lng);

        try {

            addressList= gc.getFromLocation(lat, lng, 1);

            try {
                if ( addressList.get(0).getThoroughfare() == null){
                    endereço = "Não encontrado";
                }else{
                    endereço =""+ addressList.get(0).getThoroughfare() + ", " + addressList.get(0).getSubThoroughfare(); // endereço
                }

                if (addressList.get(0).getSubLocality() == null){
                    bairro = "Não encontrado";
                }else{
                    bairro = ""+addressList.get(0).getSubLocality(); // bairro
                }

                if (addressList.get(0).getLocality() == null){
                    localidade = "Não encontrado";
                }else{
                    localidade = ""+addressList.get(0).getLocality(); // localidade
                }

                if (addressList.get(0).getAdminArea() == null) {
                    areaAdministrativa = "Não encontrado";
                }else{
                    areaAdministrativa =""+ addressList.get(0).getAdminArea() ; // Area Administrativa
                }

                pais = ""+addressList.get(0).getCountryName(); // país

                //  Log.i("Teste address",addressList.get(0).getSubThoroughfare() + " " + addressList.get(0).getSubAdminArea() + " " + addressList.get(0).getPremises() + " " + addressList.get(0).getLocale() + " " + addressList.get(0).getFeatureName());


                t1 = (TextView)findViewById(R.id.tvEndereçoCentral);
                t2 = (TextView)findViewById(R.id.tvBairroCentral);
                t3 = (TextView)findViewById(R.id.tvCidadeCentral);
                 t4 = (TextView)findViewById(R.id.tvEstadoCentral);
                t5 = (TextView)findViewById(R.id.tvPaisCentral);

                t1.setText(endereço);
                t2.setText(bairro);
                t3.setText(localidade);
                t4.setText(areaAdministrativa);
                t5.setText(pais);

            }catch (IndexOutOfBoundsException i){
                Log.i("getLocationErroAAAAA","Entrou aqui");
            }

        }catch (IOException e){
            Log.i("getLocationErroBBBBBB","Entrou aqui");
            e.printStackTrace();
        }

    }

    public void stop(View view){
        mp.stop();
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        finish();

    }

    @Override
    public void onBackPressed(){
        //
    }




}
