package com.dnshoppee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	Button _login, _forgot_pass, _register;
	protected String _user, _psw, _URL;
	protected TextView _Edt_UserName, _Edt_Password;
	protected CheckConnectivity _check;
	protected boolean _conn;
	protected Context _context = this;
	SharedPreferences _sharedpreferences;
	public static final String MYPREFERENCES = "LoginPref1";
	String TEMP = "";
	String TEMP1 = "";
	public static final String _name = "nameKey";
	public static final String _bal = "00.00";
	public static final String _pass = "passwordKey";
	public static final String _SponcerName = "sponcerName";
	public static final String _passsave = "no";
	public static final String _autologin = "no";
	protected GetResult _loginTask;
	RadioButton sms, internet;
	String Ifsms = "no";
	String phno = "7778900200";
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	ProgressDialog _pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		_login = (Button) findViewById(R.id.btn_login);
		_register = (Button) findViewById(R.id.btn_register);
		// _forgot_pass = (Button) findViewById(R.id.btn_forgot_pass);
		_Edt_Password = (TextView) findViewById(R.id.editText_Password);
		_Edt_UserName = (TextView) findViewById(R.id.editText_userID);
		sms = (RadioButton) findViewById(R.id.radio_sms);
		internet = (RadioButton) findViewById(R.id.radio_internet);
		_sharedpreferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		Intent i11 = getIntent();
		if (i11.hasExtra("sms")) {
			Editor editor = _sharedpreferences.edit();
			// editor.putString(_name, _user);
			// editor.putString(_pass, _psw);
			editor.putString(_bal, i11.getExtras().getString("sms"));
			editor.commit();
			finish();
			startActivity(new Intent(getApplicationContext(), Home1.class));
		}

		if (_sharedpreferences.getString(_passsave, "no").equals("yes")
				&& _sharedpreferences.getString(_autologin, "no").equals("yes")) {
			finish();
			startActivity(new Intent(getApplicationContext(), Home1.class));
		}

		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Ifsms = "sms";
			}
		});

		internet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Ifsms = "internet";
			}
		});

		if (_sharedpreferences.getString(Login._passsave, "no").equals("yes")) {

			_URL = "http://dnshoppee.com/MLogin.aspx?PWD="
					+ _sharedpreferences.getString(Login._pass, "") + "&UN="
					+ _sharedpreferences.getString(Login._name, "");
			Log.v("url", _URL);

			_check = new CheckConnectivity();
			_conn = _check.checknow(_context);
			if (_conn == true) {
				_loginTask = new GetResult();
				_loginTask.execute(_URL, "1");
			} else {
				showAlertDialog(Login.this, "No Internet Connection",
						"You don't have internet connection.", false);
			}
		}

		_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ForgotPassword.class);
				String n = _Edt_UserName.getText().toString().trim();
				if (n.equals("")) {
					Toast.makeText(_context, "Enter userID", Toast.LENGTH_SHORT)
							.show();
				} else {
					intent.putExtra("GID", n);
					startActivity(intent);
				}

			}
		});

		_login.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_user = _Edt_UserName.getText().toString().trim();
				_psw = _Edt_Password.getText().toString().trim();

				if (Ifsms.equals("no")) {
					showToast("Select one method");
				} else if (Ifsms.equals("sms")) {
					if (_user.equals("")) {
						showToast("Enter Username");
					} else if (_psw.equals("")) {
						showToast("Enter Password");
					} else {
						try {
							showAlertDialog2(_context, "", "", true);
							// Timer T = new Timer();
							// T.schedule(new TimerTask() {
							//
							// @Override
							// public void run() {
							// // TODO Auto-generated method stub
							// showToast("Message not received");
							// }
							// }, 30000);
						} catch (Exception e) {
							showToast("Message failed");
							Log.i("com.dnsh", e.getMessage());
						}

					}
				} else {
					showAlertDialog1(_context, "", "", false);
				}

			}
		});
	}

	protected void showToast(String string) {
		// TODO Auto-generated method stub
		Toast toast = Toast.makeText(Login.this, string, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public class GetResult extends AsyncTask<String, Void, String> {

		String line = null;
		String name1 = null;
		String temp;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			_pDialog = new ProgressDialog(Login.this);
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
				String[] s = line.split(":");
				line = s[0];
				name1 = s[1];
				temp = urls[1];
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
				_pDialog.dismiss();
				Log.d("line temp", line + " " + temp);
				if (line.equals("1") && temp.equals("0")) {
					Editor editor = _sharedpreferences.edit();
					editor.putString(_name, _user);
					editor.putString(_pass, _psw);
					editor.putString(_SponcerName, name1);
					editor.commit();
					_loginTask.cancel(true);
					Login.this.finish();
					Intent i = new Intent();
					i.setClass(Login.this, Home.class); // first
														// time
														// shows
														// welcome
					i.putExtra("ID", _user);
					startActivity(i);

				} else if (line.equals("0") && temp.equals("0")) {
					// l1.setVisibility(View.VISIBLE);

					_Edt_Password.setText("");
					_Edt_Password.requestFocus();
					showAlertDialog(_context, "Login Failed",
							"Your UserName And Password Not Matched !", false);
					_loginTask.cancel(true);

				} else if (line.equals("1") && temp.equals("1")) {

					_loginTask.cancel(true);
					Login.this.finish();
					Intent i = new Intent();
					i.setClass(Login.this, Home.class);
					i.putExtra("ID", _user);
					startActivity(i);

				} else if (line.equals("0") && temp.equals("1")) {
					TEMP = "0";
					TEMP1 = "1";
					showAlertDialog(_context, "Login Failed",
							"Your UserName And Password Not Matched !0 1",
							false);
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void showAlertDialog(Context context, String title, String message,
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

				alertDialog.cancel();
				if (TEMP.equals("0") && TEMP1.equals("1")) {
					Login.this.finish();
					_sharedpreferences = getSharedPreferences(
							Login.MYPREFERENCES, Context.MODE_PRIVATE);
					Editor editor = _sharedpreferences.edit();
					editor.clear();
					editor.commit();
					Intent i = new Intent();
					i.setClass(Login.this, Login.class);
					startActivity(i);

				}

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public class GetResult1 extends AsyncTask<String, Void, String> {

		String line = null;
		String name1 = null;
		String temp;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			_pDialog = new ProgressDialog(Login.this);
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
				String[] s = line.split(":");
				line = s[0];
				name1 = s[1];
				temp = urls[1];
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
				_pDialog.dismiss();
			}
			Toast.makeText(_context, "Message sent to your contact",
					Toast.LENGTH_LONG).show();
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
		alertDialog.setTitle("Save Password?");

		// Setting OK Button
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						_sharedpreferences = getSharedPreferences(
								Login.MYPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = _sharedpreferences.edit();
						editor.putString(Login._passsave, "yes");
						editor.commit();

						alertDialog.cancel();
						if (_user.equals("")) {
							showToast("Enter Username");
						} else if (_psw.equals("")) {
							showToast("Enter Password");
						} else {

							_URL = "http://dnshoppee.com/MLogin.aspx?PWD="
									+ _psw + "&UN=" + _user;
							// _URL =
							// "http://recharge.mlm4india.com/MLogin.aspx?UN="
							// + _user + "&Pwd=" + _psw;
							Log.v("url", _URL);

							_check = new CheckConnectivity();
							_conn = _check.checknow(_context);
							if (_conn == true) {
								_loginTask = new GetResult();
								_loginTask.execute(_URL, "0");
							} else {
								showAlertDialog(Login.this,
										"No Internet Connection",
										"You don't have internet connection.",
										false);
							}
						}
					}
				});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						alertDialog.cancel();
						if (_user.equals("")) {
							showToast("Enter Username");
						} else if (_psw.equals("")) {
							showToast("Enter Password");
						} else {

							_URL = "http://dnshoppee.com/MLogin.aspx?PWD="
									+ _psw + "&UN=" + _user;
							// _URL =
							// "http://recharge.mlm4india.com/MLogin.aspx?UN="
							// + _user + "&Pwd=" + _psw;
							Log.v("url", _URL);

							_check = new CheckConnectivity();
							_conn = _check.checknow(_context);
							if (_conn == true) {
								_loginTask = new GetResult();
								_loginTask.execute(_URL, "0");
							} else {
								showAlertDialog(Login.this,
										"No Internet Connection",
										"You don't have internet connection.",
										false);
							}
						}
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void showAlertDialog2(Context context, String title, String message,
			Boolean status) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog = new AlertDialog.Builder(context,
				AlertDialog.THEME_DEVICE_DEFAULT_DARK).create();
		// Setting Dialog Title
		alertDialog.setTitle("Auto Login?");
		alertDialog.setMessage("No need to Re-login.");

		// Setting OK Button
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						_sharedpreferences = getSharedPreferences(
								Login.MYPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = _sharedpreferences.edit();
						editor.putString(Login._passsave, "yes");
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phno, null, "D L " + _user
								+ " " + _psw, null, null);
						showToast("please wait for response");
						editor.putString(_passsave, "yes");
						editor.putString(_autologin, "yes");
						editor.putString(_name, _user);
						editor.putString(_pass, _psw);
						editor.commit();
						ProgressDialog p = new ProgressDialog(_context);
						p.setMessage("Loading ...");
						p.show();
						alertDialog.cancel();

					}
				});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						_sharedpreferences = getSharedPreferences(
								Login.MYPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = _sharedpreferences.edit();
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phno, null, "D L " + _user
								+ " " + _psw, null, null);
						showToast("please wait for response");
						editor.putString(_name, _user);
						editor.putString(_pass, _psw);
						editor.commit();
						ProgressDialog p = new ProgressDialog(_context);
						p.setMessage("Loading ...");
						p.show();

						alertDialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

}
