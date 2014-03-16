package com.example.cardsvspeople;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round 
{
	BlackCard currBlackCard;
	Map <Player,WhiteCard> cards = new HashMap<Player, WhiteCard>();
	//Player dealer = null;
	
	public Round(BlackCard blCard)//, Player player)
	{
		this.currBlackCard = blCard;
		//this.dealer = player;
	}
	/*
	 * Only a submit procedure since we 
	 * don't want to give the use the option to retrieve a card after it has been submitted
	 */
	public void SubmitCard(Player player, WhiteCard card)
	{
		cards.put(player, card);
	}
	
	public Map<Player,WhiteCard> getCards()
	{
		return this.cards;
	}
	
	public BlackCard getBlackCard()
	{
		return currBlackCard;
	}
//	public Player getDealer()
//	{
//		return dealer;
//	}
}
