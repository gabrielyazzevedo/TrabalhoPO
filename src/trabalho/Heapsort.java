package trabalho;

import java.util.ArrayList;

public class Heapsort {
    // organiza a lista do menor para o maior usando heapsort
    public static void sort(ArrayList<Reserva> lista) {
        int n = lista.size();
        
        // primeiro passo: transforma a lista em uma árvore heap
        // começa do meio da lista e vai até o início, organizando cada pedaço
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        
        // segundo passo: pega o maior elemento (que está no início) e joga pro final
        // depois reorganiza o resto e repete até ordenar tudo
        for (int i = n - 1; i > 0; i--) {
            // troca o primeiro elemento (maior) com o elemento na posição i
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp);
            
            // arruma a árvore de novo, mas agora sem considerar o final que já está ordenado
            heapify(lista, i, 0);
        }
    }

    // arruma a árvore para que o pai seja sempre maior que os filhos
    // isso é chamado de "propriedade do heap"
    private static void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i;      // começa assumindo que o pai (posição i) é o maior
        int l = 2 * i + 1;    // calcula a posição do filho da esquerda
        int r = 2 * i + 2;    // calcula a posição do filho da direita
        
        // se o filho da esquerda existir e for maior que o pai, anota ele como maior
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) 
            largest = l;
        
        // se o filho da direita existir e for maior que o maior atual, anota ele como maior
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) 
            largest = r;
        
        // se algum filho era maior que o pai, precisa trocar eles de lugar
        if (largest != i) {
            // faz a troca
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap);
            
            // chama de novo para o filho que foi trocado, porque pode ter bagunçado lá embaixo
            heapify(lista, n, largest);
        }
    }
}