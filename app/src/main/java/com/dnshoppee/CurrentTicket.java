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

public class CurrentTicket extends Activity {

	static final String KEY_TicketID = "TicketID";
	static final String KEY_Subject = "Subject";
	static final String KEY_Description = "Description";
	static final String KEY_Table = "Table"; // parent node
	ListView LvRecord;

	private SharedPreferences preferences;
	private String URL;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_ticket);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		LvRecord = (ListView) findViewById(R.id.list_currentTicket);

		URL = "http://dnshoppee.com/MAndroid.aspx?MenuID=K&GID="
				+ preferences.getString(Login._name, "");
		// URL = "http://globalappads.com/mglobalandroid.aspx?GID="
		// + preferences.getString(Login._name, "") + "&MenuID=P";

		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(URL);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(CurrentTicket.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Warning", "Time Out, Try Again !!!",
					false);
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(CurrentTicket.this, "Time Out, Try Again !!!",
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
			ArrayList<String> list4 = new ArrayList<String>();
			ArrayList<String> list5 = new ArrayList<String>();

			list1.add("TicketID");
			list4.add("Subject");
			list5.add("Description");

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				list1.add(parser.getValue(e, KEY_TicketID));
				// list2.add("globalappads");
				list4.add(parser.getValue(e, KEY_Subject));
				list5.add(parser.getValue(e, KEY_Description));
				Log.v("list1", parser.getValue(e, KEY_Description));
			}

			ReportClassfor3 adapter = new ReportClassfor3(CurrentTicket.this,
					list1, list4, list5);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
