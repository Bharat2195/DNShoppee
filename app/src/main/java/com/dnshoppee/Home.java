package com.dnshoppee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends ActionBarActivity implements OnClickListener {

	Button recharge, fund, request, pin, report, personal, ticket;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		recharge = (Button) findViewById(R.id.btn_recharge);
		fund = (Button) findViewById(R.id.btn_fund);
		request = (Button) findViewById(R.id.btn_request);
		pin = (Button) findViewById(R.id.btn_pin);
		report = (Button) findViewById(R.id.btn_report);
		personal = (Button) findViewById(R.id.btn_personal);
		ticket = (Button) findViewById(R.id.btn_ticket_1);
		
		sharedPreferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		recharge.setOnClickListener(this);
		fund.setOnClickListener(this);
		request.setOnClickListener(this);
		pin.setOnClickListener(this);
		report.setOnClickListener(this);
		personal.setOnClickListener(this);
		ticket.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_recharge:
			startActivity(new Intent(getApplicationContext(),
					RechargeHome.class));
			break;
		case R.id.btn_fund:
			startActivity(new Intent(getApplicationContext(), Fund.class));
			break;
		case R.id.btn_request:
			startActivity(new Intent(getApplicationContext(), Request.class));
			break;
		case R.id.btn_pin:
			startActivity(new Intent(getApplicationContext(), PinMenu.class));
			break;
		case R.id.btn_report:
			startActivity(new Intent(getApplicationContext(), Report.class));
			break;
		case R.id.btn_personal:
			startActivity(new Intent(getApplicationContext(), Personal.class));
			break;
		case R.id.btn_ticket_1:
			startActivity(new Intent(getApplicationContext(), TicketMenu.class));
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_logout:
			Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			sharedPreferences = getSharedPreferences(Login.MYPREFERENCES,
					Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString(Login._passsave, "no");
			editor.commit();
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
