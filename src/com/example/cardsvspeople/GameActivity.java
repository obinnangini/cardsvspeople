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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Problem is, the values of these data structures arent being updated. I should return an instance of the Game class instead.
//FIX TOMORROW!!!!!!!!!!!!!!!!!!!!!
public class GameActivity extends Activity {

//	ArrayList<ArrayList<String>> playerhands = new ArrayList<ArrayList<String>>();
	UserHandAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		String currentactivity = this.getClass().getSimpleName();
		Intent intent = getIntent();
		final String gameid = intent.getStringExtra("id");
		final String username = intent.getStringExtra("username");
				//intent.getStringExtra("username");
		final String gamename = intent.getStringExtra("gamename");
		Log.d("Obinna", "Username is " + username);
		Log.d("Obinna", "Gamename is " + gamename);
		Log.d("Life cycle notes", currentactivity + " created." );
		
		/**
		 * Need to fetch game instance from server, if number position is given, else, 
		 * create new game instance and fetch decks from server
		 */
		//Test HTTP GET 
		class TestNetwork extends AsyncTask<Void, Void, InfoBundle>
		{

			@Override
			protected InfoBundle doInBackground(Void... params) {
				
				InfoBundle bundle = null;
				Game newGame;
				Player player;
				URL uri;
				StringBuffer response = new StringBuffer();
				try 
				{
					String strtemp = "https://cardsvspeople.herokuapp.com/game/" + gameid;
					Log.d("Obinna", "URL is " + strtemp);
					uri = new URL(strtemp);
					HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
					connection.setRequestMethod("GET");
					//connection.setRequestProperty(field, newValue);
					BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String inputString;
					response = new StringBuffer();
					while((inputString = inBufferedReader.readLine()) != null)
					{
						response.append(inputString);
						
					}
					inBufferedReader.close();
					//Log.d("Obinna", response.toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Map<String, ArrayList<WhiteCard>> usersubmitMap = new HashMap<String, ArrayList<WhiteCard>>();
				ArrayList<String> usernames= new ArrayList<String>();
				ArrayList<String> gamenames = new ArrayList<String>();
				int playerscore = -1;
				ArrayList<WhiteCard> playerhand = new ArrayList<WhiteCard>();
				Map<String,Integer> playerscores = new HashMap<String, Integer>();
				BlackCard roundBlackCard;
				Round currRound;
				String dealer;
				Player tempPlayer = null;
				JSONParser parser = new JSONParser();
				Object object;
				
				try 
				{
					object = parser.parse(response.toString());
					JSONObject jsonObject = (JSONObject) object;
					String name = (String)jsonObject.get("id");
					//System.out.println("Game name is " + name);
					int tempscore = -1;
					JSONArray playersArray = (JSONArray) jsonObject.get("players");
					for (int x=0; x< playersArray.size(); x++)
					{
						JSONObject tempobj = (JSONObject) playersArray.get(x);
						String tempusernname = (String) tempobj.get("name");
						//System.out.println("Player username is " + tempusernname);
						String tempgamename = (String) tempobj.get("nickname");
						tempscore = (int)((Long) tempobj.get("score")).longValue();
						Log.d("Obinna", "USername is " + tempusernname);
						Log.d("Obinna", "Gamename is " + tempgamename);
						if(tempgamename.equals(gamename) && tempusernname.equals(username))
						{
							Log.d("Obinna", "Current player found");
							//System.out.println("Player gamename is " + tempgamename);
							playerscore = tempscore;
							//System.out.println("Player score is  " + Integer.toString(tempscore));
							ArrayList<WhiteCard> tempHand= new ArrayList<WhiteCard>();
							JSONArray handArray = (JSONArray) tempobj.get("hand");
							//System.out.println("Player cards are:");
							//System.out.println("Player hand size is:" + handArray.size());
							for(int y =0; y < handArray.size(); y++)
							{
								JSONObject temp2 = (JSONObject) handArray.get(y);
								String cardtext = (String) temp2.get("text");
								//System.out.println(cardtext);
								tempHand.add(new WhiteCard(cardtext));
							}
							tempPlayer = new Player(username, gamename, playerscore, tempHand);
						}
						usernames.add(tempusernname);
						gamenames.add(tempgamename);
						playerscores.put(tempusernname,tempscore);
					}
					JSONObject object2 = (JSONObject)jsonObject.get("round");
					String dealername = (String) object2.get("judge");
					dealer = dealername;
					//System.out.println("Current round dealer is " + dealername);
					JSONObject blCard = (JSONObject) object2.get("question");
					String blackcardtext = (String) blCard.get("text");
					int blackccardspaces= (int)((Long) blCard.get("numAnswers")).longValue();
					roundBlackCard = new BlackCard(blackcardtext, blackccardspaces);
					//System.out.println("Black card text is " + blackcardtext);
					//System.out.println("Number of spaces needed is/are: " + Integer.toString(blackccardspaces));
					//System.out.println("Submissions so far are :");
					JSONObject answerobj = (JSONObject) object2.get("answers");
					for (int z = 0; z < usernames.size(); z++)
					{
						String username = usernames.get(z);
						JSONArray playersubmitlist = (JSONArray) answerobj.get(username);
						if (playersubmitlist != null)
						{
							//System.out.println(username + " has submitted the following cards:");
							ArrayList<WhiteCard> usersubmissons = new ArrayList<WhiteCard>();
							for (int a = 0; a< playersubmitlist.size(); a++)
							{
								JSONObject submitObj = (JSONObject) playersubmitlist.get(a);
								String cardtext = (String) submitObj.get("text");
								//System.out.println(cardtext);
								WhiteCard tempCard = new WhiteCard(cardtext);
								usersubmissons.add(tempCard);
							}
							usersubmitMap.put(username, usersubmissons);
						}
						else 
						{
							//System.out.println(username + " has not submitted cards");
						}
					}
					currRound = new Round(usersubmitMap, roundBlackCard);
					newGame = new Game(usernames, gamenames, currRound, playerscores, dealer);
					bundle = new InfoBundle(newGame, tempPlayer);
					
						
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Log.d("Obinna", "AysncTask done");
				return bundle;
			}
			
			
		}
		TestNetwork task = new TestNetwork();
		task.execute();
		
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
		Game currGame = bunds.getGame();
		final Player pl = bunds.getPlayer();
		Log.d("Obinna", "AsyncTask done!" );
		Log.d("Obinna", "Usernames size is " + Integer.toString(currGame.getPlayers().size()));
		//Log.d("Obinna", "Gamenames size is " + Integer.toString(gamenames.size()));
		Log.d("Obinna","Player score is " + Integer.toString(pl.getScore()));
		Log.d("Obinna","Player hand size is " + Integer.toString(pl.getHand().size()));
		Log.d("Obinna","User submit map size is " + Integer.toString(currGame.getCurrentRound().getCards().size()));

		
		//Get hand for a specific player for display purposes
		
		//Also get current black card text for display
		//Round currRound = currGame.getCurrentRound();
		/*
		 * Display dealer view if the player is the dealer.
		 */
		//Get player for testing purposes
		Player testplayer = null;
		for(int x = 0; x< currGame.getPlayers().size(); x++)
		{
			if (currGame.getPlayers().get(x).equals(gamename))
			{
				//testplayer = currGame.getPlayers().get(x);
				Log.d("Player selected", testplayer.getGameName() + " index is " + x);
				break;
			}
		}
		if (currGame.getDealer().equals(username))
		{
			setContentView(R.layout.dealerview);
			//Hardcoded generation of round
			//currGame.FinalizeRound();
			//Map <Player,WhiteCard> roundmap = currRound.getCards();
			//
			ArrayList<String> dealerlisttext = new ArrayList<String>();
			for (String str: currGame.getCurrentRound().getCards().keySet())
			{
				for (WhiteCard card : currGame.getCurrentRound().getCards().get(str))
				{
					dealerlisttext.add(card.getText());
				}
			}
			
			TextView blackcardtext = (TextView) findViewById(R.id.dealer_black_card);
			blackcardtext.setText(currGame.getCurrentRound().getBlackCard().getText());
			blackcardtext.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v) {
					if(v.getId() == R.id.dealer_black_card)
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
					    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		 
						// show it
						alertDialog.show();
						Log.d("Black Card", "View showing");
					}
				}
			});
			//Setting listview adapter and reaction to selection
			final String[] dealerarray = dealerlisttext.toArray(new String[dealerlisttext.size()]);
			
			TwoWayView listView = (TwoWayView) findViewById(R.id.dealercardlist);
			UserHandAdapter roundadapter = new UserHandAdapter(getApplicationContext(), dealerarray);
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
					tView.setText(dealerarray[arg2]);
					
					alertDialogBuilder.setView(v1);
					//Log.d("Textview", handarray[arg2]);
					//alertDialogBuilder.setMessage("Test message");
					alertDialogBuilder
						.setPositiveButton("Select Card",
								new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
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
				    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
				 
					// show it
					alertDialog.show();
				
					
				}
			});;
		}
		else 
		{
			//Show game view
			setContentView(R.layout.activity_game);
			//Also get current black card text for display
			TextView blackcardtext = (TextView) findViewById(R.id.black_card);
			blackcardtext.setText(currGame.getCurrentRound().getBlackCard().getText());
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
			
			//Handle click of submit button
			
			Button submit =(Button) findViewById(R.id.submit_card);
			int numCards = currGame.getCurrentRound().getBlackCard().getCardsNeeded();
			submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Before calling submit, make sure that number of cards in purgatory
					//match numer of cards needed.
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
			final String[] handarray = playerhandtext.toArray(new String[playerhandtext.size()]);
			
			TwoWayView listView = (TwoWayView) findViewById(R.id.userhandlist);
			adapter = new UserHandAdapter(getApplicationContext(), handarray);
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
									//Call server and add the card to the round instance.
									//Remove from player hand and view as well
									
									String text =pl.getHand().get(arg2).getText();
									Log.d("Obinna", "ext is  " + text + " and pos is " + arg2);
									pl.RemovefromHand(arg2);
									//sLog.d("Obinna", "PLayer hand sze is " + pl.getHand().size());
									playerhandtext.clear();
									for (WhiteCard card : pl.getHand())
									{
										playerhandtext.add(card.getText());
									}
									String [] array = playerhandtext.toArray(new String[playerhandtext.size()]);
									Log.d("Obinna",Integer.toString(array.length));
									TwoWayView listView = (TwoWayView) findViewById(R.id.userhandlist);
									adapter = new UserHandAdapter(getApplicationContext(), array);
									listView.setAdapter(adapter);
									
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
