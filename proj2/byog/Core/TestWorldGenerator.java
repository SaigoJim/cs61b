package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class TestWorldGenerator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        WorldGenerator worldGen = new WorldGenerator(WIDTH, HEIGHT, 12345);
        TETile[][] finalWorldFrame = worldGen.randomWorld();

        ter.renderFrame(finalWorldFrame);
    }
}
