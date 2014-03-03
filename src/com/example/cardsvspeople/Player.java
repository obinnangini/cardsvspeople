package com.example.cardsvspeople;

import java.util.ArrayList;
import java.util.List;


public class Player 
{
	String gameName = "";
	List<WhiteCard> hand = new ArrayList<WhiteCard>();
	int currentScore = 0;
	String userName= "";
	
	public Player(String userName, String gameName)
	{
		this.userName = userName;
		this.gameName = gameName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getGameName()
	{
		return gameName;
	}
	public List<WhiteCard> getHand()
	{
		return this.hand;
	}
	
	public int getScore()
	{
		return this.currentScore;
	}
	
	public void AddtoHand(WhiteCard card)
	{
		hand.add(card);
	}
	
	public void RemovefromHand(WhiteCard card)
	{
		hand.remove(card);
	}
	public void increaseScore()
	{
		currentScore++;
	}
	
}
