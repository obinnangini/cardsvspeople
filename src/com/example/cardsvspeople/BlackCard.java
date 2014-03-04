package com.example.cardsvspeople;

import android.content.res.Resources.Theme;

public class BlackCard implements Card {
	
	String text;
	/*
	 * The number of spaces on the black card i.e
	 * the number of white cards needed.
	 */
	int spaces;
	
	
	public BlackCard(String text)
	{
		this.text = text;
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
