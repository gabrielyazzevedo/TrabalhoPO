package trabalho;

import java.util.ArrayList;

public class Quicksort {

    private ArrayList<Reserva> lista;

    public static void sort(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() <= 1)
            return;
        Quicksort qs = new Quicksort();
        qs.lista = lista;
        qs.quicksort(0, lista.size() - 1);
    }

    private void quicksort(int esq, int dir) {
        if (esq < dir) {
            int i = particiona(esq, dir);
            quicksort(esq, i - 1);
            quicksort(i + 1, dir);
        }
    }

    private int particiona(int esq, int dir) {
        // Escolhe o meio e troca com o final para evitar pior caso em listas ordenadas
        int meio = (esq + dir) / 2;
        troca(meio, dir);

        Reserva pivo = lista.get(dir);
        int i = esq;
        int j = dir - 1;

        while (i <= j) {
            while (i <= j && lista.get(i).compareTo(pivo) < 0)
                i++;
            while (i <= j && lista.get(j).compareTo(pivo) > 0)
                j--;
            if (i <= j) {
                troca(i, j);
                i++;
                j--;
            }
        }
        troca(i, dir);
        return i;
    }

    private void troca(int a, int b) {
        Reserva temp = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, temp);
    }
}