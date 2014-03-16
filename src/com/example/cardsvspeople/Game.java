package com.example.cardsvspeople;

import java.util.ArrayList;

public class Game 
{
	ArrayList<WhiteCard> whitedeck = new ArrayList<WhiteCard>();
	ArrayList<BlackCard> blackdeck = new ArrayList<BlackCard>();
	Round current = null;
	static ArrayList<Player> players = new ArrayList<Player>();
	int roundcounter = 0;
	Player dealer = null;
	
	public boolean CreateGame()
	{
		boolean completed = false;
		//Generate both decks of cards - all hardcoded at this point, should be a db pull
		whitedeck.add(new WhiteCard("Being on fire."));
		whitedeck.add(new WhiteCard("Racism."));
		whitedeck.add(new WhiteCard("Old-people smell."));
		whitedeck.add(new WhiteCard("A micropenis."));
		whitedeck.add(new WhiteCard("Women in yogurt commercials."));
		whitedeck.add(new WhiteCard("Classist undertones."));
		whitedeck.add(new WhiteCard("Sexting."));
		whitedeck.add(new WhiteCard("Roofies."));
		whitedeck.add(new WhiteCard("A windmill full of corpses."));
		whitedeck.add(new WhiteCard("The gays."));
		whitedeck.add(new WhiteCard("An oversized laptop."));
		whitedeck.add(new WhiteCard("African children."));
		whitedeck.add(new WhiteCard("An asymmetric boob job."));
		whitedeck.add(new WhiteCard("Bingeing and purging."));
		whitedeck.add(new WhiteCard("The hardworking Mexican."));
		whitedeck.add(new WhiteCard("An Oedipus complex."));
		whitedeck.add(new WhiteCard("A tiny horse."));
		whitedeck.add(new WhiteCard("Boogers."));
		whitedeck.add(new WhiteCard("Penis envy."));
		
		
		blackdeck.add(new BlackCard("How did I lose my virginity?"));
		blackdeck.add(new BlackCard("Why can't I sleep at night?"));
		blackdeck.add(new BlackCard("What's that smell?"));
		blackdeck.add(new BlackCard("I got 99 problems but _______________________ ain't one"));
	
		
		players.add(new Player("user1", "Brandon"));
		players.add(new Player("user2", "Santosh"));
		players.add(new Player("user3", "Obinna"));
		players.add(new Player("user4", "Xiaoran"));
		
		
		
		//Add whitecards to players' hands
		players.get(0).AddtoHand(whitedeck.get(0));
		players.get(0).AddtoHand(whitedeck.get(1));
		players.get(0).AddtoHand(whitedeck.get(2));
		
		players.get(1).AddtoHand(whitedeck.get(3));
		players.get(1).AddtoHand(whitedeck.get(4));
		players.get(1).AddtoHand(whitedeck.get(5));
		
		players.get(2).AddtoHand(whitedeck.get(6));
		players.get(2).AddtoHand(whitedeck.get(7));
		players.get(2).AddtoHand(whitedeck.get(8));
		
		players.get(3).AddtoHand(whitedeck.get(9));
		players.get(3).AddtoHand(whitedeck.get(10));
		players.get(3).AddtoHand(whitedeck.get(11));
		completed = true;
		
		return completed;
	}
	
	public boolean StartRound()
	{
		boolean completed = false;
		dealer = players.get(roundcounter%players.size());
		current = new Round(blackdeck.get(roundcounter%blackdeck.size()));
		roundcounter++;
		completed = true;
		return completed;
		
	}
	
	public Round getCurrentRound()
	{
		return current;
	}
	
	public Player getDealer()
	{
		return this.dealer;
	}
	
}
