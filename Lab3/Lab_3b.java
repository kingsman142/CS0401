/*
James Hahn
TA: Emilee Betz

n Bottles of Root Beer
*/

import java.util.Scanner;
import java.lang.Math;

public class Lab_3b{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		int numBottles = 0;
		int counter = 0;
		boolean keepAsking = true;

		do {
			System.out.print("Enter the number of bottles of root beer on the wall (1-99): ");

			if(sc.hasNextInt()){
				numBottles = sc.nextInt();
				if(numBottles < 1 || numBottles > 99){
					System.out.println("The number is not from 1 to 99.");
				}
				else{
					keepAsking = false;
				}
			}
			else{
				sc.next();
				System.out.println("You must enter an integer.");
			}
		} while (keepAsking);

		for(numBottles = numBottles; numBottles > 0; numBottles--){
			counter = numBottles;
			for(counter = numBottles; counter > 0; counter--){
				System.out.print("R");

			}
			System.out.print("\n");
		}

		System.out.println("There are no more bottles of root beer on the wall.");
	}
}
