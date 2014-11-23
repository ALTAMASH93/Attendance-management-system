package com.example.easyattendance;

import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.Notification.Style;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Typeface;
import android.hardware.Camera.Size;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Display_Buttons extends Activity implements OnClickListener {

	static String[] class_names;
	static Button[] list_class_names;
	static int no_of_rows=0,i=0,index_button_count=0;
	static int index_external=0;
	static String[] class_external;
	static SharedPreferences sp;
	static Editor editor;
	static int found_roll=0;
	static String[] names;
	static String[] rolls;
	static Button[] class_rolls;
	static int index_roll_names=0;
	static ScrollView sc;
	static LinearLayout sv;
	static String clicked_class_name;
	static int back_button_pressed=0;
	static boolean flag_check_if_entered=false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("My_preferences", Context.MODE_PRIVATE);
		editor=sp.edit();
		
			class_names=new String[1000];
			list_class_names=new Button[1000];
			class_external=new String[1000];
			names=new String[1000];
			rolls=new String[1000];
			class_rolls=new Button[1000];
			sc=new ScrollView(this);
			sv=new LinearLayout(this);
		
			for(int i=0;i<100;i++)
			{
				class_names[i]="";
				list_class_names[i]=null;
			}
		
	
		try
		{
		DataHandler dh=new DataHandler(getApplicationContext());
		
		class_names=dh.fetch_class_names();//get the class names registered till date
		no_of_rows=dh.count_number_of_rows();
		
		
		ScrollView sc = new ScrollView(this);
		
		sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc.setFillViewport(true);
		sc.setBackgroundColor(Color.BLACK);
		
		LinearLayout sv=new LinearLayout(this);
		sv.setOrientation(sv.VERTICAL);
		
		i=0;
	//	Button auto_button=new Button(this);
	//	auto_button.setText("---Auto Registered---");
	//	auto_button.setBackgroundColor(Color.WHITE);
	//	sv.addView(auto_button);
		TextView auto_tv=new TextView(this);
		auto_tv.setText("---Auto Registered---");
		auto_tv.setBackgroundColor(Color.WHITE);
		sv.addView(auto_tv);
	while(class_names[i]!="")
	{
		
		list_class_names[i]=new Button(this);
		list_class_names[i].setText(""+class_names[i]);
		list_class_names[i].setTextColor(Color.WHITE);
		list_class_names[i].setGravity(Gravity.LEFT);
		list_class_names[i].setTypeface(null, Typeface.BOLD);
		list_class_names[i].setTag(0);
		flag_check_if_entered=true;
		try
		{
			list_class_names[i].setOnClickListener(this);
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "cant set up on click listener for array !", Toast.LENGTH_LONG).show();
		}
		
		sv.addView(list_class_names[i]);
		i++;
	}
	if(flag_check_if_entered==false)
	{
		auto_tv.setVisibility(auto_tv.INVISIBLE);
	}
	if(sp.contains("class_names_"+0))
	{
		TextView file_button=new TextView(this);
		file_button.setText("---Fetched From Files---");
		file_button.setBackgroundColor(Color.WHITE);
		sv.addView(file_button);
	//	Toast.makeText(getApplicationContext(), "Inside here!", Toast.LENGTH_LONG).show();
		index_external=sp.getInt("INDEX_CLASS_NAMES", 0);
		for(int j=0;j<=index_external;j++)
		{
			class_external[j]=sp.getString("class_names_"+j, null);
		}
		int j1=0;
		while(j1<=index_external)
		{
			list_class_names[i]=new Button(this);
			list_class_names[i].setText(""+class_external[j1]);
			list_class_names[i].setTextColor(Color.WHITE);
			list_class_names[i].setGravity(Gravity.LEFT);
			list_class_names[i].setTypeface(null, Typeface.BOLD);
			list_class_names[i].setTag(167);
			list_class_names[i].setOnClickListener(this);
			sv.addView(list_class_names[i]);
			i++;
			j1++;
		}
	}
	else
	{
		//Toast.makeText(getApplicationContext(), "Outside here!", Toast.LENGTH_LONG).show();
	}
	
	sc.addView(sv);
	setContentView(sc);
	}	//try ends here
	catch(Exception e)
	{
		String s=e.getMessage();
		Toast.makeText(getApplicationContext(), ""+s.toString(), Toast.LENGTH_LONG).show();
	}


	}
	
	
	@Override
	public void onClick(View v) {
		try
		{
			
				Button b=(Button)v;
				String button_text=(String) b.getText();
				clicked_class_name=button_text;
				if(!v.getTag().equals(167))
				{
					Intent i=new Intent(this,Yes_SIR.class);
					i.putExtra("flag_fetched_from_file", 0);
					i.putExtra("button_clicked_text", button_text);
					startActivity(i);
				}
			
			else if(v.getTag().equals(167))
			{
				try
				{
					//Toast.makeText(getApplicationContext(), "Entering this try block", Toast.LENGTH_LONG).show();
					read_roll_numbers();
					Intent i=new Intent(this,Yes_SIR.class);
					i.putExtra("flag_fetched_from_file", 1);
					i.putExtra("NAMES_FROM_FILES",names);
					i.putExtra("ROLLS_FROM_FILES",rolls);
					i.putExtra("button_clicked_text", button_text);
					i.putExtra("index_roll_names", index_roll_names);
					//Toast.makeText(getApplicationContext(), ""+rolls[0], Toast.LENGTH_LONG).show();
					startActivity(i);
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), ""+e.getMessage().toString(), Toast.LENGTH_LONG).show();
				}
				
			}
		
			
			
			
			
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "error in onclick method!", Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	public void display_roll_numbers()
	{
		sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc.setFillViewport(true);
		sv.setOrientation(sv.VERTICAL);
		sc.setBackgroundColor(Color.BLACK);
		for(int i=0;i<index_roll_names;i++)
		{
			class_rolls[i]=new Button(this);
			class_rolls[i].setText(""+rolls[i]+"."+"  "+names[i]);
			class_rolls[i].setGravity(Gravity.LEFT);
			class_rolls[i].setTextColor(Color.WHITE);
			class_rolls[i].setId(110);
			class_rolls[i].setOnClickListener(this);
			sv.addView(class_rolls[i]);
		}
		sc.addView(sv);
		setContentView(sc);

	}
	
	public void read_roll_numbers() 
	{
		
	    try {
	    	
	    	File path_to_file=new File("/sdcard/Easy Attendance/Enter_text_files/"+clicked_class_name+".txt");
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
	
	
		
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		//super.onBackPressed();
	Intent i=new Intent(this,MainActivity.class);
	startActivity(i);
	finish();
	System.exit(0);
	}

}
