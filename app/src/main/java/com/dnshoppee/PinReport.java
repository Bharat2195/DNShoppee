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

public class PinReport extends Activity {

	static final String KEY_PinNo = "PinNo";
	static final String KEY_PackageName = "PackageName";
	static final String KEY_Registered_x0020_To = "Registered_x0020_To";
	static final String KEY_Registration_x0020_On = "Registration_x0020_On";

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

		URL = "http://dnshoppee.com/MAndroid.aspx?MenuID=H&GID="+ preferences.getString(Login._name, "");

		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(URL);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(PinReport.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Warning", "Time Out, Try Again !!!",
					false);
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(PinReport.this, "Time Out, Try Again !!!",
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

			list1.add("PinNo");
			list2.add("PackageName");
			list3.add("Registred to");
			list4.add("Register date");

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				list1.add(parser.getValue(e, KEY_PinNo));
				// list2.add("globalappads");
				list2.add(parser.getValue(e, KEY_PackageName));
				list3.add(parser.getValue(e, KEY_Registered_x0020_To));
				list4.add(parser.getValue(e, KEY_Registration_x0020_On));
			}

			ReportClassfor4 adapter = new ReportClassfor4(
					PinReport.this, list1, list2, list3, list4);
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
