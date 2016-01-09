/*
James Hahn
CS0401
Bill Laboon
TA: Emilee Betz
Lab: Mo 11:00am-12:50pm

Graded lab that creates a universe with numericrons in it.
It allows the numericrons to age and then move and eat carrots.
*/

import java.util.*;
import java.io.*;

public class Lab06{
	//Function to create universe based on the lab's instruction to place initiate each
	//character at the correct position
	public static char[] createUniverse(char[] universe){
		for(int i = 0; i < universe.length; i += 7){
			universe[i] = '0';
		}

		for(int i = 5; i < universe.length; i += 5){
			if((i % 7) != 0) universe[i] = '^';
		}

		for(int i = 0; i < universe.length; i++){
			if(universe[i] == 0){
				universe[i] = '.';
			}
		}

		return universe;
	}

	//Function to advance the world; ages the numericrons until they're adults ('2');
	//moves the adult numericrons until they find carrots to become Babies;
	//and constantly moves all adults to the right meanwhile
	public static char[] advanceWorld(char[] universe){
		for(int i = 0; i < universe.length-1; i++){
			if(universe[i+1] == '.' || universe[i+1] == '^'){
				if(universe[i] == '0'){
					universe[i] = '1';

				} else if(universe[i] == '1'){
					universe[i] = '2';

				} else if(universe[i] == '2'){
					if(universe[i+1] == '.'){
						universe[i+1] = universe[i];
						universe[i] = '.';
					} else if(universe[i+1] == '^'){
						universe[i+1] = '0';
						universe[i] = '.';
					}

					i++;
				}
			}
		}

		if(universe[universe.length-1] == '0'){
			universe[universe.length-1] = '1';
		} else if(universe[universe.length-1] == '1'){
			universe[universe.length-1] = '2';
		}

		return universe;
	}

	//Saves the current universe to world.txt
	//Also displays the current amount of babies, children, and adults in the universe
	public static void saveWorld(char[] universe) throws Exception{
		PrintWriter writer = new PrintWriter("world.txt");
		int numBabies = 0;
		int numChilds = 0;
		int numAdults = 0;

		for(char value: universe){
			if(value == '0') numBabies++;
			else if(value == '1') numChilds++;
			else if(value == '2') numAdults++;
		}
		writer.println(new String(universe));
		writer.println("Babies: " + numBabies);
		writer.println("Children: " + numChilds);
		writer.println("Adults: " + numAdults);
		writer.close();
	}

	//Function that lets the user choose whether they want to quit, save the universe, or advance the universe
	public static String chooseOption() throws Exception{
		Scanner sc = new Scanner(System.in);
		String userChoice = "";

		System.out.print("(Q)uit, (S)ave, or (A)dvance? ");
		userChoice = sc.next();

		return userChoice;
	}

	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		int universeSize = 0;
		String userChoice = "";

		System.out.print("Size of the universe: ");
		universeSize = sc.nextInt();

		char[] universe = new char[universeSize];

		createUniverse(universe);
		System.out.println(universe);

		//Main run loop based on the value of chooseOption(), which dictates what happens next in the program
		do {
			userChoice = chooseOption();
			switch(userChoice){
				case "S":
					saveWorld(universe);
					System.out.println(universe);
					break;
				case "Q":
					System.exit(0);
					break;
				case "A":
					advanceWorld(universe);
					System.out.println(universe);
					break;
				default:
					break;
			}
		} while (!userChoice.equals('Q'));
	}
}
