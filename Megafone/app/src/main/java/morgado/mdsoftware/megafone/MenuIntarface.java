package morgado.mdsoftware.megafone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuIntarface extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_intarface);

        TextView grite, denuncie, contate;
        ImageView ivGrite, ivDenuncie, ivContate;

        grite = (TextView)findViewById(R.id.tvGriteMenuInterface);
        denuncie = (TextView)findViewById(R.id.tvDenuncieMenuInterface);
        contate = (TextView)findViewById(R.id.tvContatoMenuInterface);

        ivGrite = (ImageView)findViewById(R.id.figuraUmMenuInterface);
        ivDenuncie = (ImageView)findViewById(R.id.FiguraDoisMenuInterface);
        ivContate = (ImageView)findViewById(R.id.figura3MenuInterface);

        grite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaGrite();
            }
        });

        contate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaContate();
            }
        });

        denuncie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaDenuncie();
            }
        });

        ivGrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaGrite();
            }
        });

        ivContate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaContate();
            }
        });

        ivDenuncie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaDenuncie();
            }
        });
    }

    private void irParaDenuncie(){
        startActivity(new Intent(getApplicationContext(), DenuncieMapsActivity.class));
    }

    private void irParaGrite(){
        startActivity(new Intent(getApplicationContext(), GriteAqui.class));

    }

    private void irParaContate(){
        startActivity(new Intent(getApplicationContext(), EnviarEmail.class));

    }
}
