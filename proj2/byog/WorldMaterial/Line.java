package byog.WorldMaterial;

public class Line {
    Position start;
    Position end;
    boolean xAxis;
    boolean yAxis;
    int length;
    public Line(Position s, Position e) {
        start = s;
        end = e;
        if (start.xPos == end.xPos) {
            xAxis = false;
            yAxis = true;
        } else if (start.yPos == end.yPos) {
            xAxis = true;
            yAxis = false;
        }
        length = getLength();
    }
    public Position middlePoint() {
        if (xAxis) {
            return new Position((start.xPos + end.xPos)/2, start.yPos);
        }
        return new Position(start.xPos, (start.yPos + end.yPos) / 2);
    }
    public boolean isyAxis() {
        return yAxis;
    }
    private int getLength() {
        if (isyAxis()) {
            return Math.abs(start.yPos - end.yPos + 1);
        }
        return Math.abs(start.xPos - end.xPos + 1);
    }
}
