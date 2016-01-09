/*
James Hahn
CS0401 (Laboon)
TA: Emilee Betz
Lab: Mo 11:00am-12:50pm

This file contains the class Die for Assig2.java, Over and Under betting game
*/

import java.util.*;

public class Die{
	//Initialize variables for die
	private int roll;
	private Random diceRoll = new Random();

	public Die(){

	}

	public int rollDie(){
		roll = diceRoll.nextInt(6) + 1;
		return roll;
	}
}
