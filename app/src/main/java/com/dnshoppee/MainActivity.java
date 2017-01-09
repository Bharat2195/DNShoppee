package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	Button b1, b2, b3, b4, b5, b6, b7, b8, b11, b13, b12;
	Button b111, b112, b113, b121, b122, b123, b131, b132;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		b1 = (Button) findViewById(R.id.btn_dash1);
		b2 = (Button) findViewById(R.id.btn_dash2);
		b3 = (Button) findViewById(R.id.btn_dash3);
		b4 = (Button) findViewById(R.id.btn_dash4);
		b5 = (Button) findViewById(R.id.btn_dash5);
		b6 = (Button) findViewById(R.id.btn_dash6);
		b7 = (Button) findViewById(R.id.btn_dash7);
		b8 = (Button) findViewById(R.id.btn_dash8);
		b11 = (Button) findViewById(R.id.btn_dash11);
		b12 = (Button) findViewById(R.id.btn_dash12);
		b13 = (Button) findViewById(R.id.btn_dash13);
		b111 = (Button) findViewById(R.id.btn_dash111);
		b112 = (Button) findViewById(R.id.btn_dash112);
		b113 = (Button) findViewById(R.id.btn_dash113);
		b121 = (Button) findViewById(R.id.btn_recharge);
		b122 = (Button) findViewById(R.id.btn_dash122);
		b123 = (Button) findViewById(R.id.btn_dash123);
		b131 = (Button) findViewById(R.id.btn_dash131);
		b132 = (Button) findViewById(R.id.btn_dash132);

		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b11.setOnClickListener(this);
		b12.setOnClickListener(this);
		b13.setOnClickListener(this);
		b111.setOnClickListener(this);
		b112.setOnClickListener(this);
		b113.setOnClickListener(this);
		b121.setOnClickListener(this);
		b122.setOnClickListener(this);
		b123.setOnClickListener(this);
		b131.setOnClickListener(this);
		b132.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_dash1:
			startActivity(new Intent(getApplicationContext(), EditProfile.class));
			break;

		case R.id.btn_dash2:
			startActivity(new Intent(getApplicationContext(), ChangePass.class));
			break;
		case R.id.btn_dash3:
			startActivity(new Intent(getApplicationContext(), ChangeTpass.class));
			break;
		case R.id.btn_dash4:
			startActivity(new Intent(getApplicationContext(), UsedPin.class));
			break;
		case R.id.btn_dash5:
			startActivity(new Intent(getApplicationContext(), PinReport.class));
			break;
		case R.id.btn_dash6:
			startActivity(new Intent(getApplicationContext(), UnusedPin.class));
			break;
		case R.id.btn_dash7:
			startActivity(new Intent(getApplicationContext(), MyDownline.class));
			break;
		case R.id.btn_dash8:
			startActivity(new Intent(getApplicationContext(),
					AccountStatement.class));
			break;
		case R.id.btn_dash11:
			startActivity(new Intent(getApplicationContext(), RaiseTicket.class));
			break;
		case R.id.btn_dash12:
//			startActivity(new Intent(getApplicationContext(),
//					CurrentTicket.class));
			break;
		case R.id.btn_dash13:
			startActivity(new Intent(getApplicationContext(), CloseQuery.class));
			break;

		case R.id.btn_dash111:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "DataCard");
			startActivity(intent);
			break;
		case R.id.btn_dash112:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "DTH");
			startActivity(intent);
			break;
		case R.id.btn_dash113:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Postpaid");
			startActivity(intent);
			break;
		case R.id.btn_recharge:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Prepaid");
			startActivity(intent);
			break;
		case R.id.btn_dash122:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "Special Recharge");
			startActivity(intent);
			break;
		case R.id.btn_dash123:
			intent = new Intent(getApplicationContext(), Recharge.class);
			intent.putExtra("plan", "LandLine");
			startActivity(intent);
			break;
		case R.id.btn_dash131:
			startActivity(new Intent(getApplicationContext(), RechargeReport.class));
			break;
		case R.id.btn_dash132:
			startActivity(new Intent(getApplicationContext(), FundTransfer.class));
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
			Intent i = getBaseContext().getPackageManager()
					.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

