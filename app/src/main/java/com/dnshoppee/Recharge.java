package com.dnshoppee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Recharge extends Activity {
	Spinner operator_type;
	Context context = this;
	String[] DATACARD = new String[] { "Aircel", "Airtel", "AirtelMoney",
			"BSNL", "Docomo", "Idea", "LOOP", "MGASBP", "MTNL", "MTS",
			"Reliance", "TATABP", "TataIndicome", "TikonaBP", "Uninor",
			"Videocon", "Vodafone" };

	String[] DTH = new String[] { "AirtelDTH", "DishTV", "RelianceBigTV",
			"SunDirect", "TataSky", "VideoconD2H" };

	String[] POSTPAID = new String[] { "Airtel", "BSNL", "Docomo", "Idea",
			"Vodafone", };

	String[] PREPAID = new String[] { "Aircel", "Airtel", "AirtelMoney",
			"BSNL", "Docomo", "Idea", "LOOP", "MGASBP", "MTNL", "MTS",
			"Reliance", "TATABP", "TataIndicome", "TikonaBP", "Uninor",
			"Videocon", "Vodafone" };

	String[] LANLINE = new String[] { "BSNL", "TataIndicome", "Reliance",
			"Airtel" };

	boolean flag_lanline = false, flag_bsnl = false;

	String[] SPECIALR = new String[] { "BSNL", "Docomo", "Uninor", "Videocon" };
	SharedPreferences preferences;
	TextView _showbal;
	Button btn_submit;
	EditText Enumber, Econfirm, EAmount, Epassword, Acno;
	String rechargeType, companyName, number, confirm, amount, password, acno;
	GetResult getResult;
	public ProgressDialog pDialog;
	LinearLayout lv;
	TextView Tmobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		// recharge_type = (Spinner) findViewById(R.id.spinner_RechargeType);
		operator_type = (Spinner) findViewById(R.id.spinner_Operator);
		lv = (LinearLayout) findViewById(R.id.field);
		Acno = (EditText) findViewById(R.id.Edit_acno);
		Tmobile = (TextView) findViewById(R.id.TextView0000);

		_showbal = (TextView) findViewById(R.id.text_wallet);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		Intent intent = getIntent();
		if (intent.hasExtra("plan")) {
			rechargeType = intent.getExtras().getString("plan");
			Log.i("rec", rechargeType);
			ArrayAdapter<String> adapter;
			String path;
			if (rechargeType.equals("DataCard")) {
				Tmobile.setText("Datacard No");
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, DATACARD);
			} else if (rechargeType.equals("DTH")) {
				Tmobile.setText("DTH No");
				path = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MenuID=DTH";
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, callWeb(path));
			} else if (rechargeType.equals("Postpaid")) {
				Tmobile.setText("Mobile No");
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, POSTPAID);
			} else if (rechargeType.equals("Prepaid")) {
				Tmobile.setText("Mobile No");
				path = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MenuID=Prepaid";
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, callWeb(path));
			} else if (rechargeType.equals("Special Recharge")) {
				Tmobile.setText("Mobile No");
				path = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MenuID=Special";
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, callWeb(path));
			} else {
				adapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, LANLINE);
				flag_lanline = true;
			}
			setTitle(rechargeType);
			adapter.notifyDataSetChanged();
			operator_type.setAdapter(adapter);
		}

		CheckConnectivity con = new CheckConnectivity();
		con.checknow(context);
		String urlBal = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MenuID=A&GID="
				+ preferences.getString(Login._name, "");
		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(urlBal);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(Recharge.this, "No Data Found", Toast.LENGTH_LONG)
					.show();
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(Recharge.this, "Time Out, Try Again !!!",
					Toast.LENGTH_LONG).show();
			Log.d("XML", "DOCNo Data Found");
		} else {
			Document doc = parser.getDomElement(xml);
			Log.d("dom", "doc");
			NodeList nl = doc.getElementsByTagName("Table");
			Log.d("XML", nl.getLength() + "");
			try {

				double temp = (double) Double.valueOf(parser.getValue(
						(Element) nl.item(0), "Column1"));

				_showbal.setText(new DecimalFormat("##.##").format(temp));
			} catch (NumberFormatException e) {
				_showbal.setText("00.00");
			}

		}
		// Column1
		Enumber = (EditText) findViewById(R.id.edit_mobileNum);

		Econfirm = (EditText) findViewById(R.id.edit_ConfirmNUm);

		EAmount = (EditText) findViewById(R.id.edit_Amount);

		Epassword = (EditText) findViewById(R.id.edit_Password);

		btn_submit = (Button) findViewById(R.id.btn_submit1);
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number = Enumber.getText().toString().trim();
				confirm = Econfirm.getText().toString().trim();
				amount = EAmount.getText().toString().trim();
				password = Epassword.getText().toString().trim();
				if (number.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter " + Tmobile.getText().toString().trim(),
							Toast.LENGTH_SHORT).show();
				} else if (rechargeType.length() <= 0) {
					Toast.makeText(getApplicationContext(),
							"Not received recharge type", Toast.LENGTH_SHORT)
							.show();
				} else if (confirm.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Confirm " + Tmobile.getText().toString().trim(),
							Toast.LENGTH_SHORT).show();
				} else if (!number.equals(confirm)) {
					Toast.makeText(getApplicationContext(), "Number not match",
							Toast.LENGTH_SHORT).show();
				} else if (amount.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Amount",
							Toast.LENGTH_SHORT).show();
				} else if (password.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Password",
							Toast.LENGTH_SHORT).show();
				} else {
					rechargeType = rechargeType.replaceAll(" ", "%20");
					if (flag_lanline) {
						if (flag_bsnl) {
							acno = Acno.getText().toString();
							if (acno.equals("")) {
								Toast.makeText(getApplicationContext(),
										"Please enter account number",
										Toast.LENGTH_SHORT).show();
							} else {

								String url = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MdnAndroidRecharge.aspx?MenuID=B&GID="
										+ preferences
												.getString(Login._name, "")
										+ "&rechargetype="
										+ rechargeType
										+ "&mobile="
										+ number
										+ "&amount="
										+ amount
										+ "&CompanyName="
										+ companyName
										+ "&TransPassword="
										+ password + "&Acno=" + acno;
								Log.i("url", url);
								getResult = new GetResult();
								getResult.execute(url);
							}
						}

					} else {
						// String url =
						// "http://dnshoppee.com/MdnAndroidRecharge.aspx?RechargeType="
						// + rechargeType
						// + "&Mobile="
						// + number
						// + "&Amount="
						// + amount
						// + "&CompanyName="
						// + companyName
						// + "&confirmmobile="
						// + number + "&TransPassword=" + password;
						String url = "http://dnshoppee.com/MdnAndroidRecharge.aspx?MdnAndroidRecharge.aspx?&GID="
								+ preferences.getString(Login._name, "")
								+ "&rechargetype="
								+ rechargeType
								+ "&Mobile="
								+ number
								+ "&Amount="
								+ amount
								+ "&CompanyName="
								+ companyName
								+ "&TransPassword=" + password + "&MenuID=B";
						Log.i("url", url);
						getResult = new GetResult();
						getResult.execute(url);
					}

				}
			}
		});

		operator_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				companyName = operator_type.getItemAtPosition(arg2).toString();
				companyName = companyName.replaceAll(" ", "%20");
				if (companyName.equals("BSNL") && flag_lanline) {
					lv.setVisibility(View.VISIBLE);
					flag_bsnl = true;
				} else {
					lv.setVisibility(View.INVISIBLE);
					flag_bsnl = false;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private String[] callWeb(String path) {
		// TODO Auto-generated method stub
		String urlBal = path;
		String compnyList[] = null;
		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(urlBal);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(Recharge.this, "No Data Found", Toast.LENGTH_LONG)
					.show();
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(Recharge.this, "Time Out, Try Again !!!",
					Toast.LENGTH_LONG).show();
			Log.d("XML", "DOCNo Data Found");
		} else {
			Document doc = parser.getDomElement(xml);
			Log.d("dom", "doc");
			NodeList nl = doc.getElementsByTagName("Table");
			Log.d("XML", nl.getLength() + "");
			compnyList = new String[nl.getLength()];
			for (int i = 0; i < nl.getLength(); i++) {
				compnyList[i] = parser.getValue((Element) nl.item(i),
						"Companyname");
			}

		}
		return compnyList;
	}

	private class GetResult extends AsyncTask<String, Void, String> {
		String line = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Please Wait..");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			Log.v(".doInBackground1", "doInBackground method call");

			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				Log.v(".doInBackground2", "doInBackground method call");
				HttpPost httpPost = new HttpPost(urls[0]);
				Log.v(".doInBackgroun4", "doInBackground method call");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.v(".doInBackgroun5", "doInBackground method call");
				HttpEntity httpEntity = httpResponse.getEntity();
				Log.v(".doInBackgroun6", "doInBackground method call");
				InputStream is = httpEntity.getContent();
				Log.v(".doInBackgroun7", "doInBackground method call");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 60);
				// StringBuilder sb = new StringBuilder();
				line = reader.readLine();
				Log.v(".doInBackground8", "doInBackground method call");

				is.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// Toast.makeText(MainActivity.this, "internet connection fail",
				// Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			return null;

			// return line;

		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("LINE", line);

			if (pDialog.isShowing()) {
				pDialog.dismiss();
				getResult.cancel(true);
				if (line.trim().contains("Success")) {

					showAlertDialog(context, "Success",
							"Your Recharges is Successfull", true);

				} else {
					showAlertDialog(context, "Fail", line, false);
				}
			}
		}

	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context,
				AlertDialog.THEME_DEVICE_DEFAULT_DARK).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				onBackPressed();
				// finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

}
