package com.example.cardsvspeople;

import java.util.ArrayList;




import org.lucasr.twowayview.TwoWayView;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " created." );
		String userName = getIntent().getStringExtra("name");
		
		
		//Load some data into the game
		Game currGame = new Game();
		currGame.CreateGame();
		currGame.StartRound();
		//Get hand for a specific player for display purposes
		//Also get current black card text for display
		Round currRound = currGame.getCurrentRound();
		/*
		 * Display dealer view if the player is the dealer.
		 */
		if (currGame.getDealer().getUserName().equals(userName))
		{
			
		}
		else 
		{
			//Show game view
			setContentView(R.layout.activity_game);
			TextView blackcardtext = (TextView) findViewById(R.id.black_card);
			blackcardtext.setText(currRound.getBlackCard().getText());
			blackcardtext.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.black_card)
					{
						Log.d("Black Card", "Dialog should be launched");
						//Launch popup window showing card details 
						View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
						TextView temp = (TextView) findViewById(R.id.black_card);
						TextView tView = (TextView) v1.findViewById(R.id.card_fullview);
						tView.setText(temp.getText().toString());
						tView.setTextColor(getResources().getColor(R.color.white));
						tView.setBackgroundColor(getResources().getColor(R.color.black));
						
						Log.d("Black Card", "View prepared");
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
						alertDialogBuilder.setView(v1);
						alertDialogBuilder
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.cancel();
										}
									});
						Log.d("Black Card", "Positive button set");
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
						WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					    lp.copyFrom(alertDialog.getWindow().getAttributes());
					    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
					    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		 
						// show it
						alertDialog.show();
						Log.d("Black Card", "View showing");
					}
					
				}
			});
		
			int numCards = currRound.getBlackCard().getCardsNeeded();
			ArrayList<WhiteCard> playerhand = new ArrayList<WhiteCard>();
			playerhand = Game.players.get(0).getHand();
			ArrayList<String> playerhandtext = new ArrayList<String>();
			for (int x = 0; x< playerhand.size();x++)
			{
				playerhandtext.add(playerhand.get(x).getText());
			}
			//Setting listview adapter and reaction to selection
			final String[] handarray = playerhandtext.toArray(new String[playerhand.size()]);
			
			TwoWayView listView = (TwoWayView) findViewById(R.id.userhandlist);
			listView.setAdapter(new UserHandAdapter(getApplicationContext(), handarray));
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					//Log.d("Card selected", "Dialog should be launched");
					
					//Launch popup window showing card details 
					//LayoutInflater li = LayoutInflater.from(getBaseContext());
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
					View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
					
					TextView tView = (TextView) v1.findViewById(R.id.card_fullview);
					tView.setText(handarray[arg2]);
					
					alertDialogBuilder.setView(v1);
					//Log.d("Textview", handarray[arg2]);
					//alertDialogBuilder.setMessage("Test message");
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
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				    lp.copyFrom(alertDialog.getWindow().getAttributes());
				    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
				    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
				 
					// show it
					alertDialog.show();
				
					
				}
			});;
			
		}
		
		
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
