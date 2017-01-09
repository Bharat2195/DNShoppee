package com.dnshoppee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RechargeReport1 extends Activity {

	Button reset, cancel;
	EditText numberE;
	Spinner rechargeTypeE;
	RadioButton r1, r2;

	String number, rechargetype, status = "";
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_report);
		reset = (Button) findViewById(R.id.btn_resetProfile);
		cancel = (Button) findViewById(R.id.btn_cancelProfile);
		rechargeTypeE = (Spinner) findViewById(R.id.spinner_RechargeType);
		numberE = (EditText) findViewById(R.id.edit_number);
		r1 = (RadioButton) findViewById(R.id.r1);
		r2 = (RadioButton) findViewById(R.id.r2);
		
		
		

		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status = "Success";
			}
		});

		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status = "Failure";
			}
		});

		rechargeTypeE.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				rechargetype = rechargeTypeE.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number = numberE.getText().toString().trim();
				if (rechargetype.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Select Company Name", Toast.LENGTH_SHORT).show();
				} else {
					Intent i = new Intent(getApplicationContext(),
							RechargeReportList.class);
					i.putExtra("status", status);
					i.putExtra("cname", rechargetype);
					i.putExtra("number", number);
					startActivity(i);
				}

			}
		});
		
		

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		showAskDialog(context, "Please select one", "", status);
	}
	
	@SuppressWarnings("deprecation")
	public void showAskDialog(Context context, String title, String message,
			String status) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();

		// Setting Dialog Title
		alertDialog.setTitle("Last Transactions");

		// Setting Dialog Message
		alertDialog.setMessage("Choose one");

		// Setting OK Button
		alertDialog.setButton(Dialog.BUTTON1,"Last Transactions", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				Intent i = new Intent(getApplicationContext(),
						RechargeReportList.class);
				i.putExtra("status", "");
				i.putExtra("cname", "");
				i.putExtra("number", "");
				startActivity(i);
			}
		});
		alertDialog.setButton(Dialog.BUTTON2,"By number", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
