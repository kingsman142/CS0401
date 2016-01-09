/*
James Hahn
CS 0401
Bill Laboon
TA: Emilee Betz
Lecture: 1:00-2:15pm MoWe
Lab: 11:00am-12:50pm Mo

Contains the main Assigment 5 class file.  It basically checks to see if args[0], the ballots file, exists or not.  If not, it'll exit.
Then, it creates a Graphics object to start everything else in the program.

CHECK THE FILEIO CLASS FOR THE VOTERS AND BALLOTS FILE FORMAT

----------EXTRA CREDIT----------
+2 points for issue #131
*/

import java.util.*;

public class Assig5{
	public static void main(String[] args) throws Exception{
		//Check if args[0] exists, else exits the program
		FileIO ballotsFile = new FileIO(args[0]);
		ballotsFile.setBallotsFileName(args[0]);
		if(!ballotsFile.checkFileExists()){
			System.out.println(args[0] + " doesn't exist!");
			System.exit(0);
		}

		//Create a Graphics object and starts the main program
		Graphics g = new Graphics();
		g.main(args);
	}
}
