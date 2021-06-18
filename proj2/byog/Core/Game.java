package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        finalWorldFrame = WorldGenerator.initializeTiles(finalWorldFrame);
        // Parse the CommandLineString
        String[] commands = inputParser(input);
        String state = commands[0];
        if (!state.equals("n") && !state.equals("N")) {
            return finalWorldFrame;
        }
        String seedString = commands[1];
        // Generate the Universe
        long seed = Long.parseLong(seedString);
        WorldGenerator wG = new WorldGenerator(WIDTH, HEIGHT, seed);
        finalWorldFrame = wG.getAnUniverse();
        return finalWorldFrame;
    }
    public static String[] inputParser(String input) {
        String[] returnStrings = new String[4];
        //String n = String.valueOf(input.charAt(0));
        returnStrings[0] = String.valueOf(input.charAt(0));
        StringBuilder seedSB = new StringBuilder();
        int seedEndIndex = 0;
        for (int i = 1; i < input.length(); i += 1) {
            char letter = input.charAt(i);
            if (letter >= '0' && letter <= '9') {
                seedSB.append(letter);
            } else {
                seedEndIndex = i;
                break;
            }
        }
        String seed = seedSB.toString();
        returnStrings[1] = seed;
        String save = String.valueOf(input.charAt(seedEndIndex));
        returnStrings[2] = save;
        returnStrings[3] = input.substring(seedEndIndex + 1);
        return returnStrings;
    }
}
