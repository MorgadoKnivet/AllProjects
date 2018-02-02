package com.example.moorg.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;





public class Projeto1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); // recuperou o construtor através do super
        setContentView(R.layout.activity_projeto1);

        // ---------



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projeto1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Processando...", Toast.LENGTH_SHORT);
          toast.show();
    }

    public void teste() {
        Switch s = (Switch) findViewById(R.id.musica);
        boolean checked = s.isChecked();
        Toast toast = Toast.makeText(getApplicationContext(),
                s.getText() + " : " + checked, Toast.LENGTH_SHORT);
        toast.show();
    }


 /*   Button botOk = (Button) findViewById(R.id.ok);

    botOk.setOnClickListener(new View.OnClickListener()  {
        public void onClick(View v){

            }
        });
*/

    }


