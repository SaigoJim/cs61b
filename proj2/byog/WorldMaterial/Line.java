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
        if (start.getxPos() == end.getxPos()) {
            xAxis = false;
            yAxis = true;
        } else if (start.getyPos() == end.getyPos()) {
            xAxis = true;
            yAxis = false;
        }
        length = getLength();
    }
    public Position middlePoint() {
        if (xAxis) {
            return new Position((start.getxPos() + end.getxPos()) / 2, start.getyPos());
        }
        return new Position(start.getxPos(), (start.getyPos() + end.getyPos()) / 2);
    }
    public boolean isyAxis() {
        return yAxis;
    }
    private int getLength() {
        if (isyAxis()) {
            return Math.abs(start.getyPos() - end.getyPos() + 1);
        }
        return Math.abs(start.getxPos() - end.getxPos() + 1);
    }
    /*public boolean isTwoPointOnSameLine(Position s, Position t) {
        return isOnePointOnLine(s) && isOnePointOnLine(t);
    }
    public boolean isOnePointOnLine(Position p) {
        int smallEnd, bigEnd;
        if (isyAxis()) {
            smallEnd = Math.min(start.getyPos(), end.getyPos());
            bigEnd = Math.max(start.getyPos(), end.getyPos());
            return p.getyPos() >= smallEnd && p.getyPos() <= bigEnd;
        }
        smallEnd = Math.min(start.getxPos(), end.getxPos());
        bigEnd = Math.max(start.getxPos(), end.getxPos());
        return p.getxPos() >= smallEnd && p.getxPos() <= bigEnd;
    }*/
}
