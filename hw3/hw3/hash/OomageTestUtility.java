package hw3.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();
        Map<Integer, Integer> hashCodeNumbers = new HashMap<>();
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (!hashCodeNumbers.containsKey(bucketNum)) {
                hashCodeNumbers.put(bucketNum, 0);
            }
            hashCodeNumbers.replace(bucketNum, hashCodeNumbers.get(bucketNum) + 1);
        }
        Object[] vals = hashCodeNumbers.values().toArray(new Integer[0]);
        Arrays.sort(vals);
        int minNum = (int) vals[0];
        int maxNum = (int) vals[vals.length - 1];
        if (minNum < (N / 50) || maxNum > (N / 2.5)) {
            return false;
        }
        return true;
    }
}
