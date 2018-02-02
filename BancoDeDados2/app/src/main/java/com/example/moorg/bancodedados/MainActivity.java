package com.example.moorg.bancodedados;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.moorg.bancodedados.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	public void getActivity(View view){
		Button bt = (Button) view;
		Intent intent;
		
		if(bt.getText().toString().equalsIgnoreCase("Novo usu�rio")){
			intent = new Intent(this, NewUserActivity.class);
		}
		else{
			intent = new Intent(this, ListUsersActivity.class);
		}
		
		startActivity(intent);
	}

}
