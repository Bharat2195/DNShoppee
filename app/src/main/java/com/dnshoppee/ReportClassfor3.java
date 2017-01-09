package com.dnshoppee;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class ReportClassfor3 extends ArrayAdapter<String> {
	Activity context;
	public final ArrayList<String> web1;
	public final ArrayList<String> web2;
	public final ArrayList<String> web3;

	public ReportClassfor3(Activity context, ArrayList<String> al1, ArrayList<String> al2, ArrayList<String> al3) {
		super(context, R.layout.reportfor3, al1);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.web1 = al1;
		this.web2 = al2;
		this.web3 = al3;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rootview = inflater.inflate(R.layout.reportfor3, null,true);
		
		TextView tx1 = (TextView) rootview.findViewById(R.id.r80);
		TextView tx2 = (TextView) rootview.findViewById(R.id.r81);
		TextView tx3 = (TextView) rootview.findViewById(R.id.r82);
		
		if(position == 0){
			tx1.setBackgroundResource(R.color.Pink);
			tx2.setBackgroundResource(R.color.Pink);
			tx3.setBackgroundResource(R.color.Pink);
		}
		
		tx1.setText(web1.get(position));
		tx2.setText(web2.get(position));
		tx3.setText(web3.get(position));
		return rootview;
		
	}

}
