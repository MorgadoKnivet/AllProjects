package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

/**
 * Created by MoorG on 26/02/2018.
 */
public class ListaListaMateriais extends BaseAdapter {


    private final List list;
    private final Activity act;
    Typeface bold;
    Typeface light, medium;

    public ListaListaMateriais(List list, Activity act, Typeface fontBold, Typeface font, Typeface medium) {
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

        View view = act.getLayoutInflater().inflate(R.layout.lista_lista_materiais, parent, false);

        TextView titulo = (TextView)view.findViewById(R.id.subtituloListaMateriais);
        TextView subTitulo = (TextView)view.findViewById(R.id.descricaoSubTituloListaMateriais);
        TextView preçoOrçamento = (TextView)view.findViewById(R.id.precoOrcamentoListaMateriais);
        TextView preçoOrçamentoSubstituto = (TextView)view.findViewById(R.id.precoOrcamentoListaMateriaisubstituto);
        TextView semPrecoSubstituto = (TextView)view.findViewById(R.id.precoOrcamentoSemPrecoListaMateriaisSubstituto);
        TextView semPreco = (TextView)view.findViewById(R.id.precoOrcamentoSemPrecoListaMateriais);

        TextView novoMaterial = (TextView)view.findViewById(R.id.novoServicoListaMateriais);
        TextView total = (TextView)view.findViewById(R.id.textViewTotalListaMateriais);
        TextView totalPreço = (TextView)view.findViewById(R.id.totalPreçoListaMateriais);

        Button finalizar = (Button)view.findViewById(R.id.finalizarListaMateriais);

        titulo.setTypeface(medium);
        subTitulo.setTypeface(light);
        preçoOrçamento.setTypeface(medium);
        preçoOrçamentoSubstituto.setTypeface(medium);
        novoMaterial.setTypeface(medium);

        semPreco.setTypeface(bold);
        finalizar.setTypeface(bold);
        total.setTypeface(bold);
        totalPreço.setTypeface(bold);

        OrdemServico os = (OrdemServico)list.get(position);

        //  1 -  titulo, comentário e preço
        if (os.getStatus().equals("1")){
            titulo.setVisibility(View.VISIBLE);
            subTitulo.setVisibility(View.VISIBLE);
            preçoOrçamento.setVisibility(View.VISIBLE);

        }
        // 2 - titulo, comentário e sem preço
        if (os.getStatus().equals("2")){
            titulo.setVisibility(View.VISIBLE);
            subTitulo.setVisibility(View.VISIBLE);
            semPreco.setVisibility(View.VISIBLE);

        }

        // 3 - titulo e preço
        if (os.getStatus().equals("3")){
            titulo.setVisibility(View.VISIBLE);
            preçoOrçamentoSubstituto.setVisibility(View.VISIBLE);
        }

         // 4 -  titulo e sem preço
        if (os.getStatus().equals("4")){
            titulo.setVisibility(View.VISIBLE);
            semPrecoSubstituto.setVisibility(View.VISIBLE);

        }

        // 5 - Novo Material
        if (os.getStatus().equals("5")){
            novoMaterial.setVisibility(View.VISIBLE);
        }

        // 6 - Total
        if (os.getStatus().equals("6")){
            total.setVisibility(View.VISIBLE);
            totalPreço.setVisibility(View.VISIBLE);
        }

        // 7 - Finalizar
        if (os.getStatus().equals("7")){
            finalizar.setVisibility(View.VISIBLE);
        }

        return view;

    }
}