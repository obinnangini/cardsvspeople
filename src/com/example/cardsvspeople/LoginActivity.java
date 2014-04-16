package com.example.cardsvspeople;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cardsvspeople.AsyncTasks.LoginUser;


public class LoginActivity extends Activity {

	private TextView attempts;
	private Button login;
	int counter = 3;
	private EditText userNameArea,passWordArea;
	private static String nickName ;
	Button btnShowLocation;
	//GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userNameArea = (EditText)findViewById(R.id.editText1);
		passWordArea = (EditText)findViewById(R.id.editText2);
		attempts = (TextView)findViewById(R.id.textView5);
		attempts.setText(Integer.toString(counter));
		login = (Button)findViewById(R.id.buttonLogin);

		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Closing registration screen
				// Switching to Login Screen/closing register screen
				login();
			}
		});

		/*
      btnShowLocation = (Button) findViewById(R.id.buttonGPS);

      // show location button click event
      btnShowLocation.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View arg0) {        
              // create class object

              gps = new GPSTracker(LoginActivity.this);

              // check if GPS enabled     
              if(gps.canGetLocation()){

                  double latitude = gps.getLatitude();
                  double longitude = gps.getLongitude();

                  // \n is for new line
                  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
              }else{
                  // can't get location
                  // GPS or Network is not enabled
                  // Ask user to enable GPS/network in settings
                  gps.showSettingsAlert();
              }

          }
      });
		 */
	}

	public void login(){
		//Need to query the server to see if login exists. If so, retrieve the player instance, and send that to MenuActivity.

		userNameArea = (EditText)findViewById(R.id.editText1);
		String userName = userNameArea.getText().toString();

		Log.d("usernmae = ",  userName);

		AsyncTasks.LoginUser task = new LoginUser();
		task.execute(userName);
		String response ="";
		try {
			response = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response != null)
		{
			Intent intentLogInSuccess= new Intent(this,MenuActivity.class);
			nickName =  JSONParseUser(response);
			intentLogInSuccess.putExtra("gamename", nickName);
			intentLogInSuccess.putExtra("username", userName);

			startActivity(intentLogInSuccess);
		}
		else
		{
			new AlertDialog.Builder(LoginActivity.this)
			.setTitle("Warning").setMessage("No such a user exist, please register first")
			.setNegativeButton("ok",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					// TODO Auto-generated method stub
				}
			}).show();
			Log.d("notfind = ",  "true");
		}



	}

	public void register(View view){

		Intent intentRegister= new Intent(this,RegisterActivity.class);
		startActivity(intentRegister);
	}

	public void cheat(View view){
		Intent intentLogin= new Intent(this,PlayerLocation.class);
		startActivity(intentLogin);
	}


	public boolean checkUserValid(String username)
	{
		URL uri = null;
		StringBuffer response1 = new StringBuffer("");

		try
		{
			uri = new URL("https://cardsvspeople.herokuapp.com/user/"+username);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();

			connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader inBufferedReader1 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputString1;
			response1 = new StringBuffer("");
			while((inputString1 = inBufferedReader1.readLine()) != null)
			{
				response1.append(inputString1);
			}
			inBufferedReader1.close();


			//System.out.println(response.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}


	public static String JSONParseUser(String input)
	{
		Map<String,ArrayList<String>> gameplayerslist = new HashMap<String, ArrayList<String>>();
		String gamename = "";
		JSONParser parser = new JSONParser();
		//parser.parse(input);
		try {
			JSONObject object = (JSONObject) parser.parse(input);
			//(String)object.get("name");
			gamename = (String) object.get("nickname");
			// System.out.println("Players username is " + username);
			// System.out.println("Players gamename is " + gamename);
			nickName = gamename;
			JSONArray gamelist = (JSONArray) object.get("games");
			System.out.println("Games they are currently in are:");
			for (int x = 0; x < gamelist.size(); x++)
			{
				JSONObject object2 = (JSONObject) gamelist.get(x);
				String gameid = (String) object2.get("id");
				System.out.println("Game id:" + gameid);
				JSONArray playerlist = (JSONArray) object2.get("players");
				ArrayList<String> playernames = new ArrayList<String>();
				System.out.print("Players in game are: ");
				for (int y=0; y< playerlist.size(); y++)
				{
					//JSONObject tempobj = (JSONObject) playerlist.get(y);
					String name = (String) playerlist.get(y);
					playernames.add(name);
					if (y == playerlist.size()-1)
					{
						System.out.print(name +". ");
					}
					else
					{
						System.out.print(name +", ");
					}
				}
				System.out.println();
				gameplayerslist.put(gameid, playernames);

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gamename;

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}