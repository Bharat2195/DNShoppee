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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditProfile extends Activity {
	String url, getDetailurl;
	SharedPreferences preferences;
	RadioButton r1, r2;
	String address, city, state, email, pincode, number, dat, sex;
	EditText addressE, cityE, stateE, emailE, pincodeE, numberE;
	Button reset, cancel, datE;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	ProgressDialog _pDialog;
	GetResult updateProfile;
	protected Context _context = this;
	int year, month, day;
	Calendar cal;
	Context context = this;
	XMLParser1 parser2 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		addressE = (EditText) findViewById(R.id.edit_address);
		cityE = (EditText) findViewById(R.id.edit_city);
		stateE = (EditText) findViewById(R.id.edit_state);
		emailE = (EditText) findViewById(R.id.edit_email);
		pincodeE = (EditText) findViewById(R.id.edit_pincode);
		numberE = (EditText) findViewById(R.id.edit_number);
		datE = (Button) findViewById(R.id.edit_date);
		reset = (Button) findViewById(R.id.btn_resetProfile);
		cancel = (Button) findViewById(R.id.btn_cancelProfile);
		r1 = (RadioButton) findViewById(R.id.r1);
		r2 = (RadioButton) findViewById(R.id.r2);

		getDetailurl = "http://dnshoppee.com/MAndroid.aspx?MenuID=A&GID="
				+ preferences.getString(Login._name, "");
		Log.i("getdetailurl",getDetailurl);

		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		datE.setText(year + "-" + month + "-" + day);
		
		parser2 = new XMLParser1();
		String xml = parser2.getXmlFromUrl(getDetailurl);
		Log.i("xml", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(EditProfile.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(EditProfile.this, "Time Out, Try Again !!!",
					Toast.LENGTH_LONG).show();
			Log.d("XML", "DOCNo Data Found");
		} else {
			Document dom = parser2.getDomElement(xml);
			NodeList nl = dom.getElementsByTagName("Table");
			Element e = (Element) nl.item(0);
			city = parser2.getValue(e, "city");
			address = parser2.getValue(e, "Address");
			state = parser2.getValue(e, "state");
			email = parser2.getValue(e, "email");
			pincode = parser2.getValue(e, "pincode");
			number = parser2.getValue(e, "Mobile");
			dat = parser2.getValue(e, "DOB");
			sex = parser2.getValue(e, "sex");
			cityE.setText(city);
			addressE.setText(address);
			stateE.setText(state);
			emailE.setText(email);
			pincodeE.setText(pincode);
			numberE.setText(number);
			datE.setText(dat);
			if (sex.equals("male")) {
				r1.setChecked(true);
			}
			if (sex.equals("female")) {
				r2.setChecked(true);
			}
		}

		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sex = "male";
			}
		});
		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sex = "female";
			}
		});

		datE.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(999);
			}
		});

		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				address = addressE.getText().toString().trim();
				address = address.replace(" ", "%20");
				city = cityE.getText().toString().trim();
				city = city.replace(" ", "%20");
				state = stateE.getText().toString().trim();
				state = state.replaceAll(" ", "%20");
				email = emailE.getText().toString().trim();
				email = email.replaceAll(" ", "%20");
				pincode = pincodeE.getText().toString().trim();
				number = numberE.getText().toString().trim();
				dat = datE.getText().toString().trim();
				dat = dat.replaceAll(" ", "%20");

				if (address.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter address",
							Toast.LENGTH_SHORT).show();
				} else if (city.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter city",
							Toast.LENGTH_SHORT).show();
				} else if (state.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter state",
							Toast.LENGTH_SHORT).show();
				} else if (email.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter email",
							Toast.LENGTH_SHORT).show();
				} else if (pincode.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter pincode",
							Toast.LENGTH_SHORT).show();
				} else if (pincode.length() != 6) {
					Toast.makeText(getApplicationContext(),
							"Enter Valid Pincode", Toast.LENGTH_SHORT).show();
				}  else if (dat.equals("")) {
					Toast.makeText(getApplicationContext(), "Enter date",
							Toast.LENGTH_SHORT).show();
				} else if (sex.equals("")) {
					Toast.makeText(getApplicationContext(), "Select gender",
							Toast.LENGTH_SHORT).show();
				} else {
					Log.i("address", address);
					Log.i("city", city);
					Log.i("state", state);
					Log.i("email", email);
					Log.i("pincode", pincode);
					Log.i("number", number);
					Log.i("dat", dat);
					Log.i("sex", sex);

					url = "http://dnshoppee.com/mANdroid.aspx?MenuID=B&GID="
							+ preferences.getString(Login._name, "")
							+ "&Address=" + address + "&City=" + city
							+ "&State=" + state + "&Email=" + email
							+ "&Pincode=" + pincode
							+ "&DOB=" + dat + "&sex=" + sex;

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

	private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {

		@SuppressLint("NewApi")
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			dat = arg1 + "-" + (arg2 + 1) + "-" + arg3;
			datE.setText(dat);
			arg0.setMaxDate(arg0.getDayOfMonth());
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 999) {
			DatePickerDialog dpg = new DatePickerDialog(this, mDateListener,
					year, month, day);
			return dpg;
		}
		return null;
	}

	public class GetResult extends AsyncTask<String, Void, String> {

		String line = null;
		String name1 = null;
		String temp;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			_pDialog = new ProgressDialog(EditProfile.this);
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
				if (line.trim().contains("Success")) {
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
				//alertDialog.cancel();
				finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
