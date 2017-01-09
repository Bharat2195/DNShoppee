package com.dnshoppee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {
	SharedPreferences preferences;

	@SuppressWarnings("deprecation")
	SmsManager smsManager = SmsManager.getDefault();

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context con, Intent intent) {
		// TODO Auto-generated method stub

		final Bundle bundle = intent.getExtras();
		try {
			final Object[] pduObject = (Object[]) bundle.get("pdus");
			if (bundle != null) {
				for (int i = 0; i < pduObject.length; i++) {
					SmsMessage message = SmsMessage
							.createFromPdu((byte[]) pduObject[i]);
					String phoneNumber = message.getDisplayOriginatingAddress();
					String messageCuntent = message.getDisplayMessageBody();
					String temp[] = null;

					if (phoneNumber.equals("BW-DNShop")) {
						temp = messageCuntent.split("-");
						if (messageCuntent.contains("OK")
								|| messageCuntent.contains("Sucess")
								|| messageCuntent.contains("success")) {
							Intent intent2 = new Intent(con, Login.class);
							intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent2.putExtra("sms", temp[1]);
							con.startActivity(intent2);
						} else if (messageCuntent.contains("Problem")) {
							Toast.makeText(con.getApplicationContext(),
									"There is some Problem", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(con.getApplicationContext(),
									temp[0], Toast.LENGTH_SHORT).show();
						}
					}
					Log.i("com.dnsh", phoneNumber + "" + messageCuntent);
					Toast.makeText(con, phoneNumber + "" + messageCuntent,
							Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			Log.i("com.dnsh", e.getMessage());
		}

	}

}
