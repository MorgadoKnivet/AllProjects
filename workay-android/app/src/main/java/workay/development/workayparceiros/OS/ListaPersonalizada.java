package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

/**
 * Created by MoorG on 24/10/2017.
 */

public class ListaPersonalizada extends BaseAdapter {


    private final List list;
    private final Activity act;
    Typeface fontBold;
    Typeface fontLight;
    public ListaPersonalizada(List list, Activity act, Typeface fontBold, Typeface font) {
        this.list = list;

        this.act = act;
        this.fontLight = font;
        this.fontBold = fontBold;
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



        View view = act.getLayoutInflater().inflate(R.layout.lista_personalizada,parent,false);


        OrdemServico ordemServico = (OrdemServico) list.get(position);


        TextView cliente = (TextView)view.findViewById(R.id.nomeClienteListaOS) ;
        cliente.setText(ordemServico.getNomeCliente());
        cliente.setTypeface(fontBold);

        TextView titulo = (TextView)view.findViewById(R.id.tituloServicoListaPersonalizada);
        titulo.setText(ordemServico.getTitulo());
        titulo.setTypeface(fontLight);

        ImageView icon = (ImageView)view.findViewById(R.id.iconeListaPersonalizada);

        if (ordemServico.getTipoServico().equals("Hidraulico")){
            icon.setImageResource(R.drawable.ic_3);
        }

        if (ordemServico.getTipoServico().equals("Pintor")){
            icon.setImageResource(R.drawable.icon_pintura);
        }

        TextView status = (TextView)view.findViewById(R.id.statusListaPersonalizada);
        status.setTypeface(fontBold);

        TextView desc = (TextView)view.findViewById(R.id.textDescricaoData);
        desc.setTypeface(fontLight);

        TextView prazo= (TextView)view.findViewById(R.id.dataListaPersonalizada);
        prazo.setTypeface(fontBold);


        if (ordemServico.getStatus().equals("Aguardando Orçamento")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_aguardando_orcamento);
//
            String aux = "Prazo";
            desc.setText(aux);
            prazo.setText(ordemServico.getPrazo());

        }

        if (ordemServico.getStatus().equals("Visita Marcada")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_visita_marcada);

            String aux = "Data";
            desc.setText(aux);
            prazo.setText(ordemServico.getData());
        }

        if (ordemServico.getStatus().equals("Em revisão")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_revisao);
//
            String aux = "Enviado em";
            desc.setText(aux);
            prazo.setText(ordemServico.getDataEnvio());

        }

        if (ordemServico.getStatus().equals("Enviado")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_enviado);
//
            String aux = "Enviado em";
            desc.setText(aux);
            prazo.setText(ordemServico.getDataEnvio());

        }

        if (ordemServico.getStatus().equals("Aceito")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_aprovado);
//
            String aux = "Enviado em";
            desc.setText(aux);
            prazo.setText(ordemServico.getDataEnvio());

        }

        if (ordemServico.getStatus().equals("Rejeitado")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_rejeitado);
//
            String aux = "Enviado em";
            desc.setText(aux);
            prazo.setText(ordemServico.getDataEnvio());

        }

        if (ordemServico.getStatus().equals("Finalizado")){
            status.setText(ordemServico.getStatus());
            status.setBackgroundResource(R.drawable.text_view_status_finalizado_menu);
//
            String aux = "Enviado em";
            desc.setText(aux);
            prazo.setText(ordemServico.getDataEnvio());

            view.setBackgroundResource(R.drawable.divider_cinza);

        }







        return view;
    }
}
