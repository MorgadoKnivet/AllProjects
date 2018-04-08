package workay.development.workayparceiros.OS;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

public class VisitaAndamento extends AppCompatActivity {

    private ListaVisitaAndamento listaPersonalizada;
    private ListView listView;
    String cpf, idOS, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_andamento);

        final Typeface regular = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_regular.ttf");
        TextView id = (TextView)findViewById(R.id.textViewIDVisitaAndamento) ;

        TextView visita = (TextView)findViewById(R.id.textViewVisitaVisitaAndamento) ;
        TextView diagnostico = (TextView)findViewById(R.id.textViewDiagnosticoVisitaAndamento) ;
        TextView materiais = (TextView)findViewById(R.id.textViewMateriaisVisitaAndamento) ;
        TextView revisao = (TextView)findViewById(R.id.textViewRevisaoVisitaAndamento) ;

        ImageButton butDiag = (ImageButton)findViewById(R.id.icone2VisitaAndamento) ;



        id.setTypeface(regular);
        visita.setTypeface(regular);
        diagnostico.setTypeface(regular);
        materiais.setTypeface(regular);
        revisao.setTypeface(regular);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params!= null){


                idOS = params.getString("idOS").trim();
                id.setText(idOS);
                cpf = params.getString("cpf").trim();
                email = params.getString("email").trim();
            }
        }

        butDiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Diagnostico.class);
                Bundle bundle = new Bundle();
                bundle.putString("idOS",idOS);
                bundle.putString("cpf",cpf);
                bundle.putString("email",email);
                intent.putExtras(bundle);
                startActivity(intent);
               // startActivity(new Intent(getApplicationContext(),Diagnostico.class));
            }
        });

        diagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Diagnostico.class);
                Bundle bundle = new Bundle();
                bundle.putString("idOS",idOS);
                bundle.putString("cpf",cpf);
                bundle.putString("email",email);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



        final ArrayList list = new ArrayList();
        OrdemServico os1 = new OrdemServico("");
        OrdemServico os2 = new OrdemServico("");

        OrdemServico os4 = new OrdemServico("");

        OrdemServico os6 = new OrdemServico("");

        OrdemServico os8 = new OrdemServico("");
        OrdemServico os9 = new OrdemServico("");

        os1.setStatus("coment");
        list.add(os1);

        os2.setStatus("pergunta");
        os2.setTitulo("Titulo da pergunta");
        list.add(os2);

        os4.setStatus("pergunta");
        os4.setTitulo("Nova pergunta");

        list.add(os4);

        os6.setStatus("anotacaoTitulo");
        list.add(os6);


        os8.setStatus("addAnotacao");
        list.add(os8);
        os9.setStatus("finalizar");
        list.add(os9);

        ImageView butReturn = (ImageView)findViewById(R.id.returnVisitaAndamento);
        butReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




       final Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_bold.ttf");
        final Typeface fontLight = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_light.ttf");
        final Typeface medium = Typeface.createFromAsset(getAssets(),"fonts/ubuntu_medium.ttf");

        listView = (ListView)findViewById(R.id.listViewVisitaAndamento);
        listaPersonalizada = new ListaVisitaAndamento(list, VisitaAndamento.this,font,fontLight,medium);
        listView.setAdapter(listaPersonalizada);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final OrdemServico ordemServico = (OrdemServico) parent.getAdapter().getItem(position);
               // Toast.makeText(getApplicationContext(),"Função não implementada",Toast.LENGTH_SHORT).show();

                if (ordemServico.getStatus().equals("pergunta")) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(VisitaAndamento.this);

                    final LayoutInflater inflater = LayoutInflater.from(VisitaAndamento.this);
                    final View inflator = inflater.inflate(R.layout.tela_dialog_chat, null);

                    final TextView respostaTV = (TextView) inflator.findViewById(R.id.respostaDialogChat);
                    final TextView pergunta = (TextView) inflator.findViewById(R.id.textViewTelaDialogChat);
                    pergunta.setTypeface(medium);

                    pergunta.setText(ordemServico.getTitulo());



                    final EditText resposta = (EditText) inflator.findViewById(R.id.editTextDialogChat);
                    resposta.setTypeface(fontLight);
                    respostaTV.setTypeface(medium);
                    ImageView fechar = (ImageView) inflator.findViewById(R.id.fecharDialogChat);
                    ImageButton enviar = (ImageButton) inflator.findViewById(R.id.enviarDialogChat);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.setView(inflator);
                    alertDialog.show();


                    enviar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            respostaTV.setText(resposta.getText().toString());
                            respostaTV.setVisibility(View.VISIBLE);
                            for (int i=0;i<list.size();i++){
                                OrdemServico aux = (OrdemServico)list.get(i);
                                if (aux.getTitulo().equals(ordemServico.getTitulo())){

                                    OrdemServico os7 = new OrdemServico("");
                                    os7.setStatus("resposta");
                                    os7.setTitulo(resposta.getText().toString());
                                    list.add(i+1,os7);
                                    listaPersonalizada = new ListaVisitaAndamento(list, VisitaAndamento.this,font,fontLight,medium);

                                    listView.setAdapter(listaPersonalizada);
                                    listView.setSelection(listView.getAdapter().getCount()-1);
                                    break;
                                }
                            }

                        }
                    });


                    fechar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });

                }else {
                    if (ordemServico.getStatus().equals("addAnotacao")){

                        final AlertDialog.Builder builder = new AlertDialog.Builder(VisitaAndamento.this);

                        final LayoutInflater inflater = LayoutInflater.from(VisitaAndamento.this);
                        final View inflator = inflater.inflate(R.layout.tela_dialog_chat_anotacoes, null);

                        final TextView respostaTV = (TextView) inflator.findViewById(R.id.respostaDialogChat_anot);
                        TextView pergunta = (TextView) inflator.findViewById(R.id.textViewTelaDialogChat_anot);

                       // pergunta.setText(ordemServico.getTitulo());

                        final EditText resposta = (EditText) inflator.findViewById(R.id.editTextDialogChat_anot);

                        ImageView fechar = (ImageView) inflator.findViewById(R.id.fecharDialogChat_anot);
                        ImageButton enviar = (ImageButton) inflator.findViewById(R.id.enviarDialogChat_anot);

                        pergunta.setTypeface(medium);
                        resposta.setTypeface(fontLight);
                        respostaTV.setTypeface(medium);


                        final AlertDialog alertDialog = builder.create();
                        alertDialog.setView(inflator);
                        alertDialog.show();


                        enviar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                respostaTV.setText(resposta.getText());
                                respostaTV.setVisibility(View.VISIBLE);
                                OrdemServico os = new OrdemServico("");
                                os.setStatus("anotacao");
                                os.setTitulo(resposta.getText().toString());
                                list.add(list.size()-2,os);
                                listaPersonalizada = new ListaVisitaAndamento(list, VisitaAndamento.this,font,fontLight,medium);
                                listView.setAdapter(listaPersonalizada);
                                listView.setSelection(listView.getAdapter().getCount()-1);

                            }
                        });


                        fechar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                alertDialog.dismiss();

                            }
                        });



                    }
                }

                if (ordemServico.getStatus().equals("finalizar")){

                    final AlertDialog.Builder builder = new AlertDialog.Builder(VisitaAndamento.this);

                    final LayoutInflater inflater = LayoutInflater.from(VisitaAndamento.this);
                    final View inflator = inflater.inflate(R.layout.tela_dialog_encerrar_visita, null);

                    TextView tvEncerrar = (TextView)findViewById(R.id.boxFinalizar);
                    tvEncerrar.setTypeface(font);


                    final Button encerrar = (Button)inflator.findViewById(R.id.buttonEncerrarVisita);
                    Button continuar = (Button)inflator.findViewById(R.id.buttonContinuarVisita);
                    continuar.setTypeface(fontLight);
                    encerrar.setTypeface(fontLight);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.setView(inflator);
                    alertDialog.show();

                    encerrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();

                            for (int i=0;i<list.size();i++){
                                OrdemServico aux = (OrdemServico)list.get(i);
                                if (aux.getStatus().equals("finalizar")){

                                    aux.setStatus("retomar");
                                    listaPersonalizada = new ListaVisitaAndamento(list, VisitaAndamento.this,font,fontLight,medium);

                                    listView.setAdapter(listaPersonalizada);
                                    listView.setSelection(listView.getAdapter().getCount()-1);
                                //    TextView gambiarra = (TextView)findViewById(R.id.gambiarraFocoListView);
                                 //   gambiarra.setVisibility(View.VISIBLE);
                                    break;
                                }
                            };

                        }
                    });

                    continuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();


                        }
                    });


                }

                if (ordemServico.getStatus().equals("retomar")){

                    for (int i=0;i<list.size();i++){
                        OrdemServico aux = (OrdemServico)list.get(i);
                        if (aux.getStatus().equals("retomar")){

                            aux.setStatus("finalizar");

                            listaPersonalizada = new ListaVisitaAndamento(list, VisitaAndamento.this,font,fontLight,medium);

                            listView.setAdapter(listaPersonalizada);
                            listView.setSelection(listView.getAdapter().getCount()-1);

                    //        TextView gambiarra = (TextView)findViewById(R.id.gambiarraFocoListView);
                    //        gambiarra.setVisibility(View.GONE);
                            break;
                        }
                    }



                }

            }
        });
    }






}
