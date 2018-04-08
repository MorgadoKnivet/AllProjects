package workay.development.workayparceiros.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by MoorG on 02/02/2018.
 */

public class Utilitarios extends AppCompatActivity {

    private Typeface fontBold;// =
    private Typeface fontRegular;// = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
    private Typeface fontMedium;
    private Typeface fontLight;



    public void showUpdateAppDialog(){

        MaterialDialog mMaterialDialog = new MaterialDialog(this)
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


        mMaterialDialog.show();
    }

    public void fecharTeclado(Context context, View editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void conectarGet() throws MalformedURLException {
        URL url = new URL("http://demo8993843.mockable.io/");
        HttpURLConnection urlConnection = null;


        try {
            urlConnection = (HttpURLConnection)url.openConnection();

            InputStream in = urlConnection.getInputStream();
/*
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        StringBuffer buffer = new StringBuffer();
        while ((linha = reader.readLine()) != null) {
            buffer.append(linha + "\n");
        }

        Toast.makeText(getApplicationContext(),buffer.toString(),Toast.LENGTH_SHORT).show();

        if (buffer.length() == 0) {
            return;
        }

        if (urlConnection != null) {
            urlConnection.disconnect();
        }

        if (reader != null) {
            reader.close();
        }
        */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
