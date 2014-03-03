package com.example.cardsvspeople;

import java.util.jar.Attributes.Name;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity{// implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " created." );
		Button startButton = (Button)findViewById(R.id.game_start_button);
		startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Use an intent to start game Activity, and pass user's game name
				//Fetch game name from user class, or server
				String gameName = "";
				Intent intent = new Intent(MenuActivity.this,GameActivity.class);
				intent.putExtra("name", gameName);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	protected void OnStart()
	{
		//super.onStart();
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " started." );
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " paused." );
	}
	@Override
	public void onResume() 
	{
		super.onResume();
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " resumed." );
	}
//	@Override
//	public void onClick(View v) {
//		
//		// TODO Auto-generated method stub
//		if(View.equals()
//		{
//			
//		}
//		
//	}

}
