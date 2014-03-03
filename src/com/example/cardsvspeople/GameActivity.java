package com.example.cardsvspeople;

import android.net.sip.SipErrorCode;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " created." );
		setContentView(R.layout.activity_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	@Override
	protected void onStart()
	{
		super.onStart();
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
	protected void onResume()
	{
		super.onResume();
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " resumed." );
	}

}
