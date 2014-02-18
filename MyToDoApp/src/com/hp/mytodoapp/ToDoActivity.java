package com.hp.mytodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> toDoAdapter;
	private ListView lv_items;
	private EditText et_newItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		
		et_newItem = (EditText) findViewById(R.id.et_newItem); 
		lv_items = (ListView) findViewById(R.id.lv_items);
		
		readItems();
		toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		lv_items.setAdapter(toDoAdapter);
		
		//CHecking for Intent to give us the data from Edit View
		if(getIntent().getStringExtra("editText") != null 
				&& !getIntent().getStringExtra("editText").equalsIgnoreCase("") && getIntent().getIntExtra("pos", -1) != -1){
			String editText = getIntent().getStringExtra("editText");
			int pos = getIntent().getIntExtra("pos", -1);
			todoItems.set(pos, editText);
			toDoAdapter.notifyDataSetChanged();
			saveItems();
		}
		
		//Listen For Deleting the Task
		setUpListViewDeleteListner();
		
		//Listen For Editing the Task
		setUpListViewEditListner();
	}

	
	private void setUpListViewEditListner() {
		// TODO Auto-generated method stub
		lv_items.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				// TODO Auto-generated method stub
				// first parameter is the context, second is the class of the activity to launch
			    Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
			    i.putExtra("editText", todoItems.get(pos));
			    i.putExtra("pos", pos);
			    startActivity(i); // brings up the second activity
			}
			
		});
	}
	
	
	private void setUpListViewDeleteListner() {
		// TODO Auto-generated method stub
		lv_items.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				// TODO Auto-generated method stub
				todoItems.remove(pos);
				//you may get an error because you are changing the arryaData todoItems without letting adapter know
				//You need to somehow let adapter know about data has changed 
				toDoAdapter.notifyDataSetChanged();
				saveItems();
				return false;
			}
			
		});
	}
	
	public void onAddedItem(View v){
		String itemText = et_newItem.getText().toString();
		toDoAdapter.add(itemText);
		et_newItem.setText("");
		saveItems();
	}

	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			todoItems = new ArrayList<String>();
		}
	}
	
	private void saveItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

}
