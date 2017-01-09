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

public class ChangeTpass extends Activity {
	Button _reset, _cancel;
	EditText _newpass, _oldpass, _confirmpass;
	protected String _newpsw;
	protected String _confirm;
	protected Context context = this;
	SharedPreferences preferences;
	GetResult getResult;
	public ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_tpass);
		_reset = (Button) findViewById(R.id.btn_resetPass);
		_cancel = (Button) findViewById(R.id.btn_cancelpass);
		_newpass = (EditText) findViewById(R.id.text_newpass);
		_oldpass = (EditText) findViewById(R.id.text_oldPass);
		_confirmpass = (EditText) findViewById(R.id.text_confirmpass);

		_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});

		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		_reset.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				String curpsw = _oldpass.getText().toString().trim();
				_newpsw = _newpass.getText().toString().trim();
				_confirm = _confirmpass.getText().toString().trim();
				_newpsw = _newpsw.replaceAll(" ", "%20");
				_confirm = _confirm.replaceAll(" ", "%20");
				if (curpsw.equals("")) {
					showAlertDialog(context, "Worning",
							"The Current Password field is required", false);
				} else if (_newpsw.equals("")) {
					showAlertDialog(context, "Worning",
							"The New Password field is required", false);
					_newpass.requestFocus();
				} else if (_confirm.equals("")) {
					showAlertDialog(context, "Worning",
							"The ReType Password field is required", false);
					_confirmpass.requestFocus();
				} else if (_newpsw.trim().equals(_confirm) == false) {
					showAlertDialog(context, "Worning", "Password Not Matched",
							false);
					_confirmpass.setText("");
					_confirmpass.requestFocus();
				} else {
					Log.i(_confirm, curpsw);
					Log.i(_confirm, preferences.getString(Login._name, ""));
					String url;
					// if(preferences.getString(Personal._PassType,
					// "").equals("T")){
					// url = "http://globalappads.com/mglobalandroid.aspx?GID="
					// + preferences.getString(MainActivity._name, "")
					// + "&MenuID=D&newpwd="
					// + _confirm + "&oldpwd=" + curpsw;
					// }
					url = "http://dnshoppee.com/MAndroid.aspx?MenuID=D&oldpwd="
							+ curpsw + "&newpwd=" + _confirm + "&GID="
							+ preferences.getString(Login._name, "");
					// url = "http://globalappads.com/mglobalandroid.aspx?GID="
					// + preferences.getString(Login._name, "")
					// + "&MenuID=D&newpwd="
					// + _confirm + "&oldpwd=" + curpsw;

					Log.i("url----", url);
					getResult = new GetResult();
					getResult.execute(url);
				}

			}
		});

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
				// finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
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
							"Transaction Password Change Successfully", true);
					Toast.makeText(getApplicationContext(),
							"Password Change Successfully", Toast.LENGTH_LONG)
							.show();
					onBackPressed();
				} else {
					showAlertDialog(context, "fail", line, false);
				}
			}
		}

	}

}
