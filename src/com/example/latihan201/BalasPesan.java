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
import android.widget.EditText;
import android.widget.TextView;

public class BalasPesan extends Activity {
	
	String TAG=BalasPesan.class.getSimpleName();
	ProgressDialog pDialog;
	String url_kirimpb="http://apilearning.totopeto.com/messages";
	String id,id_from,id_to,title,pesanmasuk,name_to;
	Button bkirim,bbatal;
	TextView tfrom;
	EditText inbox,balasan;

	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.balas_pesan);
		
		bkirim=(Button)findViewById(R.id.btnkirim);
		bbatal=(Button)findViewById(R.id.btnbatal);
		tfrom=(TextView)findViewById(R.id.tvfrom);
		inbox=(EditText)findViewById(R.id.etpesanmasuk);
		balasan=(EditText)findViewById(R.id.etbalasan);
		
		Intent intent=getIntent();
		title=intent.getStringExtra("from");
		pesanmasuk=intent.getStringExtra("content");
		id_from=intent.getStringExtra("contactin");
		
		inbox.setText(pesanmasuk);
		tfrom.setText(title);
		setTitle(title);
		
		bbatal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BalasPesan.this,MainFragmentActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		bkirim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new KirimPesan().execute();
				Intent intent=new Intent(BalasPesan.this,FragmentInbox.class);
				intent.putExtra("id", id_from);
				intent.putExtra("name", title);
				startActivity(intent);
			}
		});
	}
	
	private class KirimPesan extends AsyncTask<Void, Void, Void> {
	   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BalasPesan.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	
        	Intent intent=getIntent();
    		id_from=intent.getStringExtra("contactin");
    		id_to=intent.getStringExtra("from_id");
 
            // Making a request to url and getting response
            String post_params = null;
 
            JSONObject params=new JSONObject();
 
            // Read JSON
                try {
                    params.put("from_id", id_from);
                    params.put("to_id",id_to );
                    params.put("content", balasan.getText().toString());
                    post_params=params.toString();
                
                } catch (JSONException e) {
                  e.printStackTrace();
                }
            
                HttpHandler dato=new HttpHandler();
                String jsonstr=dato.makePostRequest(url_kirimpb, post_params);
                Log.e(TAG, "Json parsing error: " + jsonstr);
            return null;
        }
 
	}
	
			
}
