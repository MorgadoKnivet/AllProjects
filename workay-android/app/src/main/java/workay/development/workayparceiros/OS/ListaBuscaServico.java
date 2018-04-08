package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

public class ListaBuscaServico extends BaseAdapter {


    private final List list;
    private final Activity act;

    Typeface light;
    Typeface bold;
    public ListaBuscaServico(List list, Activity act, Typeface light, Typeface bold) {
        this.list = list;

        this.act = act;
        this.light = light;
        this.bold = bold;
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

        View view = act.getLayoutInflater().inflate(R.layout.lista_busca_servico,parent,false);

        TextView item = (TextView)view.findViewById(R.id.itemListaBuscaServico);
        item.setTypeface(light);
        String os = (String) list.get(position);

        if (!(os.equals("registrar_novo_servico")) || !(os.equals("registrar_novo_material")) ){
            item.setText(os);

        }else {
            if (os.equals("registrar_novo_servico")) {
                item.setText("Adicionar serviço não cadastrado");
                item.setTypeface(bold);
                item.setTextColor(Color.parseColor("#61bc6a"));
            }else{
                if (os.equals("registrar_novo_material")) {

                }item.setText("Adicionar material não cadastrado");
                item.setTypeface(bold);
                item.setTextColor(Color.parseColor("#61bc6a"));
            }
        }


        return view;
    }
}
