package com.example.cardsvspeople;

import java.util.ArrayList;


public class Player 
{
	String gameName = "";
	ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
	int currentScore = 0;
	String userName= "";
	Boolean selected = false;
	
	public Player(String userName, String gameName, Boolean selected)
	{
		this.userName = userName;
		this.gameName = gameName;
		this.selected = selected;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String getGameName()
	{
		return this.gameName;
	}
	public ArrayList<WhiteCard> getHand()
	{
		return this.hand;
	}
	
	public int getScore()
	{
		return this.currentScore;
	}
	
	public void AddtoHand(WhiteCard card)
	{
		this.hand.add(card);
	}
	
	public void RemovefromHand(WhiteCard card)
	{
		this.hand.remove(card);
	}
	public void increaseScore()
	{
		this.currentScore++;
	}
	public boolean isSelected(){
		return selected;
	}
	public void setSelected(Boolean selected){
		this.selected = selected;
	}
	
}
