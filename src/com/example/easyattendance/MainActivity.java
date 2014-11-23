//first screen

package com.example.easyattendance;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {		
	
	Intent i=null;
	

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.main_menu_screen);
       File f1=new File("/sdcard/Easy Attendance/Enter_text_files");
  	 
 	  if(!(f1.isDirectory() || f1.exists()))
 	  {
 		  f1.mkdirs();
 	  }
    }
	
	public void func_register_details(View v)
	{
		i=new Intent(this,Type_chooser.class);
		startActivity(i);
		
	}
	
	public void func_Load_Data(View v)
	{
		i=new Intent(this,Display_Buttons.class);
		startActivity(i);
	}
	
	public void check_stats(View v)
	{
		i=new Intent(this,Check_Stats.class);
		startActivity(i);
	}
			
	public void record_deletion(View v)
	{
		i=new Intent(this,Record_deletion.class);
		startActivity(i);
	}
		

    
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    //	super.onBackPressed();
    	Intent i=new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
		System.exit(0);

    }
    
}
