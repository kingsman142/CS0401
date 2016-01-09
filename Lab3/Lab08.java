import java.io.*;
import java.util.*;

public class Lab08{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int dimension = 0;
		int units = 0;
		World world = new World(10);
		Scientist scientist = new Scientist(world);

		do{
			System.out.print("Enter dimension (1,2,3,4,5) (negative to quit): ");
			dimension = sc.nextInt();
			if(dimension < 0) System.exit(0);
			System.out.print("Enter units to travel (negative for backwards): ");
			units = sc.nextInt();

			int[] output = scientist.move(dimension, units);

			String color = "";
			if(output[5] == 1){
				color = "Lime";
			} else if(output[5] == 2){
				color = "Cerulean";
			} else if(output[5] == 3){
				color = "Goldenrod";
			} else{
				color = "Black";
			}

			System.out.printf("Location: [%d %d %d %d %d] = %s\n", output[0], output[1], output[2], output[3], output[4], color);
		} while(dimension > 0);
	}
}
