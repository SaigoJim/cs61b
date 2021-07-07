package byog.WorldMaterial;

import byog.Core.WorldGenerator;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Character implements Material{
    Position spot;
    String identity;
    WorldGenerator belongTo;
    public Character(WorldGenerator wg, String kindOfCharacter) {
        identity = kindOfCharacter;
        belongTo = wg;
        spot = createARandomPlace();
    }
    private Position createARandomPlace() {
        Position p = belongTo.creatRandomPosition();
        while (!isRightSpot(p)) {
            p = belongTo.creatRandomPosition();
        }
        return p;
    }

    private boolean isRightSpot(Position p) {
        TETile[][] tiles = belongTo.getAnUniverse();
        int x = p.getxPos();
        int y = p.getyPos();
        if (identity.equals("player")) {
            return tiles[p.getxPos()][p.getyPos()].equals(Tileset.FLOOR);
        }
        if (identity.equals("treasure")) {
            return tiles[x][y].equals(Tileset.WALL) && anyNeighborIsFloor(tiles, x, y);
        }
        if (identity.equals("flower")) {
            return tiles[x][y].equals(Tileset.FLOOR);
        }
        return false;
    }
    private boolean anyNeighborIsFloor(TETile[][] tiles, int x, int y) {
        return tiles[x + 1][y].equals(Tileset.FLOOR) || tiles[x][y + 1].equals(Tileset.FLOOR)
                || tiles[x - 1][y].equals(Tileset.FLOOR) || tiles[x][y - 1].equals(Tileset.FLOOR);
    }
    public Position getSpot() {
        return spot;
    }
    @Override
    public void draw(TETile[][] tiles) {
        if (identity.equals("player")) {
            tiles[spot.getxPos()][spot.getyPos()] = Tileset.PLAYER;
        }
        if (identity.equals("treasure")) {
            tiles[spot.getxPos()][spot.getyPos()] = Tileset.LOCKED_DOOR;
        }
        if (identity.equals("flower")) {
            tiles[spot.getxPos()][spot.getyPos()] = Tileset.FLOWER;
        }
    }
}
