package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Request extends ActionBarActivity implements OnClickListener {

	Button ePocket,pinpurchase, topup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);

		ePocket = (Button) findViewById(R.id.btn_e_pocket_request_1);
		topup = (Button) findViewById(R.id.btn_topup_request_1);
		pinpurchase = (Button) findViewById(R.id.btn_pin_purchase_1);

		ePocket.setOnClickListener(this);
		topup.setOnClickListener(this);
		pinpurchase.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_e_pocket_request_1:
			intent = new Intent(getApplicationContext(), EwalletFundRequest.class);
			intent.putExtra("menuID", "Q");
			startActivity(intent);
			break;
		case R.id.btn_topup_request_1:
			intent = new Intent(getApplicationContext(), EwalletFundRequest.class);
			intent.putExtra("menuID", "T");
			startActivity(intent);
			break;
		case R.id.btn_pin_purchase_1:
			startActivity(new Intent(getApplicationContext(), PinPurchase.class));
			break;

		default:
			break;
		}
	}
}
