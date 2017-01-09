package com.dnshoppee;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class DialogInput extends Dialog {
	Button ok, cancel;
	EditText pass;
	public DialogInput(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_input);
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_input);
		
		 pass = (EditText) findViewById(R.id.editText1);
		
		cancel.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				onBackPressed();
				return false;
			}
		});
		
		ok.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				String password = pass.getText().toString().trim();
				Intent intent = new Intent(v.getContext(), UnusedPin.class);
				intent.putExtra("password", password);
				v.getContext().startActivity(intent);
				return false;
			}
			
		});
		
		
		
	}

}
