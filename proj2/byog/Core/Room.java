package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room extends Rectangle {
    public final TETile Wall = Tileset.WALL;
    public final TETile Floor = Tileset.FLOOR;

    Room(Position p, int width, int height) {
        super(p, width, height);
    }

    public void drawRoom(TETile[][] tiles) {
        for (int rowNum = 0; rowNum < HEIGHT; rowNum += 1) {
            int startX = p.x;
            int startY = p.y + rowNum;
            if (rowNum == 0 || rowNum == HEIGHT - 1) {
                addRow(tiles, new Position(startX, startY), WIDTH, Wall);
            } else {
                addRow(tiles, new Position(startX, startY), 1, Wall);
                addRow(tiles, new Position(startX + WIDTH - 1, startY), 1, Wall);
                addRow(tiles, new Position(startX + 1, startY), WIDTH - 2, Floor);
            }
        }
    }
    private void addRow(TETile[][] tiles, Position p, int rowWidth, TETile tile) {
        for (int i = 0; i < rowWidth; i += 1) {
            tiles[p.x + i][p.y] = tile;
        }
    }
}
