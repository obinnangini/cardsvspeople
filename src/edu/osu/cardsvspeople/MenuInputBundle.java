package edu.osu.cardsvspeople;

import java.util.ArrayList;
import java.util.Map;

public class MenuInputBundle 
{
	String gamename;
	Map<String,ArrayList<String>> list;
	
	public MenuInputBundle(String name, Map<String,ArrayList<String>> map) 
	{
		this.gamename = name;
		this.list = map;
	}

	public String getGamename() {
		return gamename;
	}

	public Map<String, ArrayList<String>> getList() {
		return list;
	}

}

