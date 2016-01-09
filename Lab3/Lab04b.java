import java.util.Scanner;

public class Lab04b{
	public static void displayChoices(){
		System.out.println("5. Self-actualization");
		System.out.println("4. Self-esteem");
		System.out.println("3. Love and Belonging");
		System.out.println("2. Safety");
		System.out.println("1. Physiological");
	}

	public static void hierarchyLevel(){
		Scanner sc = new Scanner(System.in);
		int choice = 0;

		displayChoices();
		System.out.print("\nEnter your level of life fulfillment: ");
		choice = sc.nextInt();
		System.out.println("");
		switch(choice){
			case 5:
				System.out.println("Self-actualization");
			case 4:
				System.out.println("Self-esteem");
			case 3:
				System.out.println("Love and Belonging");
			case 2:
				System.out.println("Safety");
			case 1:
				System.out.println("Physiological");
		}
	}

	public static void main(String[] args){
		hierarchyLevel();
	}
}
