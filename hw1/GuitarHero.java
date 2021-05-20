import synthesizer.GuitarString;

public class GuitarHero {
    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] notes = new GuitarString[37];

        // Create an array of 37 GuitarString objects.
        for (int i = 0; i < 37; i += 1) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12);
            GuitarString note = new GuitarString(frequency);
            notes[i] = note;
        }

        // Use keyboard.indexOf(key) to figure out which key was typed.
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int typedIndex = keyboard.indexOf((int) key);
                if (typedIndex != -1) {
                    notes[typedIndex].pluck();
                }
            }

            double sample = 0.0;
            /* compute the superposition of samples */
            for (int i = 0; i < 37; i += 1) {
                sample += notes[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < 37; i += 1) {
                notes[i].tic();
            }
        }
    }
}
