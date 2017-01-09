package com.dnshoppee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TicketMenu1 extends ActionBarActivity implements OnClickListener {

	Button ticketIssue, currentTicket, closeTicket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_menu);

		ticketIssue = (Button) findViewById(R.id.btn_ticket_issue_1);
		currentTicket = (Button) findViewById(R.id.btn_current_ticket_1);
		closeTicket = (Button) findViewById(R.id.btn_close_ticket_1);

		ticketIssue.setOnClickListener(this);
		closeTicket.setOnClickListener(this);
		currentTicket.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_ticket_issue_1:
			startActivity(new Intent(getApplicationContext(), RaiseTicket.class));
			break;
		case R.id.btn_current_ticket_1:
			startActivity(new Intent(getApplicationContext(),
					CurrentTicket.class));
			break;
		case R.id.btn_close_ticket_1:
			startActivity(new Intent(getApplicationContext(), CloseQuery.class));
			break;

		default:
			break;
		}
	}

}
