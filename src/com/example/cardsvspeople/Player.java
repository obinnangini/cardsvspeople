package com.example.cardsvspeople;

import java.util.ArrayList;
import java.util.List;


public class Player 
{
	String gameName = "";
	ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
	int currentScore = 0;
	String userName= "";
	
	public Player(String userName, String gameName)
	{
		this.userName = userName;
		this.gameName = gameName;
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
	
}
