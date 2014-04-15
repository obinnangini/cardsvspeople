package com.example.cardsvspeople;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;







import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.security.auth.PrivateCredentialPermission;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.lucasr.twowayview.TwoWayView;














import com.example.cardsvspeople.AsyncTasks.*;
import com.example.cardsvspeople.R.id;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Problem is, the values of these data structures arent being updated. I should return an instance of the Game class instead.
//FIX TOMORROW!!!!!!!!!!!!!!!!!!!!!
public class GameActivity extends Activity implements OnClickListener {

//	ArrayList<ArrayList<String>> playerhands = new ArrayList<ArrayList<String>>();
	UserHandAdapter adapter;
	Player pl= null;
	ArrayList<Integer> playercardids = new ArrayList<Integer>();
	ArrayList<String> playercardtexts = new ArrayList<String>();
	String gameid;
	String username;
	String gamename;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		String currentactivity = this.getClass().getSimpleName();
		Intent intent = getIntent();
		gameid = intent.getStringExtra("id");
		username = intent.getStringExtra("username");
				//intent.getStringExtra("username");
		gamename = intent.getStringExtra("gamename");
		Log.d("Obinna", "Username is " + username);
		Log.d("Obinna", "Gamename is " + gamename);
		Log.d("Life cycle notes", currentactivity + " created." );
		
		
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
		playercardids = new ArrayList<Integer>();
		playercardtexts = new ArrayList<String>();
		//See AsyncTasks class
		AsyncTasks.GetGame task = new GetGame();
		task.execute(gameid, username, gamename);
		
