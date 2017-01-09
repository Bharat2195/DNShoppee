package com.dnshoppee;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class AccountStatement extends Activity {

	static final String KEY_Date = "Date";
	static final String KEY_Details = "Details";
	static final String KEY_Credit = "Credit";
	static final String KEY_Debit = "Debit";
	static final String KEY_Total_x0020_Balance = "Total_x0020_Balance";

	static final String KEY_Table = "Table"; // parent node
	ListView LvRecord;

	private SharedPreferences preferences;
	private String URL;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_statement);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		LvRecord = (ListView) findViewById(R.id.list_sponserDetails);

		URL = "http://dnshoppee.com/MAndroid.aspx?MenuID=J&GID="
				+ preferences.getString(Login._name, "");
		// URL = "http://agrihub.biz/MAgriHub.aspx?GID="
		// + preferences.getString(Login._name, "") + "&MenuID=L";
		// URL = "http://globalappads.com/mglobalandroid.aspx?GID=" + 801649433
		// + "&MenuID=I";
		// URL="http://hopeworld.in/(S(uid5bgb1lbuscq45mahnto45))/MHopeAndroid.aspx?mid="+preferences.getString(MainActivity._name,
		// "")+"&MenuID=G";

		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(URL);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(AccountStatement.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Warning", "Time Out, Try Again !!!",
					false);
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(AccountStatement.this, "Time Out, Try Again !!!",
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

			list1.add("Date");
			list2.add("Details");
			list3.add("Credit");
			list4.add("Debit");
			list5.add("Balance");

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				list1.add(parser.getValue(e, KEY_Date));
				// list2.add("globalappads");
				list2.add(parser.getValue(e, KEY_Details));
				double temp = Double.valueOf(parser.getValue(e, KEY_Credit));
				list3.add(String.valueOf(temp));
				temp = Double.valueOf(parser.getValue(e, KEY_Debit));
				list4.add(String.valueOf(temp));
				temp = Double.valueOf(parser.getValue(e,
						KEY_Total_x0020_Balance));
				list5.add(String.valueOf(temp));

				Log.v("list1", parser.getValue(e, KEY_Date));
			}

			CustomList_Payment_History5 adapter = new CustomList_Payment_History5(
					AccountStatement.this, list1, list2, list3, list4, list5);
			LvRecord.setAdapter(adapter);

		}

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
