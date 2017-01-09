package com.dnshoppee;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class RechargeReportList extends Activity {

	static final String KEY_Recharge_x0020_On = "Recharge_x0020_On";
	static final String KEY_Operator = "Operator";
	static final String KEY_Mobile_x0020_No = "Mobile_x0020_No.";
	static final String KEY_Amount = "Amount";
	static final String KEY_Commission = "Commission";
	static final String KEY_TransactionNo = "TransactionNo";

	static final String KEY_Table = "Table"; // parent node
	ListView LvRecord;

	private SharedPreferences preferences;
	private String URL;
	private Context context = this;
	String status, number, companyName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_statement);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		LvRecord = (ListView) findViewById(R.id.list_sponserDetails);

		Intent intn = getIntent();
		if (intn.hasExtra("status")) {
			status = intn.getExtras().getString("status");
		}
		if (intn.hasExtra("cname")) {
			companyName = intn.getExtras().getString("cname");
		}
		if (intn.hasExtra("number")) {
			number = intn.getExtras().getString("number");
		}

		URL = "http://dnshoppee.com/MAndroid.aspx?MenuID=E&GID="
				+ preferences.getString(Login._name, "") + "&Status=" + status
				+ "&CompanyName=" + companyName + "&MobileNo=" + number;

		Log.i("URL", URL);

		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(URL);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(RechargeReportList.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Warning", "Time Out, Try Again !!!",
					false);
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(RechargeReportList.this, "Time Out, Try Again !!!",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Server Error",
					"Timeout expired, Try Again !!!", false);
			Log.d("XML", "DOCNo Data Found");
		} else {
			Document doc = parser.getDomElement(xml);
			Log.d("dom", "doc");
			NodeList nl = doc.getElementsByTagName(KEY_Table);
			Log.d("XML", nl.getLength() + "");
			ArrayList<String> list1 = new ArrayList<String>();
			ArrayList<String> list2 = new ArrayList<String>();
			ArrayList<String> list3 = new ArrayList<String>();
			ArrayList<String> list4 = new ArrayList<String>();
			ArrayList<String> list5 = new ArrayList<String>();
			ArrayList<String> list6 = new ArrayList<String>();

			list1.add("Recharge On");
			list2.add("Operator");
			list3.add("Mobile No");
			list4.add("Amount");
			list5.add("Commission");
			list6.add("TransactionNo");

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				list1.add(parser.getValue(e, KEY_Recharge_x0020_On));
				// list2.add("globalappads");
				list2.add(parser.getValue(e, KEY_Operator));
				list3.add(parser.getValue(e, KEY_Mobile_x0020_No));
				list4.add(parser.getValue(e, KEY_Amount));
				list5.add(parser.getValue(e, KEY_Commission));
				list6.add(parser.getValue(e, KEY_TransactionNo));
			}

			ReportClassfor6 adapter = new ReportClassfor6(
					RechargeReportList.this, list1, list2, list3, list4, list5,
					list6);
			LvRecord.setAdapter(adapter);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				onBackPressed();
				alertDialog.cancel();
				
				// finish();

			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
