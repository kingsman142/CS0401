import java.util.*;

public class Lab05{
	static void RollDice(int numRolls, Random rollValues){
		int numTwos = 0;
		int numThrees = 0;
		int numFours = 0;
		int numFives = 0;
		int numSixes = 0;
		int numSevens = 0;
		int numEights = 0;
		int numNines = 0;
		int numTens = 0;
		int numElevens = 0;
		int numTwelves = 0;
		int value = 0;

		for(int i = 0; i < numRolls; i++){
			value = rollValues.nextInt(11) + 2;
			switch(value){
				case 2:
					numTwos++; break;
				case 3:
					numThrees++; break;
				case 4:
					numFours++; break;
				case 5:
					numFives++; break;
				case 6:
					numSixes++; break;
				case 7:
					numSevens++; break;
				case 8:
					numEights++; break;
				case 9:
					numNines++; break;
				case 10:
					numTens++; break;
				case 11:
					numElevens++; break;
				case 12:
					numTwelves++; break;
			}
		}

		System.out.println("\nTwos: " + numTwos + "  " + numTwos + "/" + numRolls);
		System.out.println("Threes: " + numThrees + "  " + numThrees + "/" + numRolls);
		System.out.println("Fours: " + numFours + "  " + numFours + "/" + numRolls);
		System.out.println("Fives: " + numFives + "  " + numFives + "/" + numRolls);
		System.out.println("Sixes: " + numSixes + "  " + numSixes + "/" + numRolls);
		System.out.println("Sevens: " + numSevens + "  " + numSevens + "/" + numRolls);
		System.out.println("Eights: " + numEights + "  " + numEights + "/" + numRolls);
		System.out.println("Nines: " + numNines + "  " + numNines + "/" + numRolls);
		System.out.println("Tens: " + numTens + "  " + numTens + "/" + numRolls);
		System.out.println("Elevens: " + numElevens + "  " + numElevens + "/" + numRolls);
		System.out.println("Twelves: " + numTwelves + "  " + numTwelves + "/" + numRolls);
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Random randomNumbers = new Random();
		int numRolls = 0;
		String continueRolling = "yes";
		boolean keepGoing = true;

		do{
			System.out.print("\nRolls: ");
			numRolls = sc.nextInt();

			RollDice(numRolls, randomNumbers);

			System.out.print("\nDo you want to continue rolling? ");
			continueRolling = sc.next();
			continueRolling = continueRolling.toLowerCase();
		} while(continueRolling == "yes");
	}
}
