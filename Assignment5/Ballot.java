/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

Contains the Ballot class file for Assignment 5.  One Ballot contains each voting option (For example, Favorite Country), which
is stored in a JLable.  Also associated with each Ballot are all of the JToggleButtons associated with each choice.  Following from the
above example, sample JToggleButton names would be USA, Germany, France, etc.  The JLabel and all of the JToggleButtons are added onto
a JPanel, which is fed into the Graphics class, which adds the panel to the window.

Each Ballot has an ID, its buttons, its label, and a panel.

Lastly, Ballot.java inherits (extends) JPanel.
*/

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Ballot extends JPanel{
	//Declare private class variables
	private String question = "";
	private JPanel ballotPanel;
	private JToggleButton[] ballotButtons;
	private JLabel ballotQuestion;
	private ButtonListener buttonListener;
	private String[] ballotChoices;
	private int[] ballotVotes; //Keeps track of which buttons in the Ballot are selected (true) or not selected (false)
	private String ballotID;

	public Ballot(String questionInput, String[] choices, String id){
		//Initialize all of the important variables
		question = questionInput;
		ballotChoices = choices.clone();
		ballotID = id;

		ballotVotes = new int[ballotChoices.length];
		ballotButtons = new JToggleButton[choices.length];
		ballotPanel = new JPanel();
		ballotQuestion = new JLabel(question);
		buttonListener = new ButtonListener();

		//Set the layout to be a BoxLayout
		ballotPanel.setLayout(new BoxLayout(ballotPanel, BoxLayout.Y_AXIS));

		//Add all of the ballotQuestions (buttons) to the ballot's panel
		ballotPanel.add(ballotQuestion);
		for(int i = 0; i < ballotButtons.length; i++){
			ballotButtons[i] = new JToggleButton(ballotChoices[i]);
			ballotButtons[i].addActionListener(buttonListener);
			ballotPanel.add(ballotButtons[i]);
		}
	}

	//Get the number of buttons associated with the Ballot
	public int getNumButtons(){
		return ballotButtons.length;
	}

	//Disable all buttons associated with the ballot
	public void disableButtons(){
		for(JToggleButton button: ballotButtons){
			button.setEnabled(false);
		}
	}

	//Enable all buttons associated with the ballot
	public void enableButtons(){
		for(JToggleButton button: ballotButtons){
			button.setEnabled(true);
		}
	}

	//Reset all buttons associated with the ballot (after a voter casts their vote, this is called to make sure all the buttons
	//are normal).
	public void resetButtons(){
		for(JToggleButton button: ballotButtons){
			button.setForeground(Color.BLACK);
			button.setSelected(false);
		}
	}

	//For a specific button with ButtonText text, find it in this Ballot and make sure its position
	//in the votes array is false (to prevent untrue ballot vote results).
	public boolean[] unVote(boolean[] votes, String text, int votesIndex){
		for(int i = 0; i < ballotButtons.length; i++){
			if(ballotButtons[i].getText().equals(text) && votes[votesIndex] == true){
				votes[votesIndex] = false;
			}
		}

		buttonListener.votes = votes;
		//buttonListener.replaceVotes(votes);
		return votes;
	}

	//If a button is selected on this ballot and another button on this ballot is selected (clicked), then turn off the original button.
	//Prevents the ballot from having 2 choices selected at once.
	public void switchButtons(boolean[] votes, int votesIndexOfSelected, int indexOfSelected, int counter, int counterAtBeginningOfBallot, String text){
		if(votesIndexOfSelected == -1 || indexOfSelected == -1) return;

		for(int i = 0; i < ballotButtons.length; i++){
			if(votes[counterAtBeginningOfBallot] == true && i != indexOfSelected){
				for(int j = 0; j < ballotButtons.length; j++){
					if(ballotButtons[j].getText().equals(text)){
						ballotButtons[i].setForeground(Color.BLACK);
						ballotButtons[i].setSelected(false);
						votes[counterAtBeginningOfBallot] = false;
					}
				}
				votes[votesIndexOfSelected] = true;
			}
			counterAtBeginningOfBallot++;
		}
	}

	//Get the panel of the Ballot
	public JPanel getPanel(){
		//Threw this statement in here because this method only executes once per ballot, which makes it safe to initialize the buttons
		//without initializing them 100 times or more.
		buttonListener.initializeButtons();
		return ballotPanel;
	}

	//Get the buttons associated with the Ballot
	public JToggleButton[] getButtons(){
		return ballotButtons;
	}

	/*Update this specific Ballot's file with the correct number of votes per ballot choice.

	-----ballotID.txt-----
	Option1:numberOfVotes
	Option2:numberOfVotes
	Option3:numberOfVotes
	*/
	public void updateFile(){
		try{
			File file = new File(ballotID + ".txt");
			File tempFile = new File("temp.txt");
			PrintWriter writer = new PrintWriter(tempFile);
			ButtonListener buttonListener = new ButtonListener();
			ArrayList<JToggleButton> buttons = buttonListener.getButtons();
			boolean[] votes = buttonListener.getVotes();

			//If this ballot file already exists (from past voters for example), read in each line, and increase the number
			//of votes by 1 if the ballot choice at the time is selected.
			if(file.exists()){
				Scanner sc = new Scanner(file);
				for(int i = 0; i < ballotButtons.length; i++){
					String[] line = null;
					if(sc.hasNextLine()) line = sc.nextLine().split(":");

					for(int j = 0; j < buttons.size(); j++){
						if(buttons.get(j).getText().equals(ballotButtons[i].getText()) && votes[j] == true){
							ballotVotes[i] = Integer.parseInt(line[1]) + 1;
						} else if(buttons.get(j).getText().equals(ballotButtons[i].getText()) && votes[j] == false){
							ballotVotes[i] = Integer.parseInt(line[1]);
						}
					}

					writer.println(ballotChoices[i] + ":" + ballotVotes[i]);
				}
				//Close file accessors for safety
				writer.close();
				sc.close();
				file.delete();
				tempFile.renameTo(file); //Replace old ballot file with this new ballot file
			} else{
				//If the ballots file DOES NOT exist (because of first voter for example), print all of the data to this
				//new file.
				for(int i = 0; i < ballotButtons.length; i++){
					for(int j = 0; j < buttons.size(); j++){
						if(buttons.get(j).getText().equals(ballotButtons[i].getText()) && votes[j] == true){
							ballotVotes[i] = 1;
						}
					}
					writer.println(ballotChoices[i] + ":" + ballotVotes[i]);
				}
				//Close file accessors for safety
				writer.close();
				tempFile.renameTo(file);
			}
		//If in some way any of the above fails, just return
		} catch(Exception e){
			return;
		}
	}
}
