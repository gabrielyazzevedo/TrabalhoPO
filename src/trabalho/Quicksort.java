package trabalho;

import java.util.ArrayList;

public class Quicksort {
    private static final int RECURSION_LIMIT = 1000;

    public static void sort(ArrayList<Reserva> lista) {
        quicksort(lista, 0, lista.size() - 1, 0);
    }

    private static void quicksort(ArrayList<Reserva> lista, int low, int high, int depth) {
        if (low >= high) return;

        // Se recursão muito profunda, usa heapsort
        if (depth > RECURSION_LIMIT) {
            heapsortRange(lista, low, high);
            return;
        }

        // Mediana de três
        int mid = low + (high - low) / 2;
        if (lista.get(mid).compareTo(lista.get(low)) < 0) swap(lista, low, mid);
        if (lista.get(high).compareTo(lista.get(low)) < 0) swap(lista, low, high);
        if (lista.get(high).compareTo(lista.get(mid)) < 0) swap(lista, mid, high);

        Reserva pivot = lista.get(high);

        // Particionamento de 3 vias (para lidar com duplicatas)
        int i = low;
        int lt = low;
        int gt = high;

        while (i <= gt) {
            int cmp = lista.get(i).compareTo(pivot);
            if (cmp < 0) {
                swap(lista, lt++, i++);
            } else if (cmp > 0) {
                swap(lista, i, gt--);
            } else {
                i++;
            }
        }

        quicksort(lista, low, lt - 1, depth + 1);
        quicksort(lista, gt + 1, high, depth + 1);
    }

    private static void heapsortRange(ArrayList<Reserva> lista, int low, int high) {
        int n = high - low + 1;
        for (int i = low + n / 2 - 1; i >= low; i--) {
            heapify(lista, low, high, i, n);
        }
        for (int i = high; i > low; i--) {
            swap(lista, low, i);
            n--;
            heapify(lista, low, high, low, n);
        }
    }

    private static void heapify(ArrayList<Reserva> lista, int low, int high, int i, int n) {
        int largest = i;
        int l = low + 2 * (i - low) + 1;
        int r = low + 2 * (i - low) + 2;

        if (l <= high && l < low + n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        if (r <= high && r < low + n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;

        if (largest != i) {
            swap(lista, i, largest);
            heapify(lista, low, high, largest, n);
        }
    }

    private static void swap(ArrayList<Reserva> lista, int a, int b) {
        Reserva temp = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, temp);
    }
}
