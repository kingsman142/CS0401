/*
James Hahn
TA: Emilee Betz

Calculates how many steps taken to get from 0.0m to 10.0m by halving the distance
*/

public class Lab_3a{
	public static void main(String[] args){
		double distance = 0.00;
		int stepsTaken = 0;

		for(distance = 10.00; distance>0.0; distance /= 2.00){
			stepsTaken++;
		}

		System.out.println("Steps taken: " + stepsTaken); //ANSWER SHOULD BE 1078
	}
}
