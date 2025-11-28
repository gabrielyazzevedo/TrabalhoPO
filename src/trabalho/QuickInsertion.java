package trabalho;

import java.util.ArrayList;

public class QuickInsertion {
    private static final int CUTOFF = 20; // quando sobrar poucos elementos, usa insertion
    private static final int RECURSION_LIMIT = 1000; // máximo de chamadas pra não estourar a memória

    // organiza a lista do menor para o maior (mistura quicksort com insertion)
    public static void sort(ArrayList<Reserva> lista) {
        quicksort(lista, 0, lista.size() - 1, 0);
    }

    // quicksort inteligente: muda de estratégia dependendo da situação
    private static void quicksort(ArrayList<Reserva> lista, int low, int high, int depth) {
        // se sobrou pouco elemento, usa insertion sort que é mais rápido pra lista pequena
        if (high - low <= CUTOFF) {
            insertionSort(lista, low, high);
            return;
        }
        if (low >= high) return; // já ordenou tudo

        // se tá fazendo muita recursão, troca pra heapsort pra não estourar a memória
        if (depth > RECURSION_LIMIT) {
            heapsortRange(lista, low, high);
            return;
        }

        // escolhe um bom pivô: pega inicio, meio e fim e ordena eles
        int mid = low + (high - low) / 2;
        if (lista.get(mid).compareTo(lista.get(low)) < 0) swap(lista, low, mid);   // compara meio com inicio
        if (lista.get(high).compareTo(lista.get(low)) < 0) swap(lista, low, high); // compara fim com inicio
        if (lista.get(high).compareTo(lista.get(mid)) < 0) swap(lista, mid, high); // compara fim com meio

        Reserva pivot = lista.get(high); // usa o fim como pivô (já tá na posição certa)

        // separa em 3 grupos: menores, iguais e maiores que o pivô
        int i = low;   // posição atual
        int lt = low;  // onde termina os menores
        int gt = high; // onde começa os maiores

        while (i <= gt) {
            int cmp = lista.get(i).compareTo(pivot); // compara com o pivô
            if (cmp < 0) {
                swap(lista, lt++, i++); // é menor, joga pra esquerda
            } else if (cmp > 0) {
                swap(lista, i, gt--); // é maior, joga pra direita
            } else {
                i++; // é igual, deixa no meio
            }
        }

        quicksort(lista, low, lt - 1, depth + 1);  // ordena os menores
        quicksort(lista, gt + 1, high, depth + 1); // ordena os maiores (os iguais já tão no lugar)
    }

    // heapsort só pra um pedaço da lista (de low até high)
    private static void heapsortRange(ArrayList<Reserva> lista, int low, int high) {
        int n = high - low + 1; // quantos elementos tem nesse pedaço
        // transforma o pedaço numa árvore heap
        for (int i = low + n / 2 - 1; i >= low; i--) {
            heapify(lista, low, high, i, n);
        }
        // vai pegando o maior e jogando pro final
        for (int i = high; i > low; i--) {
            swap(lista, low, i); // joga o maior pro final
            n--;
            heapify(lista, low, high, low, n); // arruma o resto
        }
    }

    // arruma a árvore heap num pedaço da lista
    private static void heapify(ArrayList<Reserva> lista, int low, int high, int i, int n) {
        int largest = i;
        int l = low + 2 * (i - low) + 1; // calcula posição do filho esquerdo
        int r = low + 2 * (i - low) + 2; // calcula posição do filho direito

        // ve se o filho esquerdo é maior
        if (l <= high && l < low + n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        // ve se o filho direito é maior
        if (r <= high && r < low + n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;

        // se algum filho era maior, troca e continua arrumando
        if (largest != i) {
            swap(lista, i, largest);
            heapify(lista, low, high, largest, n);
        }
    }

    // troca dois elementos de lugar
    private static void swap(ArrayList<Reserva> lista, int a, int b) {
        Reserva temp = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, temp);
    }

    // insertion sort num pedaço da lista (de low até high)
    private static void insertionSort(ArrayList<Reserva> lista, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Reserva key = lista.get(i); // pega o elemento pra inserir
            int j = i - 1;
            // vai empurrando os maiores pra direita
            while (j >= low && lista.get(j).compareTo(key) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key); // coloca o elemento no lugar certo
        }
    }
}