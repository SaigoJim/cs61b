package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {
    int WIDTH;
    int HEIGHT;
    private final Random RANDOM;
    public WorldGenerator(int w, int h, int seed) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(seed);
    }
    public class Position {
        int xPos;
        int yPos;
        Position(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }
    public class Room {
        Position pos;
        int width;
        int height;
        public Room(Position p, int w, int h) {
            pos = p;
            width = w;
            height = h;
        }
    }
    public class HallWay {
        Position source;
        Position target;
        int length;
        public HallWay(Position s, Position t, int len) {
            source = s;
            target = t;
            length = len;
        }
    }

    private Position creatRandomPosition() {
        return new Position(RANDOM.nextInt(WIDTH - 20), RANDOM.nextInt(HEIGHT-20));
    }
    private Room creatRandomRoom() {
        Position p = creatRandomPosition();
        int w = RANDOM.nextInt(20);
        int h = RANDOM.nextInt(20);
        return new Room(p, w, h);
    }

    private void initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
    private void addRoom(TETile[][] tiles, Room room) {
        for (int rowNum = 0; rowNum < room.height; rowNum += 1) {
            int startX = room.pos.xPos;
            int startY = room.pos.yPos + rowNum;
            int rowLength = room.width;
            addRow(tiles, new Position(startX, startY), rowLength, Tileset.FLOOR);
        }
    }

    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void addRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            tiles[p.xPos + i][p.yPos] = tile;
        }
    }
    private void addRooms(TETile[][] tiles) {
        int randomNumber = RANDOM.nextInt(10);
        for (int i = 0; i < 30; i += 1) {
            Room r = creatRandomRoom();
            addRoom(tiles, r);
        }
    }
    public TETile[][] createAnRandomUniverse() {
        TETile[][] universe = new TETile[WIDTH][HEIGHT];
        initializeTiles(universe);
        addRooms(universe);
        return universe;
    }
}
