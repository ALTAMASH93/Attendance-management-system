package com.example.easyattendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Record_deletion extends Activity{
	
	Button delete_records;
	SharedPreferences sp;
	Editor ed;
	DataHandler handler;
	Cursor cs;
	static int no_of_clicks=0;
	static int no_of_clicks2=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_deletion);
		
		delete_records=(Button)findViewById(R.id.button_delete_records);
	}
	public void delete_manually_entered_data(View v)
	{
		try
		{
			no_of_clicks2++;
			if(no_of_clicks2>=2)
			{
				handler=new DataHandler(getApplicationContext());
	    		cs=handler.returnDATA();
	    		handler.delete_data();
	    		Toast.makeText(getApplicationContext(), "Deleted manually entered data!", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "press again to delete!", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Exception Encountered!"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void delete_data_fetched_from_files(View v)
	{
		
	}
	public void delete_all_attendance_records(View v)
	{
		no_of_clicks++;
		if(no_of_clicks>=2)
		{
			sp=getSharedPreferences("records", Context.MODE_PRIVATE);
			ed=sp.edit();
			ed.clear();
			ed.commit();
			Toast.makeText(getApplicationContext(), "Deleted all records!", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Press again to delete!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
	finish();
	System.exit(0);
	Intent i=new Intent(this,MainActivity.class);
	startActivity(i);
	}

}
