package com.dnshoppee;

import java.text.DecimalFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Fund1 extends ActionBarActivity implements OnClickListener {
	SharedPreferences preferences;
	Button pocketBal, walletBal, pocketTpocket, walletTwallet, pocketTwallet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fund);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);
		pocketBal = (Button) findViewById(R.id.btn_e_pocket_balance_1);
		walletBal = (Button) findViewById(R.id.btn_topup_balance_1);
		pocketTpocket = (Button) findViewById(R.id.btn_ewallet_t_twallet_1);
		walletTwallet = (Button) findViewById(R.id.btn_ewallet_t_ewallet_1);
		pocketTwallet = (Button) findViewById(R.id.btn_twallet_t_twallet_1);

		pocketBal.setOnClickListener(this);
		walletBal.setOnClickListener(this);
		pocketTpocket.setOnClickListener(this);
		walletTwallet.setOnClickListener(this);
		pocketTwallet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_e_pocket_balance_1:
			String urlBal = "http://dnshoppee.com/mAndroid.aspx?MenuID=U&GID="
					+ preferences.getString(Login._name, "")
					+ "&EwalletType=Ewallet";
			XMLParser1 parser = new XMLParser1();
			String xml = parser.getXmlFromUrl(urlBal);
			Log.d("XML", xml);
			if (xml.trim().equalsIgnoreCase("No Data Found")) {
				Toast.makeText(Fund1.this, "No Data Found", Toast.LENGTH_LONG)
						.show();
				Log.d("XML", "DOCNo Data Found");
			} else if (xml.equals(" ")) {
				Toast.makeText(Fund1.this, "Time Out, Try Again !!!",
						Toast.LENGTH_LONG).show();
				Log.d("XML", "DOCNo Data Found");
			} else {

				Toast.makeText(getApplicationContext(), xml.trim(), Toast.LENGTH_LONG)
						.show();

			}
			break;
		case R.id.btn_topup_balance_1:
			urlBal = "http://dnshoppee.com/mAndroid.aspx?MenuID=U&GID="
					+ preferences.getString(Login._name, "")
					+ "&EwalletType=TopupWallet";
			parser = new XMLParser1();
			xml = parser.getXmlFromUrl(urlBal);
			Log.d("XML", xml);
			if (xml.trim().equalsIgnoreCase("No Data Found")) {
				Toast.makeText(Fund1.this, "No Data Found", Toast.LENGTH_LONG)
						.show();
				Log.d("XML", "DOCNo Data Found");
			} else if (xml.equals(" ")) {
				Toast.makeText(Fund1.this, "Time Out, Try Again !!!",
						Toast.LENGTH_LONG).show();
				Log.d("XML", "DOCNo Data Found");
			} else {

				Toast.makeText(getApplicationContext(), xml.trim(), Toast.LENGTH_LONG)
						.show();

			}
			break;
		case R.id.btn_ewallet_t_twallet_1:
			intent = new Intent(getApplicationContext(), EwalletTTwallet.class);
			intent.putExtra("menuID", "Q");
			startActivity(intent);
			break;
		case R.id.btn_ewallet_t_ewallet_1:
			intent = new Intent(getApplicationContext(), EwalletTTwallet.class);
			intent.putExtra("menuID", "R");
			startActivity(intent);
			break;
		case R.id.btn_twallet_t_twallet_1:
			intent = new Intent(getApplicationContext(), EwalletTTwallet.class);
			intent.putExtra("menuID", "S");
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
