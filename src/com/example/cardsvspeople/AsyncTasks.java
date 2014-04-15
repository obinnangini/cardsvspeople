package com.example.cardsvspeople;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.cardsvspeople.R.id;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTasks 
{
	
	
	/**
	 * This class stores all async tasks to be called in the Application
	 * @author obinn_000
	 *
	 */
	static class GameList extends AsyncTask<String, Void,Map<String,ArrayList<String>>>
	{

		@Override
		protected Map<String,ArrayList<String>> doInBackground(String... params) 
		{
			String username = params[0];
			String response = executeHTTPGET("https://cardsvspeople.herokuapp.com/user/" + username);
			Map<String,ArrayList<String>> gameplayerslist = null;
			if (response != null)
			{
				gameplayerslist = new HashMap<String, ArrayList<String>>();
				JSONParser parser = new JSONParser();
				//parser.parse(input);
				try {
					JSONObject object = (JSONObject) parser.parse(response);
					//String username = (String) object.get("name");
					String gamename = (String) object.get("nickname");
					//System.out.println("Players username is " + username);
					//System.out.println("Players gamename is " + gamename);
					JSONArray gamelist = (JSONArray) object.get("games");
					//System.out.println("Games they are currently in are:");
					for (int x = 0; x < gamelist.size(); x++)
					{
						JSONObject object2 = (JSONObject) gamelist.get(x);
						String gameid = (String) object2.get("id");
						//System.out.println("Game id:" + gameid);
						JSONArray playerlist = (JSONArray) object2.get("players");
						ArrayList<String> playernames = new ArrayList<String>();
						//System.out.print("Players in game are: ");
						for (int y=0; y< playerlist.size(); y++)
						{
							//JSONObject tempobj = (JSONObject) playerlist.get(y);
							String name = (String) playerlist.get(y);
							playernames.add(name);
	//						if (y == playerlist.size()-1)
	//						{
	//							System.out.print(name +". ");
	//						}
	//						else 
	//						{
	//							System.out.print(name +", ");
	//						}
						}
						//System.out.println();
						gameplayerslist.put(gameid, playernames);
						
					}
				
				} 
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				return gameplayerslist;
			}
		
	}
	
	
	static class GetGame extends AsyncTask<String, Void, InfoBundle>
	{

		@Override
		protected InfoBundle doInBackground(String... params) {
			
			String gameid = params[0];
			String username = params[1];
			String gamename = params[2];
			InfoBundle bundle = null;
			Game newGame;
			//Player player;
			String response = executeHTTPGET("https://cardsvspeople.herokuapp.com/game/" + gameid);
			if(response != null)
			{
				Map<String, ArrayList<WhiteCard>> usersubmitMap = new HashMap<String, ArrayList<WhiteCard>>();
				ArrayList<String> usernames= new ArrayList<String>();
				ArrayList<String> gamenames = new ArrayList<String>();
				int playerscore = -1;
				//ArrayList<WhiteCard> playerhand = new ArrayList<WhiteCard>();
				Map<String,Integer> playerscores = new HashMap<String, Integer>();
				BlackCard roundBlackCard;
				Round currRound = null;
				String winner = null;
				String dealer = null;
				Player tempPlayer = null;
				JSONParser parser = new JSONParser();
				Object object;
				
				try 
				{
					object = parser.parse(response);
					JSONObject jsonObject = (JSONObject) object;
					String name = (String)jsonObject.get("id");
					//System.out.println("Game name is " + name);
					int tempscore = -1;
					JSONArray playersArray = (JSONArray) jsonObject.get("players");
					for (int x=0; x< playersArray.size(); x++)
					{
						JSONObject tempobj = (JSONObject) playersArray.get(x);
						String tempusernname = (String) tempobj.get("name");
						//System.out.println("Player username is " + tempusernname);
						String tempgamename = (String) tempobj.get("nickname");
						tempscore = (int)((Long) tempobj.get("score")).longValue();
						Log.d("Obinna", "USername is " + tempusernname);
						Log.d("Obinna", "Gamename is " + tempgamename);
						if(tempgamename.equals(gamename) && tempusernname.equals(username))
						{
							Log.d("Obinna", "Current player found");
							//System.out.println("Player gamename is " + tempgamename);
							playerscore = tempscore;
							//System.out.println("Player score is  " + Integer.toString(tempscore));
							ArrayList<WhiteCard> tempHand= new ArrayList<WhiteCard>();
							JSONArray handArray = (JSONArray) tempobj.get("hand");
							//System.out.println("Player cards are:");
							//System.out.println("Player hand size is:" + handArray.size());
							for(int y =0; y < handArray.size(); y++)
							{
								JSONObject temp2 = (JSONObject) handArray.get(y);
								int cardid = (int)((Long) temp2.get("id")).longValue();
								String cardtext = (String) temp2.get("text");
								//System.out.println(cardtext);
								tempHand.add(new WhiteCard(cardid, cardtext));
							}
							tempPlayer = new Player(username, gamename, playerscore, tempHand);
						}
						usernames.add(tempusernname);
						gamenames.add(tempgamename);
						playerscores.put(tempusernname,tempscore);
					}
					winner = (String) jsonObject.get("winner");
					Log.d("Obinna", "winner is " + winner);
					if (winner == null)
					{
						Log.d("Obinna", "got into if");
						JSONObject object2 = (JSONObject)jsonObject.get("round");
						String dealername = (String) object2.get("judge");
						dealer = dealername;
						//System.out.println("Current round dealer is " + dealername);
						JSONObject blCard = (JSONObject) object2.get("question");
						String blackcardtext = (String) blCard.get("text");
						int blackccardspaces= (int)((Long) blCard.get("numAnswers")).longValue();
						roundBlackCard = new BlackCard(blackcardtext, blackccardspaces);
						//System.out.println("Black card text is " + blackcardtext);
						//System.out.println("Number of spaces needed is/are: " + Integer.toString(blackccardspaces));
						//System.out.println("Submissions so far are :");
						JSONObject answerobj = (JSONObject) object2.get("answers");
						for (int z = 0; z < usernames.size(); z++)
						{
							String username1 = usernames.get(z);
							JSONArray playersubmitlist = (JSONArray) answerobj.get(username1);
							if (playersubmitlist != null)
							{
								//System.out.println(username + " has submitted the following cards:");
								ArrayList<WhiteCard> usersubmissons = new ArrayList<WhiteCard>();
								for (int a = 0; a< playersubmitlist.size(); a++)
								{
									JSONObject submitObj = (JSONObject) playersubmitlist.get(a);
									int cardid = Integer.parseInt((String)submitObj.get("id"));
									String cardtext = (String) submitObj.get("text");
									//System.out.println(cardtext);
									WhiteCard tempCard = new WhiteCard(cardid,cardtext);
									usersubmissons.add(tempCard);
								}
								usersubmitMap.put(username1, usersubmissons);
							}
							else 
							{
								//System.out.println(username + " has not submitted cards");
							}
						}
						currRound = new Round(usersubmitMap, roundBlackCard);
						
					}
					newGame = new Game(usernames, gamenames, currRound, playerscores, dealer, winner);
					bundle = new InfoBundle(newGame, tempPlayer);
					
						
				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Log.d("Obinna", "AysncTask done");
			}
			return bundle;
		}
		
	}
	
	static class DrawTwo extends AsyncTask<InputBundle, Void, ArrayList<WhiteCard>>
	{

		@Override
		protected ArrayList<WhiteCard> doInBackground(InputBundle... params) {
			InputBundle bundle = params[0];
			String gameid = bundle.getId();
			String username = bundle.getPlayerName();
			// TODO Auto-generated method stub
			 String urlParameters = null;
				try {
					urlParameters = "game=" + URLEncoder.encode(gameid, "UTF-8") +
					"&name=" + URLEncoder.encode(username, "UTF-8") + 
					"&quantity=" + URLEncoder.encode(Integer.toString(2),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 String temp = executeHTTP("PUT","http://cardsvspeople.herokuapp.com/draw", urlParameters);
				 ArrayList<WhiteCard> newCards = new ArrayList<WhiteCard>();
				newCards = getnewCards(temp);
				return newCards;
				 
		}
		
	}
	
	static class ChooseWinner extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String gameid = params[0];
			String winner = params[1];
			String urlParameters = null;
			 try {
					urlParameters = "game=" + URLEncoder.encode(gameid, "UTF-8") +
					"&winner=" + URLEncoder.encode(winner, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 String temp = executeHTTP("POST","http://cardsvspeople.herokuapp.com/vote", urlParameters);
			 return null;
		}
		
	}

	static class SubmitCard extends AsyncTask<InputBundle, Void, ArrayList<WhiteCard>>
	{
		@Override
		protected ArrayList<WhiteCard> doInBackground(InputBundle ... params) 
		{
			InputBundle bund = params[0];
			String gameid = bund.getId();
			String playername = bund.getPlayerName();
			ArrayList<Integer> cards = bund.getList();
			String urlParameters = null;
			 try {
					urlParameters = "game=" + URLEncoder.encode(gameid, "UTF-8") +
					"&name=" + URLEncoder.encode(playername, "UTF-8") + 
					"&cards=";
					for (int x = 0; x < cards.size(); x++)
					{
						String str = "";
						if (x != cards.size()-1)
						{
							str = URLEncoder.encode(Integer.toString(cards.get(x)) + ",", "UTF-8");
						}
						else
						{
							str = URLEncoder.encode(Integer.toString(cards.get(x)), "UTF-8");
						}
						urlParameters = urlParameters + str;
					}
					Log.d("Obinna",urlParameters);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// TODO Auto-generated method stub
			String temp = executeHTTP("POST","http://cardsvspeople.herokuapp.com/play", urlParameters);
			ArrayList<WhiteCard> newCards = new ArrayList<WhiteCard>();
			newCards = getnewCards(temp);
			return newCards;
		}
	}
		
	static class RegisterUser extends AsyncTask<String, Void, Void>
	{

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String username = params[0];
			String nickname = params[1];
			String password = params[2];
			
			 String urlParameters = "";
	           try {
	                  urlParameters = "name=" + URLEncoder.encode(username, "UTF-8") +
	                  "&password=" + URLEncoder.encode(nickname, "UTF-8") +
	                  "&nickname=" + URLEncoder.encode(password, "UTF-8");
	              } catch (UnsupportedEncodingException e) {
	                  // TODO Auto-generated catch block
	                  e.printStackTrace();
	              }
	           executeHTTP("POST","http://cardsvspeople.herokuapp.com/user", urlParameters);
	           
			return null;
		}
		
	}
	
	static class LoginUser extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String username = params[0];
			
		
			String result = executeHTTPGET("http://cardsvspeople.herokuapp.com/user/"+username);
			
			
	           
			return result;
		}
		
	}

	public static ArrayList<WhiteCard> getnewCards(String input)
	{
		Log.d("Obinna", "Input to get new cards is " + input);
		ArrayList<WhiteCard> cards = new ArrayList<WhiteCard>();
		JSONParser parser = new JSONParser();
		Object object;
		try 
		{
			object = parser.parse(input);
			JSONArray array = (JSONArray) object;
			//System.out.println("Card is");
			for (int x = 0; x < array.size(); x++)
			{
				JSONObject jsonObject = (JSONObject) array.get(x);
				int cardid = (int)((Long) jsonObject.get("id")).longValue();
				String cardtext = (String) jsonObject.get("text");
				cards.add(new WhiteCard(cardid, cardtext));
				//System.out.println(Integer.toString(cardid) + " : " + cardtext);
			}
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		return cards;
	}
	public static String executeHTTPGET(String url)
	{
		URL uri;
		int responseCode=0;
		StringBuffer response = new StringBuffer();
		try 
		{
			uri = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestMethod("GET");
			//connection.setRequestProperty(field, newValue);
			responseCode = connection.getResponseCode();
			BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputString;
			response = new StringBuffer();
			while((inputString = inBufferedReader.readLine()) != null)
			{
				response.append(inputString);
				
			}
			inBufferedReader.close();
			//Log.d("Obinna", response.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(responseCode == 200)
		{
			return response.toString();
		}
		else 
		{
			return null;
		}
	}

	
	public static String executeHTTP(String reqMethod, String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod(reqMethod);
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }

}
