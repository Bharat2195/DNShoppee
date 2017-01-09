package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Report extends ActionBarActivity implements OnClickListener {

	Button ePocket, topup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		ePocket = (Button) findViewById(R.id.btn_e_pocket_1);
		topup = (Button) findViewById(R.id.btn_topup_report_1);

		ePocket.setOnClickListener(this);
		topup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_e_pocket_1:
			startActivity(new Intent(getApplicationContext(), EwalletReport.class));
			break;
		case R.id.btn_topup_report_1:
			startActivity(new Intent(getApplicationContext(), TopupwalletReport.class));
			break;

		default:
			break;
		}
	}
}
