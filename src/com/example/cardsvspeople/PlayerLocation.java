package com.example.cardsvspeople;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerLocation extends Activity {
MyCustomAdapter dataAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_location);
		
		displayListView();
		
		checkRequestButtonClick();
	}

//	@Override
//	public void onClick(View v)
//	{
//		switch(v.getId())
//		{
//			case R.id.requestSelected:
//			{
//				String gameName = "Obinna";//For user view
				//String gameName = "Brandon";//To see dealer view
//				Intent intent = new Intent(PlayerLocation.this,GameActivity.class);
//				intent.putExtra("name", gameName);
//				startActivity(intent);
//				break;
//			}
//		}
//	}
	
	
	private void displayListView()
	{
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
		
		//assign listview to ListView
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
				
				
				//after printing a toast showing who has been selected, it just goes to the game
				//this will be changed by probably including a "start game" button
				String username = "obinnangini";
				String gamename = "Obinna";//For user view
				//String gameName = "Brandon";//To see dealer view
				Intent intent = new Intent(PlayerLocation.this,GameActivity.class);
				intent.putExtra("gamename", gamename);
				intent.putExtra("username", username);
				intent.putExtra("id", "ABC12d");
				
				startActivity(intent);
			}
			});
		}
	
	
	private class MyCustomAdapter extends ArrayAdapter<Player>{
		
		private ArrayList<Player> playerList;
		Context context;
		
		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<Player> playerList) {
			super(context,textViewResourceId,playerList);
			this.playerList = new ArrayList<Player>();
			this.playerList.addAll(playerList);
			this.context = context;
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
