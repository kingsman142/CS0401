public class World{
	private int dimensions = 0;
	int[][][][][] world;

	public World(int dimension){
		dimensions = dimension;
		world = new int[dimensions][dimensions][dimensions][dimensions][dimensions];
	}

	public void generateUniverse(){
		int colorValue = 0;

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				for(int k = 0; k < 10; k++){
					for(int l = 0; l < 10; l++){
						for(int m = 0; m < 10; m++){
							colorValue = (i + j + k + l + m) % 10;
							world[i][j][k][l][m] = colorValue;
						}
					}
				}
			}
		}
	}
}
