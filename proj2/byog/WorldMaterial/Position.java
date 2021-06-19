package byog.WorldMaterial;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 874267321223423L;
    private int xPos;
    private int yPos;
    public Position(int x, int y) {
        xPos = x;
        yPos = y;
    }
    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
}
