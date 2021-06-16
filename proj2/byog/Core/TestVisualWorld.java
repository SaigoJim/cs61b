package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.WorldMaterial.HallWay;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestVisualWorld {
    @Test
    public void testOverLap() {
        Room r1 = new Room(new Position(3, 1), 3, 10);
        Room r2 = new Room(new Position(3, 1), 4, 5);
        Room r3 = new Room(new Position(6, 5), 5, 6);
        assertTrue(r1.isOverLap(r2));
        assertFalse(r1.isOverLap(r3));
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 50);

        // Initialize the hexWorld
        TETile[][] world = new WorldGenerator(80, 50, 32123).getAnUniverse();
        ter.renderFrame(world);
    }
}
