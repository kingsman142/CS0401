public class Scientist{
	private World world;
	private int a = 0;
	private int b = 0;
	private int c = 0;
	private int d = 0;
	private int e = 0;

	public Scientist(World universe){
		world = universe;
	}

	public int[] move(int dimension, int numUnits){
		switch(dimension){
			case 1:
				a = (a + numUnits) % 9;
				break;
			case 2:
				b = (b + numUnits) % 9;
				break;
			case 3:
				c = (c + numUnits) % 9;
				break;
			case 4:
				d = (d + numUnits) % 9;
				break;
			case 5:
				e = (e + numUnits) % 9;
				break;
			default:
				System.exit(0);
		}

		int[] output = new int[] {a, b, c, d, e, world.world[a][b][c][d][e]};
		return output;
	}
}
