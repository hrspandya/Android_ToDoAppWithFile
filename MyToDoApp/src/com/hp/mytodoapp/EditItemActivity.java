package com.hp.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	
	EditText et_editTask;
	Button btn_saveTask;
	private int pos;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		String editText = getIntent().getStringExtra("editText");
		pos = getIntent().getIntExtra("pos", -1);
		
		//setting default value in Edit View for user 
		et_editTask = (EditText) findViewById(R.id.et_editTask);
		et_editTask.setText(editText);
		int position = editText.length();
		Editable etext = et_editTask.getText();
		Selection.setSelection(etext, position);
		
		btn_saveTask = (Button) findViewById(R.id.btn_saveTask);
		
		//Listen to Save Button Click
		setUpSaveButtonListner();
	}
	
	

	private void setUpSaveButtonListner() {
		// TODO Auto-generated method stub
		btn_saveTask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// first parameter is the context, second is the class of the activity to launch
			    Intent i = new Intent(EditItemActivity.this, ToDoActivity.class);
			    i.putExtra("editText", et_editTask.getText().toString());
			    i.putExtra("pos", pos);
			    startActivity(i); // brings up the second activity
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
