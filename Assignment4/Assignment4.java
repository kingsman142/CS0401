/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm
Lab: 11:00am-12:50pm

This is the main class file for Assignment4.  Assignment 4 is all about creating a quiz
that allows the user to answer true-false, fill in the blank, and multiple choice questions
from a file entered at the command line.  Quiz.txt is already supplied for the user as an
example quiz, but the user may create his/her own quiz if they desire.

This program will:
(1) Ask each question from the file individually
(2) Report to the user whether they answered correct or incorrect and
	their percentage of questions answered correctly.
(3) It will show the user the statistics for each question (attempts, times answered correctly, and percentage)
(4) Show the user the hardest question based on lowest percentage correct.
(5) Show the user the easiest question based on highest percentage correct.
(6) Lastly, write back to the same quiz file all of the updated information.

***EXTRA CREDIT IN THIS ASSIGNMENT***
- Allow for different types of questions in your quiz
- After the quiz is finished, sort the questions based on percentage of users who got them correct,
  showing them from highest percentage down to lowest percentage.
*/

import java.util.*;
import java.io.*;

public class Assignment4{

	//Start the quiz for the user
	public static void startQuiz(ArrayList<Question> questions){
		Scanner sc = new Scanner(System.in);

		//For each question, print out the question, all of the possible answers, and ask the user for their guess
		//Checks for the user
		for(int i = 0; i < questions.size(); i++){
			Question question = questions.get(i);
			int questionType = question.getQuestionType();
			String userAnswerString = "";

			System.out.printf("\nQuestion %d: ", i+1);
			question.printQuestion();
			question.printAnswers();

			//If it's a fill in the blank question
			if(questionType == 0){
				System.out.print("\nYour answer (fill in blank): ");
				userAnswerString = sc.next();
				question.answerQuestion(userAnswerString);
			//If it's a true-false question
			} else if(questionType == 1){
				System.out.print("\nYour answer (T or F): ");
				userAnswerString = sc.next();
				userAnswerString = userAnswerString.toUpperCase();
				while(!userAnswerString.equals("T") && !userAnswerString.equals("F")){
					System.out.println("Please enter either T or F!");
					System.out.print("\nYour answer (T or F): ");
					userAnswerString = sc.next();
					userAnswerString = userAnswerString.toUpperCase();
				}

				question.answerQuestion(userAnswerString);
			//If it's a multiple choice question
			} else{
				int userAnswerInt = 0;
				boolean firstTime = true;
				while(userAnswerInt < 1 || userAnswerInt > question.getNumAnswers()){
					if(firstTime == false) System.out.println("Please enter a valid number!");
					System.out.print("\nYour answer (enter a number): ");
					while(!sc.hasNextInt()){
						System.out.println("Please enter a valid number!");
						System.out.print("\nYour answer (enter a number): ");
						sc.next();
					}

					firstTime = false;
					userAnswerInt = Integer.parseInt(sc.next());
				}

				userAnswerString = String.valueOf(userAnswerInt);
				question.answerQuestion(userAnswerString);
			}
		}
	}

	//Go through all of the questions and find the highest percentage (easiest question).
	//Returns the index.
	public static int getHighestPercentage(ArrayList<Question> questions, int startIndex){
		double highestPercentage = questions.get(startIndex).getPercentage();
		int index = startIndex;
		for(int i = startIndex; i < questions.size(); i++){
			if(questions.get(i).getPercentage() > highestPercentage){
				highestPercentage = questions.get(i).getPercentage();
				index = i;
			}
		}

		return index;
	}

	//Go through all of the questions and find the lowest percentage (hardest question).
	//Returns the index.
	public static int getLowestPercentage(ArrayList<Question> questions, int startIndex){
		double lowestPercentage = questions.get(startIndex).getPercentage();
		int index = 0;
		for(int i = startIndex; i < questions.size(); i++){
			if(questions.get(i).getPercentage() < lowestPercentage){
				lowestPercentage = questions.get(i).getPercentage();
				index = i;
			}
		}

		return index;
	}

