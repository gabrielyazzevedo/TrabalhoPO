package trabalho;
import java.util.ArrayList;

public class Quicksort {

    public static void sort(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() == 0) {
            return;
        }
        quickSort(lista, 0, lista.size() - 1);
    }

    private static void quickSort(ArrayList<Reserva> lista, int low, int high) {
        // Otimização: Se a lista for pequena, Insertion Sort é mais rápido,
        // mas para manter o padrão Quicksort puro, mantemos apenas a recursão.
        if (low < high) {
            int pi = partition(lista, low, high);

            quickSort(lista, low, pi - 1);
            quickSort(lista, pi + 1, high);
        }
    }

    private static int partition(ArrayList<Reserva> lista, int low, int high) {
        // em vez de pegar cegamente o high, pegamos o elemento do meio
        // e trocamos com o high. Isso evita o pior caso em listas já ordenadas.
        int meio = low + (high - low) / 2;
        swap(lista, meio, high);

        Reserva pivot = lista.get(high); // Agora o pivô é o elemento que estava no meio
        
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (lista.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(lista, i, j);
            }
        }

        swap(lista, i + 1, high);
        return i + 1;
    }

    private static void swap(ArrayList<Reserva> lista, int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}