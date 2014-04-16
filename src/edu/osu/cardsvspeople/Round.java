package edu.osu.cardsvspeople;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Round 
{
	BlackCard currBlackCard;
	//Map of player name to cards they submitted.
	Map <String,ArrayList<WhiteCard>> cards = new HashMap<String, ArrayList<WhiteCard>>();
	WhiteCard winner = null;
	//	Map <String,ArrayList<WhiteCard>> cards = new HashMap<String,ArrayList<WhiteCard>>();
	public Round(Map<String, ArrayList<WhiteCard>> map,BlackCard blCard)
	{
		this.currBlackCard = blCard;
		//this.dealer = player;
		this.cards = map;
	}
	/*
	 * Only a submit procedure since we 
	 * don't want to give the use the option to retrieve a card after it has been submitted
	 */
	public void SubmitCard(String player, ArrayList<WhiteCard> cards)
	{
		this.cards.put(player, cards);
	}
	
	public Map<String,ArrayList<WhiteCard>> getCards()
	{
		return this.cards;
	}
	
	public BlackCard getBlackCard()
	{
		return currBlackCard;
	}
	
	public WhiteCard getWinningCard()
	{
		return winner;
	}
	public void setWinner()
	{
		//Send message to server.
	}
}
