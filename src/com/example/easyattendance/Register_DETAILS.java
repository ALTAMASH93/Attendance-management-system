package com.example.easyattendance;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.rtp.RtpStream;
import android.opengl.Visibility;
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

public class Register_DETAILS extends Activity implements OnClickListener {

	SQLiteDatabase db=null;
	DataHandler handler=null;
	Button b_add_rolls,b_delete_all_data;
	EditText et_class_name,et_no_of_students;
	static int no_of_clicks=0;
	
	Cursor cs=null;
	String temp_class_name=null,roll_to=null,roll_from=null;
	static int flag_can_save=1;
	static String class_name=null,no_of_students=null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
     
        et_class_name=(EditText)findViewById(R.id.et_class_name);
        et_no_of_students=(EditText)findViewById(R.id.et_NO_OF_STUDENTS);
        b_delete_all_data=(Button)findViewById(R.id.button_delete_all_data);
        b_delete_all_data.setVisibility(View.INVISIBLE);
        b_delete_all_data.setClickable(false);
        b_add_rolls=(Button)findViewById(R.id.button_ADD_ROLL_NUMBERS);
        b_add_rolls.setOnClickListener(this);
    }
    
    
    
    public void delete_all_data(View v)
    {
    		no_of_clicks++;
    		if(no_of_clicks>=2)
    		{
    			handler=new DataHandler(getApplicationContext());
        		cs=handler.returnDATA();
        		handler.delete_data();
    		}
    		else
    		{
    			Toast.makeText(getApplicationContext(), "press again to delete!", Toast.LENGTH_LONG).show();
    		}
    		
    	
    }
   
    
   

    @Override
	public void onClick(View arg0) {
		
    	switch (arg0.getId()) {
		case R.id.button_ADD_ROLL_NUMBERS:
			class_name=et_class_name.getText().toString();
			no_of_students=et_no_of_students.getText().toString();
			handler=new DataHandler(getBaseContext());
			cs=handler.returnDATA();
			if(class_name.equals("") || no_of_students.equals(""))
			{
				flag_can_save=0;
				Toast.makeText(getApplicationContext(), "Data is not entered in one or more fields !", Toast.LENGTH_LONG).show();
			}
			else if(cs.moveToFirst())
			{
				do
				{
					temp_class_name=cs.getString(0);
					if(temp_class_name.equals(class_name))
					{
						Toast.makeText(getApplicationContext(), "Data for "+class_name +"already exists!", Toast.LENGTH_SHORT).show();
						flag_can_save=0;
					}
				}while(cs.moveToNext());
			}
			if(flag_can_save==1)
			{
				Intent i=new Intent(this,Enter_roll_numbers.class);
				i.putExtra("class_name", class_name);
				i.putExtra("no_of_students", no_of_students);
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "NOT saved !", Toast.LENGTH_SHORT).show();
				flag_can_save=1;
			}
			break;
		
		
		default:
			break;
		}
		
	}
    
    
    
    
    
    
    
    
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    //	super.onBackPressed();
    	Intent i=new Intent(this,Type_chooser.class);
    	startActivity(i);
    	finish();
    	System.exit(0);
    }

	

}
