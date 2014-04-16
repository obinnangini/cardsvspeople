package edu.osu.cardsvspeople;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.osu.cardsvspeople.AsyncTasks.CreateGame;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlayerLocation extends Activity {
MyCustomAdapter dataAdapter = null;	
private GoogleMap googleMap;
double latitude;
double longitude;
String url;
String gameid;
final String TAG_USERNAME = "name";
final String TAG_NICKNAME = "nickname";
final String TAG_LAT = "lat";
final String TAG_LONG = "long";
JSONArray nearbyplayers = null;
ArrayList<Player> nearbyplayerList = new ArrayList<Player>();
String myUserName;
String gamename;

//hard-coded user name. Will be replaced with intent user name
//String myUserName = "dummy";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_location);
		
		Intent intent = getIntent();
		myUserName = intent.getStringExtra("username");
		gamename = intent.getStringExtra("gamename");
		
		//loading map by calling initializeMap()
		try{
			//Loading map
			initializeMap();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		displayListView();
		
		checkRequestButtonClick();
	}

//function to load map. If map is not created, this method will create it for you
	private void initializeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			//googleMap.setMyLocationEnabled(true);
			
			// Acquire a reference to the system Location Manager
			LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			
			// Define a listener that responds to location updates
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location){
					//Called when a new location is found by the network
					makeUseOfNewLocation(location);
				}
				
				public void onStatusChanged(String provider, int status, Bundle extras) {}
				
				public void onProviderEnabled(String provider) {}
				
				public void onProviderDisabled(String Provider) {}
			};
			
			//Register the listener with the location manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
		}
	
		//check if the map is successfully created or not
		if (googleMap == null) {
			Toast.makeText(getApplicationContext(), "Sorry, unable to create map", Toast.LENGTH_SHORT).show();
		}
	
}
	
	public void makeUseOfNewLocation(Location location) {
		
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title("My Current Location");
		
		googleMap.addMarker(marker);
		
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
				new LatLng(latitude,longitude)).zoom(5).build();
		
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		
	}
	
	private void displayListView()
	{
		/*
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("user1", "Brandon", false));
		playerList.add(new Player("user2", "Santosh", false));
		playerList.add(new Player("user3", "Obinna", false));
		playerList.add(new Player("user4", "Xiaoran", false));
		playerList.add(new Player("user5", "Sandeep", false));
		playerList.add(new Player("user6", "Umang", false));
		playerList.add(new Player("user7", "Angelo", false));
		playerList.add(new Player("user8", "Angie", false));
		
		
		//instantiate array adapter with string array
		dataAdapter = new MyCustomAdapter(this,R.layout.player_info,playerList);
		
		ListView listview = (ListView) findViewById(R.id.listView1);
		
		//assign listview to dataAdapter
		listview.setAdapter(dataAdapter);
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
			int position, long id){
				//when clicked, show a toast indicating the item has been clicked
				Player player = (Player) parent.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"Clicked On Row: " + player.getGameName(),
						Toast.LENGTH_LONG).show(); 
			}
		});
		*/
		
		//url parameters
		//String urllatitude = String.valueOf(latitude);
		//String urllongitude = String.valueOf(longitude);
/*		
		
		
		//URL to get JSON Array
		
*/		//url = "http://cardsvspeople.herokuapp.com/nearby?name=dummy&lat=100&long=200";
		
		//String urllatitude = "100";
		//String urllongitude = "200";
		
		url = "http://cardsvspeople.herokuapp.com/nearby?name=" + myUserName + "&lat=" + latitude + "&long=" + longitude;
		
		//url = "http://sheltered-bastion-2512.herokuapp.com/feed.json";
				
		new JSONParse().execute();
	
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONArray> {
		private ProgressDialog pDialog;
		@Override
		   protected void onPreExecute() {
			
//			pDialog = new ProgressDialog(PlayerLocation.this);
//			pDialog.setMessage("Getting Data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
		}
/*		@Override
		   protected JSONArray doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			Log.d("url", url);
			//Getting JSON from URL
			JSONArray jsonarray = jParser.getJSONArrayFromURL(url);
			return jsonarray;
		}
*/
		@Override
        protected JSONArray doInBackground(String... args) {
            
           // Gson gson = new Gson();
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            JSONArray jsonArray = null;
            System.out.println(url);
            //Get JSON object and do the magic using GSON
            HttpGet httpGet = new HttpGet(url);
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    Log.d("Builder.toString() is:", builder.toString());
                    jsonArray = new JSONArray(builder.toString());
                    System.out.println(jsonArray.length());
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //dealItemList.add(gson.fromJson(jsonObject.toString(), DealItem.class));

//                   }
                } else {
                    Log.e(this.getClass().getName(), "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                
            } catch (IOException e) {
                e.printStackTrace();
                
            } catch (JSONException e) {
                e.printStackTrace();
                
            }

            return jsonArray;

        }

		@Override
		protected void onPostExecute(JSONArray jsonarray){
	//		pDialog.dismiss();
			try {
				for(int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonarray.get(i);
					String name = (String) jsonObject.get(TAG_USERNAME);
					String nickname = (String) jsonObject.get(TAG_NICKNAME);
					String latitude = (String) jsonObject.get(TAG_LAT);
					double latdouble = Double.parseDouble(latitude);
					String longitude = (String) jsonObject.get(TAG_LONG);
					double longdouble = Double.parseDouble(longitude);
					Player player = new Player(name, nickname, false);
					player.setLatitude(latdouble);
					player.setLongitude(longdouble);
					nearbyplayerList.add(player);
				}				
				
				//instantiate array adapter with string array
				dataAdapter = new MyCustomAdapter(PlayerLocation.this,R.layout.player_info,nearbyplayerList);
				
				ListView listview = (ListView) findViewById(R.id.listView1);
				
				//assign listview to dataAdapter
				listview.setAdapter(dataAdapter);
				
				//add a map pin for each nearby player
				
				for(int i = 0;i < nearbyplayerList.size();i++)
				{
					double latitude = nearbyplayerList.get(i).getLatitude();
					double longitude = nearbyplayerList.get(i).getLongitude();
					
					MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(nearbyplayerList.get(i).getGameName());
					
					googleMap.addMarker(marker);
					
				}				
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void checkRequestButtonClick(){
		Button myButton = (Button) findViewById(R.id.requestSelected);
		
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StringBuffer response = new StringBuffer();
				response.append("The following were selected...\n");
				
				for(int i = 0; i<dataAdapter.playerList.size();i++){
					Player player = dataAdapter.playerList.get(i);
					if(player.isSelected())
					{
						response.append("\n" + player.getGameName());
					}
					
				}
				
				Toast.makeText(getApplicationContext(),
						response,Toast.LENGTH_LONG).show();
				
				//Create list of selected players
				ArrayList<String> selectedPlayerNames = new ArrayList<String>();
				
				for(int i = 0; i < dataAdapter.playerList.size(); i++)
				{
					if(dataAdapter.playerList.get(i).isSelected())
					{
						selectedPlayerNames.add(dataAdapter.playerList.get(i).getUserName());
					}
				}				
				selectedPlayerNames.add(myUserName);
				
				AsyncTasks.CreateGame task = new CreateGame();
				task.execute(selectedPlayerNames);
				String gameid = null;
				try {
					gameid = task.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("Santosh","gameid is "+gameid);
				
				//after printing a toast showing who has been selected, it just goes to the game
				//this will be changed by probably including a "start game" button
				System.out.println("Game ID is "+gameid);
				
				//String gameName = "Fake";//For user view
				//String gameName = "Brandon";//To see dealer view
				Intent intent = new Intent(PlayerLocation.this,GameActivity.class);
				intent.putExtra("gamename", gamename);
				intent.putExtra("id", gameid);
				intent.putExtra("username", myUserName);
				//"id","username","gamename" are the keys
				startActivity(intent);
			}
			});
		}
	
	private class MyCustomAdapter extends ArrayAdapter<Player>{
		
		private ArrayList<Player> playerList;
		Context context;
		
		public MyCustomAdapter(Context jsonParse, int textViewResourceId,
				ArrayList<Player> playerList) {
			super(jsonParse,textViewResourceId,playerList);
			this.playerList = new ArrayList<Player>();
			this.playerList.addAll(playerList);
			this.context = jsonParse;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View row = null;
			
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			
			convertView = inflater.inflate(R.layout.player_info, parent, false);
			
			TextView name = (TextView) convertView.findViewById(R.id.code);
			CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
			
			checkbox.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Player player = (Player) cb.getTag();
					Toast.makeText(getApplicationContext(),
							"Clicked On Checkbox: " + cb.getText() + " is " + cb.isChecked(),
							Toast.LENGTH_LONG).show();
				      player.setSelected(cb.isChecked());
				}
				
			});
			
			Player player = playerList.get(position);
			checkbox.setText(player.getGameName());
			checkbox.setChecked(player.isSelected());
			checkbox.setTag(player);
			
			return convertView;
			
		}
		
	}
}
