package com.example.cardsvspeople;

public class BlackCard implements Card {
	
	String text;
	/*
	 * The number of spaces on the black card i.e
	 * the number of white cards needed.
	 */
	int spaces;
	
	
	public BlackCard(String text, int spaces)
	{
		this.text = text;
		this.spaces = spaces;
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}

	@Override
	public String getCardType() {
		// TODO Auto-generated method stub
		return "Black";
	}
	
	public int getCardsNeeded()
	{
		return this.spaces;
	}

}
