package com.example.cardsvspeople;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter
{
	Context context;
	String[] ids;
	ArrayList<ArrayList<String>> playernames;
	private static LayoutInflater inflater = null;
	
	public GameListAdapter(Context context, String[] idlist, ArrayList<ArrayList<String>> players)
	{
		this.context = context;
		this.playernames = players;
		this.ids = idlist;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ids.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		if(convertView == null)
//		{
			convertView= inflater.inflate(R.layout.game_row, null);
			TextView title = (TextView) convertView.findViewById(R.id.game_title);
			title.setText("Game " +  (position+1));
			TextView playerstext = (TextView) convertView.findViewById(R.id.game_players);
			String playerString = "Players: ";
			ArrayList<String> gameplayers = playernames.get(position);
			for (int x = 0; x< gameplayers.size(); x++)
			{
				if (x == gameplayers.size() -1)
				{
					
					playerString = playerString + gameplayers.get(x);
				}
				else 
				{
					playerString = playerString + gameplayers.get(x) + ",";
				}
			}
			Log.d("Obinna", "Full list is " + playerString);
			playerstext.setText(playerString);
		
//		}
//		
		return convertView;
	}

}
