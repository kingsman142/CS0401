/*
James Hahn
CS0401 (Laboon)
TA: Emilee Betz
Lab: Mo 11:00am-12:50pm

This is assignment 2, the Over and Under betting game.
It uses File I/O to store user stats, which can be accessed later
if the user returns to the game.  It also uses two different classes,
Die.java and Player.java, which create the Die and Player objects
necessary for the game to process.

A new class that we learned in lecture, PrintWriter, is used to write to the files,
while we utilize scanner to read the file instead of our usual use of it to
read in user input.

***Extra Credit***
Made it so two people with the same first name would have two different accounts
Submitted Issue #124 on Assignment 3 Repo for 2 extra credit points
*/

import java.util.*;
import java.io.*;

public class Assig2{
	public static void playGame(File userInfo, Player player, int roundsWon, int roundsPlayed, Die die1, Die die2) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);

		//Variables to keep track of rolls
		int firstRoll = 0;
		int secondRoll = 0;
		int rollSum = 0;

		//Variables for money
		double betAmount = 0.00;
		double addMoneyAmount = 0.00;

		//Variables for necessary input
		String betChoice = "";
		String input = "";
		String continuePlaying = "";
		String addMoneyPrompt = "";

		//If the user enters the game with no money to bet, then they must add money to their account or leave (quit).
		//Keeps on asking until the user enters a valid amount of money or leaves.
		if(player.getMoney() <= 0){
			do {
				System.out.print("\nYou currently have no money in your account.  Would you like to add some ('no' to quit)? ");
				addMoneyPrompt = sc.next();
				addMoneyPrompt = addMoneyPrompt.toLowerCase();

				if(addMoneyPrompt.equals("yes")){
					do {
						System.out.print("\nHow much would you like to add? ");

						while(!sc.hasNextDouble()){
							System.out.println("\nPlease enter a valid amount of money.");
							System.out.print("\nHow much would you like to add? ");
							sc.next();
						}
						addMoneyAmount = sc.nextDouble();

						if(addMoneyAmount <= 0.00){
							System.out.println("\nYou can't add $0 or negative dollars!");
						} else{
							player.addMoney(addMoneyAmount);
						}

					} while(player.getMoney() <= 0);

				} else if(addMoneyPrompt.equals("no")){
					System.out.println("\nSorry, but you can't play a betting game without money.  See you next time!");
					player.playerToFile(userInfo);
					player.playerToString();
					System.exit(0);

				} else{
					System.out.println("\nInvalid input. Please try again.\n");
				}

			} while(!addMoneyPrompt.equals("yes") && !addMoneyPrompt.equals("no"));
		}

		//The main game loop
		do{
			//Asks the user for a bet amount and checks for valid input
			do{
				System.out.printf("\nEnter bet amount ($%.2f available, 'quit' to exit): ", player.getMoney());
				while(!sc.hasNextDouble()){
					if(sc.next().toLowerCase().equals("quit")){
						System.out.println("\nThanks for trying out the game!\n");
						player.playerToFile(userInfo);
						player.playerToString();
						System.exit(0);
					}

					System.out.println("\nYou can't bet words/characters. Try again.");
					System.out.printf("\nEnter bet amount ($%.2f available, 'quit' to exit): ", player.getMoney());
				}

				betAmount = sc.nextDouble();

				if(betAmount > player.getMoney()){
					System.out.println("\nYou can't bet more money than you have!");
				} else if(betAmount < 0.00){
					System.out.println("\nYou can't bet negative dollars!");
				} else if(betAmount == 0.00){
					System.out.println("\nYou can't bet $0.");
				}

			} while(betAmount > player.getMoney() || betAmount <= 0.00);

			//Asks the user for their betting choice (under, over, or seven) and checks for valid input
			do{
				System.out.print("\nYour betting choice ('under', 'over', 'seven'): ");
				betChoice = sc.next();
				betChoice = betChoice.toLowerCase();

				if(!betChoice.equals("under") && !betChoice.equals("over") && !betChoice.equals("seven")){
					System.out.println("\nPlease enter a correct betting choice.");
				}

			} while(!betChoice.equals("under") && !betChoice.equals("over") && !betChoice.equals("seven"));

			//Roll the dice, calculate the sum, and output the results
			firstRoll = die1.rollDie();
			secondRoll = die2.rollDie();
			rollSum = firstRoll + secondRoll;
			System.out.printf("\nFirst roll: %d\n", firstRoll);
			System.out.printf("Second roll: %d\n", secondRoll);
			System.out.printf("Sum of rolls: %d\n", rollSum);

			//Checks if the user won or lost based on what their betting choice was
			if(betChoice.equals("under")){
				if(rollSum < 7){
					System.out.printf("\nYou won $%.2f!\n", betAmount);
					player.addMoney(betAmount);
					player.wonGame();
				} else if(rollSum >= 7){
					System.out.printf("\nYou lost $%.2f!\n", betAmount);
					player.subtractMoney(betAmount);
					player.lostGame();
				}

			} else if(betChoice.equals("seven")){
				if(rollSum == 7){
					System.out.printf("\nYou won $%.2f!\n", betAmount*4);
					player.addMoney(betAmount*4);
					player.wonGame();
				} else if(rollSum != 7){
					System.out.printf("\nYou lost $%.2f!\n", betAmount);
					player.subtractMoney(betAmount);
					player.lostGame();
				}

			} else if(betChoice.equals("over")){
				if(rollSum > 7){
					System.out.printf("\nYou won $%.2f!\n", betAmount);
					player.addMoney(betAmount);
					player.wonGame();
				} else if(rollSum <= 7){
					System.out.printf("\nYou lost $%.2f!\n", betAmount);
					player.subtractMoney(betAmount);
					player.lostGame();
				}
			}

			//Lets the user know how much money they have left in case they forget
			System.out.printf("Money left: $%.2f\n\n", player.getMoney());

			/*
			The instructions for the program say that it should terminate if the user runs out of money,
			so this checks if the user has absolutely no money left, and if so, the program tells them
			and immediately exits, while printing out stats and writing to the file.  This prevents
			the user from continuing to play the game.

			If there was the option to take a loan out (which I was initially thinking of doing, but decided otherwise),
			then this if statement would be non-existent until the user reaches the maximum borrow amount.
			*/
			if(player.getMoney() == 0.00){
				System.out.println("You have run out of money! Please come back next time to add some more!\n");
				player.playerToFile(userInfo);
				player.playerToString();
				System.exit(0);
			}

			//Asks the user if they want to continue playing the game, which would continue the loop.
			//Also checks for valid input.
			do{
				System.out.print("Continue playing (yes or no)? ");
				continuePlaying = sc.next();
				continuePlaying = continuePlaying.toLowerCase();

				if(!continuePlaying.equals("yes") && !continuePlaying.equals("no")){
					System.out.println("\nInvalid answer. Try again.\n");
				} else if(continuePlaying.equals("no")){
					System.out.println("\nThanks for playing the game!");
					player.playerToFile(userInfo);
					player.playerToString();
					System.exit(0);
				}

			} while(!continuePlaying.equals("yes") && !continuePlaying.equals("no"));

		} while(continuePlaying.equals("yes"));
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);

		//Initializes variables necessary for the program and greets the user
		String firstName = "";
		String lastName = "";

		System.out.println("\nWelcome to Over, Under, or Seven! The world famous, addicting, betting game! Let us get to know you first.\n");
		System.out.print("First name: ");
		firstName = sc.next();
		System.out.print("Last name : ");
		lastName = sc.next();

		//Next bunch of code is creating the file name for the user, then itializes the objects for the game
		String filename = firstName+lastName+".txt";
		File userInfo = new File(filename);

		Player player = new Player(firstName, lastName);
		Die die1 = new Die();
		Die die2 = new Die();

		//Checks if the file (a.k.a. user) already exists, and if so, grab the data from it
		if(!userInfo.createNewFile()){
			Scanner reader = new Scanner(userInfo);
			System.out.printf("\nWelcome back %s %s!\n\n", firstName, lastName);
			reader.nextLine();
			reader.nextLine();
			player.addMoney(reader.nextDouble());
			player.initializeStats(reader.nextInt(), reader.nextInt());
			player.playerToString();
		}

		//Calls the playGame function to start the game
		playGame(userInfo, player, player.getRoundsWon(), player.getRoundsPlayed(), die1, die2);
	}
}
