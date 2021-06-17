package byog.WorldMaterial;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class HallWay {
    Line source;
    Line target;
    boolean intersected;
    public HallWay(Room r1, Room r2) {
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
        }
        intersected = isIntersected();
    }
    private boolean isIntersected() {
        return source.xAxis != target.xAxis;
    }

    public void drawHallWay(TETile[][] tiles) {
       if (intersected) {
           drawIntersected(tiles);
       } else {
           drawNotIntersected(tiles);
       }
    }
    private void drawIntersected(TETile[][] tiles) {
        Position startPoint, endPoint;
        startPoint = target.middlePoint();
        endPoint = source.middlePoint();
        drawL(tiles, startPoint, endPoint);
    }
    private void drawHallWayCorner(TETile[][] tiles, Position tP) {
        drawRow(tiles, tP, 3, Tileset.WALL);
        drawRow(tiles, new Position(tP.xPos, tP.yPos + 1), 1, Tileset.WALL);
        drawRow(tiles, new Position(tP.xPos + 1, tP.yPos + 1), 1, Tileset.FLOOR);
        drawRow(tiles, new Position(tP.xPos + 2, tP.yPos + 1), 1, Tileset.WALL);
        drawRow(tiles, new Position(tP.xPos, tP.yPos + 2), 3, Tileset.WALL);

        sealPort(tiles, tP);
    }
    private void sealPort(TETile[][] tiles, Position tP) {
        int x = tP.xPos + 1;
        int y = tP.yPos + 1;

        // downPort
        if (tiles[x][y - 2] == Tileset.NOTHING) {
            tiles[x][y - 1] = Tileset.WALL;
        }
        // upPort
        if (tiles[x][y + 2] == Tileset.NOTHING) {
            tiles[x][y + 1] = Tileset.WALL;
        }
        // leftPort
        if (tiles[x - 2][y] == Tileset.NOTHING) {
            tiles[x - 1][y] = Tileset.WALL;
        }
        // rightPort
        if (tiles[x + 2][y] == Tileset.NOTHING) {
            tiles[x + 1][y] = Tileset.WALL;
        }
    }
    private void drawNotIntersected(TETile[][] tiles) {
        if (isMatched()) {
            drawMiddleMatch(tiles);
        } else {
            drawMiddleNotMatch(tiles);
        }
    }
    private boolean isMatched() {
        if (isIntersected()) {
            return false;
        }
        Position startPoint, endPoint;
        startPoint = target.middlePoint();
        endPoint = source.middlePoint();
        int sourceHalfWidth = source.length / 2;
        int distanceBetweenMiddle;
        if (source.isyAxis()) {
            distanceBetweenMiddle = Math.abs(startPoint.yPos - endPoint.yPos);
        } else {
            distanceBetweenMiddle = Math.abs(startPoint.xPos - endPoint.xPos);
        }
        return distanceBetweenMiddle < sourceHalfWidth;
    }
    private void drawMiddleMatch(TETile[][] tiles) {
        Position startPoint, endPoint;
        startPoint = target.middlePoint();
        if (source.isyAxis()) {
            endPoint = new Position(source.start.xPos, startPoint.yPos);
        } else {
            endPoint = new Position(startPoint.xPos, source.start.yPos);
        }
        drawLineSegment(tiles, startPoint, endPoint);
    }
    private void drawMiddleNotMatch(TETile[][] tiles) {
        Position startPoint, endPoint;
        startPoint = target.middlePoint();
        endPoint = source.middlePoint();
        drawL(tiles, startPoint, endPoint);
        drawHallWayCorner(tiles, startPoint);
        drawHallWayCorner(tiles, endPoint);
        openPort(tiles, startPoint);
        openPort(tiles, endPoint);
    }
    private void openPort(TETile[][] tiles, Position tP) {
        int x = tP.xPos;
        int y = tP.yPos;
        // centerPort
        if ((tiles[x - 1][y] == Tileset.FLOOR && tiles[x + 1][y] == Tileset.FLOOR)
                || (tiles[x][y - 1] == Tileset.FLOOR && tiles[x][y + 1] == Tileset.FLOOR)) {
            tiles[x][y] = Tileset.FLOOR;
        }
        // downPort
        else if (tiles[x - 1][y - 1] == Tileset.FLOOR && tiles[x + 1][y - 1] == Tileset.FLOOR) {
            tiles[x][y - 1] = Tileset.FLOOR;
        }
        // upPort
        else if (tiles[x - 1][y + 1] == Tileset.FLOOR && tiles[x + 1][y + 1] == Tileset.FLOOR) {
            tiles[x][y + 1] = Tileset.FLOOR;
        }

    }

    private void drawL(TETile[][] tiles, Position startPoint, Position endPoint) {
        Position turnPoint;
        turnPoint = new Position(endPoint.xPos, startPoint.yPos);
        drawLineSegment(tiles, startPoint, turnPoint);
        drawLineSegment(tiles, turnPoint, endPoint);
        drawHallWayCorner(tiles, turnPoint);
    }
    private void drawLineSegment(TETile[][] tiles, Position s, Position e) {
        if (s.yPos == e.yPos) {
            int length = Math.abs(s.xPos - e.xPos) + 1;
            Position p;
            if (s.xPos <= e.xPos) {
                p = s;
            } else {
                p = e;
            }
            drawRow(tiles, p, length, Tileset.WALL);
            drawRow(tiles, new Position(p.xPos, p.yPos + 1), length, Tileset.FLOOR);
            drawRow(tiles, new Position(p.xPos, p.yPos + 2), length, Tileset.WALL);
        }
        if (s.xPos == e.xPos) {
            int length = Math.abs(s.yPos - e.yPos) + 1;
            Position p;
            if (s.yPos <= e.yPos) {
                p = s;
            } else {
                p = e;
            }
            drawCol(tiles, p, length, Tileset.WALL);
            drawCol(tiles, new Position(p.xPos + 1, p.yPos), length, Tileset.FLOOR);
            drawCol(tiles, new Position(p.xPos + 2, p.yPos), length, Tileset.WALL);
        }
    }
    private void drawCol(TETile[][] tiles, Position p, int colLength, TETile tile) {
        for (int i = 0; i < colLength; i += 1) {
            if (tiles[p.xPos][p.yPos + i] != Tileset.FLOOR) {
                tiles[p.xPos][p.yPos + i] = tile;
            }
            /*if (tiles[p.xPos][p.yPos + i] == Tileset.NOTHING || tiles[p.xPos][p.yPos + i] == Tileset.WALL) {
                tiles[p.xPos][p.yPos + i] = tile;
            }*/
        }
    }
    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void drawRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            if (tiles[p.xPos + i][p.yPos] != Tileset.FLOOR) {
                tiles[p.xPos + i][p.yPos] = tile;
            }
            /*if (tiles[p.xPos + i][p.yPos] == Tileset.NOTHING || tiles[p.xPos + i][p.yPos] == Tileset.WALL) {
                tiles[p.xPos + i][p.yPos] = tile;
            }*/
        }
    }
}
