package morgado.mdsoftware.alertmaps;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    // Constante de verificação de senha
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private ArrayList<LatLng> list;

    private GoogleMap mMap;
    private Marker marker;
    View mapView;

    private boolean allowNetwork;
    private LocationManager locationManager;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    //  var de leitura de BD
    Set set = new HashSet();
    Map<String, Object> map, mapL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMap);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
           SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                   .findFragmentById(R.id.map);
           mapFragment.getMapAsync(this);

        mapView = mapFragment.getView();
        progressDialog = new ProgressDialog(getApplicationContext());

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


        ImageButton procurarTarefa = (ImageButton) findViewById(R.id.butProcurarTarefa);
        procurarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encontrarEndereco(((EditText)findViewById(R.id.procurarTarefa)).getText().toString());
            }
        });



    }

    // onMapReady é chamado quando o Fragment do mapa é instanciado
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng Rio = new LatLng(-23.2301558,-42.9121089);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Rio,0));
        list = new ArrayList<LatLng>();

       //   progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(""+mAuth.getCurrentUser().getUid()).child("Makers");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (Map<String, Object>) dataSnapshot.getValue();

                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }



                for (Object i : set) {

                    String palavra = (String) i;

                    lerLatLng(palavra);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Add a marker in Sydney and move the camera
       //  LatLng sydney = new LatLng(-34, 151);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("Script", "setOnMapClickListener()" + latLng.latitude + " " + latLng.longitude);



                     customAddMarker(latLng);
                //   drawRoute();

            }
        });

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,20,30);


            View compassButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            RelativeLayout.LayoutParams layoutParamsCompass = (RelativeLayout.LayoutParams)
                    compassButton.getLayoutParams();

            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            layoutParamsCompass.setMargins(20,0,0,30);



        }


    }
    // FIM //

    // método que insere maker no mapa, chamado pelo click no mapa
    public void customAddMarker(LatLng latLng ){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_alert_maps);

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latLng.latitude, latLng.longitude)).icon(icon);


        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(update);
        mMap.addMarker(marker);

        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_"));
        Tarefa tarefa = new Tarefa();
        tarefa.setLatLng(latLng);
        regMarkers.setValue(tarefa);





    }






    // Métodos relacionado a GPS  e permissões //



