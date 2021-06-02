package byog.Core;

import byog.TileEngine.TETile;

public class Rectangle {
    public Position p;
    public int WIDTH;
    public int HEIGHT;

    Rectangle(Position p, int width, int height) {
        this.p = p;
        WIDTH = width;
        HEIGHT = height;
    }
}

