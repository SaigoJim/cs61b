package byog.WorldMaterial;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Room implements Serializable, Material {
    private static final long serialVersionUID = 17236732674223L;
    Position pos;
    int width;
    int height;
    int mark;
    public Room(Position p, int w, int h, int mark) {
        pos = p;
        width = w;
        height = h;
        this.mark = mark;
    }

    public boolean isOverLap(Room other) {
        if (whichDirection(other) != 0) {
            return false;
        }
        return true;
    }
    private int whichDirection(Room other) {
        if (isUp(other)) {
            if (isLeft(other)) {
                // upLeft
                return 1;
            } else if (isRight(other)) {
                // upRight
                return 2;
            }
            // upCenter
            return 3;
        } else if (isBottom(other)) {
            if (isLeft(other)) {
                // bottomLeft
                return 4;
            } else if (isRight(other)) {
                // bottomRight
                return 5;
            }
            // bottomCenter
            return 6;
        } else if (isLeft(other)) {
            // leftCenter
            return 7;
        } else if (isRight(other)) {
            // rightCenter
            return 8;
        }
        return 0;
    }
    public static Line[] returnTwoLines(Room r1, Room r2) {
        Line[] twoLines = new Line[2];
        Line source = null;
        Line target = null;
        switch (r1.whichDirection(r2)) {
            case 1:
                // upLeft
                source = r1.upLine(); target = r2.rightLine(); break;
            case 2:
                // upRight
                source = r1.upLine(); target = r2.leftLine(); break;
            case 3:
                // upCenter
                source = r1.upLine(); target = r2.bottomLine(); break;
            case 4:
                // bottomLeft
                source = r1.bottomLine(); target = r2.rightLine(); break;
            case 5:
                // bottomRight
                source = r1.bottomLine(); target = r2.leftLine(); break;
            case 6:
                // bottomCenter
                source = r1.bottomLine(); target = r2.upLine(); break;
            case 7:
                // leftCenter
                source = r1.leftLine(); target = r2.rightLine(); break;
            case 8:
                // rightCenter
                source = r1.rightLine(); target = r2.leftLine(); break;
            default:
                break;
        }
        twoLines[0] = source;
        twoLines[1] = target;
        return twoLines;
    }
    private boolean isUp(Room other) {
        return other.pos.getyPos() >= pos.getyPos() + height;
    }
    private boolean isBottom(Room other) {
        return other.pos.getyPos() + other.height <= pos.getyPos();
    }
    private boolean isLeft(Room other) {
        return other.pos.getxPos() + other.width <= pos.getxPos();
    }
    private boolean isRight(Room other) {
        return other.pos.getxPos() >= pos.getxPos() + width;
    }

    private Line upLine() {
        return new Line(new Position(pos.getxPos(), pos.getyPos() + height - 1),
                new Position(pos.getxPos() + width - 1, pos.getyPos() + height - 1));
    }
    private Line bottomLine() {
        return new Line(new Position(pos.getxPos(), pos.getyPos()),
                new Position(pos.getxPos() + width - 1, pos.getyPos()));
    }
    private Line leftLine() {
        return new Line(new Position(pos.getxPos(), pos.getyPos()),
                new Position(pos.getxPos(), pos.getyPos() + height - 1));
    }
    private Line rightLine() {
        return new Line(new Position(pos.getxPos() + width - 1, pos.getyPos()),
                new Position(pos.getxPos() + width - 1, pos.getyPos() + height - 1));
    }
    private void drawRoom(TETile[][] tiles) {
        for (int rowNum = 0; rowNum < height; rowNum += 1) {
            int startX = pos.getxPos();
            int startY = pos.getyPos() + rowNum;
            int rowLength = width;
            if (rowNum > 0 && rowNum < height - 1) {
                addRow(tiles, new Position(startX, startY), 1, Tileset.WALL);
                addRow(tiles, new Position(startX + 1, startY), rowLength - 2, Tileset.FLOOR);
                addRow(tiles, new Position(startX + rowLength - 1, startY), 1, Tileset.WALL);
            } else {
                addRow(tiles, new Position(startX, startY), rowLength, Tileset.WALL);
            }
        }
        //tiles[pos.getxPos()][pos.getyPos()] =
        // new TETile((char)(mark + 'a'), Color.green, Color.black, "mark");
    }
    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void addRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            tiles[p.getxPos() + i][p.getyPos()] = tile;
        }
    }

    @Override
    public void draw(TETile[][] tiles) {
        drawRoom(tiles);
    }

    @Override
    public Position getSpot() {
        return pos;
    }
}
