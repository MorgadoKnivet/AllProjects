package morgado.mdsoftware.alertmaps;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MoorG on 24/10/2017.
 */

public class ListaPersonalizada extends BaseAdapter {


    private final List cursos;
    private final Activity act;
    public ListaPersonalizada(List cursos, Activity act) {
        this.cursos = cursos;

        this.act = act;
    }

    @Override
    public int getCount() {
        return cursos.size();

        // O próprio método já diz o que ele faz: conta quantos itens existem na lista.
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);

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
      //  EditText titulo = (EditText)view.findViewById(R.id.tituloEditTextLP) ;
        TextView endereço = (TextView) view.findViewById(R.id.tvEndereçoListaP);
        TextView cidade = (TextView) view.findViewById(R.id.tvCidadeListaP);
        TextView pais = (TextView) view.findViewById(R.id.tvOutrosListaP);
        TextView numero = (TextView) view.findViewById(R.id.tvNumeroListaP);
        TextView estado = (TextView) view.findViewById(R.id.tvEstadoListaP);

        Tarefa tarefa = (Tarefa)cursos.get(position);

        endereço.setText(tarefa.getEndereço());
        numero.setText(tarefa.getNumero());
        cidade.setText(tarefa.getLocalidade());
        estado.setText(tarefa.getAreaAdministrativa());
        pais.setText(tarefa.getPais());

        return view;
    }
}
