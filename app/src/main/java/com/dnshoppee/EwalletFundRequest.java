package com.dnshoppee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EwalletFundRequest extends Activity {
	String url, getDetailurl;
	SharedPreferences preferences;
	String address, state, email, pincode, number, sex, bcode, menuID;
	EditText addressE, stateE, emailE, pincodeE, numberE, bcodeE;
	Button reset, cancel;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	ProgressDialog _pDialog;
	GetResult updateProfile;
	protected Context _context = this;
	int year, month, day;
	Calendar cal;
	Context context = this;
	XMLParser1 parser2;
	TextView tName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ewallet_fund_request);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		addressE = (EditText) findViewById(R.id.edit_address);
		bcodeE = (EditText) findViewById(R.id.EditText01);
		stateE = (EditText) findViewById(R.id.edit_state);
		emailE = (EditText) findViewById(R.id.edit_email);
		pincodeE = (EditText) findViewById(R.id.edit_pincode);
		numberE = (EditText) findViewById(R.id.edit_number);
		reset = (Button) findViewById(R.id.btn_resetProfile);
		cancel = (Button) findViewById(R.id.btn_cancelProfile);
		tName =(TextView) findViewById(R.id.textView1);
		
		Intent intent = getIntent();
		if(intent.hasExtra("menuID")){
			menuID = intent.getExtras().getString("menuID");
			if(menuID.equals("T")){
				sex = "TopupWallet";
				tName.setText("Twallet fund request");
			}else{
				sex = "EWallet";
				tName.setText("Ewallet fund request");
			}
			
		}

		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				address = addressE.getText().toString().trim();
				address = address.replace(" ", "%20");
				state = stateE.getText().toString().trim();
				state = state.replaceAll(" ", "%20");
				email = emailE.getText().toString().trim();
				email = email.replaceAll(" ", "%20");
				pincode = pincodeE.getText().toString().trim();
				number = numberE.getText().toString().trim();
				bcode = bcodeE.getText().toString().trim();

				if (address.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Remark",
							Toast.LENGTH_SHORT).show();
				} else if (state.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Amount",
							Toast.LENGTH_SHORT).show();
				} else if (email.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter Transaction number", Toast.LENGTH_SHORT)
							.show();
				} else if (pincode.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Bank name",
							Toast.LENGTH_SHORT).show();
				} else if (number.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter Branch name", Toast.LENGTH_SHORT).show();
				} else if (sex.equals("")) {
					Toast.makeText(getApplicationContext(), "Select gender",
							Toast.LENGTH_SHORT).show();
				} else if (bcode.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter branch code", Toast.LENGTH_SHORT).show();
				} else {
					Log.i("address", address);
					Log.i("state", state);
					Log.i("email", email);
					Log.i("pincode", pincode);
					Log.i("number", number);
					Log.i("sex", sex);

					url = "http://dnshoppee.com/mANdroid.aspx?MenuId=T&GID="
							+ preferences.getString(Login._name, "")
							+ "&EwalletType=" + sex + "&Remarks=" + address
							+ "&Amount=" + state + "&Transactionno=" + email
							+ "&BankName=" + pincode + "&BranchName=" + number
							+ "&BranchCode=" + bcode;

					updateProfile = new GetResult();
					updateProfile.execute(url);
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

	}

	public class GetResult extends AsyncTask<String, Void, String> {

		String line = null;
		String name1 = null;
		String temp;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			_pDialog = new ProgressDialog(EwalletFundRequest.this);
			_pDialog.setMessage("Please Wait..");
			_pDialog.setCancelable(false);
			_pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			Log.v(".doInBackground1", "doInBackground method call");

			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				Log.v(".doInBackground2", "doInBackground method call");
				HttpPost httpPost = new HttpPost(urls[0]);
				Log.v(".doInBackgroun3", "doInBackground method call");
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				Log.v(".doInBackgroun4", "doInBackground method call");
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.v(".doInBackgroun5", "doInBackground method call");
				HttpEntity httpEntity = httpResponse.getEntity();
				Log.v(".doInBackgroun6", "doInBackground method call");
				InputStream is = httpEntity.getContent();
				Log.v(".doInBackgroun7", "doInBackground method call");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
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
			} catch (Exception e) {
				// Toast.makeText(MainActivity.this, "internet connection fail",
				// Toast.LENGTH_LONG).show();
			}

			// return line;

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("LINE", line);

			if (_pDialog.isShowing()) {
				if (line.contains("Success")) {
					showAlertDialog1(_context, "Success", "Profile Edited",
							true);
				} else {
					showAlertDialog1(_context, "Fail", line, false);
				}
				_pDialog.cancel();
			}
		}

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void showAlertDialog1(Context context, String title, String message,
			Boolean status) {
		// TODO Auto-generated method stub
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
				onBackPressed();
				// alertDialog.cancel();
				finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
