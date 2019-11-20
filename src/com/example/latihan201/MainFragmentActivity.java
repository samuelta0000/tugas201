package com.example.latihan201;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class MainFragmentActivity extends Activity {
	ActionBar.Tab TabInbox, TabOutbox;
	Fragment fragmentTabInbox = new FragmentInbox();
	Fragment fragmentTabOutbox = new FragmentOutbox();
	String contactin,contact_name;

	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_fragment);
		
		Intent intent=getIntent();
		contactin=intent.getStringExtra("id");
		contact_name=intent.getStringExtra("name");
		
		Bundle bundle=new Bundle();
		bundle.putString("id", contactin);
		bundle.putString("name", contact_name);
		fragmentTabInbox.setArguments(bundle);
		fragmentTabOutbox.setArguments(bundle);
		
        ActionBar actionBar = getActionBar();
        getActionBar().setTitle(contact_name);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        TabInbox = actionBar.newTab().setIcon(R.drawable.ic_launcher).setText("Tab Inbox");
        TabOutbox = actionBar.newTab().setIcon(R.drawable.ic_launcher).setText("Tab Outbox");
        
        TabInbox.setTabListener(new TabListener(fragmentTabInbox));
        TabOutbox.setTabListener(new TabListener(fragmentTabOutbox));
		
		actionBar.addTab(TabInbox);
		actionBar.addTab(TabOutbox);

	}
 



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    


}
