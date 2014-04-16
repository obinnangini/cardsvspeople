package edu.osu.cardsvspeople;

public class WhiteCard  implements Card{

	int id;
	String text;
	public WhiteCard(int id, String input)
	{
		this.id = id;
		this.text = input;
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return this.text;
	}
	public int getId()
	{
		return this.id;
	}
	@Override
	public String getCardType() {
		// TODO Auto-generated method stub
		return "White";
	}

}
