package edu.osu.cardsvspeople;

import java.util.ArrayList;

import android.util.Log;


public class Player 
{
	String gameName = "";
	ArrayList<WhiteCard> hand = new ArrayList<WhiteCard>();
	int currentScore = 0;
	String userName= "";
	boolean selected = false;
	double latitude = 0;
	double longitude = 0;
	
	public Player(String userName, String gameName, boolean selected)
	{
		this.userName = userName;
		this.gameName = gameName;
		this.selected = selected;
	}
	public Player(String username, String gamename, int score, ArrayList<WhiteCard> hand)
	{
		this.userName = username;
		this.gameName = gamename;
		this.currentScore = score;
		this.hand = hand;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	public void setScore(int score)
	{
		this.currentScore = score;
	}
	public void createHand(ArrayList<WhiteCard> hand)
	{
		this.hand = hand;
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
	
	public void RemovefromHand(int pos)
	{
		WhiteCard card = this.hand.remove(pos);
		Log.d("Obinna", "Card " + card.getText() +" has been removed");
		
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
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	public double getLatitude() {
		return this.latitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	public double getLongitude(){
		return this.longitude;
	}
}
