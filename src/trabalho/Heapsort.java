package trabalho;

import java.util.ArrayList;

public class Heapsort {
    public static void sort(ArrayList<Reserva> lista) {
        int n = lista.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp);
            heapify(lista, i, 0);
        }
    }

    private static void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;
        if (largest != i) {
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap);
            heapify(lista, n, largest);
        }
    }
}