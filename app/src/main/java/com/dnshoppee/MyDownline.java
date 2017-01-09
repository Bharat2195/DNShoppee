package com.dnshoppee;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MyDownline extends Activity {

	static final String KEY_MemberID = "MemberID";
	static final String KEY_MemberName = "MemberName";
	static final String KEY_CoAppNo = "JoiningDate";
	static final String KEY_EntryDate = "ActivationDate";
	static final String KEY_ActivationDate = "PackageName";
	static final String KEY_Amount = "Downline_x0020_Member";

	static final String KEY_Table = "Table"; // parent node
	ListView LvRecord;

	private SharedPreferences preferences;
	private String URL;
	private Context context = this;
	private String plan = "Silver";
	private String side = "right";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_statement);
		preferences = getSharedPreferences(Login.MYPREFERENCES,
				Context.MODE_PRIVATE);

		LvRecord = (ListView) findViewById(R.id.list_sponserDetails);
		//showAskDialog(context, "", "", true);
		updateView();

	}
	
	public void updateView(){
		
		URL = "http://dnshoppee.com/MAndroid.aspx?MenuID=I&GID="+preferences.getString(Login._name, "");

		XMLParser1 parser = new XMLParser1();
		String xml = parser.getXmlFromUrl(URL);
		Log.d("XML", xml);
		if (xml.trim().equalsIgnoreCase("No Data Found")) {
			Toast.makeText(MyDownline.this, "No Data Found",
					Toast.LENGTH_LONG).show();
			showAlertDialog(context, "Warning", "Time Out, Try Again !!!",
					false);
			Log.d("XML", "DOCNo Data Found");
		} else if (xml.equals(" ")) {
			Toast.makeText(MyDownline.this, "Time Out, Try Again !!!",
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

			list1.add("MemberID");
			list2.add("MemberName");
			list3.add("JoiningDate");
			list4.add("ActivationDate");
			list5.add("PackageName");
			list6.add("Members");

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				list1.add(parser.getValue(e, KEY_MemberID));
				// list2.add("globalappads");
				list2.add(parser.getValue(e, KEY_MemberName));
				list3.add(parser.getValue(e, KEY_CoAppNo));
				list4.add(parser.getValue(e, KEY_EntryDate));
				list5.add(parser.getValue(e, KEY_ActivationDate));
				list6.add(parser.getValue(e, KEY_Amount));

				Log.v("list1", parser.getValue(e, KEY_MemberID));
			}

			ReportClassfor6 adapter = new ReportClassfor6(
					MyDownline.this, list1, list2, list3, list4, list5, list6);
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
	@SuppressWarnings("deprecation")
	public void showAskDialog(Context context, String title, String message,
			Boolean status) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();

		// Setting Dialog Title
		alertDialog.setTitle("Direct Downline");

		// Setting Dialog Message
		alertDialog.setMessage("Choose Plan");

		// Setting OK Button
		alertDialog.setButton(Dialog.BUTTON1,"Silver", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				plan = "Silver";
				// finish();
				updateView();
			}
		});
		alertDialog.setButton(Dialog.BUTTON2,"Gold", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				plan = "Gold";
				// finish();
				updateView();
			}
		});
		alertDialog.setButton(Dialog.BUTTON3,"Platinum", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
				// finish();
				plan = "Platinum";
				updateView();
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
