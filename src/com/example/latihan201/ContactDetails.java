package com.example.latihan201;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetails extends Activity {

	TextView tname, taddress, temail, tphone, tdob, tcreated, tupdated;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
		
		tname = (TextView) findViewById(R.id.tvname);
		taddress = (TextView) findViewById(R.id.tvaddressv);
		temail = (TextView) findViewById(R.id.tvemailv);
		tphone = (TextView) findViewById(R.id.tvphonev);
		tdob = (TextView) findViewById(R.id.tvdobv);
		tcreated = (TextView) findViewById(R.id.tvcreatedv);
		tupdated = (TextView) findViewById(R.id.tvupdatedv);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String address = intent.getStringExtra("address");
		String email = intent.getStringExtra("email");
		String phone = intent.getStringExtra("phone");
		String dob = intent.getStringExtra("dob");
		String created_at = intent.getStringExtra("created_at");
		String updated_at = intent.getStringExtra("updated_at");
		
		tname.setText(name);
		taddress.setText(address);
		temail.setText(email);
		tphone.setText(phone);
		tdob.setText(dob);
		tcreated.setText(created_at);
		tupdated.setText(updated_at);
	}
}
