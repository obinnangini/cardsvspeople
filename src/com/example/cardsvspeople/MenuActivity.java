package com.example.cardsvspeople;

import java.util.jar.Attributes.Name;

import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " created." );
		Button startButton = (Button)findViewById(R.id.game_start_button);
		startButton.setOnClickListener(this);
		Button helpButton = (Button) findViewById(R.id.help_button);
		helpButton.setOnClickListener(this);
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
	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.game_start_button:
			{
				//Use an intent to start game Activity, and pass user's game name
				//Fetch game name from user class, or server
				String gameName = "Obinna";//For user view
				//String gameName = "Brandon";//To see dealer view
				Intent intent = new Intent(MenuActivity.this,PlayerLocation.class);
				intent.putExtra("name", gameName);
				startActivity(intent);
				break;
			}
			case R.id.help_button:
			{
				Log.d("Help Button", "Button pressed");
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
				alertDialogBuilder.setMessage(getResources().getString(R.string.help_message));
				alertDialogBuilder
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
				
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
			 
				// show it
				alertDialog.show();
				break;
			
			}
				
		}
	}

	

}
