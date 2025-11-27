package trabalho;

import java.util.ArrayList;

public class Quicksort {
    private static final int RECURSION_LIMIT = 1000; // limite para evitar stack overflow

    // ordena lista usando quicksort
    public static void sort(ArrayList<Reserva> lista) {
        quicksort(lista, 0, lista.size() - 1, 0);
    }

    // quicksort recursivo com proteção contra pior caso
    private static void quicksort(ArrayList<Reserva> lista, int low, int high, int depth) {
        if (low >= high) return; // caso base: 0 ou 1 elemento

        if (depth > RECURSION_LIMIT) { // proteção contra stack overflow
            heapsortRange(lista, low, high); // troca para heapsort se recursão muito profunda
            return;
        }

        // mediana de três para escolher melhor pivô
        int mid = low + (high - low) / 2;
        if (lista.get(mid).compareTo(lista.get(low)) < 0) swap(lista, low, mid); // ordena low, mid
        if (lista.get(high).compareTo(lista.get(low)) < 0) swap(lista, low, high); // ordena low, high
        if (lista.get(high).compareTo(lista.get(mid)) < 0) swap(lista, mid, high); // ordena mid, high

        Reserva pivot = lista.get(high); // usa high como pivô após ordenação

        // particionamento de 3 vias (separa <, =, >)
        int i = low;
        int lt = low; // índice para elementos menores
        int gt = high; // índice para elementos maiores

        while (i <= gt) {
            int cmp = lista.get(i).compareTo(pivot);
            if (cmp < 0) {
                swap(lista, lt++, i++); // menor que pivô, move para esquerda
            } else if (cmp > 0) {
                swap(lista, i, gt--); // maior que pivô, move para direita
            } else {
                i++; // igual ao pivô, deixa no meio
            }
        }

        quicksort(lista, low, lt - 1, depth + 1); // ordena menores
        quicksort(lista, gt + 1, high, depth + 1); // ordena maiores (iguais já estão no lugar)
    }

    // heapsort aplicado apenas em intervalo [low, high]
    private static void heapsortRange(ArrayList<Reserva> lista, int low, int high) {
        int n = high - low + 1; // tamanho do intervalo
        for (int i = low + n / 2 - 1; i >= low; i--) {
            heapify(lista, low, high, i, n); // constrói heap máximo
        }
        for (int i = high; i > low; i--) {
            swap(lista, low, i); // move maior (raiz) para final
            n--; // reduz tamanho do heap
            heapify(lista, low, high, low, n); // reconstrói heap
        }
    }

    // mantém propriedade do heap (pai >= filhos) dentro de intervalo
    private static void heapify(ArrayList<Reserva> lista, int low, int high, int i, int n) {
        int largest = i;
        int l = low + 2 * (i - low) + 1; // calcula índice filho esquerdo
        int r = low + 2 * (i - low) + 2; // calcula índice filho direito

        if (l <= high && l < low + n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l; // verifica filho esquerdo
        if (r <= high && r < low + n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r; // verifica filho direito

        if (largest != i) {
            swap(lista, i, largest); // troca com maior filho
            heapify(lista, low, high, largest, n); // propaga correção
        }
    }

    // troca elementos nas posições a e b
    private static void swap(ArrayList<Reserva> lista, int a, int b) {
        Reserva temp = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, temp);
    }
}
