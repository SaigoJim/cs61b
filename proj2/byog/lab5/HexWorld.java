package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.MOUNTAIN;
        }
    }

    /** Initialize tiles with setting background nothing. */
    private static void initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** */
    private static void addHexagon(TETile[][] tiles, int xPos, int yPos, int length,TETile tile) {
        int lPosX = xPos;
        int lPosY = yPos;
        addLowerHalf(tiles, lPosX, lPosY, length, tile);

        int uPosX = xPos;
        int uPosY = yPos + 2*length - 1;
        addUpperHalf(tiles, uPosX, uPosY, length, tile);
    }

    private static void addLowerHalf(TETile[][] tiles, int xPos, int yPos, int length,TETile tile){
        for (int y = yPos, i = 0; y < yPos + length; y += 1, i += 1) {
            int begin = xPos - i;
            int end = xPos + length + i;
            for (int x = begin; x < end; x += 1) {
                tiles[x][y] = tile;
            }
        }
    }

    private static void addUpperHalf(TETile[][] tiles, int xPos, int yPos, int length,TETile tile) {
        for (int y = yPos, i = 0; y > yPos - length; y -= 1, i += 1) {
            int begin = xPos - i;
            int end = xPos + length + i;
            for (int x = begin; x < end; x += 1) {
                tiles[x][y] = tile;
            }
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Initialize the hexWorld
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        initializeTiles(hexWorld);

        addHexagon(hexWorld, 40, 40, 4, Tileset.FLOWER);

        ter.renderFrame(hexWorld);
    }
}
