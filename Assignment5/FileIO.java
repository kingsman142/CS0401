/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

This is the FileIO class for Assignment 5.  It deals with all file input and output (in regards to the voters and ballots files).
It contains many methods to pull data from files, and create arrays/ArrayLists of voters and ballots to be accessed easily
in other classes.

----------FORMAT OF VOTER FILE (voters.txt)----------
voterID:voterName:booleanOfVotedOrNot
voterID:voterName:booleanOfVotedOrNot

----------FORMAT OF BALLOT FILE (ballots.txt usually)----------
numBallots
ballotID:ballotTopic:ballotChoice1,ballotChoice2,ballotChoice3
ballotID:ballotTopic:ballotChoice1,ballotChoice2
ballotID:ballotTopic:ballotChoice1,ballotChoice2,ballotChoice3,ballotChoice4,ballotChoice5

*****THESE ABOVE 2 FORMATS MUST BE USED FOR THEIR RESPECTIVE FILES*****
*/

import java.io.*;
import java.util.*;

public class FileIO{
	//Declare private variables for the class
	private String filename = "";
	private File file;

	//Declare static variables (private) for the class
	private static String[][] voterArray;
	private static String ballotsFileName;
	private static String votersFileName = "voters.txt";

	//Constructor 1
	public FileIO(){

	}

	//Constructor 2
	public FileIO(String fileN){
		filename = fileN;
		file = new File(filename);
	}

	//Set the filename of the ballots file (usually ballots.txt)
	public void setBallotsFileName(String newName){
		ballotsFileName = newName;
	}

	//Get the filename of the ballots file
	public String getBallotsFileName(){
		return ballotsFileName;
	}

	//Initialize the array of voters (couldn't put this in the constructor because of StackOverFlowException)
	public void initializeVoters(){
		voterArray = getVoterData();
	}

	//Read the ballots.txt file for all of the ballot data
	public ArrayList<String> getBallotData(){
		try{
			Scanner sc = new Scanner(file);
			ArrayList<String> ballots = new ArrayList<String>();
			int numBallots = Integer.parseInt(sc.nextLine());
			for(int i = 0; i < numBallots; i++){
				ballots.add(sc.nextLine().trim());
			}

			sc.close();
			return ballots;
		} catch(Exception e){
			System.out.println("Failed to get ballot data!");
			return null;
		}
	}

	//Create an arraylist of Ballots to be easily accessed from any class
	public ArrayList<Ballot> createBallots(){
		ArrayList<String> ballotsData = getBallotData();
		String[][] ballotArray = new String[ballotsData.size()][];
		ArrayList<Ballot> ballots = new ArrayList<Ballot>();

		for(int i = 0; i < ballotArray.length; i++){
			ballotArray[i] = ballotsData.get(i).split(":");
			String[] choices = ballotArray[i][2].split(",");
			Ballot newBallot = new Ballot(ballotArray[i][1], choices, ballotArray[i][0]);
			ballots.add(newBallot);
		}

		return ballots;
	}

	//Grab the data of every Voter from the voter's file (usually voters.txt)
	public String[][] getVoterData(){
		try{
			Scanner sc = new Scanner(file);
			ArrayList<String> voters = new ArrayList<String>();
			String[][] voterArray;

			while(sc.hasNextLine()){
				voters.add(sc.nextLine().trim());
			}

			voterArray = new String[voters.size()][3];

			for(int i = 0; i < voters.size(); i++){
				voterArray[i] = voters.get(i).split(":");
			}

			sc.close();
			return voterArray;
		} catch(Exception e){
			return null;
		}
	}

	//Create an arraylist of Voters to be easily accessed from any class
	public ArrayList<Voter> createVoters(){
		ArrayList<Voter> voters = new ArrayList<Voter>();
		for(int i = 0; i < voterArray.length; i++){
			String id = voterArray[i][0];
			String name = voterArray[i][1];
			String voted = voterArray[i][2];
			voters.add(new Voter(id, name, voted));
		}

		return voters;
	}

	//Check if the ballots or voters file exists.
	//The file checked is determined by the initial File input in the constructor.
	public boolean checkFileExists(){
		if(!file.exists()){
			return false;
		}

		return true;
	}

	//Check the ID that the user entered when they clicked the Login button to see if they're an actual voter
	public boolean checkValidID(String id){
		ArrayList<Voter> voters = createVoters();
		for(Voter voter: voters){
			if(voter.getVoterID().equals(id)){
				return true;
			}
		}

		return false;
	}

	//Get the index of the voter (in the Voter arraylist returned from createVoters())
	public int getVoterIndex(String id){
		ArrayList<Voter> voters = createVoters();
		for(int i = 0; i < voters.size(); i++){
			if(voters.get(i).getVoterID().equals(id)){
				return i;
			}
		}

		return -1;
	}

	//Update the voters.txt file if a voter casts his/her vote
	public void updateVoterFile(String id){
		try{
			ArrayList<Voter> voters = createVoters();
			File voterFile = new File("voters.txt");
			File tempTempFile = new File("tempTemp.txt");
			PrintWriter writer = new PrintWriter(tempTempFile);

			if(voterFile.exists()){
				Scanner sc = new Scanner(voterFile);
				for(Voter voter: voters){
					String[] line = null;
					if(sc.hasNextLine()) line = sc.nextLine().trim().split(":");

					//If the line that the Scanner read in has the same ID as the ID that the method
					//is trying to set to true (saying that they just cast their vote), then only
					//change the 3rd value in the line and just print that same format out.
					if(line[0].equals(id)){
						writer.print(id + ":");
						writer.print(line[1] + ":");
						writer.println("true");
					//If the ID that the program scanned in does not match the ID that the program is Looking
					//for, just print it back into the file like it was found.
					} else{
						writer.print(line[0] + ":");
						writer.print(line[1] + ":");
						writer.println(line[2]);
					}
				}
				//Close accessors for safety
				writer.close();
				sc.close();
				for(int i = 0; !voterFile.delete(); i++){
					//Debugging reasons only because the voterFile never deletes on the first attempt for some reason.
					//So I put it in a for loop to make sure that is 100% deletes the original voter file.
					//System.out.printf("Failed to delete after %d attempt(s)!\n", i+1);
				}
				tempTempFile.renameTo(voterFile); //replace the old voter file
			//If the voters file doesn't exist, just return the method.  The voters file should ALWAYS exist.
			} else{
				return;
			}
		} catch(Exception e){
			return;
		}
	}
}
