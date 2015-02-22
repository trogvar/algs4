public class ShellSort {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3)
            h = h * 3 + 1; // 1, 4, 13, 40, ...

        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++)
            {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                    exch(a, j, j - h);
            }
            h = h /3;
        }

    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        String[] strings = { "S", "H", "E", "L", "L", "S", "O", "R", "T", "E",
                "X", "A", "M", "P", "L", "E" };
        sort(strings);
        for (String s : strings)
            System.out.println(s);
    }

}
