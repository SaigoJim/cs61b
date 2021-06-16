package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.WorldMaterial.HallWay;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {
    int WIDTH;
    int HEIGHT;
    private final Random RANDOM;
    TETile[][] UNIVERSE;
    public WorldGenerator(int w, int h, int seed) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(seed);
        UNIVERSE = createAnRandomUniverse();
    }
    public TETile[][] getAnUniverse() {
        return UNIVERSE;
    }

    private TETile[][] createAnRandomUniverse() {
        TETile[][] universe = new TETile[WIDTH][HEIGHT];
        universe = initializeTiles(universe);
        List<Room> rooms = getRandomRooms();
        List<HallWay> hallWays = getHallways(rooms);
        universe = drawInRoomsAndHallways(universe, rooms, hallWays);
        return universe;
    }
    private TETile[][] initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }
    private Position creatRandomPosition() {
        return new Position(RANDOM.nextInt(WIDTH - 10), RANDOM.nextInt(HEIGHT - 10));
    }
    private Room creatRandomRoom() {
        Position p = creatRandomPosition();
        int w = RANDOM.nextInt(Math.min(WIDTH - p.xPos, 20));
        w = Math.max(6, w);
        int h = RANDOM.nextInt(Math.min(HEIGHT - p.yPos, 20));
        h = Math.max(6, h);
        return new Room(p, w, h);
    }
    private List<Room> getRandomRooms() {
        int randomNumber = RANDOM.nextInt(20);
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < Math.max(100, randomNumber); i += 1) {
            Room r = creatRandomRoom();
            boolean isOverLapped = false;
            for (Room o : rooms) {
                if (r.isOverLap(o)) {
                    isOverLapped = true;
                    break;
                }
            }
            if (!isOverLapped) {
                rooms.add(r);
            }
        }
        return rooms;
    }
    private List<HallWay> getHallways(List<Room> rooms) {
        //HallWay hw1 = new HallWay(rooms.get(0), rooms.get(1));
        ArrayList<HallWay> hallWays = new ArrayList<>();
        Room r = rooms.get(0);
        for (int i = 1; i < rooms.size(); i += 1) {
            hallWays.add(new HallWay(r, rooms.get(i)));
            r = rooms.get(i);
        }
        return hallWays;
    }
    private TETile[][] drawInRoomsAndHallways(TETile[][] tiles, List<Room> rooms, List<HallWay> hallWays) {
        for (Room r : rooms) {
            r.drawRoom(tiles);
        }
        for (HallWay h : hallWays) {
            h.drawHallWay(tiles);
        }
        return tiles;
    }

}
