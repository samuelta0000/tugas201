
package com.example.latihan201;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FragmentInbox extends Fragment{
	

    String TAG=MainFragmentActivity.class.getSimpleName();
    ProgressDialog pDialog;
    String url="http://apilearning.totopeto.com/messages/inbox?id=";
    ArrayList<HashMap<String,String>> inboxList;
    TextView tinbox;
    Button pbpesan;
    ListView linbox;
    String inbox_count;
    String contactin,contactn;
    
    

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentinbox, container, false);
		
        tinbox=(TextView)rootView.findViewById(R.id.tvinbox);
        linbox=(ListView)rootView.findViewById(R.id.lvinbox);
        pbpesan=(Button)rootView.findViewById(R.id.btntlspesan);
        
        contactin=getArguments().getString("id");
        contactn=getArguments().getString("name");
        
        pbpesan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FragmentInbox.this.getActivity(),PesanBaru.class);
				intent.putExtra("name", contactn);
				intent.putExtra("id", contactin);
				
				startActivity(intent);
				
			}
		});
        
        linbox.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this, "Tested!", Toast.LENGTH_SHORT).show();
				HashMap<String, String> hm =inboxList.get(arg2);
				
				//Intent intentContactDetails = new Intent(MainActivity.this, ContactDetails.class);
				Intent intent=new Intent(getActivity(),BalasPesan.class);
				//intentContactDetails.putExtra("id", hm.get("id"));
				intent.putExtra("id", hm.get("id"));
				intent.putExtra("from_id", hm.get("from_id"));
				intent.putExtra("content", hm.get("content"));
				intent.putExtra("from", hm.get("from"));
				intent.putExtra("contactin", contactin);
				//startActivity(intentContactDetails);			
				startActivity(intent);
			}
		});
        
        inboxList=new ArrayList<HashMap<String,String>>();
        
        return rootView;

  }
	
	private class GetInboxs extends AsyncTask<Void, Void, Void> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @SuppressLint("SimpleDateFormat") @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
 
            // Making a request to curl and getting response
            String jsonStr = sh.makeServiceCall(url+contactin);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            // Read JSON
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    inbox_count=jsonObj.getString("total");
                    
                    JSONArray data = jsonObj.getJSONArray("data");
                    // looping through All data
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);

                        String id=c.getString("id");
                        String from_id=c.getString("from_id");
                        String content = c.getString("content");
                        
                        String date=c.getString("created_at");
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                        Date dated=format.parse(date);
                        String created_at=format.format(dated);
                        
                        
                        String from = c.getString("from");
 
                        // tmp hash map for single inboxList
                        HashMap<String, String> inbox= new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        inbox.put("id", id);
                        inbox.put("from_id", from_id);
                        inbox.put("content", content);
                        inbox.put("created_at", created_at);
                        inbox.put("from", from);
 
                        // adding inboxList to inboxList list
                        
                        inboxList.add(inbox);
                    }
                    
                    
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(getActivity(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                
                } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                Log.e(TAG, "Couldn't get json from server.");
 
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
            tinbox.setText(inbox_count);
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), inboxList,
                    R.layout.list_inbox, new String[]{"content", "created_at",
                    "from"}, new int[]{R.id.content,
                    R.id.created_at, R.id.from});
 
            linbox.setAdapter(adapter);
        }
 
    }
	public void onResume(){
	 	   super.onResume();
	 	   new GetInboxs().execute();
	    }
}
