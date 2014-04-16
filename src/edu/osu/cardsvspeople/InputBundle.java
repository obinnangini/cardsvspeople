package edu.osu.cardsvspeople;

import java.util.ArrayList;

public class InputBundle 
{
	String id;
	String playerusername;
	ArrayList<Integer> cardslist;
	public InputBundle(String input, String player, ArrayList<Integer> list)
	{
		this.id = input;
		this.playerusername = player;
		this.cardslist = list;
	}
	public String getId()
	{
		return this.id;
	}
	public ArrayList<Integer> getList()
	{
		return this.cardslist;
	}
	public String getPlayerName()
	{
		return this.playerusername;
	}

}
