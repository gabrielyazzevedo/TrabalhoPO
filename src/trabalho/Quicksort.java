package trabalho;
import java.util.ArrayList;

public class Quicksort {

    // organiza a lista do menor para o maior
    public static void sort(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() == 0) {
            return; // se tá vazia, não faz nada
        }
        quickSort(lista, 0, lista.size() - 1);
    }

    // divide a lista em pedaços e vai ordenando
    private static void quickSort(ArrayList<Reserva> lista, int low, int high) {
        if (low < high) {
            int pi = partition(lista, low, high); // divide e acha a posição do pivô

            quickSort(lista, low, pi - 1);  // ordena a metade menor que o pivô
            quickSort(lista, pi + 1, high); // ordena a metade maior que o pivô
        }
    }

    // separa os menores pra esquerda e os maiores pra direita do pivô
    private static int partition(ArrayList<Reserva> lista, int low, int high) {
        // pega o elemento do meio como pivô (evita problema quando lista já tá ordenada)
        int meio = low + (high - low) / 2;
        swap(lista, meio, high); // joga o pivô pro final

        Reserva pivot = lista.get(high); // pivô é o elemento que tava no meio
        
        int i = (low - 1); // i marca onde tá a fronteira dos menores

        // percorre a lista comparando com o pivô
        for (int j = low; j < high; j++) {
            if (lista.get(j).compareTo(pivot) <= 0) { // se é menor ou igual ao pivô
                i++;                      // avança a fronteira
                swap(lista, i, j);       // joga o menor pra esquerda
            }
        }

        swap(lista, i + 1, high); // coloca o pivô no lugar certo (entre menores e maiores)
        return i + 1;             // retorna a posição final do pivô
    }

    // troca dois elementos de lugar
    private static void swap(ArrayList<Reserva> lista, int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}