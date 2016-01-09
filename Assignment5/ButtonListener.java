/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

Contains the ButtonListener class for Assignment 5.  The ButtonListener class implements ActionListener, therefore is required to
have an actionPerformed() method.  This entire class basically deals with any input through button pressing (login button, cast vote
button, choosing a ballot choice).
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ButtonListener implements ActionListener{
	//Declare private static variables for all ButtonListeners to see
	private static String voterID = "-1";
	private static ArrayList<Ballot> _ballots = new ArrayList<Ballot>();
	public static boolean[] votes;
	private static ArrayList<JToggleButton> buttons;

	//Make these static variables public so I can directly access them from Graphics.java.
	//It's not really necessary to make getters and setters for these 4 simple variables.
	public static boolean switchButton = false;
	public static String switchButtonText = "";
	public static boolean unvoteButton = false;
	public static String unvoteButtonText = "";

	public ButtonListener(){

	}

	//This is where all the action happens.  If an action is performed (button press for example), it goes through this method
	//that takes an ActionEvent, which is created at the time of the action.
	public void actionPerformed(ActionEvent e){
		//Try to cast the ActionEvent source as JToggleButton (if successful, the button is a ballot choice)
		try{
			JToggleButton theButton = (JToggleButton) e.getSource();
			String buttonText = theButton.getText();

			if(theButton.isSelected()){
				theButton.setForeground(Color.RED);
				switchButton = true;
				switchButtonText = buttonText;
				updateButtons(buttonText); //switches this button's respective vote position to 1 (true), meaning it is selected
			} else if(!theButton.isSelected()){
				theButton.setForeground(Color.BLACK);
				unvoteButton = true;
				unvoteButtonText = buttonText;
			}
		//If the program can't cast the source to a JToggleButton, cast it as a JButton, which is 100% what the source will be at this point.
		//This button will be one of two things: the login button or the cast vote button.
		} catch(Exception exception){
			JButton theButton = (JButton) e.getSource();

			String buttonText = theButton.getText();

			//If the user hits the Login button, check if the ID is valid.
			//If the ID is not valid, don't let them vote.  However, if it is valid, disable login and enable everything else.
			if(buttonText.equals("Login")){
				String voterID = JOptionPane.showInputDialog(theButton, "Enter your voter ID: ", "Login", 3);
				FileIO votersFile = new FileIO("voters.txt");
				ArrayList<Voter> voters = votersFile.createVoters();

				if(votersFile.checkValidID(voterID)){
					Voter theVoter = voters.get(votersFile.getVoterIndex(voterID));
					if(theVoter.getVotedStatus().equals("false")){
						setVoterID(voterID);
					} else{
						JOptionPane.showMessageDialog(theButton, "You already voted " + theVoter.getVoterName() + "!");
					}
				} else{
					JOptionPane.showMessageDialog(theButton, "Invalid ID!", "Error", 0);
				}
			//If the user hits the cast vote button, update the respective ballot files and disable every button except
			//for the login button.
			} else if(buttonText.equals("Cast vote")){
				int confirm = JOptionPane.showConfirmDialog(theButton, "Are you sure?");
				FileIO votersFile = new FileIO("voters.txt");
				ArrayList<Voter> voters = votersFile.createVoters();
				Voter theVoter = voters.get(votersFile.getVoterIndex(voterID));

				if(confirm == 0){
					JOptionPane.showMessageDialog(theButton, "Thanks for voting!");
					updateBallots();
					votersFile.updateVoterFile(theVoter.getVoterID());
					setVoterID("-1");
					votersFile.initializeVoters();
				}
			}
		}
	}

	//Grab the number of buttons from all of the ballots in the program.
	//This will define the votes array, which stores all of the true and false values...
	//as to whether the button is really selected or not (because button.isSelected() won't work for me for some reason).
	public void setVotesLength(){
		FileIO nullFile = new FileIO(); //need to create this just to gain access to the filename of the ballots file
		FileIO ballotsFile = new FileIO(nullFile.getBallotsFileName());
		ArrayList<Ballot> throwAwayList = ballotsFile.createBallots();
		int numButtons = 0;

		for(Ballot ballot: throwAwayList){
			numButtons += ballot.getNumButtons();
		}

		votes = new boolean[numButtons];
	}

	//Return the voter ID of the voter to mostly the Graphics class in order to check for a valid ID
	public String getVoterID(){
		return voterID;
	}

	//Set the voter ID if the ID check is valid (set to -1 after a user has cast their vote so any valid ID checks are automatically false)
	public void setVoterID(String input){
		voterID = input;
	}

	//This method starts from the ButtonListener class but ends up in the Ballot class, updating the file of each Ballot
	public void updateBallots(){
		FileIO nullFile = new FileIO(); //need to create this just to gain access to the filename of the ballots file
		FileIO ballotFile = new FileIO(nullFile.getBallotsFileName());
		ArrayList<Ballot> ballots = ballotFile.createBallots();

		for(Ballot ballot: ballots){
			ballot.updateFile();
		}
	}

	//Initialize the buttons ArrayList because there the program messes up if the voter doesn't vote for anything, the buttons array
	//will be null.
	public void initializeButtons(){
		FileIO nullFile = new FileIO(); //need to create this just to gain access to the filename of the ballots file
		FileIO ballotFile = new FileIO(nullFile.getBallotsFileName());
		_ballots = ballotFile.createBallots();
		buttons = new ArrayList<JToggleButton>();
		for(Ballot ballot: _ballots){
			JToggleButton[] ballotButtons = ballot.getButtons();
			for(int i = 0; i < ballotButtons.length; i++){
				buttons.add(ballotButtons[i]);
			}
		}
	}

	//Update the true/false value of every button in the program, and set every value to its respective position in the votes array
	public void updateButtons(String text){
		FileIO nullFile = new FileIO(); //need to create this just to gain access to the filename of the ballots file
		FileIO ballotFile = new FileIO(nullFile.getBallotsFileName());
		_ballots = ballotFile.createBallots();
		buttons = new ArrayList<JToggleButton>();
		for(Ballot ballot: _ballots){
			JToggleButton[] ballotButtons = ballot.getButtons();
			for(int i = 0; i < ballotButtons.length; i++){
				buttons.add(ballotButtons[i]);
			}
		}

		int numButtons = 0;
		for(Ballot ballot: _ballots){
			numButtons += ballot.getButtons().length;
		}

		for(int i = 0; i < numButtons; i++){
			if(buttons.get(i).getText().equals(text)){
				votes[i] = true;
			}
		}
	}

	//Return the buttons ArrayList which stores all of the buttons in the program
	public ArrayList<JToggleButton> getButtons(){
		return buttons;
	}

	//Get the votes array, which stores the true/false value of each button (whether it is selected or not)
	public boolean[] getVotes(){
		return votes;
	}

	public void replaceVotes(boolean[] newVotes){
		votes = newVotes;
	}
}
