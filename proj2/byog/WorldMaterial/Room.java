package byog.WorldMaterial;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;

public class Room {
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
    public int whichDirection(Room other) {
        if (isUp(other)) {
            if (isLeft(other)) {
                // upLeft
                return 1;
            } else if(isRight(other)) {
                // upRight
                return 2;
            }
            // upCenter
            return 3;
        } else if (isBottom(other)) {
            if (isLeft(other)) {
                // bottomLeft
                return 4;
            } else if(isRight(other)) {
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
    public boolean isUp(Room other) {
        return other.pos.yPos >= pos.yPos + height;
    }
    public boolean isBottom(Room other) {
        return other.pos.yPos + other.height <= pos.yPos;
    }
    public boolean isLeft(Room other) {
        return other.pos.xPos + other.width <= pos.xPos;
    }
    public boolean isRight(Room other) {
        return other.pos.xPos >= pos.xPos + width;
    }
    public Position upCenter() {
        return new Position(pos.xPos + width/2 -1, pos.yPos + height);
    }
    public Position bottomCenter() {
        return new Position(pos.xPos + width/2 -1, pos.yPos);
    }
    public Position leftCenter() {
        return new Position(pos.xPos, pos.yPos + height/2 -1);
    }
    public Position rightCenter() {
        return new Position(pos.xPos + width, pos.yPos + height/2 -1);
    }
    public Line upLine() {
        return new Line(new Position(pos.xPos, pos.yPos + height - 1),
                new Position(pos.xPos + width - 1, pos.yPos + height - 1));
    }
    public Line bottomLine() {
        return new Line(new Position(pos.xPos, pos.yPos), new Position(pos.xPos + width - 1, pos.yPos));
    }
    public Line leftLine() {
        return new Line(new Position(pos.xPos, pos.yPos), new Position(pos.xPos, pos.yPos + height - 1));
    }
    public Line rightLine() {
        return new Line(new Position(pos.xPos + width - 1, pos.yPos), new Position(pos.xPos + width - 1, pos.yPos + height - 1));
    }
    public void drawRoom(TETile[][] tiles) {
        for (int rowNum = 0; rowNum < height; rowNum += 1) {
            int startX = pos.xPos;
            int startY = pos.yPos + rowNum;
            int rowLength = width;
            if (rowNum > 0 && rowNum < height - 1) {
                addRow(tiles, new Position(startX, startY), 1, Tileset.WALL);
                addRow(tiles, new Position(startX + 1, startY), rowLength - 2, Tileset.FLOOR);
                addRow(tiles, new Position(startX + rowLength - 1, startY), 1, Tileset.WALL);
            } else {
                addRow(tiles, new Position(startX, startY), rowLength, Tileset.WALL);
            }
        }
        //tiles[pos.xPos][pos.yPos] = new TETile((char)(mark + '0'), Color.green, Color.black, "mark");
    }
    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void addRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            tiles[p.xPos + i][p.yPos] = tile;
        }
    }
}
