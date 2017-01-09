package com.dnshoppee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends ActionBarActivity {

	Button ok, cancel;
	EditText numberE;
	String number;
	public ProgressDialog pDialog;
	public List<NameValuePair> params = new ArrayList<NameValuePair>();
	Context context = this;
	GetResult getResult;
	SharedPreferences sharedPreferences;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		ok = (Button) findViewById(R.id.button1);
		cancel = (Button) findViewById(R.id.button2);
		numberE = (EditText) findViewById(R.id.edit_forgot_pass);
		sharedPreferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);
		Intent intent = getIntent();
		if(intent.hasExtra("GID")){
			uid = intent.getExtras().getString("GID").toString();
		}
		

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number = numberE.getText().toString().trim();

				if (number.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Enter mobile number", Toast.LENGTH_SHORT).show();
				} else {
					String url = "http://dnshoppee.com/mANdroid.aspx?MenuId=X&GID="
							+ uid
							+ "&Mobile="
							+ number;
					getResult = new GetResult();
					getResult.execute(url);
				}
			}
		});
	}

	public class GetResult extends AsyncTask<String, Void, Void> {
		String line = null;
		String name = null;
		String temp = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(ForgotPassword.this);
			pDialog.setMessage("please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... urls) {
			// TODO Auto-generated method stub

			Log.v("doinbackL", "1");

			try {
				DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
				Log.v("doinbackL", "2");
				HttpPost httpPost = new HttpPost(urls[0]);
				Log.v("doinbackL", "3");
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				Log.v("doinbackL", "4");
				HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
				Log.v("doinbackL", "5");
				HttpEntity httpEntity = httpResponse.getEntity();
				Log.v("doinbackL", "6");
				InputStream is = httpEntity.getContent();
				Log.v("doinbackL", "7");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				Log.v("doinbackL", "8");
				line = reader.readLine();
				is.close();
			} catch (Exception e) {
				Log.v("getresultL", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Log.d("lineL", line);

			if (pDialog.isShowing()) {
				pDialog.cancel();
				if (line.contains("Success")) {
					showAlertDialog(context, "Success",
							"Submited successfully", true);
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

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
