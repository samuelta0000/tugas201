package com.example.latihan201;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactAdd extends Activity {

	private String TAG = ContactAdd.class.getSimpleName();
    private ProgressDialog pDialog;
 
    // URL to get contacts JSON
    private static String url = "http://apilearning.totopeto.com/contacts";
    TextView tname, temail, tphone,taddress,tdob;
    Button btnkirim,btnkembali;
    
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.add_contact);
    	
    	tname = (TextView) findViewById(R.id.etname);
		temail = (TextView) findViewById(R.id.etemail);
		tphone = (TextView) findViewById(R.id.etphone);
		taddress = (TextView) findViewById(R.id.etaddress);
		tdob = (TextView) findViewById(R.id.etdob);
		btnkirim=(Button) findViewById(R.id.kirim);
		btnkembali=(Button) findViewById(R.id.kembali);
		
		btnkirim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AddContact().execute();
				Intent intent=new Intent(ContactAdd.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		btnkembali.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intentcancel=new Intent(ContactAdd.this,MainActivity.class);
				startActivity(intentcancel);
				finish();
			}
		});
		
		
    }

    private class AddContact extends AsyncTask<Void, Void, Void> {
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ContactAdd.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
 
            // Making a request to url and getting response
            String post_params = null;
 
            JSONObject params=new JSONObject();
 
            // Read JSON
                try {
                    params.put("name", tname.getText().toString());
                    params.put("address", taddress.getText().toString());
                    params.put("phone", tphone.getText().toString());
                    params.put("email", temail.getText().toString());
                    params.put("dob", tdob.getText().toString());
                    post_params=params.toString();
                
                } catch (JSONException e) {
                  e.printStackTrace();
                }
            
                HttpHandler data=new HttpHandler();
                String jsonstr=data.makePostRequest(url, post_params);
                Log.e(TAG, "Json parsing error: " + jsonstr);
            return null;
        }
 
    }   

}
