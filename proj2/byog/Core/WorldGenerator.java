package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.WorldMaterial.HallWay;
import byog.WorldMaterial.Material;
import byog.WorldMaterial.Position;
import byog.WorldMaterial.Room;

import byog.WorldMaterial.Character;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator implements Serializable {
    private static final long serialVersionUID = 48787879342344L;
    private int WIDTH;
    private int HEIGHT;
    private int FLOWERNUM;
    private final Random RANDOM;
    private TETile[][] UNIVERSE;
    private Character playerX;
    private Position player;
    private Position treasureSpot;
    private int playerFlower;
    private boolean isArrived;
    public WorldGenerator(int w, int h) {
        RANDOM = new Random(0);
        WIDTH = w;
        HEIGHT = h;
        UNIVERSE = new TETile[WIDTH][HEIGHT];
        UNIVERSE = initializeTiles(UNIVERSE);
        isArrived = false;
    }
    public WorldGenerator(int w, int h, long seed) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(seed);
        //UNIVERSE = createAnRandomUniverse();
        UNIVERSE = constructBuildings();
        UNIVERSE = createCharacters(UNIVERSE);
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
        if (UNIVERSE[x][y].equals(Tileset.FLOWER)) {
            playerFlower += 1;
            return true;
        }
        if (UNIVERSE[x][y].equals(Tileset.LOCKED_DOOR) && playerFlower < FLOWERNUM) {
            return false;
        }
        return !(UNIVERSE[x][y].equals(Tileset.WALL) || UNIVERSE[x][y].equals(Tileset.NOTHING));
    }
    private void cleanPlayer() {
        UNIVERSE[player.getxPos()][player.getyPos()] = Tileset.FLOOR;
    }
    public boolean isArrived() {
        return isArrived;
    }

    private TETile[][] constructBuildings() {
        TETile[][] universe = new TETile[WIDTH][HEIGHT];
        universe = initializeTiles(universe);
        List<Room> rooms = getRandomRooms();
        List<HallWay> hallWays = getHallways(rooms);
        universe = drawInRoomsAndHallways(universe, rooms, hallWays);
        return universe;
    }
    private TETile[][] createCharacters(TETile[][] universe) {

        Material tempPlayer = new Character(this, "player");
        tempPlayer.draw(universe);
        Material tempTreasure = new Character(this, "treasure");
        tempTreasure.draw(universe);
        player = tempPlayer.getSpot();
        treasureSpot = tempTreasure.getSpot();
        creatRandomFlowers(universe);
        return universe;
    }

    private void creatRandomFlowers(TETile[][] tiles) {
        FLOWERNUM = 3;
        for (int i = 0; i < FLOWERNUM; i += 1) {
            Material flower = new Character(this, "flower");
            flower.draw(tiles);
        }
    }
    private TETile[][] initializeTiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }

    private TETile[][] drawInPlayerAndTreasure(TETile[][] tiles, Position p, Position t) {
        if (p.equals(t) && playerFlower == FLOWERNUM) {
            tiles[p.getxPos()][p.getyPos()] = Tileset.UNLOCKED_DOOR;
            isArrived = true;
            return tiles;
        }
        tiles[p.getxPos()][p.getyPos()] = Tileset.PLAYER;
        tiles[t.getxPos()][t.getyPos()] = Tileset.LOCKED_DOOR;
        return tiles;
    }

    public Position creatRandomPosition() {
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
            r.draw(tiles);
        }
        for (HallWay h : hallWays) {
            h.draw(tiles);
        }
        return tiles;
    }
}
