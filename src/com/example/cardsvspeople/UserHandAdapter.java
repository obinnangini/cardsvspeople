package com.example.cardsvspeople;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserHandAdapter extends BaseAdapter
{
	Context context;
	String[] cardtext;
	
	private static LayoutInflater inflater = null;
	public UserHandAdapter(Context context,String[] input)
	{
		this.context = context;
		this.cardtext = input;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cardtext.length;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return cardtext[arg0];
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
//		
//		if(arg1 == null)
//		{
			arg1= inflater.inflate(R.layout.whitecard, null);
			TextView title = (TextView) arg1.findViewById(R.id.card_text);
			
			
			//Set text of layoiut 
			title.setText(cardtext[arg0]);
		
//		}
//		
		return arg1;
	}

}


