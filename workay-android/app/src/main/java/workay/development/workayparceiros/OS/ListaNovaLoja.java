package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

/**
 * Created by MoorG on 24/10/2017.
 */

public class ListaNovaLoja extends BaseAdapter {


    private final List list;
    private final Activity act;
    Typeface fontBold;
    Typeface fontLight;
    public ListaNovaLoja(List list, Activity act, Typeface fontBold, Typeface font) {
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


        View view = act.getLayoutInflater().inflate(R.layout.lista_novo_material, parent, false);

        TextView loja = (TextView)view.findViewById(R.id.textViewLojaNovoMaterial);
        TextView preco = (TextView)view.findViewById(R.id.textViewPreçoNovoMaterial);
        TextView rs = (TextView)view.findViewById(R.id.rsListaNovoMaterial);
        TextView add = (TextView)view.findViewById(R.id.addOutraLojaListaNovoMaterial);
        EditText etLoja = (EditText)view.findViewById(R.id.editTextLojaNovoMaterial);
        EditText total = (EditText)view.findViewById(R.id.editTextPreçoNovoMaterial);

        OrdemServico os = (OrdemServico) list.get(position);

        if (os.getStatus().equals("1")){
            loja.setVisibility(View.VISIBLE);
            preco.setVisibility(View.VISIBLE);
            rs.setVisibility(View.VISIBLE);
            etLoja.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
            preco.setText(os.getPreco());
            etLoja.setText(os.getLoja());

        }
        // 2 - titulo, comentário e sem preço
        if (os.getStatus().equals("2")){
            add.setVisibility(View.VISIBLE);

        }

        return view;
    }
}
