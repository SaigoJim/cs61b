package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
        //game.start();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //Generate random string of letters of length n
        StringBuilder returnSB = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            char c = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            returnSB.append(c);
        }
        String randomNLenString = returnSB.toString();
        return randomNLenString;
    }

    public void drawFrame(String s) {
        //Take the string and display it in the center of the screen
        //If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        if (!gameOver) {
            drawHeader();
        }
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();
    }
    private void drawHeader() {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(6, this.height - 2, "Round: " + round);
        if (playerTurn) {
            StdDraw.text(this.width / 2, this.height - 2, "Type!");
        } else {
            StdDraw.text(this.width / 2, this.height - 2, "Watch!");
        }
        String encourageMessage = ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
        StdDraw.text(this.width - 5, this.height - 2, encourageMessage);
    }
    public void flashSequence(String letters) {
        //Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            String s = String.valueOf(letters.charAt(i));
            drawFrame(s);
            StdDraw.pause(2000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //Read n letters of player input
        StringBuilder inputSB = new StringBuilder();
        while (inputSB.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                inputSB.append(c);
                String showSoFarString = inputSB.toString();
                drawFrame(showSoFarString);
                StdDraw.pause(1000);
            }
        }
        return inputSB.toString();
    }

    public void startGame() {
        //Set any relevant variables before the game starts
        round = 1;
        playerTurn = false;
        gameOver = false;
        //Establish Game loop
        while (true) {
            String randomString = generateRandomString(round);
            flashSequence(randomString);
            playerTurn = true;
            drawFrame("");
            String playerAnswer = solicitNCharsInput(round);
            playerTurn = false;
            if (playerAnswer.equals(randomString)) {
                round += 1;
            } else {
                gameOver = true;
                break;
            }
        }
        String lostMessage = "You are a Loser!";
        drawFrame(lostMessage);
        StdDraw.pause(2000);
    }
}
