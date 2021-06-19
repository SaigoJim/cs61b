package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.WorldMaterial.HallWay;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator implements Serializable {
    private static final long serialVersionUID = 48787879342344L;
    private int WIDTH;
    private int HEIGHT;
    private final Random RANDOM;
    private TETile[][] UNIVERSE;
    private Position player;
    private Position treasureSpot;
    public WorldGenerator(int w, int h) {
        RANDOM = new Random(0);
        WIDTH = w;
        HEIGHT = h;
        UNIVERSE = new TETile[WIDTH][HEIGHT];
        UNIVERSE = initializeTiles(UNIVERSE);
    }
    public WorldGenerator(int w, int h, long seed) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(seed);
        UNIVERSE = createAnRandomUniverse();
    }
    public TETile[][] getAnUniverse() {
        return UNIVERSE;
    }
    public void movePlayer(char move) {
        int x = player.getxPos();
        int y = player.getyPos();
        if (move == 'W' || move == 'w') {
            movePlayer(x, y + 1);
        } else if (move == 'S' || move == 's') {
            movePlayer(x, y - 1);
        } else if (move == 'A' || move == 'a') {
            movePlayer(x - 1, y);
        } else if (move == 'D' || move == 'd') {
            movePlayer(x + 1, y);
        }
    }
    private void movePlayer(int x, int y) {
        if (!isValidMove(x, y)) {
            return;
        }
        cleanPlayer();
        player = new Position(x, y);
        UNIVERSE = drawInPlayerAndTreasure(UNIVERSE, player, treasureSpot);
    }
    private boolean isValidMove(int x, int y) {
        //return UNIVERSE[x][y] == Tileset.FLOOR;
        TETile l = UNIVERSE[x][y];
        return !UNIVERSE[x][y].equals(Tileset.WALL);
    }
    private void cleanPlayer() {
        UNIVERSE[player.getxPos()][player.getyPos()] = Tileset.FLOOR;
    }
    private TETile[][] createAnRandomUniverse() {
        TETile[][] universe = new TETile[WIDTH][HEIGHT];
        universe = initializeTiles(universe);
        List<Room> rooms = getRandomRooms();
        List<HallWay> hallWays = getHallways(rooms);
        universe = drawInRoomsAndHallways(universe, rooms, hallWays);
        player = creatRandomPlayer(universe);
        treasureSpot = creatRandomTreasureSpot(universe);
        universe = drawInPlayerAndTreasure(universe, player, treasureSpot);
        return universe;
    }
    private TETile[][] initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }
    private Position creatRandomPlayer(TETile[][] tiles) {
        Position p = creatRandomPosition();
        while (!tiles[p.getxPos()][p.getyPos()].equals(Tileset.FLOOR)) {
            p = creatRandomPosition();
        }
        return p;
    }
    private Position creatRandomTreasureSpot(TETile[][] tiles) {
        Position p = creatRandomPosition();
        while (!isRightSpot(tiles, p)) {
            p = creatRandomPosition();
        }
        return p;
    }
    private boolean isRightSpot(TETile[][] tiles, Position p) {
        int x = p.getxPos();
        int y = p.getyPos();
        if (x < 0 || x >= tiles.length) {
            return false;
        }
        if (y < 0 || y >= tiles[0].length) {
            return false;
        }
        if (!tiles[x][y].equals(Tileset.WALL)) {
            return false;
        }
        if (tiles[x + 1][y].equals(Tileset.FLOOR) || tiles[x][y + 1].equals(Tileset.FLOOR)
                || tiles[x - 1][y].equals(Tileset.FLOOR) || tiles[x][y - 1].equals(Tileset.FLOOR)) {
            return true;
        }
        return false;
    }
    private TETile[][] drawInPlayerAndTreasure(TETile[][] tiles, Position p, Position t) {
        tiles[p.getxPos()][p.getyPos()] = Tileset.PLAYER;
        tiles[t.getxPos()][t.getyPos()] = Tileset.LOCKED_DOOR;
        return tiles;
    }
    private Position creatRandomPosition() {
        return new Position(RANDOM.nextInt(WIDTH - 10), RANDOM.nextInt(HEIGHT - 10));
    }
    private Room creatRandomRoom(int mark) {
        Position p = creatRandomPosition();
        int w = RANDOM.nextInt(Math.min(WIDTH - p.getxPos(), 20));
        w = Math.max(6, w);
        int h = RANDOM.nextInt(Math.min(HEIGHT - p.getyPos(), 20));
        h = Math.max(6, h);
        return new Room(p, w, h, mark);
    }
    private List<Room> getRandomRooms() {
        int randomNumber = RANDOM.nextInt(20);
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < Math.max(100, randomNumber); i += 1) {
            Room r = creatRandomRoom(rooms.size());
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
        ArrayList<HallWay> hallWays = new ArrayList<>();
//        HallWay hw = new HallWay(rooms.get(2), rooms.get(3));
//        HallWay hw1 = new HallWay(rooms.get(14), rooms.get(15));
//        hallWays.add(hw);
//        hallWays.add(hw1);
        Room r = rooms.get(0);
        for (int i = 1; i < rooms.size(); i += 1) {
            hallWays.add(new HallWay(r, rooms.get(i)));
            r = rooms.get(i);
        }
        return hallWays;
    }
    private TETile[][] drawInRoomsAndHallways(
            TETile[][] tiles, List<Room> rooms, List<HallWay> hallWays) {
        for (Room r : rooms) {
            r.drawRoom(tiles);
        }
        for (HallWay h : hallWays) {
            h.drawHallWay(tiles);
        }
        return tiles;
    }
}
