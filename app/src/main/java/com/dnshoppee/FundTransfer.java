package com.dnshoppee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FundTransfer extends Activity {
	EditText e1, e2, e3;
	Button b1, b2;
	String memberID, amount, wallet;
	Context context = this;
	SharedPreferences preferences;
	GetResult getResult;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fund_transfer);
		b1 = (Button) findViewById(R.id.btn_submit1);
		b2 = (Button) findViewById(R.id.btn_cancel1);
		e1 = (EditText) findViewById(R.id.e1);
		e2 = (EditText) findViewById(R.id.e2);
		e3 = (EditText) findViewById(R.id.e3);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				memberID = e1.getText().toString().trim();
				amount = e2.getText().toString().trim();
				wallet = e3.getText().toString().trim();

				if (memberID.equals("")) {
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				} else if (memberID.contains(" ")) {
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				} else if (amount.equals("")) {
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				} else if (wallet.equals("")) {
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				} else {

					String url = "http://recharge.mlm4india.com/MAndroid.aspx?MenuID=N&GID="
							+ preferences.getString(Login._name, "")
							+ "&amount="
							+ amount
							+ "&TransferMemberID="
							+ memberID + "&PWD=" + wallet;
					// String url =
					// "http://recharge.mlm4india.com/MAndroid.aspx?MenuID=N&fromMember="
					// + preferences.getString(Login._name, "")
					// + "&TransferMemberID="
					// + memberID
					// + "&Amount="
					// + amount
					// + "&PWD=" + wallet;
					Log.i("url", url);
					getResult = new GetResult();
					getResult.execute(url);
				}
			}
		});
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
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
				Log.v(".doInBackground8", line);
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
							"Request Send Successfully", true);

				} else {
					if (line.contains("Password")) {
						showAlertDialog(context, "Fail", "Wrong Password",
								false);
					} else {
						showAlertDialog(context, "Fail", line, false);
					}
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

				onBackPressed();
				alertDialog.cancel();
				finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
