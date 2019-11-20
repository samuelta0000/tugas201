package com.example.latihan201;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PesanBaru extends Activity implements OnItemSelectedListener {
	
	String TAG=MainFragmentActivity.class.getSimpleName();
	ProgressDialog pDialog;
	String url_contacts="http://apilearning.totopeto.com/contacts";
	String url_kirimpb="http://apilearning.totopeto.com/messages";
	ArrayList<HashMap<String, String>> contactList;
	List<String> names=new ArrayList<String>();
	Button btncancelpb,btnkirimpb;
	Spinner spinpb;
	String id_to,id_from,title;
	EditText etpb;
	
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.pesan_baru);
		
		btncancelpb=(Button)findViewById(R.id.cancelpb);
		btnkirimpb=(Button)findViewById(R.id.kirimpb);
		spinpb=(Spinner)findViewById(R.id.spinner);
		etpb=(EditText)findViewById(R.id.etpesanbaru);
		
		Intent intent=getIntent();
		id_from=intent.getStringExtra("id");
		title=intent.getStringExtra("name");
		setTitle("dari : "+title);
		
		btncancelpb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PesanBaru.this,MainFragmentActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		spinpb.setOnItemSelectedListener(this);
		
		
		btnkirimpb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new KirimPesan().execute();
				Intent intent=new Intent(PesanBaru.this,MainFragmentActivity.class);
				intent.putExtra("id", id_from);
				intent.putExtra("name",title);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
		
		HashMap<String,String>hm=contactList.get(position);
		id_to=hm.get("id");
	}
	
	public void onNothingSelected(AdapterView<?> parent){
	}
	
	private class ListContacts extends AsyncTask<Void, Void, Void> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PesanBaru.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url_contacts);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
 
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
 
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        
                        String id = c.getString("id");
                        String name = c.getString("name");
 
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
 
                        // adding contact to contact list
                        contactList.add(contact);
                    }
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
             * Updating parsed JSON data into ListView
             * */
            
            for (int i = 0; i < contactList.size(); i++) {
            	HashMap<String,String>hm=contactList.get(i);
            	names.add(hm.get("name"));
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(PesanBaru.this,android.R.layout.simple_spinner_dropdown_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinpb.setAdapter(adapter);
        }
 
    }

	private class KirimPesan extends AsyncTask<Void, Void, Void> {
	   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PesanBaru.this);
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
                    params.put("from_id", id_from);
                    params.put("to_id", id_to);
                    params.put("content", etpb.getText().toString());
                    post_params=params.toString();
                
                } catch (JSONException e) {
                  e.printStackTrace();
                }
            
                HttpHandler data=new HttpHandler();
                String jsonstr=data.makePostRequest(url_kirimpb, post_params);
                Log.e(TAG, "Json parsing error: " + jsonstr);
            return null;
        }
 
    }

		
	public void onResume(){
	 	   super.onResume();
	 	   contactList=new ArrayList<HashMap<String,String>>();
	 	   new ListContacts().execute();
	    }

}
