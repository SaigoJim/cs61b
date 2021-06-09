package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.HexWorld;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestVisualWorld {
    @Test
    public void testWorld() {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 80);

        // Initialize the hexWorld
        TETile[][] world = new WorldGenerator(80, 80, 12345).createAnRandomUniverse();
        ter.renderFrame(world);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 80);

        // Initialize the hexWorld
        TETile[][] world = new WorldGenerator(80, 80, 12345).createAnRandomUniverse();
        ter.renderFrame(world);
    }
}
