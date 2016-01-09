/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: MWe 1:00-2:15pm
Lab: M 11:00am-12:50pm

This file contains the Question class for Assignment4.  It stores all of the necessary information required for every StringBuilder
question in the quiz.
*/

import java.util.*;

public class Question{
	private String question = "";
	private ArrayList<String> answers = new ArrayList<String>();
	private String userAnswerString = "";
	private int userAnswerInt = 0;
	private int numAnswers = 0;
	private int correctAnswer = 0;
	private int totalAttempts = 0;
	private int answeredCorrect = 0;
	private double percentage = 0.0;
	private boolean trueFalse = false;
	private boolean fillBlank = false;
	private boolean correct = false;

	//static variable over all Questions to determine how many have been answered correctly
	private static int questionsCorrect = 0;

	public Question(String questString, int numOfAnswers, ArrayList<String> allAnswers, int answerIndex, int attempts, int correctAnswers){
		question = questString;
		numAnswers = numOfAnswers;
		correctAnswer = answerIndex;
		totalAttempts = attempts;
		answeredCorrect = correctAnswers;
		answers = allAnswers;

		if(numAnswers == 1){
			fillBlank = true;
		} else if(numAnswers == 2){
			trueFalse = true;
		}
	}

	//Returns amount of times this question has been attempted
	public int getTotalAttempts(){
		return totalAttempts;
	}

	//Returns amount of times this question was answered correct
	public int getTimesAnsweredCorrect(){
		return answeredCorrect;
	}

	//Returns the total number of answers to the question (2 in true false, 1 in fill blank, ...)
	public int getNumAnswers(){
		return numAnswers;
	}

	//Returns the static variable referring to how many questions in the quiz were...
	//answered correctly.
	public int getNumCorrect(){
		return questionsCorrect;
	}

	//Get the percentage correct for the question
	public double getPercentage(){
		return percentage;
	}

	//Get the string variable of the question
	public String getQuestion(){
		return question;
	}

	//Get the arraylist containing all of the possible answers for the question
	public ArrayList<String> getAnswers(){
		return answers;
	}

	//Get the index of the correct answer for the question
	public int getCorrectAnswerIndex(){
		return correctAnswer;
	}

	//0 = fill in the blank question
	//1 = true false question
	//2 = multiple choice question
	public int getQuestionType(){
		if(fillBlank) return 0;
		else if(trueFalse) return 1;
		else return 2;
	}

	public void printQuestion(){
		System.out.println(question);
	}

	//Print out different statements for different questions
	public void printAnswers(){
		int i = 1;
		for(String answer: answers){
			if(!trueFalse && !fillBlank) System.out.println("( " + i + " ) " + answer);
			else if(trueFalse) System.out.println(answer);
			i++;
		}
	}

	//Takes in the user's input for use in correctIncorrect()
	public void answerQuestion(String answer){
		if(!fillBlank && !trueFalse){
			userAnswerInt = Integer.parseInt(answer);
		} else{
			userAnswerString = answer;
		}
	}

	//Determines if the user answered the question correctly or incorrectly
	private void correctIncorrect(){
		//Multiple choice question
		if(!fillBlank && !trueFalse){
			if(answers.get(userAnswerInt-1).equals(answers.get(correctAnswer-1))){
				correct = true;
				answeredCorrect();
			} else{
				correct = false;
				answeredIncorrect();
			}
		//Fill in the blank question
		} else if(fillBlank){
			if(userAnswerString.toLowerCase().equals(answers.get(0).toLowerCase())){
				correct = true;
				answeredCorrect();
			} else{
				correct = false;
				answeredIncorrect();
			}
		//True false question
		} else if(trueFalse){
			if((correctAnswer == 2 && userAnswerString.equals("F")) || (correctAnswer == 1 && userAnswerString.equals("T"))){
				correct = true;
				answeredCorrect();
			} else{
				correct = false;
				answeredIncorrect();
			}
		}
	}

	//Calculates the percentage of times that the question was...
	//answered correctly.
	public void calculateTotalPercentage(){
		percentage = (double) answeredCorrect/totalAttempts*100.0;
	}

	//The user answered the question correctly
	public void answeredCorrect(){
		totalAttempts++;
		questionsCorrect++;
		answeredCorrect++;
	}

	//The user answered the question incorrectly
	public void answeredIncorrect(){
		totalAttempts++;
	}

	//Print the question, the user's guess, the correct answer, and whether it was answered correctly or not
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n" + question);
		builder.append("\nCorrect answer: " + answers.get(correctAnswer-1));

		if(!fillBlank && !trueFalse){
			builder.append("\nYour guess: " + answers.get(userAnswerInt-1));
		} else{
			builder.append("\nYour guess: " + userAnswerString);
		}

		correctIncorrect(); //Calculates whether the question was answered correct or incorrect
		if(correct){
			builder.append("\nCorrect!");
		} else if(!correct){
			builder.append("\nIncorrect.");
		}

		return builder.toString();
	}
}
