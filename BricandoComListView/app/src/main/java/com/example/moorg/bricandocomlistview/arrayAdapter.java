package com.example.moorg.bricandocomlistview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MoorG on 24/05/2017.
 */

public class arrayAdapter extends BaseAdapter {
    private final List cursos;
    private final Activity act;
    public arrayAdapter(List cursos, Activity act) {
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
       // Curso curso = cursos.get(position);
        TextView titulo = (TextView)
                view.findViewById(R.id.titulo);
        TextView linhaUm = (TextView)
                view.findViewById(R.id.linha1);
      //  ImageButton imgButton = (ImageButton)view.findViewById(R.id.imageButton);

        // http://blog.alura.com.br/personalizando-uma-listview-no-android/

            titulo.setText("Titulo" );
            linhaUm.setText("Aqui vai entrar o endereço");

     //   imgButton.setImageResource(R.mipmap.google);



        return view;

        // Inflater faz um layout xml virar um objeto para vc utilizar suas views.

        // esse é o método responsável pela construção de cada item!
    }
}
