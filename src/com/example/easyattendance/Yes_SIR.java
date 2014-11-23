package com.example.easyattendance;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

	public class Yes_SIR extends ActionBarActivity implements OnClickListener{
		
		String clicked_class_name,coded_sequential_roll,coded_additional_roll;
		static String[] decoded_additional_roll;
		static String[] decoded_sequential_roll;
		static int[] int_additional_roll;
		static int[] int_sequential_roll;
		static int[] int_all_sequentials;
		static Button[] button_rolls;
		static int get_ii=0;
		static int back_button_pressed=0;
		static int index_decode_additionals=0;
		static int index_decode_sequentials=0;
		static int index_all=0;
		static int index_button=0;
		static int i3=0;
		static boolean[] flag_present;
		static String s_f="";
		static String file_name_to_use="";
		static int the_sp_index=-1;
		static String[] file_names;
		static String[] file_rolls;
		static int index_roll_names=0;
		static int fetched_from_file=-1,final_index=-1,final_sequential_index=-1;
	 
		
		ScrollView sc;
		LinearLayout sv;
		SharedPreferences sp;
		Editor ed;
		

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sc=new ScrollView(this);
		sv=new LinearLayout(this);
		file_rolls=new String[1000];
		file_names=new String[1000];
		
		sp=getSharedPreferences("records", Context.MODE_PRIVATE);
		ed=sp.edit();
		initialize_all_variables();
		DataHandler dh=new DataHandler(getApplicationContext());
			
		clicked_class_name=getIntent().getStringExtra("button_clicked_text");
		fetched_from_file=getIntent().getIntExtra("flag_fetched_from_file", 0);
		coded_sequential_roll=dh.fetch_roll_numbers(clicked_class_name, 10);//10 for coded_sequential_roll
		coded_additional_roll=dh.fetch_roll_numbers(clicked_class_name, 20);//20 for coded_additional_roll
			
	if(fetched_from_file!=1)
	{
			decode_additionals();
			decode_sequentials();
			convert_String_to_int_array();
			bubble_sort_rolls();
			get_all_sequentials();
			try {
				display_buttons();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "cant display buttons", Toast.LENGTH_LONG).show();
			}
	}
	else if(fetched_from_file==1)
	{
		try
		{
		clicked_class_name=getIntent().getStringExtra("button_clicked_text");
		index_roll_names=getIntent().getIntExtra("index_roll_names", 0);
		file_names=getIntent().getStringArrayExtra("NAMES_FROM_FILES");
		file_rolls=getIntent().getStringArrayExtra("ROLLS_FROM_FILES");
	
			//Toast.makeText(getApplicationContext(), ""+file_rolls[0], Toast.LENGTH_LONG).show();
		
		display_rolls_from_file();
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Exception in display_rolls_from_file: "+e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	}//end of on create function
	
	
	
	
	public void convert_String_to_int_array()
	{
		for(int i=0;i<index_decode_additionals;i++)
		{
			int_additional_roll[i]=Integer.parseInt(decoded_additional_roll[i]);
		}
		
		for(int i=0;i<index_decode_sequentials;i++)
		{
			int_sequential_roll[i]=Integer.parseInt(decoded_sequential_roll[i]);
		}
	}
	
	
	
	public void get_all_sequentials()
	{
		int temp1=0,temp2=0,temp3=0;
		int local_index_all_sequentials=0;
		while(temp2<=int_sequential_roll[index_decode_sequentials-1])
		{
			temp2=int_sequential_roll[local_index_all_sequentials];
			temp3=int_sequential_roll[local_index_all_sequentials+1];
			
			while(temp2<=temp3)
			{
				int_all_sequentials[index_all]=temp2;
				temp2++;
				index_all++;
			}
			
			local_index_all_sequentials+=2;
			
		}
	}
	

	
	public void initialize_all_variables()
	{
		back_button_pressed=0;
		decoded_additional_roll=new String[1000];
		decoded_sequential_roll=new String[1000];
		int_additional_roll=new int[1000];
		int_sequential_roll=new int[1000];
		int_all_sequentials=new int[1000];
		button_rolls=new Button[1000];
		
		for(int i=0;i<1000;i++)
		{
			decoded_additional_roll[i]="";
			decoded_sequential_roll[i]="";
			
			int_additional_roll[i]=-1;
			int_sequential_roll[i]=-1;
			int_all_sequentials[i]=-1;
		}
	}
	
	public void decode_additionals()
	{
		String temp=coded_additional_roll;
		temp.trim();
		int index=0;
		
		while(index<coded_additional_roll.length())
		{
			while(temp.charAt(index)==' ')
			{
				if(index>=coded_additional_roll.length()-1)
					break;
				index++;
			}
			if(index>=coded_additional_roll.length()-1)
					break;
			while(temp.charAt(index)!=' ')
			{
				if(index>=coded_additional_roll.length()-1)
					break;
				decoded_additional_roll[index_decode_additionals]+=temp.charAt(index);
				index++;
			}
			index_decode_additionals++;
		}
	/*	for(int i=0;i<=index_decode_additionals;i++)
		{
			Toast.makeText(getApplicationContext(), ""+decoded_additional_roll[i], Toast.LENGTH_LONG).show();
		}*/
		
		
	}
	
	public void decode_sequentials()
	{
		String temp=coded_sequential_roll;
		temp.trim();
		int index=0;
		int index_decode_additionals=0;
		while(index<coded_sequential_roll.length())
		{
			while(temp.charAt(index)==' ')
			{
				if(index>=coded_sequential_roll.length()-1)
					break;
				index++;
			}
			
			if(index>=coded_sequential_roll.length()-1)
				break;
			
			while(temp.charAt(index)!=' ')
			{
				if(index>=coded_sequential_roll.length()-1)
					break;
				decoded_sequential_roll[index_decode_sequentials]+=temp.charAt(index);
				index++;
			}
			index_decode_sequentials++;
		}
	/*	for(int i=0;i<=index_decode_sequentials;i++)
		{
		//	Toast.makeText(getApplicationContext(), ""+decoded_sequential_roll[i], Toast.LENGTH_LONG).show();
		}*/
		
		
	}
	
	public void bubble_sort_rolls()
	{
		int i2=0,j2=0,temp=0;
		for(i2=0;i2<index_decode_sequentials;i2++)
		{
			for(j2=0;j2+1<index_decode_sequentials;j2++)
			{
				if(int_sequential_roll[j2]>int_sequential_roll[j2+1])
				{
					temp=int_sequential_roll[j2];
					int_sequential_roll[j2]=int_sequential_roll[j2+1];
					int_sequential_roll[j2+1]=temp;
				}
				
			}
		}
		
		for(i2=0;i2<index_decode_additionals;i2++)
		{
			for(j2=0;j2+1<index_decode_additionals;j2++)
			{
				if(int_additional_roll[j2]>int_additional_roll[j2+1])
				{
					temp=int_additional_roll[j2];
					int_additional_roll[j2]=int_additional_roll[j2+1];
					int_additional_roll[j2+1]=temp;
				}
				
			}
		}
	}

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	
	try
	{
		if(v.getId()==0)
		{
			v.setId(1);
			//v.setBackgroundResource(android.R.drawable.btn_default);
			v.setBackgroundColor(Color.GREEN);
		}
		else if(v.getId()==1)
		{
			v.setId(0);
			v.setBackgroundColor(Color.RED);
		}
	
		
	}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Exception encountered!", Toast.LENGTH_LONG).show();
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
	        if (id == R.id.action_settings) 
	        {
	            return true;
	        }
	        
	        else if(id==R.id.action_save)
	        {
	        	write_data_to_a_file();
	        	save_corresponding_date();
	        	try
	        	{
	        		save_data_to_internal_memory();
	        	}
	        	catch(Exception e)
	        	{
	        		Toast.makeText(getApplicationContext(), "Exception:"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	        	}
	        	
	        	Toast.makeText(getApplicationContext(), "file saved!", Toast.LENGTH_LONG).show();
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
	    public void save_corresponding_date()
	    {
	    	try
	    	{
	    	int index_date=0;
	    	String date = new SimpleDateFormat("MM-dd").format(new Date());
	    	String time=new SimpleDateFormat("HH:mm:ss").format(new Date());
	    	index_date=sp.getInt("index_date_2", 0);
	    	ed.putString(clicked_class_name+index_date+"date", date);
	    	ed.putString(clicked_class_name+index_date+"time", time);
	 //   	Toast.makeText(getApplicationContext(), "Saved at index :"+index_date, Toast.LENGTH_LONG).show();
	    	index_date++;
	    	ed.putInt("index_date_2", index_date);
	    	ed.commit();
	    	
	  //  	Toast.makeText(getApplicationContext(), "Saved date is :"+date, Toast.LENGTH_LONG).show();
	   // 	Toast.makeText(getApplicationContext(), "Saved time is :"+time, Toast.LENGTH_LONG).show();
	    	}
	    	catch(Exception e)
	    	{
	    		Toast.makeText(getApplicationContext(), "Exception in saving date:\n"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    	}
	    }
	    
	    
	    public void save_data_to_internal_memory()
	    {
	    	int i=0;
	    	int j=-1;
	    	int index_date=0;
	    	String date = new SimpleDateFormat("MM-dd").format(new Date());
	    	index_date=sp.getInt("index_date", -1);
	    	ed.putString(""+clicked_class_name+index_date, date);
	    	index_date++;
	    	ed.putInt("index_date", index_date);
	    	
	    	String temp="",temp2="";
	    	
	    	 if(getIntent().getIntExtra("flag_fetched_from_file", 0)==1)
	    	 {
	    		 while(i<index_roll_names)
	    		 {
	    			 temp="";
		    		 temp+=sp.getString(""+clicked_class_name+file_rolls[i], "");
		 	    	 temp+=button_rolls[i].getId();
		 	    	 ed.putString(""+clicked_class_name+file_rolls[i], temp);
		 	    //	 Toast.makeText(getApplicationContext(), "key "+clicked_class_name+file_rolls[i]+" value "+temp, Toast.LENGTH_LONG).show();
		 	    	 i++;
	    		 }
	    		
	    	 }
	    	 else
	    	 {
	    		 while(i<index_all)
	 	    	{
	 	    		temp="";
	 	    		temp+=sp.getString(""+clicked_class_name+button_rolls[i].getText(), "");
	 	    		temp+=button_rolls[i].getId();
	 	    		ed.putString(""+clicked_class_name+button_rolls[i].getText(), temp);
	 	    		//ed.remove(""+clicked_class_name+button_rolls[i].getText());
	 	    		i++;
	 	    	}
	 	    	i=final_sequential_index;
	 	    	while(i<final_index)
	 	    	{
	 	    		temp="";
	 	    		temp+=sp.getString(""+clicked_class_name+button_rolls[i].getText(), "");
	 	    		temp+=button_rolls[i].getId();
	 	    		ed.putString(""+clicked_class_name+button_rolls[i].getText(), temp);
	 	    	//	ed.remove(""+clicked_class_name+button_rolls[i].getText());
	 	    		i++;
	 	    	}
	    	 }
	    	
	    
	    ed.commit();
	    	
	    //	Toast.makeText(getApplicationContext(), "Data saved is : "+sp.getString(""+clicked_class_name+button_rolls[final_index-1].getText(), ""), Toast.LENGTH_LONG).show();
	    //	Toast.makeText(getApplicationContext(), "button text  : "+button_rolls[final_index-1].getText(), Toast.LENGTH_LONG).show();
	    	//check_attendance_percentage();
	    }
	    
	 	    public void change_to_correct_format()
	    {
	    	int local_i=0;
	    	while(local_i<s_f.length())
	    	{
	    		if(s_f.charAt(local_i)=='/')
	    		{
	    			file_name_to_use=s_f.replace('/', ':');
	    			//Toast.makeText(getApplicationContext(), "found at "+local_i, Toast.LENGTH_LONG).show();
	    			//.makeText(getApplicationContext(), "ss = "+ss, Toast.LENGTH_LONG).show();
	    		}
	    		local_i++;
	    	}
	    }
	   
	 	public void write_data_to_a_file()
	    {
	    	  Calendar c = Calendar.getInstance();
	    	  SimpleDateFormat sdf=new SimpleDateFormat();
	    	  SimpleDateFormat sdf2=new SimpleDateFormat("MMM");
	    	  String month_name=sdf2.format(c.getTime());
	    	  s_f=sdf.format(c.getTime());	
	    	  change_to_correct_format();
	    	  try
	    	  {
	    	  //Toast.makeText(getApplicationContext(), "output is "+s_f, Toast.LENGTH_LONG).show();
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  Toast.makeText(getApplicationContext(), "Exception is "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    	  }
	    	  int i11=0;
	    	  FileOutputStream fos=null;
	    	  OutputStreamWriter osw = null;
	    	  File f1=new File("/sdcard/Easy Attendance/"+"Attendance Records"+"/"+clicked_class_name+"/"+month_name);
	    	 
	    	  if(!(f1.isDirectory() || f1.exists()))
	    	  {
	    		  f1.mkdirs();
	    	  }
	    		 
	    	  String s="";
	    	  	try
		          {

		        	Toast.makeText(getApplicationContext(), "File name : "+s_f, Toast.LENGTH_LONG).show();
		          	File myfile=new File(f1,file_name_to_use+".txt");
		          	myfile.createNewFile();
		          	fos=new FileOutputStream(myfile);
		          	osw=new OutputStreamWriter(fos);
		          	osw.append("CLASS NAME :  "+clicked_class_name);
		          	osw.append("\nDATE & TIME : "+s_f+"\n");
		          	osw.append("\n\n---ABSENTEES---\n");
		          }
	    	  	catch(Exception e)
	    	  	{
	    	  		Toast.makeText(getApplicationContext(), "Exception :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    	  	}
	    	  	
	    	  if(getIntent().getIntExtra("flag_fetched_from_file", 0)==1)
	    	  {
	    		  get_ii=index_roll_names;
	    	  }
	    	  while(i11<get_ii)
	    	  {
			    	s=button_rolls[i11].getText().toString();
			    	try 
			    	{
			    		if(button_rolls[i11].getId()==0)
			    		{
			    			osw.append(""+s+"\n");
			    		}
			    				
					} 
			    	catch (IOException e) 
					{
						Toast.makeText(getApplicationContext(), "Exception :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
			    	i11++;
	    	 
			  }
	    	  i11=0;
	    	  try 
	    	  {
				osw.append("\n\n---PRESENT STUDENTS---\n");
	    	  } 
	    	  catch (IOException e) 
	    	  {
	    		  Toast.makeText(getApplicationContext(), ""+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    	  }
	    	  while(i11<get_ii)
	    	  {
			    	s=button_rolls[i11].getText().toString();
			    	try 
			    	{
			    		if(button_rolls[i11].getId()==1)
			    				osw.append(""+s+"\n");
					} 
			    	catch (IOException e) 
					{
						Toast.makeText(getApplicationContext(), "Exception :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
			    	i11++;
	    	 
			  }
	    	  
	    	  try
	    	  {
	    		  osw.close();
			      fos.close();
	    	  }
			  catch(Exception e)
			     {
			          	Toast.makeText(getApplicationContext(), "Exception :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
			     }
	    		 
	    	 
	    }
	
	public void display_rolls_from_file() throws Exception
	{
		sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc.setFillViewport(true);
		sv.setOrientation(sv.VERTICAL);
		sc.setBackgroundColor(Color.BLACK);
		the_sp_index=-1;
		for(int i=0;i<index_roll_names;i++)
		{
			button_rolls[i]=new Button(this);
			button_rolls[i].setId(1);
			button_rolls[i].setText(""+file_rolls[i]+"."+"  "+file_names[i]);
			the_sp_index++;
			ed.putString(""+clicked_class_name+the_sp_index+"$$", ""+file_rolls[i]);
			//Toast.makeText(getApplicationContext(), "key "+clicked_class_name+the_sp_index+"$$"+"value "+""+file_rolls[i], Toast.LENGTH_LONG).show();
			ed.commit();
			button_rolls[i].setTextColor(Color.WHITE);
			button_rolls[i].setOnClickListener(this);
			button_rolls[i].setGravity(Gravity.LEFT);
			sv.addView(button_rolls[i]);
		}
		sc.addView(sv);
		setContentView(sc);
	}
	
	public void display_buttons() throws Exception
	{
		sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc.setFillViewport(true);
		sv.setOrientation(sv.VERTICAL);
		sc.setBackgroundColor(Color.BLACK);
		index_button=0;
		while(index_button<index_all)
		{
			the_sp_index++;
			ed.putString(""+clicked_class_name+the_sp_index+"$$", ""+int_all_sequentials[index_button]);
			ed.commit();
			//Toast.makeText(getApplicationContext(), "saved key is "+clicked_class_name+the_sp_index+"$$", Toast.LENGTH_LONG).show();
			button_rolls[index_button]=new Button(this);
			button_rolls[index_button].setText(""+int_all_sequentials[index_button]);
			button_rolls[index_button].setId(1);
			button_rolls[index_button].setTextColor(Color.WHITE);
			button_rolls[index_button].setOnClickListener(this);
			sv.addView(button_rolls[index_button]);
			index_button++;
		}
		final_sequential_index=index_button;
		int i1=0;
		while(i1<index_decode_additionals)
		{
			
			
			button_rolls[index_button]=new Button(this);
			button_rolls[index_button].setText(""+int_additional_roll[i1]);
			the_sp_index++;
			ed.putString(""+clicked_class_name+the_sp_index+"$$", ""+int_additional_roll[i1]);
			ed.commit();
		//	Toast.makeText(getApplicationContext(), "saved key is "+clicked_class_name+the_sp_index+"$$", Toast.LENGTH_LONG).show();
			button_rolls[index_button].setTextColor(Color.WHITE);
			button_rolls[index_button].setId(1);
			button_rolls[index_button].setOnClickListener(this);
			sv.addView(button_rolls[index_button]);
			i1++;
			index_button++;
		}
		get_ii=index_button;
		final_index=index_button;
		sc.addView(sv);
		setContentView(sc);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		//finish();
		//System.exit(0);
		back_button_pressed++;
		if(back_button_pressed>=2)
		{
			Intent s=new Intent(this,Display_Buttons.class);
			startActivity(s);
			finish();
			System.exit(0);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Press again to exit !", Toast.LENGTH_LONG).show();
		}
		
	}

}//end of class

