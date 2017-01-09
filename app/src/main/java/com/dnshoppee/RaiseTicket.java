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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class RaiseTicket extends Activity {
	Button _confirm, _cancel;
	LinearLayout _btnLogout;
	EditText _memberName, _subject, _message;
	Spinner _selectTicket;
	String _SmemberName, _Ssubject, _Smessage, _SselectTicket;
	protected Context context = this;
	SharedPreferences preferences;
	GetResult getResult;
	public ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raise_ticket);
		_confirm = (Button) findViewById(R.id.btn_ConfirmRaise);
		_cancel = (Button) findViewById(R.id.btn_CancelRaise);
		_memberName = (EditText) findViewById(R.id.text_memberName);
		_subject = (EditText) findViewById(R.id.text_subject);
		_selectTicket = (Spinner) findViewById(R.id.select_ticket);
		_message = (EditText) findViewById(R.id.text_message);

		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		_memberName.setText(preferences.getString(Login._SponcerName, ""));

		_confirm.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				_SmemberName = _memberName.getText().toString().replaceAll(" ", "%20");

				_Ssubject = _subject.getText().toString().trim();
				_Smessage = _message.getText().toString().trim();
				_Ssubject = _Ssubject.replaceAll(" ", "%20");
				_Smessage = _Smessage.replaceAll(" ", "%20");

				_SselectTicket = _selectTicket.getSelectedItem().toString()
						.replaceAll(" ", "%20");
				Log.e("_SselectTicket", _SselectTicket);
				if (_SmemberName.equals("")) {
					showAlertDialog(context, "Worning",
							"The Member field is required", false);

				} else if (_Ssubject.equals("")) {
					showAlertDialog(context, "Worning", "The subject required",
							false);
				} else if (_Smessage.equals("")) {
					showAlertDialog(context, "Worning",
							"The message field is required", false);
					_message.requestFocus();
				} else if (_SselectTicket.equals("")) {
					showAlertDialog(context, "Worning",
							"The select field is required", false);
				} else {
					String url;
					// if(preferences.getString(Personal._PassType,
					// "").equals("T")){
					// url = "http://globalappads.com/mglobalandroid.aspx?GID="
					// + preferences.getString(MainActivity._name, "")
					// + "&MenuID=D&newpwd="
					// + _confirm + "&oldpwd=" + curpsw;
					// }

					// url =
					// "http://www.globalappads.com/MGlobalAndroid.aspx?GID="
					// + preferences.getString(MainActivity._name, "")
					// + "&MenuID=S&MemberName="
					// + "Demo Website"
					// + " &Type="
					// + _SselectTicket;
					
					url = "http://recharge.mlm4india.com/MAndroid.aspx?MenuID=M&GID="+preferences.getString(Login._name, "")+"&subject="+_Ssubject+"&type="+_SselectTicket+"&Description=" + _Smessage;

//					url = "http://globalappads.com/MGlobalAndroid.aspx?GID="
//							+ preferences.getString(MainActivity._name, "")
//							+ "&MenuID=S&MemberName="+_SmemberName+"&Subject=" + _Ssubject + "&Type="
//							+ _SselectTicket + "&Description=" + _Smessage;

					// url =
					// "http://www.globalappads.com/MGlobalAndroid.aspx?GID="+preferences.getString(MainActivity._name,
					// "")+"&MenuID=S&MemberName=DemoWebsite&Type=Login%20Issue";

					Log.i("url----", url);
					getResult = new GetResult();
					getResult.execute(url);
				}

			}
		});
		
		_btnLogout = (LinearLayout) findViewById(R.id.btn_logout);
		_btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restartFirstActivity();
			}
		});
	}
	
	private void restartFirstActivity() {
		Intent i = getBaseContext().getPackageManager()
				.getLaunchIntentForPackage(getBaseContext().getPackageName());

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
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
				if (line.trim().equalsIgnoreCase(
						"Your Ticket Is Submitted Successfully To Admin!!!!")) {

					showAlertDialog(context, "Success",
							"Submitted Successfully", true);

				} else {
					showAlertDialog(context, "Fail", line, false);
				}
			}
		}

	}

}
