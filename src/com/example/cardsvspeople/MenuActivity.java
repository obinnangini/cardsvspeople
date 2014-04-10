package com.example.cardsvspeople;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.jar.Attributes.Name;






import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener {

	String username = "";
	String gamename = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		username = "obinnangini";//To see user view
		gamename = "Obinna";
		//final String username = "btmills";//To see dealer view
		//final String gamename = "Brandon";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		String currentactivity = this.getClass().getSimpleName();
		Log.d("Life cycle notes", currentactivity + " created." );
		Button startButton = (Button)findViewById(R.id.game_start_button);
		startButton.setOnClickListener(this);
		Button helpButton = (Button) findViewById(R.id.help_button);
		helpButton.setOnClickListener(this);
		ListView listView = (ListView) findViewById(R.id.game_list);
		/**
		 * Get List of games on from server to populate the list of ongoing games
		 * This is commented for now
		 */
		Map<String,ArrayList<String>> gameplayerslist = new HashMap<String, ArrayList<String>>();
		
		class GameList extends AsyncTask<Void, Void,Map<String,ArrayList<String>>>
		{

			@Override
			protected Map<String,ArrayList<String>> doInBackground(Void... arg0) 
			{
				StringBuffer response = new StringBuffer();
				URL uri;
				try 
				{
					uri = new URL("https://cardsvspeople.herokuapp.com/user/btmills");
					HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
					connection.setRequestMethod("GET");
					BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String inputString;
					response = new StringBuffer();
					while((inputString = inBufferedReader.readLine()) != null)
					{
						response.append(inputString);
						
					}
					inBufferedReader.close();
				} 
				catch (MalformedURLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Map<String,ArrayList<String>> gameplayerslist = new HashMap<String, ArrayList<String>>();
				
				
				JSONParser parser = new JSONParser();
				//parser.parse(input);
				try {
					JSONObject object = (JSONObject) parser.parse(response.toString());
					String username = (String) object.get("name");
					String gamename = (String) object.get("nickname");
					//System.out.println("Players username is " + username);
					//System.out.println("Players gamename is " + gamename);
					JSONArray gamelist = (JSONArray) object.get("games");
					//System.out.println("Games they are currently in are:");
					for (int x = 0; x < gamelist.size(); x++)
					{
						JSONObject object2 = (JSONObject) gamelist.get(x);
						String gameid = (String) object2.get("id");
						//System.out.println("Game id:" + gameid);
						JSONArray playerlist = (JSONArray) object2.get("players");
						ArrayList<String> playernames = new ArrayList<String>();
						//System.out.print("Players in game are: ");
						for (int y=0; y< playerlist.size(); y++)
						{
							//JSONObject tempobj = (JSONObject) playerlist.get(y);
							String name = (String) playerlist.get(y);
							playernames.add(name);
//							if (y == playerlist.size()-1)
//							{
//								System.out.print(name +". ");
//							}
//							else 
//							{
//								System.out.print(name +", ");
//							}
						}
						//System.out.println();
						gameplayerslist.put(gameid, playernames);
						
					}
				
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return gameplayerslist;
			}
			
		}
		GameList task = new GameList();
		task.execute();
		try {
			gameplayerslist = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (String key: gameplayerslist.keySet())
//		{
//			for (String str: gameplayerslist.get(key))
//				{
//					Log.d("Obinna",str + " ");
//				}
//			//System.out.println();
//		}
		final String [] gameids= gameplayerslist.keySet().toArray(new String[gameplayerslist.keySet().size()]);
		ArrayList<ArrayList<String>> gameplayers = new ArrayList<ArrayList<String>>();
		for (String str : gameplayerslist.keySet())
		{
			gameplayers.add(gameplayerslist.get(str));
		}
		Log.d("Obinna","Game player size" + Integer.toString(gameplayerslist.size()));
		listView.setAdapter(new GameListAdapter(getApplicationContext(), gameids, gameplayers));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				//If game is already set up, head to game activity, and send position of game instance and name of user.
				Intent intent = new Intent(MenuActivity.this,GameActivity.class);
				Log.d("Obinna", "Game id is " + (gameids[arg2]));
				intent.putExtra("id", gameids[arg2]);
				//Log.d("Obinna", "Game id is " + gameids[arg2]);
				intent.putExtra("username", username);
				intent.putExtra("gamename", gamename);
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
	@Override
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.game_start_button:
			{
				//Use an intent to start game Activity, and pass user's game name
				//Fetch game name from user class, or server
				Intent intent = new Intent(MenuActivity.this,PlayerLocation.class);
				intent.putExtra("username", username);
				intent.putExtra("gamename", gamename);
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
