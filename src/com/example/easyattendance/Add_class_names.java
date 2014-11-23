package com.example.easyattendance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Add_class_names extends Activity {
	EditText et_class_name;
	static SharedPreferences sp;
	static Editor editor;
	static String[] class_names;
	static int index_class_names=-1;
	Button b_fetch_details,b_delete,b_display_class_names;
	static Button[] button_classes;
	ScrollView sc;
	LinearLayout sv;
	static int found_roll=0;
	static String[] names;
	static String[] rolls;
	static String entered_class_name="";
	static int index_roll_names=0;
	static String current_class="";
	static int get_current_id=-1;
	static int no_of_clicks=0;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_class_name);
		sp=getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
		editor=sp.edit();
		initialize_variables();
		load_back_values();
	}
	
	public void initialize_variables()
	{
		class_names=new String[1000];
		et_class_name=(EditText)findViewById(R.id.et_class_name);
		
		b_delete=(Button)findViewById(R.id.button_DELETE);
		b_fetch_details=(Button)findViewById(R.id.button_fetch_details);
		b_display_class_names=(Button)findViewById(R.id.button_display_class_names);
		sc=new ScrollView(this);
		sc.setId(198);
		sv=new LinearLayout(this);
		button_classes=new Button[1000];
		names=new String[1000];
		rolls=new String[1000];
	}
	
	public void read_roll_numbers() 
	{
		
	    try {
	   
	    	File path_to_file=new File("/sdcard/Easy Attendance/Enter_text_files/"+"cs4"+".txt");
	    	BufferedReader br=new BufferedReader(new FileReader(path_to_file));
	    	String line,roll="",name="";
	    	int index=0;
	    	String line1="";
	    	while((line=br.readLine())!=null)	//check for end of file
	    	{
	    		line1=line.trim();
	    		if(!line1.equals("") && (check_if_digit(line1.charAt(index))))
	    		{
					        		while(index<line.length())		//check for end of line
					        		{
							        			
							        			
							        			while(line.charAt(index)==' ' && index<line.length())
							        			{
							        				index++;
							        			}
							        			if(check_if_digit(line.charAt(index)) && index<line.length())
							        			{
							        				found_roll=1;
							        				while(check_if_digit(line.charAt(index)) && index<line.length())
							        				{
							        					roll+=line.charAt(index);
							        					//rolls[index_roll_names]+=line.charAt(index);
							        					//index_roll_names++;
								        				index++;
							        				}
							        				
							        			}
							        			else
							        			{
							        				break;
							        			}
							        			if(found_roll==1)
							        			{
									        			while(line.charAt(index)==' ' && index<line.length())
									        			{
									        				index++;
									        			}
									        			
									        			while(index<line.length() )
									        			{
									        				//names[index_roll_names]+=line.charAt(index);
									        				name+=line.charAt(index);
									        				//index_roll_names++;
									        				index++;
									        			}
									        			index++;
							        			}
							        			else
							        			{
							        				break;
							        			}
							        	}	//line ends
	    	
	        		if(found_roll==1)
	        		{
	        			names[index_roll_names]=name;
	        			rolls[index_roll_names]=roll;
	        			index_roll_names++;
			        //	Toast.makeText(getApplicationContext(), "Roll:"+roll, Toast.LENGTH_LONG).show();
			        //	Toast.makeText(getApplicationContext(), "Name is:"+name, Toast.LENGTH_LONG).show();
	        		}
	        		
			        	index=0;
			        	name="";
			        	roll="";
	        		
	    		
	    	}//check if line is not empty	
	    		
	    		else
	    		{
	    			
	    		}
	    		
	    	
	    	
	    	}//file ends
	    }//try block ends
	   catch(Exception e)
	    {
	    	Toast.makeText(getApplicationContext(), ""+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    }
	    

	    }

	public boolean check_if_digit(char c)
	{
		if(c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9')
		{
			return true;
		}
		return false;
	}

	public void load_back_values()
	{
		if(sp.contains("class_names_"+0))
		{
			
			index_class_names=sp.getInt("INDEX_CLASS_NAMES", 0);
			for(int i=0;i<=index_class_names;i++)
			{
				class_names[i]=sp.getString("class_names_"+i, null);
			}
		}
	}

	public void fetch_details(View v)
	{
		if(check_if_exists() && !et_class_name.getText().toString().equals(""))
		{
			current_class=et_class_name.getText().toString();
			index_class_names++;
			class_names[index_class_names]=et_class_name.getText().toString();
			editor.putInt("INDEX_CLASS_NAMES", index_class_names);
			
			editor.putString("class_names_"+index_class_names, class_names[index_class_names]);
			editor.commit();
		}
		Toast.makeText(getApplicationContext(), "class names registered till now..\n", Toast.LENGTH_LONG).show();
		
		for(int i=0;i<=index_class_names;i++)
		{
			Toast.makeText(getApplicationContext(), ""+class_names[i]+"at index "+i, Toast.LENGTH_LONG).show();
		}
		
		//	delete_all_details();
		
		
		
	}
	
	public void delete_all_data(View v)
	{
		no_of_clicks++;
		if(no_of_clicks>=2)
		{
			delete_all_details();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Deleted successfully!\nYou can re-register any time!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void delete_all_details() 
	{
		index_class_names=-1;
		editor.clear();
		editor.commit();
		Toast.makeText(getApplicationContext(), "deleted data", Toast.LENGTH_LONG).show();
	}
	
	public boolean check_if_exists()
	{
		for(int i=0;i<=index_class_names;i++)
		{
			if(et_class_name.getText().toString().equals(sp.getString("class_names_"+i, null)))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void display_class_names(View v) 
	{
		try
		{
			sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			sc.setFillViewport(true);
			sv.setOrientation(sv.VERTICAL);
			sc.setBackgroundColor(Color.BLACK);
			for(int i=0;i<=index_class_names;i++)
			{
				button_classes[i]=new Button(this);
				button_classes[i].setId(1);
				button_classes[i].setTextColor(Color.WHITE);
				button_classes[i].setText(""+class_names[i]);
				sv.addView(button_classes[i]);
			}
			sc.addView(sv);
			setContentView(sc);
			
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), ""+e.getMessage().toString()+"\n"+e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
		}
		
		
	}
	

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	
			Intent i=new Intent(this,Type_chooser.class);
			//Toast.makeText(getApplicationContext(), "Entering here", Toast.LENGTH_LONG).show();
			startActivity(i);
			finish();
			System.exit(0);
		
		
		
	}
}
