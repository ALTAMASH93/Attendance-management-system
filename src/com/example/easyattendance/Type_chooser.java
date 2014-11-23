package com.example.easyattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Type_chooser extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_chooser);
	}
	
	public void func_insert_rolls(View v)
	{
		Intent i=new Intent(this,Register_DETAILS.class);
		startActivity(i);
	}
	
	public void func_fetch_from_file(View v)
	{
		Intent i=new Intent(this,Add_class_names.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent i=new Intent(this,MainActivity.class);
		startActivity(i);
		finish();
		System.exit(0);
	}
}
