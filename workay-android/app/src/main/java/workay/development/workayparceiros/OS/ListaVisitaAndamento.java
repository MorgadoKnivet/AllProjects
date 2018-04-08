package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

public class ListaVisitaAndamento extends BaseAdapter {


    private final List list;
    private final Activity act;
    Typeface bold;
    Typeface light, medium;
    public ListaVisitaAndamento(List list, Activity act, Typeface fontBold, Typeface font, Typeface medium) {
        this.list = list;

        this.act = act;
        this.light = font;
        this.bold = fontBold;
        this.medium = medium;
    }

    @Override
    public int getCount() {
        return list.size();

        // O próprio método já diz o que ele faz: conta quantos itens existem na lista.
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);

        //Veja que ele quer saber um item a partir de uma posição. Isso é fácil!
        // Basta apenas retornamos por meio do método get() mandando a posição:
    }

    @Override
    public long getItemId(int position) {
        return 0;
        // Não vamos utilizar agora, manter o return o
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater().inflate(R.layout.lista_visita_andamento,parent,false);

        TextView boxTitulo = (TextView)view.findViewById(R.id.boxTitulo);
        TextView boxDesc = (TextView)view.findViewById(R.id.boxDescricao);
        TextView boxPergunta = (TextView)view.findViewById(R.id.boxPergunta) ;
        TextView boxResposta = (TextView)view.findViewById(R.id.boxResposta) ;
        TextView boxAnotacao = (TextView)view.findViewById(R.id.boxAnotacao);
        TextView boxAnotNova = (TextView)view.findViewById(R.id.boxNovaAnotacao);
        TextView boxFinalizar = (TextView)view.findViewById(R.id.boxFinalizar);
        TextView boxRetomar = (TextView)view.findViewById(R.id.boxFinalizado);

        boxTitulo.setTypeface(bold);
        boxDesc.setTypeface(light);
        boxPergunta.setTypeface(medium);
        boxResposta.setTypeface(medium);
        boxAnotacao.setTypeface(medium);
        boxFinalizar.setTypeface(light);
        boxRetomar.setTypeface(light);




        OrdemServico os = (OrdemServico)list.get(position);

        if (os.getStatus().equals("coment")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.VISIBLE);
            boxDesc.setVisibility(View.VISIBLE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
        }

        if (os.getStatus().equals("pergunta")){
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);
            boxPergunta.setVisibility(View.VISIBLE);
            boxResposta.setVisibility(View.GONE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
            boxPergunta.setText(os.getTitulo());
        }

        if (os.getStatus().equals("resposta")){
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);
            boxResposta.setVisibility(View.VISIBLE);
            boxPergunta.setVisibility(View.GONE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
            if (!(os.getTitulo().equals(""))){
                boxResposta.setText(os.getTitulo());
            }

        }

        if (os.getStatus().equals("anotacaoTitulo")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.VISIBLE);
            boxDesc.setVisibility(View.GONE);
            boxTitulo.setText("Anotações");
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
        }

        if (os.getStatus().equals("anotacao")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);

            boxAnotacao.setVisibility(View.VISIBLE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
            if (!(os.getTitulo().equals(""))) {
                boxAnotacao.setText(os.getTitulo());
            }
        }

        if (os.getStatus().equals("addAnotacao")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.VISIBLE);
            boxFinalizar.setVisibility(View.GONE);
        }

        if (os.getStatus().equals("finalizar")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.VISIBLE);
        }

        if (os.getStatus().equals("retomar")){
            boxPergunta.setVisibility(View.GONE);
            boxResposta.setVisibility(View.GONE);
            boxTitulo.setVisibility(View.GONE);
            boxDesc.setVisibility(View.GONE);
            boxAnotacao.setVisibility(View.GONE);
            boxAnotNova.setVisibility(View.GONE);
            boxFinalizar.setVisibility(View.GONE);
            boxRetomar.setVisibility(View.VISIBLE);

        }

        return view;
    }
}
