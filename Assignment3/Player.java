/*
James Hahn
CS0401 (Laboon)
TA: Emilee Betz
Lab: Mo 11:00am-12:50pm

This file contains the class Player for Assig2.java, the Over and Under betting game
*/

import java.util.*;
import java.io.*;

public class Player{
	//Initialize all variables for the class
	private int roundsPlayed = 0;
	private int totalRoundsPlayed = 0;
	private int roundsWon = 0;
	private int totalRoundsWon = 0;
	private int roundsLost = 0;
	private int totalRoundsLost = 0;

	private double initialMoney = 0.00;
	private double currentMoney = 0.00;
	private double loanAmount = 0.00;
	private double moneyNetGain = 0.00;

	private String firstName = "";
	private String lastName = "";

	//Initialize player
	public Player(String firstNameArg, String lastNameArg){
		firstName = firstNameArg;
		lastName = lastNameArg;
	}

	//Parameters are taken from the player's file and initialized into the player object
	public void initializeStats(int roundsPlayedTotal, int roundsWonTotal){
		totalRoundsPlayed = roundsPlayedTotal;
		totalRoundsWon = roundsWonTotal;
		totalRoundsLost = roundsPlayedTotal - roundsWonTotal;
	}

	//Next 7 functions are self-explanatory procedures and get functions
	public double getMoney(){
		return currentMoney;
	}

	public int getRoundsWon(){
		return roundsWon;
	}

	public int getRoundsPlayed(){
		return roundsPlayed;
	}

	public void wonGame(){
		roundsWon++;
		roundsPlayed++;
	}

	public void lostGame(){
		roundsLost++;
		roundsPlayed++;
	}

	public void addMoney(double amount){
		/*
		If the player hasn't played a round yet, and their current money is 0, then they must have started the game with no money.
		Therefore, their initial money before playing any rounds is the amount they add.
		This is assigned here so the program can calculate the net money gain/loss later.
		*/
		if(roundsPlayed == 0 && currentMoney == 0){
			initialMoney = amount;
		}

		currentMoney += amount;
	}

	public void subtractMoney(double amount){
		currentMoney -= amount;
	}

	//Updates the all-time stats for printing the player and writing the player to a file
	public void updateHistoricStats(){
		totalRoundsWon += roundsWon;
		totalRoundsPlayed += roundsPlayed;
		totalRoundsLost += roundsLost;
		moneyNetGain = currentMoney - initialMoney;
	}

	//Prints out all of the player's stats
	public void playerToString(){
		System.out.println("----------Your stats----------");
		System.out.printf("| Name: %s %s \n", firstName, lastName);
		System.out.printf("| Rounds won: %d this game (%d total) \n", roundsWon, totalRoundsWon);
		System.out.printf("| Rounds lost: %d this game (%d total)\n", roundsLost, totalRoundsLost);
		System.out.printf("| Rounds played: %d this game (%d total)\n", roundsPlayed, totalRoundsPlayed);
		System.out.printf("| Money left: $%.2f \n", currentMoney);
		System.out.printf("| Money loss/gain: $%.2f\n", moneyNetGain);
	}

	//Writes the player's stats to a file for use if they come back to the game
	public void playerToFile(File userInfo) throws FileNotFoundException {
		//Updates stats so there are no wrong statistics in the user's file
		updateHistoricStats();

		//Prints line by line to the user's file
		PrintWriter writer = new PrintWriter(userInfo);
		writer.println(lastName);
		writer.println(firstName);
		writer.println(currentMoney);
		writer.println(totalRoundsPlayed);
		writer.println(totalRoundsWon);
		writer.close();
	}
}
