package byog.WorldMaterial;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class HallWay {
    Line source, target;
    Position firstPoint, secondPoint, thirdPoint;
    public HallWay(Room r1, Room r2) {
        Line[] twoLines = Room.returnTwoLines(r1, r2);
        source = twoLines[0];
        target = twoLines[1];
        firstPoint = target.middlePoint();
        thirdPoint = source.middlePoint();
        secondPoint = getSecondPoint(firstPoint, thirdPoint);
        if (isMatched()) {
            thirdPoint = secondPoint;
        }
    }
    private Position getSecondPoint(Position first, Position third) {
        if (!source.isyAxis() && !target.isyAxis()
                && source.start.getyPos() < target.start.getyPos()) {
            return new Position(first.getxPos(), third.getyPos());
        }
        return new Position(third.getxPos(), first.getyPos());
    }

    public void drawHallWay(TETile[][] tiles) {
        drawThreePoints(tiles, firstPoint, secondPoint, thirdPoint);
    }
    private void drawThreePoints(
            TETile[][] tiles, Position first, Position second, Position third) {
        drawLineSegment(tiles, first, second);
        drawLineSegment(tiles, second, third);
        drawHallWayCorner(tiles, second);
        drawHallWayCorner(tiles, first);
        drawHallWayCorner(tiles, third);
        openPort(tiles, first);
        openPort(tiles, third);
    }

    private void drawHallWayCorner(TETile[][] tiles, Position tP) {
        drawRow(tiles, tP, 3, Tileset.WALL);
        drawRow(tiles, new Position(tP.getxPos(), tP.getyPos() + 1), 1, Tileset.WALL);
        drawRow(tiles, new Position(tP.getxPos() + 1, tP.getyPos() + 1), 1, Tileset.FLOOR);
        drawRow(tiles, new Position(tP.getxPos() + 2, tP.getyPos() + 1), 1, Tileset.WALL);
        drawRow(tiles, new Position(tP.getxPos(), tP.getyPos() + 2), 3, Tileset.WALL);

        sealPort(tiles, tP);
    }
    private void sealPort(TETile[][] tiles, Position tP) {
        int x = tP.getxPos() + 1;
        int y = tP.getyPos() + 1;

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
    private void openPort(TETile[][] tiles, Position tP) {
        int x = tP.getxPos();
        int y = tP.getyPos();
        // leftPort
        if (openPort(tiles, x, y)) {
            return;
        }
        // centerPort
        if (openPort(tiles, x + 1, y)) {
            return;
        }
        // rightPort
        if (openPort(tiles, x + 1, y)) {
            return;
        }
    }
    private boolean openPort(TETile[][] tiles, int x, int y) {
        if ((tiles[x - 1][y] == Tileset.FLOOR && tiles[x + 1][y] == Tileset.FLOOR)
                || (tiles[x][y - 1] == Tileset.FLOOR && tiles[x][y + 1] == Tileset.FLOOR)) {
            tiles[x][y] = Tileset.FLOOR;
            return true;
        }
        return false;
    }

    private boolean isIntersected() {
        return source.xAxis != target.xAxis;
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
            distanceBetweenMiddle = Math.abs(startPoint.getyPos() - endPoint.getyPos());
        } else {
            distanceBetweenMiddle = Math.abs(startPoint.getxPos() - endPoint.getxPos());
        }
        return distanceBetweenMiddle < sourceHalfWidth;
    }

    private void drawLineSegment(TETile[][] tiles, Position s, Position e) {
        if (s == e) {
            return;
        }
        if (s.getyPos() == e.getyPos()) {
            int length = Math.abs(s.getxPos() - e.getxPos()) + 1;
            Position p;
            if (s.getxPos() <= e.getxPos()) {
                p = s;
            } else {
                p = e;
            }
            drawRow(tiles, p, length, Tileset.WALL);
            drawRow(tiles, new Position(p.getxPos(), p.getyPos() + 1), length, Tileset.FLOOR);
            drawRow(tiles, new Position(p.getxPos(), p.getyPos() + 2), length, Tileset.WALL);
        }
        if (s.getxPos() == e.getxPos()) {
            int length = Math.abs(s.getyPos() - e.getyPos()) + 1;
            Position p;
            if (s.getyPos() <= e.getyPos()) {
                p = s;
            } else {
                p = e;
            }
            drawCol(tiles, p, length, Tileset.WALL);
            drawCol(tiles, new Position(p.getxPos() + 1, p.getyPos()), length, Tileset.FLOOR);
            drawCol(tiles, new Position(p.getxPos() + 2, p.getyPos()), length, Tileset.WALL);
        }
    }
    private void drawCol(TETile[][] tiles, Position p, int colLength, TETile tile) {
        for (int i = 0; i < colLength; i += 1) {
            if (tiles[p.getxPos()][p.getyPos() + i] != Tileset.FLOOR) {
                tiles[p.getxPos()][p.getyPos() + i] = tile;
            }
            /*if (tiles[p.getxPos()][p.getyPos() + i] ==
            Tileset.NOTHING || tiles[p.getxPos()][p.getyPos() + i] == Tileset.WALL) {
                tiles[p.getxPos()][p.getyPos() + i] = tile;
            }*/
        }
    }
    /** Add a row of tile from Position p with length of rowLength in the Tiles. */
    private static void drawRow(TETile[][] tiles, Position p, int rowLength, TETile tile) {
        for (int i = 0; i < rowLength; i += 1) {
            if (tiles[p.getxPos() + i][p.getyPos()] != Tileset.FLOOR) {
                tiles[p.getxPos() + i][p.getyPos()] = tile;
            }
            /*if (tiles[p.getxPos() + i][p.getyPos()] ==
            Tileset.NOTHING || tiles[p.getxPos() + i][p.getyPos()] == Tileset.WALL) {
                tiles[p.getxPos() + i][p.getyPos()] = tile;
            }*/
        }
    }
}
