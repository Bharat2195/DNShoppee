package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RechargeHome extends ActionBarActivity implements OnClickListener {

	Button prepaid, special, postpaid, datacard, dth, history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_home);

		prepaid = (Button) findViewById(R.id.btn_recharge_1);
		special = (Button) findViewById(R.id.btn_special_recharge_1);
		postpaid = (Button) findViewById(R.id.btn_postpaid_1);
		datacard = (Button) findViewById(R.id.btn_data_card_1);
		dth = (Button) findViewById(R.id.btn_dth_1);
		history = (Button) findViewById(R.id.btn_recharge_history_1);

		prepaid.setOnClickListener(this);
		special.setOnClickListener(this);
		postpaid.setOnClickListener(this);
		datacard.setOnClickListener(this);
		dth.setOnClickListener(this);
		history.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_recharge_1:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Prepaid");
			startActivity(intent);
			break;
		case R.id.btn_special_recharge_1:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Special Recharge");
			startActivity(intent);
			break;
		case R.id.btn_postpaid_1:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Postpaid");
			startActivity(intent);
			break;
		case R.id.btn_data_card_1:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "DataCard");
			startActivity(intent);
			break;
		case R.id.btn_dth_1:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "DTH");
			startActivity(intent);
			break;
		case R.id.btn_recharge_history_1:
			startActivity(new Intent(getApplicationContext(),
					RechargeReport.class));
			break;

		default:
			break;
		}
	}
}
