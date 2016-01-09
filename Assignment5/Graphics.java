/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

This contains the Graphics class file for Assignment 5.  This is arguably the most important class file in all of my Assignment 5.
It creates the window, adds panels, which contain the buttons and labels for each ballot, and loops infinitely (until the window is closed)
switching between enabling and disabling buttons when needed.

The original window size is set to 500x200, but window.pack() is used along with FlowLayout, which almst guarantees the program
won't end up as a 500x200 window.  Originally a GridLayout(1,0) was used, but I didn't like the way it looked so I just changed it.
*/

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Graphics{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 200;
	public static JFrame window;
	public static JButton loginButton;
	public static JButton castVoteButton;
	public static ButtonListener buttonListener;
	public static ArrayList<Ballot> ballots;

	public Graphics(){
		//Initializes window and manipulates it before most of the program starts
		window = new JFrame("Voting Ballot");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WIDTH, HEIGHT);
		window.setVisible(true);
		window.setLayout(new FlowLayout());
	}

	public static void main(String[] args){
		FileIO ballotsFile = new FileIO(args[0]);
		FileIO votersFile = new FileIO("voters.txt");
		votersFile.initializeVoters();
		ballots = ballotsFile.createBallots();

		for(Ballot ballot: ballots){
			window.add(ballot.getPanel());
			ballot.disableButtons();
		}

		//Create Login and Cast Vote buttons and add them to the window
		buttonListener = new ButtonListener();
		buttonListener.setVotesLength();
		loginButton = new JButton("Login");
		loginButton.addActionListener(buttonListener);
		window.add(loginButton);
		castVoteButton = new JButton("Cast vote");
		castVoteButton.addActionListener(buttonListener);
		window.add(castVoteButton);

		window.pack();
		window.setVisible(true);

		while(true){
			//While the ID that the voter enters is false, don't do anything to the application ever
			while(!votersFile.checkValidID(buttonListener.getVoterID())){
				loginButton.setEnabled(true);
				castVoteButton.setEnabled(false);
				for(Ballot ballot: ballots){
					ballot.resetButtons();
					ballot.disableButtons();
				}
			}

			//After a valid ID is entered, read in all of the voters and find the specific voter that is read to vote
			ArrayList<Voter> voters = votersFile.createVoters();
			Voter theVoter = voters.get(votersFile.getVoterIndex(buttonListener.getVoterID()));
			JOptionPane.showMessageDialog(window, theVoter.getVoterName() + ", please make your choices");

			while(votersFile.checkValidID(buttonListener.getVoterID())){
				//Then, enable every button except for the Login button
				loginButton.setEnabled(false);
				castVoteButton.setEnabled(true);
				for(Ballot ballot: ballots){
					ballot.enableButtons();
				}

				if(buttonListener.switchButton){
					for(int i = 0; switchVoteChoice(buttonListener.switchButtonText) == -1; i++){

					}
					buttonListener.switchButton = false;
					buttonListener.switchButtonText = "";
				}

				if(buttonListener.unvoteButton){
					unvoteButton(buttonListener.unvoteButtonText);
					buttonListener.unvoteButton = false;
					buttonListener.unvoteButtonText = "";
				}
			}
		}
	}

	//If the voter switches votes in one ballot (Button A is selected, but Button B is clicked and therefore selected, and both buttons
	//are in the same Ballot, then switch them so only one is activated at a time), it activates the correct button and deactivates
	//an already existing button in the same Ballot.
	public static int switchVoteChoice(String text){
		boolean[] votes = buttonListener.getVotes();
		boolean found = false;
		int votesIndexOfSelected = -1;
		int indexOfSelected = -1;
		int ballotIndex = -1;
		int counterAtBeginningOfBallot = 0;
		int counter = 0;

		for(int i = 0; i < ballots.size(); i++){
			JToggleButton[] ballotButtons = ballots.get(i).getButtons();
			if(!found){
				counterAtBeginningOfBallot = counter;
				for(int j = 0; j < ballotButtons.length; j++){
					if(ballotButtons[j].getText().equals(text)){
						ballotIndex = i;
						indexOfSelected = j;
						votesIndexOfSelected = counter;
						found = true;
					}
					counter++;
				}
			}
		}

		//Go into the Ballot that is associated with the button switch and finalize the switching of the buttons
		if(ballotIndex != -1) ballots.get(ballotIndex).switchButtons(votes, votesIndexOfSelected, indexOfSelected, counter, counterAtBeginningOfBallot, text);
		return indexOfSelected;
	}

	//(1) If Button A is selected, its respective votes (array) value is changed to true.
	//(2) But if Button A is selected again, therefore it is not selected, its respective votes (array) value is changed to false.
	//This method only deals with step 2.
	public static void unvoteButton(String text){
		boolean[] votes = buttonListener.getVotes();
		int counter = 0;
		int ballotIndex = -1;
		int votesIndex = -1;
		boolean found = false;

		for(int i = 0; i < ballots.size(); i++){
			JToggleButton[] ballotButtons = ballots.get(i).getButtons();
			if(!found){
				for(int j = 0; j < ballotButtons.length; j++){
					if(ballotButtons[j].getText().equals(text) && votes[counter] == true){
						ballotIndex = i;
						votesIndex = counter;
						found = true;
					}
					counter++;
				}
			}
		}

		//Go into the Ballot of the specific button and call its unVote() method
		votes = ballots.get(ballotIndex).unVote(votes, text, votesIndex);
	}
}
