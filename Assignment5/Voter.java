/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

This is the Voter class for Assignment 5.  It is very short and straightforward.  It only contains 3 value for each voter; his/her id, name, and
status of whether of not the voter has voted (true or false).
*/

public class Voter{
	private String id;
	private String name;
	private String voted;

	public Voter(String inputID, String inputName, String inputVoted){
		id = inputID;
		name = inputName;
		voted = inputVoted;
	}

	public String getVoterID(){
		return id;
	}

	public String getVoterName(){
		return name;
	}

	public String getVotedStatus(){
		return voted;
	}
}