// Método para verificar se o GPS está ativado //
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(MapsActivity.this,
                            "Permissão de Localização Negada, ...:(", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    // FIM //

    // Métodos do ciclo de vida do Fragment
    // eles trabalham com a localização, chamando os métodos de location listener

    // quando a activity é mostrada para o usuário
    @Override
    public void onResume() {
        super.onResume();

        allowNetwork = true;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent it = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }

   // fim//


    // Métodos do Location Listener         //

    @Override
    public void onLocationChanged(Location location) {
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            allowNetwork = false;
        }

        if(allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)){
           configurarLocalizaçãoAtual(new LatLng(location.getLatitude(), location.getLongitude())); // método ainda não implementado
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //      FIM                 //


    // Aqui é colocado no mapa aquele pontinho azul de localização atual
    public void configurarLocalizaçãoAtual(LatLng latLng){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

            metodoCentral(latLng.latitude,latLng.longitude);
            mMap.setMyLocationEnabled(true);

            MyLocation myLocation = new MyLocation();
            mMap.setLocationSource(myLocation);
            myLocation.setLocation(latLng);

        }
    }
    // Fim  //



    // Classe utilizada para auxiliar o my location, SIM É UM CLASSE DENTRO DE UMA CLASSE  //
    // Basicamente esse classe faz o Location rodar

    public class MyLocation implements LocationSource {
        private OnLocationChangedListener listener;

        @Override
        public void activate(OnLocationChangedListener listener) {
            this.listener = listener;

            Log.i("Script", "activate()");
        }

        @Override
        public void deactivate() {
            Log.i("Script", "deactivate()");
        }


        public void setLocation(LatLng latLng){
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);

            if(listener != null){
                listener.onLocationChanged(location);
            }
        }
    }

    // FIM DE CLASSE INTERNA //








    public void encontrarEndereco(String endereco){
        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this);

        try {
            list = (ArrayList<Address>) geocoder.getFromLocationName(endereco, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();

        }
        if(list != null && list.size() > 0) {
            Address a = list.get(0);

            double auxLat = a.getLatitude();
            double auxLng = a.getLongitude();


            /*
            String addressN = "Endereço: " + a.getThoroughfare() + ", " ; // Rua
            //   address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
            addressN += "" + a.getFeatureName() + ". \n";   // Numero
            addressN += "Bairro: " + a.getSubLocality() + ";\n"; // Bairro
            addressN += "" + a.getLocality() + " ";
            addressN += ", " + a.getAdminArea() + " ";
            addressN += ", " + a.getCountryName() + ".";
            */

            LatLng latLng = new LatLng(auxLat,auxLng);

            customAddMarker(latLng);

        }else{
            Toast.makeText(MapsActivity.this,"Local não encontrado",Toast.LENGTH_SHORT).show();
        }



    }

    public void lerLatLng(String id){
        final ArrayList arrayList = new ArrayList();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);
                    arrayList.add(aux);


                }
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_alert_maps);
                try {
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng( Double.parseDouble(""+ arrayList.get(0)) ,  Double.parseDouble(""+ arrayList.get(1)))).icon(icon);

                mMap.addMarker(marker);

                }catch (IndexOutOfBoundsException i){
                   // tratar erro
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu,menu);
        return true;
    }

    public void irParaTarefas(){
        startActivity(new Intent(getApplicationContext(), VisualizarTarefas.class));
        finish();
    }

    public void irParaDeslogar(){
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tarefasMenu:
                irParaTarefas();
                return true;
            /*
            case R.id.deslogar:
                irParaDeslogar();
                return true;
             */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void metodoCentral(final double lat, final double lng ){
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(""+mAuth.getCurrentUser().getUid()).child("Makers");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                for (Object i : set) {

                    String palavra = (String) i;

                   metodoCentral(palavra, lat, lng);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void metodoCentral(final String id, final double lat, final double lng){
        final ArrayList arrayList = new ArrayList();
        final DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Usuários").child(mAuth.getCurrentUser().getUid()).child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);
                    arrayList.add(aux);
                    Log.i("Excluir Marker",""+aux);

                }

                try {

                    DecimalFormat df = new DecimalFormat("00.000");
                    String latInterno = df.format(lat);
                    String lngInterno = df.format(lng);

                    double auxLat = Double.parseDouble(""+arrayList.get(0));
                    double auxLng = Double.parseDouble(""+arrayList.get(1));

                    String latBd = df.format(auxLat);
                    String lngBB = df.format(auxLng);

                    if ( (latInterno.equals(latBd)) &&  (lngInterno.equals(lngBB)) ){
                        Tarefa tarefa = new Tarefa();
                        regMarkers.setValue(tarefa);

                        Geocoder gc = new Geocoder(MapsActivity.this);
                        List<Address> addressList;
                        String endereço,bairro,localidade,areaAdministrativa,pais;
                        Log.i("construirLista Dados",""+lat + " " + lng);

                        try {


                            addressList= gc.getFromLocation(auxLat, auxLng, 1);

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

                                Intent intentAux1 = new Intent(getApplicationContext(), CentralClasse.class);
                                // aux.setClass(this,Calculadora.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("endereco",endereço);
                                bundle.putString("bairro",bairro);
                                bundle.putString("localidade",localidade);
                                bundle.putString("areaAdm",areaAdministrativa);
                                bundle.putString("pais",pais);

                                intentAux1.putExtras(bundle);
                                startActivity(intentAux1);
                                finish();
                                return;

                            }catch (IndexOutOfBoundsException i){
                                Log.i("getLocationErroAAAAA","Entrou aqui");
                            }

                        }catch (IOException e){
                            Log.i("getLocationErroBBBBBB","Entrou aqui");
                            e.printStackTrace();
                        }



                        //  e
                    }


                }catch (IndexOutOfBoundsException i){
                    // erro tratado
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
