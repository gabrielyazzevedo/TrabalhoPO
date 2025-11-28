package trabalho;

import java.util.ArrayList;

public class Heapsort {

    public static void sort(ArrayList<Reserva> lista) {
        int quant = lista.size();

        for (int i = quant / 2 - 1; i >= 0; i--) {
            refazHeap(lista, i, quant - 1);
        }

        // Troca o maior (raiz) com o último e reconstrói o heap
        for (int i = quant - 1; i > 0; i--) {
            // Troca a raiz (0) com a posição atual (i)
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp);

            // Refaz o heap considerando o tamanho reduzido (0 até i-1)
            refazHeap(lista, 0, i - 1);
        }
    }

    // Método para manter a propriedade do Heap
    private static void refazHeap(ArrayList<Reserva> lista, int esq, int dir) {
        int i = esq;
        int mF = 2 * i + 1; // mF = Maior Filho (inicialmente o da esquerda)
        Reserva raiz = lista.get(i); // O elemento que estamos tentando posicionar
        boolean heap = false;

        while ((mF <= dir) && (!heap)) {
            // Verifica se o filho da direita existe e se é maior que o da esquerda
            if (mF < dir) {
                // Se filhoEsq < filhoDir, então o maior filho é o da direita
                if (lista.get(mF).compareTo(lista.get(mF + 1)) < 0) {
                    mF++;
                }
            }

            // Verifica se a raiz é menor que o maior filho
            if (raiz.compareTo(lista.get(mF)) < 0) {
                // Se for menor, o filho sobe
                lista.set(i, lista.get(mF));
                i = mF; // Avança o índice para baixo
                mF = 2 * i + 1; // Calcula o próximo filho à esquerda
            } else {
                // Se a raiz já é maior que os filhos, a posição está correta
                heap = true;
            }
        }

        // Coloca a raiz na posição definitiva encontrada
        lista.set(i, raiz);
    }
}