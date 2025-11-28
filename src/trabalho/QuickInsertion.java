package trabalho;

import java.util.ArrayList;

public class QuickInsertion {

    private ArrayList<Reserva> lista;

    public static void sort(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() <= 1)
            return;

        QuickInsertion qi = new QuickInsertion();
        qi.lista = lista;
        qi.quickInsertion(0, lista.size() - 1);
    }

    private void quickInsertion(int esq, int dir) {
        // Requisito: Se o trecho tiver 20 elementos ou menos, usa Inserção Direta
        if ((dir - esq + 1) <= 20) {
            insercaoDireta(esq, dir);
            return;
        }

        if (esq < dir) {
            int indicePivo = particao(esq, dir);
            quickInsertion(esq, indicePivo - 1);
            quickInsertion(indicePivo + 1, dir);
        }
    }

    private int particao(int esq, int dir) {
        // Troca o elemento do meio com o da direita para evitar pior caso
        int meio = (esq + dir) / 2;
        troca(meio, dir);

        Reserva pivo = lista.get(dir);
        int i = esq - 1;

        for (int j = esq; j < dir; j++) {
            if (lista.get(j).compareTo(pivo) <= 0) {
                i++;
                troca(i, j);
            }
        }
        troca(i + 1, dir);
        return i + 1;
    }

    // Método de Inserção Direta para pequenos vetores
    private void insercaoDireta(int esq, int dir) {
        for (int i = esq + 1; i <= dir; i++) {
            Reserva temp = lista.get(i);
            int j = i - 1;

            while (j >= esq && lista.get(j).compareTo(temp) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, temp);
        }
    }

    private void troca(int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}