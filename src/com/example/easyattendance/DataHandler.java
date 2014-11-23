package com.example.easyattendance;

import java.sql.RowId;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.ContactsContract.Contacts.Data;
import android.widget.Toast;

public class DataHandler {

public static final String CLASS_NAME = "class_name";
public static final String NO_OF_STUDENTS = "no_of_students";
public static final String SEQUENTIAL_PAIRS = "sequential_pairs";
public static final String ADDITIONAL_ROLLS = "additional_rolls";
public static final String TABLE_NAME = "mytable";
public static final String DATA_BASE_NAME = "mydatabase";
public static final int DATA_BASE_VERSION = 1;
public static final String TABLE_CREATE = 
"create table mytable (class_name text not null,no_of_students text ,sequential_pairs text,additional_rolls text,id INTEGER PRIMARY KEY AUTOINCREMENT);";

DatabaseHelper dbhelper;
Context ctx;
SQLiteDatabase db;
static String[] name_of_columns=new String[]{CLASS_NAME,NO_OF_STUDENTS,SEQUENTIAL_PAIRS,ADDITIONAL_ROLLS};
static int index_count=0;
//static int id=0;



public DataHandler(Context ctx)
{
	this.ctx=ctx;
	dbhelper=new DatabaseHelper();
}

class DatabaseHelper extends SQLiteOpenHelper
{

	public DatabaseHelper() {
		super(ctx, DATA_BASE_NAME, null, DATA_BASE_VERSION);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try
		{
			db.execSQL(TABLE_CREATE);
		}
		catch(SQLiteException e)
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS mytable ");
		onCreate(db);
	}
	
}
/*public DataHandler open() throws SQLException
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getWritableDatabase();
	return this;
}*/


public void close()
{
	db.close();
}
	
public long insertData(String local_class_name,String local_no_of_students,String local_sequential_pairs,String local_additional_rolls)
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getWritableDatabase();
	ContentValues content=new ContentValues();
	Cursor check = returnDATA();
	
	if(check.moveToFirst())
	{
		
	}
	content.put(CLASS_NAME, local_class_name);
	content.put(NO_OF_STUDENTS, local_no_of_students);
	content.put(SEQUENTIAL_PAIRS, local_sequential_pairs);
	content.put(ADDITIONAL_ROLLS,local_additional_rolls);
	
	return db.insert(TABLE_NAME, null, content) ;
}

public Cursor returnDATA()
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getReadableDatabase();
	String[] projection=new String[]{CLASS_NAME,NO_OF_STUDENTS,SEQUENTIAL_PAIRS,ADDITIONAL_ROLLS};
	Cursor z= db.query(TABLE_NAME, projection, null, null, null, null,null);
	return z;
}

public void delete_data()
{
	Cursor cs=null;
	String[] projection=new String[]{CLASS_NAME,NO_OF_STUDENTS,SEQUENTIAL_PAIRS,ADDITIONAL_ROLLS};
	try
	{
	cs=db.query(TABLE_NAME,projection, null, null, null, null, null);
	}
	catch(Exception e)
	{
		Toast.makeText(ctx, "some problem while deleting", Toast.LENGTH_LONG).show();
	}
	Integer i=cs.getCount();
if(i>0)
{
	db=dbhelper.getWritableDatabase();
	db.delete("mytable", null, null);
	Toast.makeText(ctx, "Delete successful !", Toast.LENGTH_LONG).show();
}
else
{
	Toast.makeText(ctx, "Nothing more to delete!", Toast.LENGTH_LONG).show();
}
}

public String[] fetch_class_names()
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getReadableDatabase();
	Cursor cs;
	int c=0;
	
	cs=db.query(TABLE_NAME,name_of_columns, null, null, null, null, null);
	String[] class_name=new String[100];
	for(int i=0;i<100;i++)
	{
		class_name[i]="";
	}
	if(cs.moveToFirst())
	{
		do
		{
			class_name[index_count]=cs.getString(0);
			index_count++;
		}while(cs.moveToNext());
	
	}//if ends here
	index_count=0;
	return class_name;
}//function ends here


public String fetch_roll_numbers(String local_class_name,int code_to_return)
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getReadableDatabase();
	Cursor cs;
	int c=0;
	
	cs=db.query(TABLE_NAME,name_of_columns, null, null, null, null, null);
	String[] class_name=new String[100];
	for(int i=0;i<100;i++)
	{
		class_name[i]="";
	}
	if(cs.moveToFirst())
	{
		do
		{
			class_name[index_count]=cs.getString(0);
			if(local_class_name.equals(class_name[index_count]))
			{
				String coded_sequential_roll=cs.getString(2);
				String coded_additional_roll=cs.getString(3);
				if(code_to_return==10)
				{
				//	Toast.makeText(ctx, "Returning "+coded_sequential_roll,Toast.LENGTH_LONG).show();
					return coded_sequential_roll;
				}
				else if(code_to_return==20)
				{
					//Toast.makeText(ctx, "Returning "+coded_additional_roll,Toast.LENGTH_LONG).show();
					return coded_additional_roll;
					
				}
				else
				{
				//	Toast.makeText(ctx, "Returning null!", Toast.LENGTH_LONG).show();
				}
			}//end of if block checking condition
			index_count++;
			
		}while(cs.moveToNext());
	
	}//if ends here
	index_count=0;
	return null;
}//function ends here




public Integer count_number_of_rows()
{
	dbhelper=new DatabaseHelper();
	db=dbhelper.getReadableDatabase();
	
	Cursor cs;
	cs=db.query(TABLE_NAME,name_of_columns, null, null, null, null, null);
	Integer i=cs.getCount();
	return i;
}

}
