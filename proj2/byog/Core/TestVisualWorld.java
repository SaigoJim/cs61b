package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;
import org.junit.Test;

import static byog.Core.Game.inputParser;
import static org.junit.Assert.*;

public class TestVisualWorld {
    @Test
    public void testOverLap() {
        Room r1 = new Room(new Position(3, 1), 3, 10, 1);
        Room r2 = new Room(new Position(3, 1), 4, 5, 2);
        Room r3 = new Room(new Position(6, 5), 5, 6, 3);
        assertTrue(r1.isOverLap(r2));
        assertFalse(r1.isOverLap(r3));
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 50);

        // Initialize the hexWorld
        // 6787898
        // 234344
        TETile[][] world = new WorldGenerator(80, 50, 1234567).getAnUniverse();
        ter.renderFrame(world);
    }
    @Test
    public void testString() {
        String i = "n5197880843569031643s";
        String[] commands = inputParser(i);

        assertEquals("n", commands[0]);
        assertEquals("5197880843569031643", commands[1]);
        assertEquals("s", commands[2]);

    }
}
