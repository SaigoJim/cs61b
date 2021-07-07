package byog.WorldMaterial;

import byog.TileEngine.TETile;

public interface Material {
    public void draw(TETile[][] tiles);
    public Position getSpot();
}
