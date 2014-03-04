package com.example.cardsvspeople;

import java.util.ArrayList;
import java.util.List;

public class Round 
{
	BlackCard currBlackCard;
	List<WhiteCard> cards = new ArrayList<WhiteCard>();
	
	public Round(BlackCard blCard)
	{
		this.currBlackCard = blCard;
	}
	/*
	 * Only a submit procedure since we 
	 * don't want to give the use the option to retrieve a card after it has been submitted
	 */
	public void SubmitCard(WhiteCard card)
	{
		cards.add(card);
	}
	
	public List<WhiteCard> getCards()
	{
		return this.cards;
	}
}
