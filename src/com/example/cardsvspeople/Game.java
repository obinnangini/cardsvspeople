package com.example.cardsvspeople;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game 
{
	ArrayList<WhiteCard> whitedeck = new ArrayList<WhiteCard>();
	ArrayList<BlackCard> blackdeck = new ArrayList<BlackCard>();
	Round current = null;
	ArrayList<String> playerusernames = new ArrayList<String>();
	ArrayList<String> playergamenames = new ArrayList<String>();
	Map<String, Integer> playerscores = new HashMap<String, Integer>();
	int roundcounter = 0;
	String dealer = null;
	public Game(ArrayList<String> usernames, ArrayList<String> gamenames, Round round, Map<String, Integer> scores, String dealerusername)
	{
		this.playerusernames = usernames;
		this.playergamenames = gamenames;
		current = round;
		this.playerscores = scores;
		this.dealer = dealerusername;
	}
	
//	public boolean CreateGame()
//	{
//		boolean completed = false;
//		//Generate both decks of cards - all hardcoded at this point, should be a db pull
//		whitedeck.add(new WhiteCard("Being on fire."));
//		whitedeck.add(new WhiteCard("Racism."));
//		whitedeck.add(new WhiteCard("Old-people smell."));
//		whitedeck.add(new WhiteCard("A micropenis."));
//		whitedeck.add(new WhiteCard("Women in yogurt commercials."));
//		whitedeck.add(new WhiteCard("Classist undertones."));
//		whitedeck.add(new WhiteCard("Sexting."));
//		whitedeck.add(new WhiteCard("Roofies."));
//		whitedeck.add(new WhiteCard("A windmill full of corpses."));
//		whitedeck.add(new WhiteCard("The gays."));
//		whitedeck.add(new WhiteCard("An oversized laptop."));
//		whitedeck.add(new WhiteCard("African children."));
//		whitedeck.add(new WhiteCard("An asymmetric boob job."));
//		whitedeck.add(new WhiteCard("Bingeing and purging."));
//		whitedeck.add(new WhiteCard("The hardworking Mexican."));
//		whitedeck.add(new WhiteCard("An Oedipus complex."));
//		whitedeck.add(new WhiteCard("A tiny horse."));
//		whitedeck.add(new WhiteCard("Boogers."));
//		whitedeck.add(new WhiteCard("Penis envy."));
//		
//		
//		blackdeck.add(new BlackCard("How did I lose my virginity?",1));
//		blackdeck.add(new BlackCard("Why can't I sleep at night?",1 ));
//		blackdeck.add(new BlackCard("What's that smell?",1));
//		blackdeck.add(new BlackCard("I got 99 problems but _______________________ ain't one",1));
//	
//		
////		players.add(new Player("user1", "Brandon",false));
////		players.add(new Player("user2", "Santosh",false));
////		players.add(new Player("user3", "Obinna", false));
////		players.add(new Player("user4", "Xiaoran",false));
////		
////		
////		
////		//Add whitecards to players' hands
////		players.get(0).AddtoHand(whitedeck.get(0));
////		players.get(0).AddtoHand(whitedeck.get(1));
////		players.get(0).AddtoHand(whitedeck.get(2));
////		
////		players.get(1).AddtoHand(whitedeck.get(3));
////		players.get(1).AddtoHand(whitedeck.get(4));
////		players.get(1).AddtoHand(whitedeck.get(5));
////		
////		players.get(2).AddtoHand(whitedeck.get(6));
////		players.get(2).AddtoHand(whitedeck.get(7));
////		players.get(2).AddtoHand(whitedeck.get(8));
////		
////		players.get(3).AddtoHand(whitedeck.get(9));
////		players.get(3).AddtoHand(whitedeck.get(10));
////		players.get(3).AddtoHand(whitedeck.get(11));
//		completed = true;
//		
//		return completed;
//	}
//	
//	public boolean StartRound()
//	{
//		boolean completed = false;
//		dealer = players.get(roundcounter%players.size());
//		current = new Round(blackdeck.get(roundcounter%blackdeck.size()));
//		roundcounter++;
//		completed = true;
//		return completed;
//		
//	}
//	
	public Round getCurrentRound()
	{
		return current;
	}
	
	public String getDealer()
	{
		return this.dealer;
	}
	
	public ArrayList<String> getPlayers()
	{
		return this.playerusernames;
	}
	
////	public void FinalizeRound()
////	{
////		current.SubmitCard(players.get(0), new WhiteCard("The hardworking Mexican."));
////		current.SubmitCard(players.get(1), new WhiteCard("An Oedipus complex."));
////		current.SubmitCard(players.get(2), new WhiteCard("A tiny horse."));
////		current.SubmitCard(players.get(3), new WhiteCard("Boogers."));
////	}
//	
	public boolean CardsAreSubmitted()
	{
		boolean done = false;
		if (current.getCards().size() == playerusernames.size())
		{
			done = true;
		}
		return done;
	}
	
}
