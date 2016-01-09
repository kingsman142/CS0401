import java.util.*;
import java.io.*;

public class Lab09{
	public static int[][] deepCopy(int[][] arr){
		int[][] newArr = new int[arr.length][];

		for(int i = 0; i < arr.length; i++){
			newArr[i] = new int[arr[i].length];

			for(int j = 0; j < arr[i].length; j++){
				newArr[i][j] = arr[i][j];
			}
		}

		return newArr;
	}

	public static void printArray(int[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public static int lowestIndex(int [][] arr, int startIndex, int subArray){
		int index = startIndex;
		int lowValue = arr[subArray][startIndex];

		for(int i = startIndex; i < arr[subArray].length; i++){
			if(arr[subArray][i] < lowValue){
				lowValue = arr[subArray][i];
				index = i;
			}
		}

		return index;
	}

	public static void bubbleSort(int[][] arr, int startArray){
		int numSwaps = 0;

		for(int i = 0; i < arr[startArray].length-1; i++){
			for(int j = 0; j < arr[startArray].length-1; j++){
				if(arr[startArray][j+1] < arr[startArray][j]){
					int temp = arr[startArray][j];
					arr[startArray][j] = arr[startArray][j+1];
					arr[startArray][j+1] = temp;
					numSwaps++;
				}
			}
		}

		System.out.printf("Swaps: %d\n", numSwaps);
	}

	public static void selectionSort(int[][] arr, int startArray){
		int numSwaps = 0;

		for(int i = 0; i < arr[startArray].length-1; i++){
			int temp = arr[startArray][i];
			int lowIndex = lowestIndex(arr, i, startArray);

			if(arr[startArray][lowIndex] < arr[startArray][i]){
				arr[startArray][i] = arr[startArray][lowIndex];
				arr[startArray][lowIndex] = temp;
				numSwaps++;
			}
		}

		System.out.printf("Swaps: %d\n", numSwaps);
	}

	public static void main(String[] args){
		int[][] arr = { {8, 9, 5, 6, 4, 3},
			   {9, 0, 14, 13, 10, 8, 2, 1, 17, 18, 19, 201, 220, 235, 2},
		       {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200 },
		       {22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 1},
		       {20, 18, 13, 12, 11, 9, 6, 5, 4, 3, 2, 1, -87, -900, -9, -909, -911, -80, -44, -32, -1000} };

		int[][] arr2 = deepCopy(arr);
		printArray(arr2);

		System.out.println("\n==========Start Selection Sort==========");
		for(int i = 0; i < arr2.length; i++){
			System.out.println("\nSwapping subarray: " + i);
			selectionSort(arr2, i);
			printArray(arr2);
		}

		System.out.println("\n==========Start Bubble Sort==========");
		for(int i = 0; i < arr.length; i++){
			System.out.println("\nSwapping subarray: " + i);
			bubbleSort(arr, i);
			printArray(arr);
		}
	}
}