		InfoBundle bunds = null;
		try {
			bunds = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bunds == null)
		{
			setContentView(R.layout.error_page);
			TextView error = (TextView) findViewById(R.id.error_msg);
			error.setText("Error: Game not found");
		}
		else 
		{
			final Game currGame = bunds.getGame();
			pl = bunds.getPlayer();
			
			if(currGame.getWinner() != null)
			{
				setContentView(R.layout.error_page);
				TextView error = (TextView) findViewById(R.id.error_msg);
				error.setText("Game is over! Winner is " + currGame.getWinner());
				error.setBackgroundColor(getResources().getColor(R.color.white));
				error.setTextColor(getResources().getColor(R.color.black));
				
			}
			else
			{
				//Get hand for a specific player for display purposes
				
				//Also get current black card text for display
				//Round currRound = currGame.getCurrentRound();
				/*
				 * Display dealer view if the player is the dealer.
				 * Pull two new cards is black card needs three cards
				 */
				for(int x = 0; x< currGame.getPlayers().size(); x++)
				{
					if (currGame.getPlayers().get(x).equals(gamename))
					{
						//testplayer = currGame.getPlayers().get(x);
						//Log.d("Player selected", testplayer.getGameName() + " index is " + x);
						break;
					}
				}
				if (currGame.getDealer().equals(username))
				{
					setContentView(R.layout.dealerview);
					ArrayList<Integer> dealerlistids = new ArrayList<Integer>();
					final ArrayList<String> dealerlisttext = new ArrayList<String>();
					for (String str: currGame.getCurrentRound().getCards().keySet())
					{
						for (WhiteCard card : currGame.getCurrentRound().getCards().get(str))
						{
							dealerlistids.add(card.getId());
							dealerlisttext.add(card.getText());
						}
					}
					
					TextView blackcardtext = (TextView) findViewById(R.id.dealer_black_card);
					blackcardtext.setText(currGame.getCurrentRound().getBlackCard().getText());
					blackcardtext.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
						{
						
							Log.d("Black Card", "Dialog should be launched");
							//Launch popup window showing card details 
							View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
							TextView temp = (TextView) findViewById(R.id.dealer_black_card);
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
			 
							// show it
							alertDialog.show();
							Log.d("Black Card", "View showing");
						}
					});
					//Setting listview adapter and reaction to selection
					
					TwoWayView listView = (TwoWayView) findViewById(R.id.dealercardlist);
					UserHandAdapter roundadapter = new UserHandAdapter(getApplicationContext(), dealerlisttext);
					listView.setAdapter(roundadapter);
					listView.setOnItemClickListener(new OnItemClickListener() {
	
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							//Log.d("Card selected", "Dialog should be launched");
							
							//Launch popup window showing card details 
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
							View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
							
							TextView tView = (TextView) v1.findViewById(R.id.card_fullview);
							tView.setText(dealerlisttext.get(arg2));
							
							alertDialogBuilder.setView(v1);
							alertDialogBuilder
								.setPositiveButton("Select Card",
										new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												//Call async task to post winner, and then 
											AsyncTasks.ChooseWinner task = new ChooseWinner();
											task.execute(gameid, username);
											onStart();
											}
										});
								alertDialogBuilder.
								setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
								{
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// Get rid of dialog
										dialog.cancel();
									}
								});
							
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
							WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
						    lp.copyFrom(alertDialog.getWindow().getAttributes());
						    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
						 
							// show it
							alertDialog.show();
						
							
						}
					});;
				}
				else 
				{
					//Show game view
					setContentView(R.layout.activity_game);
					for (WhiteCard crd : pl.getHand())
					{
						playercardids.add(crd.getId());
						playercardtexts.add(crd.getText());
					}
	
					TextView scoreView = (TextView) findViewById(R.id.scoreview);
					scoreView.setText("Score: " + Integer.toString(pl.getScore()));
					//Also get current black card text for display
					TextView purg = (TextView) findViewById(R.id.purg_1);
					purg.setOnClickListener(this);
					purg = (TextView) findViewById(R.id.purg_2);
					purg.setOnClickListener(this);
					purg = (TextView) findViewById(R.id.purg_3);
					purg.setOnClickListener(this);
					//Log.d("Obinna", "Got past listener sset");
					TextView blackcardtext = (TextView) findViewById(R.id.black_card);
					blackcardtext.setText(currGame.getCurrentRound().getBlackCard().getText());
					blackcardtext.setOnClickListener(new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
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
			 
							// show it
							alertDialog.show();
							Log.d("Black Card", "View showing");
						
						}
					});
					
					//Handle click of submit button
					
					final Button submit =(Button) findViewById(R.id.submit_card);
					final int numCards = currGame.getCurrentRound().getBlackCard().getCardsNeeded();
					submit.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Log.d("Obinna", "Submit button pushed");
							ArrayList<String> cardstosubmit = new ArrayList<String>();
							//Submit cards to round and clear purgatory spots
							TextView purg1 = (TextView) findViewById(R.id.purg_1);
							String purg1text = purg1.getText().toString();
							if(!(purg1text.equals("")))
							{
								cardstosubmit.add(purg1text);
							}
							TextView purg2 = (TextView) findViewById(R.id.purg_2);
							String purg2text = purg2.getText().toString();
							if(!(purg2text.equals("")))
							{
								cardstosubmit.add(purg2text);
							}
							TextView purg3 = (TextView) findViewById(R.id.purg_3);
							String purg3text = purg3.getText().toString();
							if(!(purg3text.equals("")))
							{
								cardstosubmit.add(purg3text);
							}
							
							if(cardstosubmit.size() != numCards)
							{
								Toast.makeText(getApplicationContext(), "Incorrect number of cards submitted for round", Toast.LENGTH_SHORT).show();
							}
							else 
							{
								if(currGame.getCurrentRound().getBlackCard().getCardsNeeded() == 3)
								{
									InputBundle bundle = new InputBundle(gameid, username, null);
									AsyncTasks.DrawTwo task2= new DrawTwo();
									task2.execute(bundle);
									ArrayList<WhiteCard> extraCards = new ArrayList<WhiteCard>();
									try {
										extraCards = task2.get();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									for (WhiteCard crd : extraCards)
									{
										pl.AddtoHand(crd);
									}
									
								}
								//Send cardstosubmit to server, clear textviews
								ArrayList<Integer> cardstosubmitids = new ArrayList<Integer>();
								//Get ids of cards to be able to submit them to server
								for (String textString : cardstosubmit)
								{
									cardstosubmitids.add(playercardids.get(playercardtexts.indexOf(textString)));
									//Log.d("Obinna", "Card id is " + Integer.toString(playercardids.get(playercardtexts.indexOf(textString))));
								}
								InputBundle bundle = new InputBundle(gameid,username, cardstosubmitids);
								AsyncTasks.SubmitCard task = new SubmitCard();
								ArrayList<WhiteCard> newCards = new ArrayList<WhiteCard>();
								task.execute(bundle);
								try {
									newCards = task.get();
									for (WhiteCard crd: newCards)
									{
										pl.AddtoHand(crd);
										adapter.add(crd.getText());
									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								adapter.notifyDataSetChanged();
								purg1.setText("");
								purg2.setText("");
								purg3.setText("");
								submit.setEnabled(false);
	
							}
							
						}
					});
					final ArrayList<String> playerhandtext = new ArrayList<String>();
					//Log.d("Obinna", Integer.toString(playerhand.size()));
					//Setting listview adapter and reaction to selection
					//Get player hand
					int pos = -1;
					for (WhiteCard card : pl.getHand())
					{
						playerhandtext.add(card.getText());
					}
					
					TwoWayView listView = (TwoWayView) findViewById(R.id.userhandlist);
					adapter = new UserHandAdapter(getApplicationContext(), playerhandtext);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new OnItemClickListener() {
	
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							//Log.d("Card selected", "Dialog should be launched");
							
							//Launch popup window showing card details 
							//LayoutInflater li = LayoutInflater.from(getBaseContext());
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
							View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
							
							TextView tView = (TextView) v1.findViewById(R.id.card_fullview);
							//String tviewtextString = handarray[arg2];
							tView.setText(pl.getHand().get(arg2).getText());
							
							alertDialogBuilder.setView(v1);
							//Log.d("Textview", handarray[arg2]);
							//alertDialogBuilder.setMessage("Test message");
							alertDialogBuilder
							.setPositiveButton("Select Card",
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											//Move text to purgatory view
											//Remove from player hand and view as well
											
											String text =pl.getHand().get(arg2).getText();
											Log.d("Obinna", "Text is  " + text + " and pos is " + arg2);
											pl.RemovefromHand(arg2);
											adapter.remove(arg2);
											//sLog.d("Obinna", "PLayer hand sze is " + pl.getHand().size());
											adapter.notifyDataSetChanged();									
											TextView purg1 = (TextView) findViewById(R.id.purg_1);
											TextView purg2 = (TextView) findViewById(R.id.purg_2);
											TextView purg3 = (TextView) findViewById(R.id.purg_3);
											if(purg2.getText().toString().equals(""))
											{
												purg2.setText(text);
											}
											else if (purg1.getText().toString().equals("") && (purg3.getText().toString().equals("")))
											{
												String tempString = purg2.getText().toString();
												purg2.setText("");
												purg3.setText(text);
												purg1.setText(tempString);
											}
											else if(purg1.getText().toString().equals("") ) 
											{
												purg1.setText(text);
											}
											else if (purg3.getText().toString().equals("")) 
											{
												purg3.setText(text);	
											}
											else 
											{
												Toast.makeText(getApplicationContext(), "Purgatory list full", Toast.LENGTH_SHORT).show();
											}
											
											
										}
									});
							alertDialogBuilder.
							setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
							{
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// Get rid of dialog
									dialog.cancel();
								}
							});
						
							
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
							WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
						    lp.copyFrom(alertDialog.getWindow().getAttributes());
						    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
							// show it
							alertDialog.show();
						
							
						}
					});;
					
				}
				
	//			String currentactivity = this.getClass().getSimpleName();
	//			Log.d("Life cycle notes", currentactivity + " started." );
			}
		}
		
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

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
//			case R.id.submit_card:
//			{
//				Log.d("Obinna", "Submit button pushed");
//				ArrayList<String> cardstosubmit = new ArrayList<String>();
//				//Submit cards to round and clear purgatory spots
//				TextView purg1 = (TextView) findViewById(R.id.purg_1);
//				String purg1text = purg1.getText().toString();
//				if(!(purg1text.equals("")))
//				{
//					cardstosubmit.add(purg1text);
//				}
//				TextView purg2 = (TextView) findViewById(R.id.purg_2);
//				String purg2text = purg2.getText().toString();
//				if(!(purg2text.equals("")))
//				{
//					cardstosubmit.add(purg2text);
//				}
//				TextView purg3 = (TextView) findViewById(R.id.purg_3);
//				String purg3text = purg3.getText().toString();
//				if(!(purg3text.equals("")))
//				{
//					cardstosubmit.add(purg3text);
//				}
//				//Send cardstosubmit to server, clear textviews
//				purg1.setText("");
//				purg2.setText("");
//				purg3.setText("");
//				break;
//			}
			case R.id.purg_1:
			case R.id.purg_2:
			case R.id.purg_3:
			{
				final String text = ((TextView) v).getText().toString();
				//((TextView) v).setText("");
				if(!(text.equals("")))
				{
					//Launch AlertDialog to allow user remove card from purgatory
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
					View v1 = getLayoutInflater().inflate(R.layout.cardpopup, null);
					TextView tView = (TextView) v1.findViewById(R.id.card_fullview);
					//String tviewtextString = handarray[arg2];
					tView.setText(text);
					
					alertDialogBuilder.setView(v1);
					alertDialogBuilder
					.setPositiveButton("Remove Card",
							new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									//Remove from purgatory and add back to player hand
									//Get card id as well
									int cardid = playercardids.get(playercardtexts.indexOf(text));
									pl.AddtoHand(new WhiteCard(cardid,text));
									adapter.add(text);
									//sLog.d("Obinna", "PLayer hand sze is " + pl.getHand().size());
									adapter.notifyDataSetChanged();
									
									((TextView)v).setText("");
									
								}
							});
					alertDialogBuilder.
					setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
					{
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Get rid of dialog
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				    lp.copyFrom(alertDialog.getWindow().getAttributes());
				    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
					// show it
					alertDialog.show();
				}
				break;
				
			}
			
		}
	}


}