	//Sorts all questions from highest question to lowest percentage
	public static void sortQuestions(ArrayList<Question> questions){
		int highestIndex;

		//Starts sorting by searching through the entire array...
		//and slowly shortens the number of items it has to checks
		for(int i = 0; i < questions.size()-1; i++){
			//Temp variable so the program doesn't lose this question
			Question temp = questions.get(i);

			//Find the index with the highest percentage
			highestIndex = getHighestPercentage(questions, i+1);

			//Swap the two Questions in the arraylist
			//System.out.println(questions.get(highestIndex).getPercentage() + " and " + questions.get(i).getPercentage());
			if((questions.get(highestIndex).getPercentage() > questions.get(i).getPercentage()) && (highestIndex != i)){
				questions.add(i+1, questions.get(highestIndex));
				questions.remove(i);
				questions.add(highestIndex+1, temp);
				questions.remove(highestIndex);
			}

			// for(Question question: questions){
			// 	System.out.printf("%s (%.2f%%)\n", question.getQuestion(), question.getPercentage());
			// }
			// System.out.printf("HighestIndex: %d\n\n", highestIndex);
		}

		//Prints out all of the questions in order
		System.out.println("------Questions from Highest percentage to Lowest percentage------\n");
		for(Question question: questions){
			System.out.printf("%s (%.2f%%)\n", question.getQuestion(), question.getPercentage());
		}
	}

	//At the end of the quiz, write all of the necessary information of each question...
	//back to the file in the correct format.
	public static void writeToFile(File quizFile, ArrayList<Question> questions) throws Exception{
		PrintWriter writer = new PrintWriter(quizFile);

		for(Question question: questions){
			writer.println(question.getQuestion());
			writer.println(question.getNumAnswers());
			for(String answer: question.getAnswers()){
				writer.println(answer);
			}
			writer.println(question.getCorrectAnswerIndex());
			writer.println(question.getTotalAttempts());
			writer.println(question.getTimesAnsweredCorrect());
		}

		writer.close();
	}

	//Show all of the results from the quiz.
	//(1) Percentage of questions correct
	//(2) Easiest question and hardest question
	//(3) For each question: times tried, times correct, and percentage correct
	public static void showResults(ArrayList<Question> questions){
		System.out.println(" ___________________________________________________________________________________________________ ");
		System.out.println("|                                                                                                   |");
		System.out.println("|                                           Your Results                                            |");
		System.out.println("|___________________________________________________________________________________________________|");

		for(Question question: questions){
			System.out.println(question.toString());
		}

		System.out.printf("\n----------------------------------------------\nPercentage of questions correct: %.2f%% (%d/%d)\n----------------------------------------------\n", (double) questions.get(0).getNumCorrect()/questions.size()*100.0, questions.get(0).getNumCorrect(), questions.size());

		for(Question question: questions){
			System.out.print("\nQuestion: ");
			question.printQuestion();
			question.calculateTotalPercentage();
			System.out.printf("\tTimes tried: %d\n", question.getTotalAttempts());
			System.out.printf("\tTimes correct: %d\n", question.getTimesAnsweredCorrect());
			System.out.printf("\tPercentage correct: %.2f%%\n", question.getPercentage());
		}

		System.out.println("\n------------------------------------------------------------------");
		System.out.print("Easiest question: ");
		questions.get(getHighestPercentage(questions, 0)).printQuestion();
		System.out.printf("\tPercentage correct: %.2f%%\n", questions.get(getHighestPercentage(questions, 0)).getPercentage());
		System.out.print("\nHardest question: ");
		questions.get(getLowestPercentage(questions, 0)).printQuestion();
		System.out.printf("\tPercentage correct: %.2f%%\n", questions.get(getLowestPercentage(questions, 0)).getPercentage());
		System.out.println("------------------------------------------------------------------");
	}

	public static void main(String[] args) throws Exception{
		//Creates a File object from the command-line argument
		File quizFile = new File(args[0]);
		Scanner quizReader = new Scanner(quizFile);
		ArrayList<Question> questions = new ArrayList<Question>();

		//If the file from the command-line doesn't exist, automatically exit
		if(!quizFile.exists()){
			System.out.println("That file does not exist! Exiting!");
			System.exit(0);
		}

		//Go through the entire quiz file and grab the input...
		//to create a new Question object every time it finds a question.
		while(quizReader.hasNextLine()){
			String questionString = quizReader.nextLine();
			int numAnswers = Integer.valueOf(quizReader.nextLine());
			ArrayList<String> answers = new ArrayList<String>();
			for(int i = 0; i < numAnswers; i++){
				answers.add(quizReader.nextLine());
			}

			int answerIndex = Integer.parseInt(quizReader.nextLine());
			int attempts = Integer.parseInt(quizReader.nextLine());
			int correctAnswers = Integer.parseInt(quizReader.nextLine());
			Question newQuestion = new Question(questionString, numAnswers, answers, answerIndex, attempts, correctAnswers);
			questions.add(newQuestion);
		}

		startQuiz(questions);
		showResults(questions);
		writeToFile(quizFile, questions);
		sortQuestions(questions);
	}
}
