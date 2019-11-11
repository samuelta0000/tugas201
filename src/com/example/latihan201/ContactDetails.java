package com.example.latihan201;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.widget.Toast;

	
	public class ContactDetails extends Activity {
		
	    private String TAG = ContactDetails.class.getSimpleName();
	    private ProgressDialog pDialog;
	 
	    // URL to get contacts JSON
	    private static String url = "http://apilearning.totopeto.com/contacts/";
	 
	    ArrayList<HashMap<String, String>> contactList;
	    
	    Button btnkembali;
        TextView tname, taddress, temail, tphone, tdob, tcreated, tupdated;
        
        String name,address,email,phone,dob;
	    
	    protected void onCreate(Bundle savedInstanceState){
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.activity_contact_details);
	    	
	    	tname = (TextView) findViewById(R.id.tvname);
    		taddress = (TextView) findViewById(R.id.tvaddressv);
    		temail = (TextView) findViewById(R.id.tvemailv);
    		tphone = (TextView) findViewById(R.id.tvphonev);
    		tdob = (TextView) findViewById(R.id.tvdobv);
    		btnkembali=(Button) findViewById(R.id.kembali);
    		
    		new GetContacts().execute();
    		
    		btnkembali.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intentcancel=new Intent(ContactDetails.this,MainActivity.class);
					startActivity(intentcancel);
				}
			});
	    }

	    private class GetContacts extends AsyncTask<Void, Void, Void> {
	    	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(ContactDetails.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setCancelable(false);
	            pDialog.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	            HttpHandler sh = new HttpHandler();
	            
	            Intent intent = getIntent();
	    		String id = intent.getStringExtra("id");
	    		
	 
	            // Making a request to url and getting response
	            String jsonStr = sh.makeServiceCall(url+id);
	 
	            Log.e(TAG, "Response from url: " + jsonStr);
	 
	            // Read JSON
	            if (jsonStr != null) {
	            	
	                try {
	                    JSONObject jsonObj = new JSONObject(jsonStr);
	 
	                    // Getting JSON Array node
	                    JSONObject c = jsonObj.getJSONObject("contact");
	                 
	                    name=c.getString("name");
	                    address=c.getString("address");
	                    email=c.getString("email");
	                    phone=c.getString("phone");
	                    dob=c.getString("dob");
	                    
	                
	                } catch (final JSONException e) {
	                    Log.e(TAG, "Json parsing error: " + e.getMessage());
	                    runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                            Toast.makeText(getApplicationContext(),
	                                    "Json parsing error: " + e.getMessage(),
	                                    Toast.LENGTH_LONG)
	                                    .show();
	                        }
	                    });
	                }
	            } else {
	                Log.e(TAG, "Couldn't get json from server.");
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                        Toast.makeText(getApplicationContext(),
	                                "Couldn't get json from server. Check LogCat for possible errors!",
	                                Toast.LENGTH_LONG)
	                                .show();
	                    }
	                });
	 
	            }
	 
	            return null;
	        }
	 
	        
	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            // Dismiss the progress dialog
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	            /**
	             * Updating parsed JSON data into TextView
	             * */	    		
	    		tname.setText(name);
	    		taddress.setText(address);
	    		temail.setText(email);
	    		tphone.setText(phone);
	    		tdob.setText(dob);
	    
	        }
	 
	    }   

	
}
