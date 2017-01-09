package com.dnshoppee;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList_Payment_History5 extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> web1;
	private final ArrayList<String> web2;
	private final ArrayList<String> web3;
	private final ArrayList<String> web4;
	private final ArrayList<String> web5;

	public CustomList_Payment_History5(Activity context, ArrayList<String> AL1,
			ArrayList<String> AL2, ArrayList<String> AL3, ArrayList<String> AL4,ArrayList<String> AL5) {
		super(context, R.layout.list_accountstatement, AL1);
		this.context = context;
		this.web1 = AL1;
		this.web2 = AL2;
		this.web3 = AL3;
		this.web4 = AL4;
		this.web5 = AL5;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.account_statement_list,
				null, true);

		TextView txt1 = (TextView) rowView.findViewById(R.id.txt_EntryDate);
		TextView txt2 = (TextView) rowView.findViewById(R.id.txt_TransType);
		TextView txt3 = (TextView) rowView.findViewById(R.id.txt_Credit);
		TextView txt4 = (TextView) rowView.findViewById(R.id.txt_Debit);
		TextView txt5 = (TextView) rowView.findViewById(R.id.txt_txt11);
		if (position == 0) {
			txt1.setBackgroundResource(R.color.Pink);
			txt2.setBackgroundResource(R.color.Pink);
			txt3.setBackgroundResource(R.color.Pink);
			txt4.setBackgroundResource(R.color.Pink);
			txt5.setBackgroundResource(R.color.Pink);
			txt1.setText(web1.get(position));
			txt2.setText(web2.get(position));
			txt3.setText(web3.get(position));
			txt4.setText(web4.get(position));
			txt5.setText(web5.get(position));
		} else {
			// String s[]=web1.get(position).split("T");
			txt1.setText(web1.get(position));
			txt2.setText(web2.get(position));
			txt3.setText(web3.get(position));
			txt4.setText(web4.get(position));
			txt5.setText(web5.get(position));
		}
		return rowView;
	}
}