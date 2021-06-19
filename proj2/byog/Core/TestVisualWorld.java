package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;
import org.junit.Test;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
    public void testInputParser() {
        String i = "n5197880843569031643s";
        char[] chars = i.toCharArray();
        int index = 0;

//        String command = inputParser(i, index);
//        index = index + command.length();
//        assertEquals("n5197880843569031643s", command);
//        assertEquals(i.length(), index);
//        System.out.println(i.substring(0, 1));
    }

    public static class World implements Serializable {
        int width;
        int height;

        public World(int x, int y) {
            width = x;
            height = y;
        }
    }

    @Test
    public void testSerializable() {
        World w = new World(2, 3);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream("output.txt"));
            objectOutputStream.writeObject(w);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
