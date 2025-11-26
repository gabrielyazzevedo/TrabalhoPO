import java.util.ArrayList;

public class Quicksort {
    public static void sort(ArrayList<Reserva> lista) {
        quicksort(lista, 0, lista.size() - 1);
    }

    private static void quicksort(ArrayList<Reserva> lista, int low, int high) {
        if (low < high) {
            int pi = partition(lista, low, high);
            quicksort(lista, low, pi - 1);
            quicksort(lista, pi + 1, high);
        }
    }

    private static int partition(ArrayList<Reserva> lista, int low, int high) {
        Reserva pivot = lista.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (lista.get(j).compareTo(pivot) < 0) {
                i++;
                Reserva temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Reserva temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);
        return i + 1;
    }
}
