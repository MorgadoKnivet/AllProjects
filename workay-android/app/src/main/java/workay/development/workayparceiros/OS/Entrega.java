package workay.development.workayparceiros.OS;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import workay.development.workayparceiros.R;

public class Entrega extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega);

        final Typeface bold= android.graphics.Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        final Typeface light = android.graphics.Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        final Typeface medium = android.graphics.Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");
        final Typeface regular = android.graphics.Typeface.createFromAsset(getAssets(),"fonts/ubuntu_regular.ttf");

        TextView quaseLa = (TextView)findViewById(R.id.estamosQuaseLáEntrega);
        quaseLa.setTypeface(regular);
        TextView desc = (TextView)findViewById(R.id.ultimaConferidaEntrega);
        desc.setTypeface(light);

        TextView servico= (TextView)findViewById(R.id.serviçosEntrega);
        servico.setTypeface(light);
        TextView qtdServicos = (TextView)findViewById(R.id.xServicosEntraga);
        qtdServicos.setTypeface(bold);
        TextView rsServicos = (TextView)findViewById(R.id.rsServicosEntrega);
        rsServicos.setTypeface(bold);
        TextView precoServicos = (TextView)findViewById(R.id.precoServicosEntrega);
        precoServicos.setTypeface(light);

        TextView materiais = (TextView)findViewById(R.id.materiaisEntrega);
        materiais.setTypeface(light);
        TextView qtdMateriais = (TextView)findViewById(R.id.xMateriaisEntrega);
        qtdMateriais.setTypeface(bold);
        TextView rsMateriais = (TextView)findViewById(R.id.rsMateriaisEntrega);
        rsMateriais.setTypeface(bold);
        TextView precoMateriais = (TextView)findViewById(R.id.precoMateriaisEntrega);
        precoMateriais.setTypeface(light);

        TextView dias = (TextView)findViewById(R.id.diasPrazosEntrega);
        dias.setTypeface(light);
        TextView qtdDias = (TextView)findViewById(R.id.xPrazoEntrega);
        qtdDias.setTypeface(bold);

        TextView precoTotal = (TextView)findViewById(R.id.precoTotalEntrega);
        precoTotal.setTypeface(light);
        TextView rsPrecoTotal = (TextView)findViewById(R.id.rsPrecoTotalEntrega);
        rsPrecoTotal.setTypeface(bold);
        TextView valorPrecoTotal = (TextView)findViewById(R.id.valorPrecoTotalEntrega);
        valorPrecoTotal.setTypeface(light);

        Button enviar = (Button)findViewById(R.id.buttonEnviarEntrega);
        enviar.setTypeface(medium);
        Button revisarServicos = (Button)findViewById(R.id.buttonRevisarServicosEntrega);
        revisarServicos.setTypeface(medium);
        Button revisarMateriais = (Button)findViewById(R.id.buttonRevisarMateriaisEntregaNovo);
        revisarMateriais.setTypeface(medium);

        TextView visita = (TextView)findViewById(R.id.textViewVisitaEntrega) ;
        TextView diagnostico = (TextView)findViewById(R.id.textViewDiagnosticoEntrega) ;
        TextView materiaisRodape = (TextView)findViewById(R.id.textViewMateriaisEntrega) ;
        TextView revisao = (TextView)findViewById(R.id.textViewRevisaoEntrega) ;

        visita.setTypeface(regular);
        diagnostico.setTypeface(regular);
        materiaisRodape.setTypeface(regular);
        revisao.setTypeface(regular);



        // TextView quaseLa = (TextView)findViewById(R.id.);
    }
}
