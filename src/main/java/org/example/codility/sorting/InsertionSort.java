package org.example.codility.sorting;

public class InsertionSort {

    // 1, 2, 3, 4, 5, 2, 9, 3, 2, 5 => 9 > 5 & asc = true
    // asc = false & fasle => true
    /**
     * 1, 2, 3, 4, 5, 2, 9, 3, 2, 5
     * asc = true => (asc ^ (2 ? 5)) = true => 2 ? 5 = false =? 2 > 5
     * 
     * @param arrays
     * @param asc
     * @return
     */
    public static int[] sort(int[] arrays, boolean asc) {
        for (int i = 1; i < arrays.length; ++i) {
            int j = i;
            int x = arrays[j];
            while (j > 0 && (x > arrays[j - 1] ^ asc)) {
                arrays[j] = arrays[j - 1];
                j--;
            }
            arrays[j] = x;
        }
        return arrays;
    }

    public static void main(String[] args) {
        int[] values = { 4, 2, 1, 5, 6, 1, 0 };
        values = sort(values, false);

        for (int i : values) {
            System.out.println(i);
        }
    }
}
