package com.example.easyattendance;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Toast;

public class Enter_roll_numbers extends Activity implements android.view.View.OnClickListener{
	EditText et_roll_from=null,et_roll_to=null,et_additional_rolls=null;
	Button b_save_all_data=null,b_load_all=null,b_delete_all=null;
	String roll_from=null,roll_to=null,class_name=null,no_of_students=null;
	static String[] sequential_pairs,additional_roll,decode_sequence,decode_additional;
	
	String coded_sequential_pairs="",coded_additional_roll="";
	static int index_count_sequential_pairs=0,index_count_additional_pairs=0;
	static int index_decode_sequence=0,index_decode_additional_data=0;
	static int retrieval_index=0;
	static String[] retrieval_additionals;
	static int flag_consistency=1;
	
	
	SQLiteDatabase db=null;
	DataHandler handler=null;
	Cursor cs=null;
	
	static String load_class_name="",load_no_of_students="";
	static String[] load_decoded_sequential_pairs,load_decoded_additional_roll;
	static String load_coded_sequential_pairs,load_coded_additional_pairs;
	static int flag_sequential_pairs=0,flag_additional_pairs=0;
	static String[] retrieve_all_rolls;
	static String the_complete_string="";
	static String[] extract_individual_rolls;
	static int individual_rolls=0;
	static int i2=0;
	static char[] altamash;
	static int i=0;
	static char[] part_by_part;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.register_roll_numbers);
	   additional_roll=new String[100];
	   sequential_pairs=new String[100];
	   retrieve_all_rolls=new String[1000];
	   extract_individual_rolls=new String[1000];
	   retrieval_additionals=new String[1000];
	   
	   for(int i=0;i<1000;i++)
	   {
		   extract_individual_rolls[i]="";
		   retrieve_all_rolls[i]="";
		   retrieval_additionals[i]="";
	   }
	  
		   
	   
	   class_name=getIntent().getStringExtra("class_name");
	   no_of_students=getIntent().getStringExtra("no_of_students");
	   b_save_all_data=(Button)findViewById(R.id.button_save);
	   et_roll_from=(EditText)findViewById(R.id.et_roll_from);
	   et_roll_to=(EditText)findViewById(R.id.et_roll_to);
	   et_additional_rolls=(EditText)findViewById(R.id.et_non_sequential_roll_numbers);
	   b_load_all=(Button)findViewById(R.id.button_load_all);
	   b_delete_all=(Button)findViewById(R.id.button_delete_all_data);
	   b_save_all_data.setOnClickListener(this);
	}
	
	public void retrieve_rolls()
	{
			
			handler=new DataHandler(getBaseContext());
			try
			{
				cs=handler.returnDATA();
			}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "cursor issue", Toast.LENGTH_LONG).show();
			}
			
			try
			{
				i=0;
				retrieval_index=0;
			if(cs.moveToFirst())
			{
				do
				{
					load_coded_sequential_pairs="";
					load_coded_sequential_pairs=cs.getString(2);
					//load_coded_additional_pairs=cs.getString(3);
					the_complete_string="";
					the_complete_string=load_coded_sequential_pairs;
					the_complete_string= the_complete_string.trim();
				
				while(i<the_complete_string.length())
				{
					while(the_complete_string.charAt(i)==' ')
					{
						i++;
						if(i>=the_complete_string.length())
							break;
					}
					while(the_complete_string.charAt(i)!=' ')
					{
						retrieve_all_rolls[retrieval_index]+=the_complete_string.charAt(i);
						i++;
						if(i>=the_complete_string.length())
							break;
					}
					retrieval_index++;
					
				}
		//		for(int i=0;i<retrieval_index;i++)
			//		Toast.makeText(getApplicationContext(), ""+retrieve_all_rolls[i], Toast.LENGTH_SHORT).show();
					
					//Toast.makeText(getApplicationContext(), "String 1 is :"+retrieve_all_rolls[0]+"\nString 2 is :"+retrieve_all_rolls[1], Toast.LENGTH_LONG).show();
				
				}while(cs.moveToNext());
				//Toast.makeText(getApplicationContext(), "all ok", Toast.LENGTH_LONG).show();
				
				
			
			}
		}//end of try block	
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "problem", Toast.LENGTH_LONG).show();
		}
			finally
			{
				handler.close();
			}
		
		
		
	}

	
		
	public void retrieve_all_rolls_individually(View v)
	{
		i=0;
		retrieval_index=0;
		for(int i=0;i<1000;i++)
		{
			retrieval_additionals[i]="";
			retrieve_all_rolls[i]="";
		}
	//	Toast.makeText(getApplicationContext(), "Displaying sequential rolls :", Toast.LENGTH_SHORT).show();
		retrieve_rolls();
		i=0;
		retrieval_index=0;
		
		//Toast.makeText(getApplicationContext(), "Displaying additional rolls :", Toast.LENGTH_SHORT).show();
		retrieve_rolls_2();
	}
	public void retrieve_rolls_2()
	{
			
			handler=new DataHandler(getBaseContext());
			try
			{
				cs=handler.returnDATA();
			}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "cursor issue", Toast.LENGTH_LONG).show();
			}
			
			try
			{
				i=0;
				retrieval_index=0;
				
			if(cs.moveToFirst())
			{
				do
				{
				//	load_coded_sequential_pairs=cs.getString(2);
					load_coded_additional_pairs="";
					load_coded_additional_pairs=cs.getString(3);
					the_complete_string="";
					the_complete_string=load_coded_additional_pairs;
					the_complete_string= the_complete_string.trim();
				
				while(i<the_complete_string.length())
				{
					while(the_complete_string.charAt(i)==' ')
					{
						i++;
						if(i>=the_complete_string.length())
							break;
					}
					while(the_complete_string.charAt(i)!=' ')
					{
						retrieval_additionals[retrieval_index]+=the_complete_string.charAt(i);
						i++;
						if(i>=the_complete_string.length())
							break;
					}
					retrieval_index++;
					
				}
			//	for(int i=0;i<retrieval_index;i++)
				//	Toast.makeText(getApplicationContext(), ""+retrieval_additionals[i], Toast.LENGTH_SHORT).show();
					
					//Toast.makeText(getApplicationContext(), "String 1 is :"+retrieve_all_rolls[0]+"\nString 2 is :"+retrieve_all_rolls[1], Toast.LENGTH_LONG).show();
				
				}while(cs.moveToNext());
				
				i=0;
				retrieval_index=0;
				
				
				
			
			}
		}//end of try block	
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "problem", Toast.LENGTH_LONG).show();
		}
			finally
			{
				handler.close();
			}
		
		
		
	}

	public void check_data_correctness_remove_redundancy()
	{
		//final in which data is stored after retrieval from database are retrieve_all_rolls[] & retrieve_additionals index of both starting from 0
		int i=0;
		if((retrieve_all_rolls.length+1)%2!=0)	//from-to pairs are not formed
		{
			flag_consistency=0;
			Toast.makeText(getApplicationContext(), "DATA INCOMPLETE/ILLOGICAL !", Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), "For each FROM there must be a to and vice versa!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void convert_str_array_to_str()
	{
		for(int i=0;i<sequential_pairs.length;i++)
		{
			coded_sequential_pairs=sequential_pairs[i]+" ";
		}
		
		for(int i=0;i<additional_roll.length;i++)
		{
			coded_additional_roll=additional_roll[i]+" ";
		}
	
	}
	
	public void func_spacebar(View v)
	{
			
		
			String s=et_additional_rolls.getText().toString();
			s=s+" ";
			et_additional_rolls.setText(""+s);
			int cursor1=et_additional_rolls.length();
		//	Toast.makeText(getApplicationContext(), "cursor position is :"+cursor1, Toast.LENGTH_LONG).show();
			et_additional_rolls.setSelection(cursor1);
	}
	
	public void add_more_sequential_rolls(View v)
	{
		
		Toast.makeText(getApplicationContext(), "Enter more values!", Toast.LENGTH_LONG).show();
		index_count_sequential_pairs++;
		sequential_pairs[index_count_sequential_pairs]=et_roll_from.getText().toString();
		index_count_sequential_pairs++;
		sequential_pairs[index_count_sequential_pairs]=et_roll_to.getText().toString();
		index_count_sequential_pairs++;
		
		et_roll_to.setText("");
		et_roll_from.setText("");
		et_roll_from.setSelection(0);
		
	}
	
	public void add_more_non_sequential_rolls(View v)
	{
		
		Toast.makeText(getApplicationContext(), "Enter more values!", Toast.LENGTH_LONG).show();
		index_count_additional_pairs++;
		additional_roll[index_count_additional_pairs]=et_additional_rolls.getText().toString();
		index_count_additional_pairs++;
		et_additional_rolls.setText("");
	}
	
	public void check_if_ok(View v)
	{
		Toast.makeText(getApplicationContext(), "Loading data...!", Toast.LENGTH_LONG).show();
		
		load_decoded_additional_roll=new String[100];
		load_decoded_sequential_pairs=new String[100];
		for(int i=0;i<100;i++)
		{
			load_decoded_additional_roll[i]="";
			load_decoded_sequential_pairs[i]="";
		}
		handler=new DataHandler(getBaseContext());
		//handler.open();
		
		Cursor z=handler.returnDATA();
		if(z.moveToFirst())
		{
			do
			{
				load_class_name=z.getString(0);
				load_no_of_students=z.getString(1);
				load_coded_sequential_pairs=z.getString(2);
				load_coded_additional_pairs=z.getString(3);
				Toast.makeText(getBaseContext(), "Class Name: "+load_class_name+" \nNo of students : "+load_no_of_students+
						"\nCoded sequentials: "+load_coded_sequential_pairs+"\nCoded additionals :"+load_coded_additional_pairs,Toast.LENGTH_LONG).show();
			}while(z.moveToNext());
		}
		handler.close();
	}
	@Override
	public void onClick(View v) {	//save all data
		roll_from=et_roll_from.getText().toString();
		roll_to=et_roll_to.getText().toString();
	
	
		additional_roll[index_count_additional_pairs]=et_additional_rolls.getText().toString();
		sequential_pairs[index_count_sequential_pairs]=roll_from+" "+roll_to;
	
			for(int i=0;i< sequential_pairs.length;i++)
			{
				if(sequential_pairs[i]!=null)
					coded_sequential_pairs=coded_sequential_pairs+" "+sequential_pairs[i]+" ";
				
			}
		
		
			for(int i=0;i<additional_roll.length;i++)
			{
				if(additional_roll[i]!=null)
					coded_additional_roll=coded_additional_roll+" "+additional_roll[i]+" ";
				
			}
			
			if(coded_additional_roll.equals("") || coded_additional_roll.equals(" ") || coded_additional_roll.equals("  "))
			{
				if(coded_sequential_pairs.equals("") || coded_sequential_pairs.equals(" ") || coded_sequential_pairs.equals("  "))
				flag_consistency=0;
			}
		
		if(flag_consistency==1)
		{
		handler=new DataHandler(getBaseContext());
		cs=handler.returnDATA();
	
		long id2=handler.insertData(class_name, no_of_students, coded_sequential_pairs,coded_additional_roll );
		Toast.makeText(getBaseContext(), "DATA SAVED!", Toast.LENGTH_LONG).show();
		et_additional_rolls.setText("");
		et_roll_from.setText("");
		et_roll_to.setText("");
		}
		else
		{
			Toast.makeText(getApplicationContext(), "NOT Saved!Data entered is inconsistent!", Toast.LENGTH_LONG).show();
		}
	//	Intent i=new Intent(this,Register_DETAILS.class);
	//	startActivity(i);
		
	}
	
	 
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
		System.exit(0);
	}
	

}
