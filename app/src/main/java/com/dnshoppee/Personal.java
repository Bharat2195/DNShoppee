package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Personal extends ActionBarActivity implements OnClickListener {

	Button editProfile, changePass, changeTpass, downline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);

		editProfile = (Button) findViewById(R.id.btn_edit_profile_1);
		changePass = (Button) findViewById(R.id.btn_change_pass_1);
		changeTpass = (Button) findViewById(R.id.btn_change_t_pass_1);
		downline = (Button) findViewById(R.id.btn_downline_1);

		editProfile.setOnClickListener(this);
		changePass.setOnClickListener(this);
		changeTpass.setOnClickListener(this);
		downline.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_edit_profile_1:
			startActivity(new Intent(getApplicationContext(), EditProfile.class));
			break;
		case R.id.btn_change_pass_1:
			startActivity(new Intent(getApplicationContext(), ChangePass.class));
			break;
		case R.id.btn_change_t_pass_1:
			startActivity(new Intent(getApplicationContext(), ChangeTpass.class));
			break;
		case R.id.btn_downline_1:
			startActivity(new Intent(getApplicationContext(), MyDownline.class));
			break;

		default:
			break;
		}
	}
}
