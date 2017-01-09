package com.dnshoppee;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PinMenu1 extends ActionBarActivity implements OnClickListener {

	Button unusedpin, usedpin, pintransfer, pinpurchase, pinreport;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_menu);

		unusedpin = (Button) findViewById(R.id.btn_unused_pin_1);
		usedpin = (Button) findViewById(R.id.btn_usedpin_1);
		pintransfer = (Button) findViewById(R.id.btn_pin_transfer_1);
		pinpurchase = (Button) findViewById(R.id.btn_pin_purchase_1);
		pinreport = (Button) findViewById(R.id.btn_pin_report_1);

		unusedpin.setOnClickListener(this);
		usedpin.setOnClickListener(this);
		pintransfer.setOnClickListener(this);
		pinpurchase.setOnClickListener(this);
		pinreport.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_unused_pin_1:
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog_input);
			Button ok = (Button) dialog.findViewById(R.id.button1);
			Button cancel = (Button) dialog.findViewById(R.id.button2);
			final EditText pass = (EditText) dialog
					.findViewById(R.id.editText1);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
					;
				}
			});
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String password = pass.getText().toString().trim();
					if (password.equals("")) {
						Toast.makeText(getApplicationContext(),
								"Enter Transaction password",
								Toast.LENGTH_SHORT).show();
					} else {
						dialog.cancel();
						Intent intent = new Intent(getApplicationContext(),
								UsedPin.class);
						intent.putExtra("password", password);
						startActivity(intent);
					}

				}
			});
			dialog.show();

			break;
		case R.id.btn_usedpin_1:
			startActivity(new Intent(getApplicationContext(), UnusedPin.class));
			
			break;
		case R.id.btn_pin_transfer_1:
			startActivity(new Intent(getApplicationContext(), PinTransfer.class));
			break;
		case R.id.btn_pin_purchase_1:
			startActivity(new Intent(getApplicationContext(), PinPurchase.class));
			break;
		case R.id.btn_pin_report_1:
			startActivity(new Intent(getApplicationContext(), PinReport.class));
			break;

		default:
			break;
		}
	}

}
