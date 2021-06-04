package hw3.hash;

public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        int x = 1;
        for (int i = 0; i < 10; i += 1) {
            System.out.println(i + "th power: " + x);
            x = x * 256;
        }

        int total1 = 0;
        int[] params1 = new int[]{9, 8, 7, 6, 5 ,4, 3, 2, 1};
        for (int i : params1) {
            total1 = total1 * 256;
            total1 = total1 + i;
        }
        System.out.println("totol1: " + total1);

        int total2 = 0;
        int[] params2 = new int[]{4, 3, 2, 1};
        for (int i : params2) {
            total2 = total2 * 256;
            total2 = total2 + i;
        }
        System.out.println("totol2: " + total2);
    }
} 
