package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
public class TestVisualWorld {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 50);

        // Initialize the hexWorld
        TETile[][] world = new WorldGenerator(80, 50, 32123).createAnRandomUniverse();
        ter.renderFrame(world);
    }
}
