package workay.development.workayparceiros.OS;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import workay.development.workayparceiros.Class.OrdemServico;
import workay.development.workayparceiros.R;

/**
 * Created by MoorG on 26/02/2018.
 */
public class ListaDiagnostico extends BaseAdapter {


    private final List list;
    private final Activity act;
    Typeface bold;
    Typeface light, medium;
    private Double soma = 0.0;
    private int cont = 0;

    public ListaDiagnostico(List list, Activity act, Typeface fontBold, Typeface font, Typeface medium) {
        this.list = list;

        this.act = act;
        this.light = font;
        this.bold = fontBold;
        this.medium = medium;
        this.soma = soma;
    }

    public ListaDiagnostico(List list, Activity act, Typeface fontBold, Typeface font, Typeface medium, double soma) {
        this.list = list;

        this.act = act;
        this.light = font;
        this.bold = fontBold;
        this.medium = medium;
        this.soma = soma;
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

        View view = act.getLayoutInflater().inflate(R.layout.lista_diagnostico, parent, false);

        TextView titulo = (TextView)view.findViewById(R.id.subtituloDiagnostico);
        TextView subTitulo = (TextView)view.findViewById(R.id.descricaoSubTituloDiagnostico);
        TextView preçoOrçamento = (TextView)view.findViewById(R.id.precoOrcamentoDiagnostico);
        TextView preçoOrçamentoSubstituto = (TextView)view.findViewById(R.id.precoOrcamentoDiagnosticoSubstituto);
        TextView novoServico = (TextView)view.findViewById(R.id.novoServicoDiagnostico);
        TextView prazo = (TextView)view.findViewById(R.id.textViewPrazoDiagnostico);
        TextView prazoTv = (TextView)view.findViewById(R.id.respostaPrazoDiagnostico);
        TextView total = (TextView)view.findViewById(R.id.textViewTotalDiagnostico);
        TextView totalPreço = (TextView)view.findViewById(R.id.totalPreçoDiagnostico);

        titulo.setTypeface(medium);
        subTitulo.setTypeface(light);
        preçoOrçamento.setTypeface(medium);
        preçoOrçamentoSubstituto.setTypeface(medium);
        novoServico.setTypeface(medium);

        prazo.setTypeface(bold);
        prazoTv.setTypeface(bold);
        total.setTypeface(bold);
        totalPreço.setTypeface(bold);

        OrdemServico os = (OrdemServico)list.get(position);


        try {
            if (cont<list.size()-3) {
                if (os.getStatus().equals("1")||os.getStatus().equals("2")){


                    soma = soma + Double.parseDouble(os.getPreco());

                    cont=cont+1;
                    Log.i("Resultado","ResultadoSoma "+soma + " Cont: "+cont);
                }
            }
        }catch (NullPointerException n){
            soma = soma + 0;
            Log.i("Resultado","ResultadoSomaErroNullPointerException "+soma + " Cont: "+cont);
        }catch (NumberFormatException n){
            soma = soma + 0;
            Log.i("Resultado","ResultadoSomaErroNumberFormatException "+soma + " Cont: "+cont);
        }

        // Titulo, comentário e preço
        if (os.getStatus().equals("1")){
            titulo.setVisibility(View.VISIBLE);
            subTitulo.setVisibility(View.VISIBLE);
            preçoOrçamento.setVisibility(View.VISIBLE);
            titulo.setText(os.getTitulo());
            subTitulo.setText(os.getDescricao());
            preçoOrçamento.setText(os.getPreco());

        }

        // Titulo e preço
        if (os.getStatus().equals("2")){
            titulo.setVisibility(View.VISIBLE);
            preçoOrçamentoSubstituto.setVisibility(View.VISIBLE);
            titulo.setText(os.getTitulo());
            preçoOrçamentoSubstituto.setText(os.getPreco());

        }

        // Novo serviço
        if (os.getStatus().equals("3")){
            novoServico.setVisibility(View.VISIBLE);

        }
        // Prazo
        if (os.getStatus().equals("4")){
            prazo.setVisibility(View.VISIBLE);
           prazoTv.setVisibility(View.VISIBLE);


            prazoTv.setText(os.getPrazo());
        }

        // Preço total
        if (os.getStatus().equals("5")){

            total.setVisibility(View.VISIBLE);
            totalPreço.setVisibility(View.VISIBLE);


            if (os.getPreco()==null) {
                totalPreço.setText(soma+"");
            }else{
                if (!os.getPreco().equals("erro_valor_retornar_prazo_vazio")){
                    totalPreço.setText(os.getPreco());
                }else{
                    totalPreço.setText(soma+"");
                }

            }
        }

        return view;


    }

    public double getSoma(){
        return soma;
    }


}