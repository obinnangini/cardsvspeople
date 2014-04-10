package com.example.cardsvspeople;

public class InfoBundle 
{
	Game game;
	Player player;
	public InfoBundle(Game game, Player pl)
	{
		this.game = game;
		player = pl;
	}

	public Player getPlayer()
	{
		return this.player;
		
	}
	public Game getGame()
	{
		return this.game;
	}
}
