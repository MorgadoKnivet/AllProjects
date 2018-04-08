package workay.development.workayparceiros.OS;




import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import workay.development.workayparceiros.R;


public class DetalhesOrdemServico extends AppCompatActivity {

    private String tipo;
    private static int colorAprovado = Color.parseColor("#61bc6a");
    private static int visitaMarcada = Color.parseColor("#fad961");
    private static int emRevisao = Color.parseColor("#DDEEDF");
    private static int orcamentoEnviado = Color.parseColor("#95ca9b");
    private static int orcamentoReprovado = Color.parseColor("#f2594b");
    private static int orcamentoFinalizado = Color.parseColor("#d1d2d4");
    private static int aguardandoOrcamento = Color.parseColor("#fcbf10");
    Typeface fontBold ;
    Typeface fontLight;
    Typeface fontMedium;
    private String id, cpf, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_ordem_servico);

        Toolbar toolbar = (Toolbar)findViewById(R.id.barNavegationDetalhesOS);

        fontBold = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        fontMedium= Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");

        TextView client = (TextView)findViewById(R.id.nomeClienteDetalhesOS);
        client.setTypeface(fontBold);

        TextView titulo = (TextView)findViewById(R.id.tituloDetalhesOS);
        titulo.setTypeface(fontLight);

        TextView status = (TextView)findViewById(R.id.statusDetalhesOS);
        titulo.setTypeface(fontMedium);

        TextView endereco = (TextView)findViewById(R.id.enderecoDetalhesOS);
        endereco.setTypeface(fontBold);

        TextView txEnderco = (TextView)findViewById(R.id.tvEnderecoDetalheOS);
        txEnderco.setTypeface(fontBold);

        TextView textViewUm = (TextView)findViewById(R.id.textViewUmDetalhesOS);
        textViewUm.setTypeface(fontBold);
        TextView dataUm = (TextView)findViewById(R.id.dateUmDetalhesOS);
        dataUm.setTypeface(fontBold);

        TextView textViewDois = (TextView)findViewById(R.id.textViewDoisDetalhesOS);
        TextView dataDois = (TextView)findViewById(R.id.dateDoisDetalhesOS);

        TextView textViewTres = (TextView)findViewById(R.id.textViewTresDetalhesOS);
        TextView dataTres = (TextView)findViewById(R.id.dateTresDetalhesOS);

        textViewDois.setTypeface(fontBold);
        dataDois.setTypeface(fontBold);

        textViewTres.setTypeface(fontBold);
        dataTres.setTypeface(fontBold);

        ImageView icon = (ImageView)findViewById(R.id.iconeDetalhesOS);
        final Button button = (Button)findViewById(R.id.buttonDetalhesOS);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                endereco.setText( params.getString("endereco").trim());
                client.setText(params.getString("client").trim());
                titulo.setText(  params.getString("titulo").trim());
                toolbar.setTitle(params.getString("id"));
                cpf = params.getString("cpf");
                email = params.getString("email");
                id = params.getString("id");

                setSupportActionBar(toolbar);

                if (params.getString("status").equals("Aguardando Orçamento")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_aguardando_orcamento);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Prazo para envio do orçamento: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));

                    button.setBackgroundColor(aguardandoOrcamento);

                }

                if (params.getString("status").equals("Visita Marcada")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_visita_marcada);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita marcada para:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("marcacao"));

                    button.setText("Iniciar Visita");
                    button.setBackgroundColor(visitaMarcada);
                }

                if (params.getString("status").equals("Em revisão")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_revisao);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Orçamento enviado em: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));

                    button.setBackgroundColor(emRevisao);
                }

                if (params.getString("status").equals("Enviado")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_enviado);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Orçamento enviado em: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));



                    button.setBackgroundColor(orcamentoEnviado);

                }

                if (params.getString("status").equals("Aceito")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_aprovado);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Orçamento enviado em: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));

                    textViewTres.setVisibility(View.VISIBLE);
                    textViewTres.setText("Serviço Marcado Para:");
                    dataTres.setVisibility(View.VISIBLE);
                    dataTres.setText(params.getString("marcacao"));

                    button.setBackgroundColor(colorAprovado);

                }

                if (params.getString("status").equals("Finalizado")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_finalizado);

                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Orçamento enviado em: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));

                    textViewTres.setVisibility(View.VISIBLE);
                    textViewTres.setText("Serviço Realizado:");
                    dataTres.setVisibility(View.VISIBLE);
                    dataTres.setText(params.getString("realizacao"));

                    button.setBackgroundColor(orcamentoFinalizado);
                }

                if (params.getString("status").equals("Rejeitado")){
                    status.setText(params.getString("status"));
                    status.setBackgroundResource(R.drawable.text_view_status_rejeitado);


                    textViewUm .setVisibility(View.VISIBLE);
                    textViewUm .setText("Visita realizada em:");
                    dataUm.setVisibility(View.VISIBLE);
                    dataUm.setText(params.getString("realizacao"));

                    textViewDois.setVisibility(View.VISIBLE);
                    textViewDois.setText("Orçamento enviado em: ");
                    dataDois.setVisibility(View.VISIBLE);
                    dataDois.setText(params.getString("envio"));

                    button.setBackgroundColor(orcamentoReprovado);

                }


                // Falta implementar Finalizado e rejeitado



                tipo = params.getString("tipo").trim();
                if (tipo.equals("Hidraulico")){
                    icon.setImageResource(R.drawable.ic_3);
                }

                if (tipo.equals("Pintor")){
                    icon.setImageResource(R.drawable.icon_pintura);
                }


            }
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Iniciar Visita")){
                    Intent intent = new Intent(getApplicationContext(),VisitaAndamento.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idOS",id);
                    bundle.putString("cpf",cpf);
                    bundle.putString("email",email);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.voltar:
                startActivity(new Intent(getApplicationContext(), MenuOrdemServico.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }



}
