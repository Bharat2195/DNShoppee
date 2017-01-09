package com.dnshoppee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PinTransfer extends ActionBarActivity {

	EditText  noOfPinsE, memberIDE;
	String Pname, noOfPins, memberID;
	Button purchase, cancel;
	Spinner PnameE;
	GetResult updateProfile;
	SharedPreferences preferences;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	ProgressDialog _pDialog;
	Context _context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_transfer);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		PnameE = (Spinner) findViewById(R.id.spinner_RechargeType);
		memberIDE = (EditText) findViewById(R.id.EditText01);
		noOfPinsE = (EditText) findViewById(R.id.edit_number);
		purchase = (Button) findViewById(R.id.btn_resetProfile);
		cancel = (Button) findViewById(R.id.btn_cancelProfile);
		
		PnameE.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Pname = PnameE.getItemAtPosition(arg2).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		purchase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Pname = Pname.replace(" ", "%20");
				noOfPins = noOfPinsE.getText().toString().trim();
				noOfPins = noOfPins.replaceAll(" ", "%20");
				memberID = memberIDE.getText().toString().trim();
				memberID = memberID.replaceAll(" ", "%20");

				if (noOfPins.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter Number of Pin", Toast.LENGTH_SHORT).show();
				} else if (Pname.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter Package Name", Toast.LENGTH_SHORT).show();
				} else if (memberID.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter Member ID",
							Toast.LENGTH_SHORT).show();
				} else {

					String url = "http://dnshoppee.com/mANdroid.aspx?MenuId=P&GID="
							+ preferences.getString(Login._name, "")
							+ "&MemberId="
							+ memberID
							+ "&PackageName="
							+ Pname
							+ "&NoofPins=" + noOfPins;

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
			_pDialog = new ProgressDialog(PinTransfer.this);
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
					showAlertDialog1(_context, "Success", "Pin transfered",
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
