import java.util.Scanner;

public class Lab04a{
	public static void displayMenu(){
		System.out.println("\n(1) Buy people ice cream");
		System.out.println("(2) Steal ice cream from people");
		System.out.println("(3) Dream about people eating ice cream");
		System.out.println("(4) Die");
		System.out.print  ("Choice: ");
	}

	public static int calculateUtils(){
		Scanner sc = new Scanner(System.in);
		int input = 0;
		int peopleImpacted = 0;
		int utils = 0;

		do{
			displayMenu();

			if(sc.hasNextInt()){
				input = sc.nextInt();
			} else {
				System.out.println("\nInvalid input.\n");
				continue;
			}

			switch(input){
				case 1:
					System.out.print("How many people were impacted? ");
					peopleImpacted = sc.nextInt();
					System.out.println("\nUtils: +" + 3*peopleImpacted);
					utils += 3*peopleImpacted;
					break;
				case 2:
					System.out.print("How many people were impacted? ");
					peopleImpacted = sc.nextInt();
					System.out.println("\nUtils: " + (-5)*peopleImpacted);
					utils -= 5*peopleImpacted;
					break;
				case 3:
					System.out.print("How many people were impacted? ");
					peopleImpacted = sc.nextInt();
					System.out.println("\nUtils: 0");
					break;
				case 4:
					System.out.println("You died!");
					return utils;
				default:
					System.out.println("Input is out of range.");
					break;
			}
		} while(input != 4);

		return utils;
	}

	public static void printMorality(String name){
		int utils = calculateUtils();

		System.out.println("\nFinal utils: " + utils);
		if(utils > 0) System.out.println(name +"'s life was morally good.");
		else if(utils == 0) System.out.println(name + "'s life was morally neutral.");
		else if(utils < 0) System.out.println(name + "'s life was morally bad.");
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);

		boolean alive = true;
		String name = "";

		System.out.print("Enter your name: ");
		name = sc.next();

		printMorality(name);
	}
}
