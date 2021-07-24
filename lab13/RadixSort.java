/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxWidth = Integer.MIN_VALUE;
        for (String ascii : asciis) {
            maxWidth = maxWidth < ascii.length() ? ascii.length() : maxWidth;
        }
        String[] strs = new String[asciis.length];
        System.arraycopy(asciis, 0, strs, 0, strs.length);
        for (int i = maxWidth - 1; i > - 1; i -= 1) {
            sortHelperLSD(strs, i);
        }
        return strs;
    }
    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // gather all the counts for each value
        int[] counts = new int[256];
        for (String ascii : asciis) {
            int pos = placeOfStringAtIndex(ascii, index);
            counts[pos] += 1;
        }

        // A generalized implementation of
        // counting sort that uses start position calculation
        int currNum = 0;
        for (int i = 0; i < counts.length; i += 1) {
            int temp = counts[i];
            counts[i] = currNum;
            currNum += temp;
        }

        String[] strs = new String[asciis.length];
        System.arraycopy(asciis, 0, strs, 0, strs.length);
        for (int i = 0; i < asciis.length; i += 1) {
            String item = strs[i];
            int asciiValue = placeOfStringAtIndex(item, index);
            int place = counts[asciiValue];
            asciis[place] = item;
            counts[asciiValue] += 1;
        }
    }

    private static int placeOfStringAtIndex(String ascii, int index) {
        if (index > ascii.length() - 1) {
            return 0;
        } else {
            char radixChar = ascii.charAt(index);
            int pos = (int) radixChar;
            return pos;
        }
    }
    /**
     * Does MSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] msd(String[] asciis) {
        int maxWidth = Integer.MIN_VALUE;
        for (String ascii : asciis) {
            maxWidth = maxWidth < ascii.length() ? ascii.length() : maxWidth;
        }
        String[] strs = new String[asciis.length];
        System.arraycopy(asciis, 0, strs, 0, strs.length);
        sortHelperMSD(strs, 0, strs.length, 0, maxWidth);
        return strs;
    }
    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index, int maxWidth) {
        if (asciis.length < 1 || end -start == 1 || index >= maxWidth) {
            return;
        }
        // Optional MSD helper method for optional MSD radix sort
        // gather all the counts for each value
        int[] counts = new int[256];
        for (int i = start; i < end; i += 1) {
            String ascii = asciis[i];
            int pos = placeOfStringAtIndex(ascii, index);
            counts[pos] += 1;
        }
        // A generalized implementation of
        // counting sort that uses start position calculation
        int currNum = 0;
        for (int i = 0; i < counts.length; i += 1) {
            int temp = counts[i];
            counts[i] = currNum;
            currNum += temp;
        }
        String[] strs = new String[asciis.length];
        System.arraycopy(asciis, 0, strs, 0, strs.length);
        for (int i = start; i < end; i += 1) {
            String item = strs[i];
            int asciiValue = placeOfStringAtIndex(item, index);
            // Increment
            int place = counts[asciiValue] + start;
            asciis[place] = item;
            counts[asciiValue] += 1;
        }
        for (int nextEnd = start; nextEnd <= end; nextEnd += 1) {
            if (nextEnd == end) {
                sortHelperMSD(asciis, start, nextEnd, index + 1, maxWidth);
                start = nextEnd;
            }
            if (nextEnd == end) {
                break;
            }
            int startPos = placeOfStringAtIndex(asciis[start], index);
            int nextEndPos = placeOfStringAtIndex(asciis[nextEnd], index);
            if (startPos != nextEndPos) {
                sortHelperMSD(asciis, start, nextEnd, index + 1, maxWidth);
                start = nextEnd;
            }
        }
    }
}
