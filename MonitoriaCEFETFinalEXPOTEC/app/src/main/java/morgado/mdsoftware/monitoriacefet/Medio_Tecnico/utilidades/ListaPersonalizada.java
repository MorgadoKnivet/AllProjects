package morgado.mdsoftware.monitoriacefet.Medio_Tecnico.utilidades;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import morgado.mdsoftware.monitoriacefet.Medio_Tecnico.outros.Monitoria;
import morgado.mdsoftware.monitoriacefet.R;

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
        View view = act.getLayoutInflater().inflate(R.layout.activity_lista_personalizada_layout_2_0,parent,false);

        TextView monitor = (TextView) view.findViewById(R.id.nomeLP);
        TextView materia = (TextView) view.findViewById(R.id.materiaLP);
        TextView auxdado1 = (TextView) view.findViewById(R.id.dado1);
        TextView auxdado2 = (TextView)view.findViewById(R.id.dado2);
        TextView auxdado3 = (TextView)view.findViewById(R.id.dado3);
        TextView auxdado4 = (TextView)view.findViewById(R.id.dado4);
        TextView auxdado5 = (TextView)view.findViewById(R.id.dado5);
       TextView auxTvDados = (TextView)view.findViewById(R.id.tvDadosLVPersonalizada);


        // http://blog.alura.com.br/personalizando-uma-listview-no-android/

        Monitoria monitoria = (Monitoria) cursos.get(position);





        if (!(TextUtils.isEmpty(monitoria.getMonitor()))) {
            monitor.setText("Monitor: "+monitoria.getMonitor());
        }else {
            monitor.setVisibility(View.GONE);
            auxTvDados.setVisibility(View.GONE);
        }

        if (!(TextUtils.isEmpty(monitoria.getMateria()))) {
            materia.setText(monitoria.getMateria());
        }else {
            materia.setVisibility(View.GONE);
        }

        if (!(TextUtils.isEmpty(monitoria.getZdado1()))) {
            auxdado1.setText(monitoria.getZdado1());
        }else {
            auxdado1.setVisibility(View.GONE);
        }

        if (!(TextUtils.isEmpty(monitoria.getZdado2()))){
            auxdado2.setText(monitoria.getZdado2());
        }else {
            auxdado2.setVisibility(View.GONE);
        }


        if (!(TextUtils.isEmpty(monitoria.getZdado3()))) {
            auxdado3.setText(monitoria.getZdado3());
        }else {
            auxdado3.setVisibility(View.GONE);
        }


        if (!(TextUtils.isEmpty(monitoria.getZdado4()))) {
            auxdado4.setText(monitoria.getZdado4());
        }else {
            auxdado4.setVisibility(View.GONE);
        }

        if (!(TextUtils.isEmpty(monitoria.getZdado5()))) {
            auxdado5.setText(monitoria.getZdado3());
        }else {
            auxdado5.setVisibility(View.GONE);
        }
        /*

        if (!(TextUtils.isEmpty(monitoria.getObservação()))) {
            auxobs.setText("Observação: "+monitoria.getObservação());
        }else {
            auxobs.setVisibility(View.GONE);
        }
        */

        //   titulo.setText("Titulo");


        //     titulo.setText((""+ getItem(1)));

        //   imgButton.setImageResource(R.mipmap.google);



        return view;

        // Inflater faz um layout xml virar um objeto para vc utilizar suas views.

        // esse é o método responsável pela construção de cada item!
    }

}
