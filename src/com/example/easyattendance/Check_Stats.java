package com.example.easyattendance;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Check_Stats extends Activity{
	Button b_fetch_data,b_fetch_all,b_delete_data,b_date_wise_data,b_filter,b_filter_2;
	EditText et_class_name,et_roll_no,et_individual_class_name,et_percentage;
	static String class_name="",roll_no="";
	SharedPreferences sp;
	Editor ed;
	static int flag_content_view=-1;
	ScrollView sc,sc2,sc3;
	LinearLayout sv,sv2,sv3;
	HorizontalScrollView hsv;
	EditText et_filter_2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_stats);
		initialize_variables();
		
		sc = new ScrollView(this);
		sv=new LinearLayout(this);
		sc2 = new ScrollView(this);
		sv2=new LinearLayout(this);
		sc3 = new ScrollView(this);
		sv3=new LinearLayout(this);
	
		b_filter=(Button)findViewById(R.id.button_filter);
		hsv=new HorizontalScrollView(this);
		hsv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		hsv.setFillViewport(true);
		
		sc.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc.setFillViewport(true);
		//sc.setBackgroundColor(Color.BLACK);
		sv.setOrientation(sv.VERTICAL);
		sv3.setOrientation(sv.VERTICAL);
		sc3.addView(sv3);
		TextView tv1=new TextView(this);
	
    	tv1.setText("Rolls"+"\t\t\t"+"Attendance Records");
    	
    	sv.addView(tv1);
    	sc.addView(sv);
	}
	
	public void initialize_variables()
	{
		sp=getSharedPreferences("records", Context.MODE_PRIVATE);
		ed=sp.edit();
		et_filter_2=(EditText)findViewById(R.id.et_filter_2);
		et_percentage=(EditText)findViewById(R.id.et_percentage);
		et_class_name=(EditText)findViewById(R.id.et_class_name);
		b_filter_2=(Button)findViewById(R.id.button_filter_2);
		b_fetch_all=(Button)findViewById(R.id.button_fetch_all);
		//b_delete_data=(Button)findViewById(R.id.button_delete_data);
		b_date_wise_data=(Button)findViewById(R.id.button_date_wise_data);
	}
	
	
	/*public void delete_shared_preferences_data(View v)
	{
		ed.clear();
		ed.commit();
		//ed.apply();
	}*/
	
	
	public void func_filter_students(View v)
	{
		String temp="";
		String roll="";
		int i=0;
		
		class_name=et_class_name.getText().toString().trim();
		try
		{
			if(!class_name.equals(""))
			{
			//	String per=et_percentage.getText().toString();
				if(sp.contains(""+class_name+i+"$$"))
				{
					while(!sp.getString(""+class_name+i+"$$", "").equals(""))
					{
						temp=sp.getString(""+class_name+i+"$$", "");//student roll number
						check_attendance_percentage(temp, 2);
						
						i++;
					}
					
					flag_content_view=1;
					setContentView(sc3);
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Enter a valid class name", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Enter a valid class name :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			class_name="";
		}
	
	}
	
	public void func_filter_students_2(View v)
	{
		String temp="";
		String roll="";
		int i=0;
		
		class_name=et_class_name.getText().toString().trim();
		try
		{
			if(!class_name.equals(""))
			{
			//	String per=et_percentage.getText().toString();
				if(sp.contains(""+class_name+i+"$$"))
				{
					while(!sp.getString(""+class_name+i+"$$", "").equals(""))
					{
						temp=sp.getString(""+class_name+i+"$$", "");//student roll number
						check_attendance_percentage(temp, 3);
						
						i++;
					}
					
					flag_content_view=1;
					setContentView(sc3);
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Enter a valid class name", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "One or more fields are empty :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			class_name="";
		}
	
	}
	public void func_date_wise_data(View v)//for entire class
	{
		String temp="";
		String temp_time="";
		String roll="";
		int i=0;
		ScrollView sc1=new ScrollView(this);
		sc1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		sc1.setFillViewport(true);
		LinearLayout lv1=new LinearLayout(this);//horizontal
		lv1.setOrientation(lv1.VERTICAL);
		LinearLayout lv2=new LinearLayout(this);//vertical
		lv1.addView(lv2);
		lv2.setOrientation(lv2.VERTICAL);
		hsv.addView(lv1);
		sc1.addView(hsv);
		TextView tv=new TextView(this);
		String  c1;
		class_name=et_class_name.getText().toString().trim();
		try
		{
			if(!class_name.equals(""))
			{
				i=0;
				if(sp.contains(""+class_name+i+"date"))
				{
					String coded_date="";
					String coded_time="";
					while(!(sp.getString(""+class_name+i+"date", " ").equals(" ")))
					{
						TextView tv3=new TextView(this);
						temp=sp.getString(""+class_name+i+"date", "");
						coded_date+="\t\t"+temp;
						temp_time=sp.getString(""+class_name+i+"time", "");
						coded_time+="\t"+temp_time;
						//tv3.setText("\t\t"+temp+"\n\t"+temp_time+"\t");
						try
						{
							//lv1.addView(tv3);//adding date and time
						}
						catch(Exception e)
						{
							Toast.makeText(getApplicationContext(), "yes its lv1"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
						}
						
						i++;
						Toast.makeText(getApplicationContext(), "i is "+i, Toast.LENGTH_LONG).show();
					} 
					i=0;
					int temp_once=1;
					while(!sp.getString(""+class_name+i+"$$", "").equals(""))
					{
						
						TextView tv4=new TextView(this);
						TextView tv6=new TextView(this);
						temp=sp.getString(""+class_name+i+"$$", "");//student roll number
						if(temp_once==1)
						{
							//tv4.setText("\n"+temp);
							//lv2.addView(tv4);
							TextView temp_i=new TextView(this);
							TextView temp_i2=new TextView(this);
							temp_once=-1;
						//	temp_i2.setText(""+coded_date);
						//	temp_i.setText("\n");
						//	lv2.addView(temp_i2);
						//	lv2.addView(temp_i);
						}
						String attendance_string=sp.getString(""+class_name+temp, "");//attendance string
						int index_attendance_string=0;
						
						String formatted_string="";
						String temp_formatted_string="";
						while(index_attendance_string < attendance_string.length())
						{
							if(attendance_string.charAt(index_attendance_string)=='1')
							{
								temp_formatted_string="P";
							}
							else if(attendance_string.charAt(index_attendance_string)=='0')
							{
								temp_formatted_string="A";
							}
							formatted_string+=temp_formatted_string+"\t\t\t";
							index_attendance_string++;
						}
						temp="("+temp+")";
						temp+="\t\t\t"+formatted_string;
						temp=temp.trim();
						tv4.setText("\n"+temp);
						tv6.setBackgroundResource(R.drawable.cell_image);
						lv2.addView(tv4);
						lv2.addView(tv6);
						i++;
						
					}
					
					flag_content_view=1;
					try
					{
					setContentView(sc1);
					}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), "set content view is throwing error!", Toast.LENGTH_LONG).show();
					}
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Enter a valid class name", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Error in here :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			class_name="";
			c1="";
		}
	}
	
	
	
	
	public void func_fetch_all(View v)
	{
		String temp="";
		String roll="";
		int i=0;
		
		class_name=et_class_name.getText().toString().trim();
		try
		{
			if(!class_name.equals(""))
			{
				if(sp.contains(""+class_name+i+"$$"))
				{
					while(!sp.getString(""+class_name+i+"$$", "").equals(""))
					{
						temp=sp.getString(""+class_name+i+"$$", "");//student roll number
						check_attendance_percentage(temp,1);
						i++;
					}
					
					flag_content_view=1;
					setContentView(sc);
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Enter a valid class name", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Enter a valid class name :"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			class_name="";
		}
	
	}

	 public void check_attendance_percentage(String temp,int flag)
	    {
		 //	Toast.makeText(getApplicationContext(), "check 1", Toast.LENGTH_LONG).show();
	    	String c1=sp.getString(""+class_name+temp, "");//this is the attendance string e.g.-1001111
	    	c1=c1.trim();
	    	int i=0;
	    	float student_count=0;
	    	float total_count=0;
	    	
	    try
	    {
	    	i=0;
	    	while(	(c1.charAt(i)=='1' || c1.charAt(i)=='0') && i<c1.length()  )
	    	{
	    	
	    			if(c1.charAt(i)=='1')
		    		{
		    			student_count++;
		    		}
		    		total_count++;
		    		i++;
		    		
	    	}
	    }
	    catch(Exception e)
	    {
	    	//Toast.makeText(getApplicationContext(), "Exception 2:"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
	    }
	    	
	    	
	    	float per=(student_count/total_count)*100;
	   
	    	String temp1=String.format("%.2f", per);
	    	per=Float.parseFloat(temp1);
	    	if(flag==1)//normal execution
	    	{
	    	TextView tv2=new TextView(this);
	    	tv2.setText(""+temp+"--------("+(int)student_count+"/"+(int)total_count+")----"+temp1+"%\n");
	    	tv2.setEms(10);
	    	sv.addView(tv2);
	    	}
	    	else if(flag==2)
	    	{
	    		TextView tv2=new TextView(this);
	    		int percentage_attendance=((int)student_count/(int)total_count)*100;
	    		String st_min_percentage=et_percentage.getText().toString();
	    		int min_percentage=Integer.parseInt(st_min_percentage);
	    		if(percentage_attendance>=min_percentage)
	    		{
	    			tv2.setText(""+temp+"--------("+(int)student_count+"/"+(int)total_count+")----"+temp1+"%\n");
			    	tv2.setEms(10);
			    	sv3.addView(tv2);
	    		}
		    	
	    	}
	    	else if(flag==3)
	    	{
	    		TextView tv2=new TextView(this);
	    		int percentage_attendance=((int)student_count/(int)total_count)*100;
	    		String st_min_percentage=et_filter_2.getText().toString();
	    		int min_percentage=Integer.parseInt(st_min_percentage);
	    		if(percentage_attendance<=min_percentage)
	    		{
	    			tv2.setText(""+temp+"--------("+(int)student_count+"/"+(int)total_count+")----"+temp1+"%\n");
			    	tv2.setEms(10);
			    	sv3.addView(tv2);
	    		}
		    	
	    	}
	    	
	    }
	 
	 
	 
	 
	 @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
		
	 }
}
