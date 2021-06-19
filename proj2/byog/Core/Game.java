package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
    TERenderer ter = new TERenderer();
    boolean gameOver = false;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private WorldGenerator WG = new WorldGenerator(WIDTH, HEIGHT);
    TETile[][] finalWorldFrame;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        String startCommand = mainMenu();
        if (startCommand.equals("Q") || startCommand.equals("q")) {
            StdDraw.clear(StdDraw.WHITE);
            StdDraw.show();
            return;
        }
        ter.initialize(WIDTH, HEIGHT + 1);
        TETile[][] tiles = playWithInputString(startCommand);
        ter.renderFrame(tiles);
        while (!gameOver) {
            String playerCommand;
            playerCommand = mouseAndKeyBoard(finalWorldFrame);
            tiles = playWithInputString(playerCommand);
            ter.renderFrame(tiles);
        }
    }
    private String mouseAndKeyBoard(TETile[][] tiles) {
        StringBuilder inputSB = new StringBuilder();
        while (true) {
            if (StdDraw.isMousePressed()) {
                ter.renderFrame(tiles);
                int x = (int) StdDraw.mouseX();
                int y = (int) StdDraw.mouseY();
                String description = tiles[x][y].description();
                StdDraw.setPenColor(Color.white);
                StdDraw.textLeft(0, HEIGHT, description);
                StdDraw.show();
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            inputSB.append(key);
            if (key == ':') {
                continue;
            } else {
                break;
            }
        }
        return inputSB.toString();
    }
//    private void headsUPDisplay(TETile[][] tiles) {
//        while (true) {
//        if (StdDraw.isMousePressed()) {
//            ter.renderFrame(tiles);
//            int x = (int) StdDraw.mouseX();
//            int y = (int) StdDraw.mouseY();
//            String description = tiles[x][y].description();
//            StdDraw.setPenColor(Color.white);
//            StdDraw.textLeft(0, HEIGHT, description);
//            StdDraw.show();
//        }
//        }
//    }
    private String typedInCommand() {
        StringBuilder inputSB = new StringBuilder();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            inputSB.append(key);
            if (key == ':') {
                continue;
            } else {
                break;
            }
        }
        return inputSB.toString();
    }
    private String mainMenu() {
        int width = 40;
        int height = 60;
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(midWidth, midHeight + 20, "CS61B: The Game");
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, midHeight + 2, "New Game(N)");
        StdDraw.text(midWidth, midHeight, "Load Game(N)");
        StdDraw.text(midWidth, midHeight - 2, "Quit Game(Q)");

        StdDraw.show();
        String input = implicitCharsInput();
        return input;
    }
    private String implicitCharsInput() {
        StringBuilder inputSB = new StringBuilder();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            inputSB.append(key);
            if (key == 'S' || key == 's' || key == 'L' || key == 'l') {
                break;
            }
            if (key == 'Q' || key == 'q') {
                break;
            }
        }
        return inputSB.toString();
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
        finalWorldFrame = inputProcessor(input);
        if (finalWorldFrame == null) {
            finalWorldFrame = initializer();
        }
        return finalWorldFrame;
    }
    private TETile[][] initializer() {
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < tiles.length; x += 1) {
            for (int y = 0; y < tiles[0].length; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }
    private TETile[][] inputProcessor(String input) {
        int inputIndex = 0;
        while (inputIndex < input.length()) {
            String command = inputParser(input, inputIndex);
            inputIndex += command.length();
            command = commandCleaner(command);
            finalWorldFrame = commandExecutor(command);
            if (gameOver) {
                break;
            }
        }
        return finalWorldFrame;
    }
    private static String inputParser(String input, int inputIndex) {
        char[] inputs = input.toCharArray();
        if (inputs[inputIndex] == 'L' || inputs[inputIndex] == 'l') {
            return input.substring(inputIndex, inputIndex + 1);
        } else if (inputs[inputIndex] == ':') {
            return input.substring(inputIndex, inputIndex + 2);
        }
        String command;
        command = collectCommand(inputs, inputIndex);
        return command;
    }
    private static String collectCommand(char[] inputs, int head) {
        StringBuilder commandSB = new StringBuilder();
        for (int i = head; i < inputs.length; i += 1) {
            char c = inputs[i];
            if (c == ':') {
                break;
            }
            commandSB.append(c);
            if (c == 's' || c == 'S') {
                break;
            }
        }
        return commandSB.toString();
    }
    private static String commandCleaner(String command) {
        char head = command.charAt(0);
        if (head == 'n' || head == 'N') {
            return command.substring(1, command.length() - 1);
        }
        return command;
    }
    private TETile[][] commandExecutor(String command) {
        char[] commands = command.toCharArray();
        char head = commands[0];
        TETile[][] tiles = null;
        if (head >= '0' && head <= '9') {
            long seed = Long.parseLong(command);
            WG = new WorldGenerator(WIDTH, HEIGHT, seed);
        } else if (command.equals("L") || command.equals("l")) {
            WG = deSerializeWorldGenerator();
            if (WG == null) {
                gameOver = true;
                return tiles;
            }
            gameOver = false;
        } else if (command.equals(":Q") || command.equals(":q")) {
            // Serialize
            gameOver = true;
            serializeWorldGenerator();
        } else {
            for (char c : commands) {
                WG.movePlayer(c);
            }
        }
        tiles = WG.getAnUniverse();
        return tiles;
    }
    private void serializeWorldGenerator() {
        File f = new File("./savefile.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(WG);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    private WorldGenerator deSerializeWorldGenerator() {
        File f = new File("./savefile.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                WorldGenerator loadWorld = (WorldGenerator) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new WorldGenerator(WIDTH, HEIGHT);
    }
}
