package trabalho;

import java.util.ArrayList;

public class Heapsort {
    // ordena lista usando heapsort
    public static void sort(ArrayList<Reserva> lista) {
        int n = lista.size();
        // constrói heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        // extrai elementos do heap
        for (int i = n - 1; i > 0; i--) {
            // move raiz para o final
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp);
            // reconstrói heap
            heapify(lista, i, 0);
        }
    }

    // mantém propriedade do heap
    private static void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i; // raiz
        int l = 2 * i + 1; // filho esquerdo
        int r = 2 * i + 2; // filho direito
        // verifica se filho esquerdo é maior
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        // verifica se filho direito é maior
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;
        // se raiz não é maior, troca e continua
        if (largest != i) {
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap);
            heapify(lista, n, largest);
        }
    }
}