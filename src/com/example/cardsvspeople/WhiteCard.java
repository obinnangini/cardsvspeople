package com.example.cardsvspeople;

public class WhiteCard  implements Card{

	String text;
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}
	@Override
	public String getCardType() {
		// TODO Auto-generated method stub
		return "White";
	}

}
