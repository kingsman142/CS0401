import java.util.*;
import java.io.*;

public class Lab07{
	public static String[] martinRutabagas(String[] garden, int amount){
		int counter = 0;

		for(int i = 0; i < garden.length; i++){
			if(garden[i] == "***" && counter < amount){
				garden[i] = Integer.toString(amount);
				counter++;
			}

			if(!garden[garden.length-1].equals("***")){
				garden = resizeMartin(garden);
				System.out.println("Martin new garden size: " + garden.length);
			}
		}

		return garden;
	}

	public static String[] panglossRutabagas(String[] garden, int amount){
		int counter = 0;

		for(int i = 0; i < garden.length; i++){
			if(garden[i] == "***" && counter < amount){
				garden[i] = Integer.toString(amount);
				counter++;
			}

			if(!garden[garden.length-1].equals("***")){
				garden = resizePangloss(garden);
				System.out.println("Pangloss new garden size: " + garden.length);
			}
		}

		return garden;
	}

	public static String[] resizePangloss(String[] garden){
		int newGardenLength = garden.length*2;

		String[] newGarden = new String[newGardenLength];

		for(int i = 0; i < garden.length-1; i++){
			newGarden[i] = garden[i];
		}

		return newGarden;
	}

	public static String[] resizeMartin(String[] garden){
		int newGardenLength = garden.length + 2;

		String[] newGarden = new String[newGardenLength];

		for(int i = 0; i < garden.length-1; i++){
			newGarden[i] = garden[i];
		}

		return newGarden;
	}

	public static void main(String[] args){
		String[] gardenMartin = new String[5];
		String[] gardenPangloss = new String[5];
		Random generator = new Random();
		int rutabagasGrown = 0;
		int season = 1;

		for(int i = 0; i < 5; i++){
			gardenMartin[i] = "***";
			gardenPangloss[i] = "***";
		}

		while(season <= 40){
			rutabagasGrown = generator.nextInt(5);

			System.out.printf("Season: %d, %d rutabaga(s)\n", season, rutabagasGrown);
			gardenMartin = martinRutabagas(gardenMartin, rutabagasGrown);
			gardenPangloss = panglossRutabagas(gardenPangloss, rutabagasGrown);

			season++;
		}

		for(int i = 0; i < gardenMartin.length; i++){
			System.out.println(gardenMartin[i]);
		}

		for(int i = 0; i < gardenPangloss.length; i++){
			System.out.println(gardenPangloss[i]);
		}
	}
}
