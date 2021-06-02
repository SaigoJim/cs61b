package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;

public class WorldGenerator {
    private int WIDTH;
    private int HEIGHT;
    private int seed;

    public WorldGenerator(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        this.seed = seed;
    }

    private void initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private List<Room> creatAListOfRooms(int seed) {
        return null;
    }

    private List<Hallway> creatAListOfHallwaysConnectingRooms(int seed, List<Room> rooms) {
        return null;
    }

    private void addRoomsAndHallways(TETile[][] tiles, List<Room> rooms, List<Hallway> hallways) {
        for (Room r : rooms) {
            r.drawRoom(tiles);
        }
        for (Hallway h : hallways) {
            h.drawHallway(tiles);
        }
    }

    public TETile[][] randomWorld() {
        TETile[][] returnedWorld = new TETile[WIDTH][HEIGHT];
        initializeTiles(returnedWorld);

        /*List<Room> roomList = new ArrayList<>();
        roomList.add(new Room(new Position(10, 20), 10, 10));
        for (Room r : roomList) {
            r.drawRoom(returnedWorld);
        }*/

        List<Room> roomList = creatAListOfRooms(seed);
        List<Hallway> hallwayList = creatAListOfHallwaysConnectingRooms(seed, roomList);

        addRoomsAndHallways(returnedWorld, roomList, hallwayList);
        return returnedWorld;
    }
}
