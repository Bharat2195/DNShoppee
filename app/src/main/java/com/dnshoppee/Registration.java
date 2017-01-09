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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity {
	String pinno, sponserid, name, mobile, email, pass, confirm_pass, ewallet,
			confirm_ewallet, side;
	EditText Epinno, Esponserid, Ename, Emobile, Eemail, Epass, Econfirm_pass,
			Eewallet, Econfirm_ewallet;
	Button submit, cancel;
	Context context = this;
	ProgressDialog pDialog;
	GetResult getResult;
	RadioButton r1, r2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Epinno = (EditText) findViewById(R.id.e1);
		Esponserid = (EditText) findViewById(R.id.e10);
		Ename = (EditText) findViewById(R.id.e3);
		Emobile = (EditText) findViewById(R.id.e4);
		Eemail = (EditText) findViewById(R.id.e5);
		Epass = (EditText) findViewById(R.id.e6);
		Econfirm_pass = (EditText) findViewById(R.id.e7);
		Eewallet = (EditText) findViewById(R.id.e8);
		Econfirm_ewallet = (EditText) findViewById(R.id.e9);
		submit = (Button) findViewById(R.id.btn_dash1);
		cancel = (Button) findViewById(R.id.btn_b2);
		r1 = (RadioButton) findViewById(R.id.radioLeft);
		r2 = (RadioButton) findViewById(R.id.radioRight);

		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				side = "Left";
			}
		});
		
		r2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				side = "Right";
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}

		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pinno = Epinno.getText().toString().trim();
				sponserid = Esponserid.getText().toString().trim();
				sponserid = sponserid.replaceAll(" ", "%20");
				name = Ename.getText().toString().trim();
				name = name.replaceAll(" ", "%20");
				mobile = Emobile.getText().toString().trim();
				email = Eemail.getText().toString().trim();
				email = email.replaceAll(" ", "%20");
				pass = Epass.getText().toString().trim();
				pass = pass.replaceAll(" ", "%20");
				confirm_pass = Econfirm_pass.getText().toString().trim();
				confirm_pass = confirm_pass.replaceAll(" ", "%20");
				ewallet = Eewallet.getText().toString().trim();
				ewallet = ewallet.replaceAll(" ", "%20");
				confirm_ewallet = Econfirm_ewallet.getText().toString().trim();
				confirm_ewallet = confirm_ewallet.replaceAll(" ", "%20");

				if (pinno.equals("")) {
					Toast.makeText(context, "Enter Pin no", Toast.LENGTH_SHORT)
							.show();
				} else if (sponserid.equals("")) {
					Toast.makeText(context, "Enter Sponser ID",
							Toast.LENGTH_SHORT).show();
				} else if (name.equals("")) {
					Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT)
							.show();
				} else if (mobile.equals("") || mobile.length() != 10) {
					Toast.makeText(context, "Enter valid Mobile number",
							Toast.LENGTH_SHORT).show();
				} else if (email.equals("") || !email.contains("@")) {
					Toast.makeText(context, "Enter valid email ID",
							Toast.LENGTH_SHORT).show();
				} else if (pass.equals("")) {
					Toast.makeText(context, "Enter Password",
							Toast.LENGTH_SHORT).show();
				} else if (pass.length() < 6) {
					Toast.makeText(context, "password is short",
							Toast.LENGTH_SHORT).show();
				} else if (confirm_pass.equals("")) {
					Toast.makeText(context, "Confirm Password",
							Toast.LENGTH_SHORT).show();
				} else if (!pass.equals(confirm_pass)) {
					Toast.makeText(context, "Password not match",
							Toast.LENGTH_SHORT).show();
				} else if (ewallet.equals("")) {
					Toast.makeText(context, "Enter Ewallet Password",
							Toast.LENGTH_SHORT).show();
				} else if (ewallet.length() < 6) {
					Toast.makeText(context, "ewallet password is short",
							Toast.LENGTH_SHORT).show();
				} else if (confirm_ewallet.equals("")) {
					Toast.makeText(context, "Confirm Ewallet Password",
							Toast.LENGTH_SHORT).show();
				} else if (!ewallet.equals(confirm_ewallet)) {
					Toast.makeText(context, "Ewallet password not match",
							Toast.LENGTH_SHORT).show();
				}else if(side.equals("")){
					Toast.makeText(context, "Please Select side",
							Toast.LENGTH_SHORT).show();
				} else {

					String url = "http://recharge.mlm4india.com/Registration.aspx?MenuID=R&PinNo="
							+ pinno
							+ "&SponsorID="
							+ sponserid
							+ "&MemberName="
							+ name
							+ "&Mobile="
							+ mobile
							+ "&Email="
							+ email
							+ "&Password="
							+ pass
							+ "&Side=" + side + "&EwalletPassword=" + ewallet;

					// String url =
					// "http://back.agrihub.biz/registration.aspx?MenuId=LL&pinno="
					// + pinno
					// + "&PlacementID="
					// + placementid
					// + "&SponsorID="
					// + sponserid
					// + "&MemberName="
					// + name
					// + "&Mobile="
					// + mobile
					// + "&Email="
					// + email
					// + "&Password="
					// + pass
					// + "&EwalletPassword="
					// + ewallet;
					// String url =
					// "http://agrihub.biz/MAgriHub.aspx?MenuId=LL&pinno="
					// + pinno
					// + "&PlacementID="
					// + placementid
					// + "&SponsorID="
					// + sponserid
					// + "&MemberName="
					// + name
					// + "&Mobile="
					// + mobile
					// + "&Email="
					// + email
					// + "&Password="
					// + pass
					// + "&EwalletPassword="
					// + ewallet;
					getResult = new GetResult();
					getResult.execute(url);
				}
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
				String[] line1 = line.split(":");
				line = line1[0];
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
				if (line.trim().equalsIgnoreCase("1")) {
					showAlertDialog(context, "Success",
							"Successfuly Registred", true);

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

}
