package trabalho;

import java.util.ArrayList;

// cada bolinha da árvore guarda uma reserva e aponta pros filhos
class NodeABB {
    Reserva reserva;
    NodeABB left, right; // filho da esquerda e da direita (começam vazios)

    NodeABB(Reserva reserva) {
        this.reserva = reserva;
    }
}

// árvore binária de busca: organiza reservas para buscar rápido
public class ABB {
    private NodeABB root; // raiz da árvore (topo)

    // monta a árvore a partir de uma lista de reservas
    public void buildFromList(ArrayList<Reserva> lista) {
        heapsort(lista); // primeiro ordena a lista
        root = buildBalanced(lista, 0, lista.size() - 1); // depois monta a árvore pegando sempre o elemento do meio
    }

    // ordena a lista do menor pro maior usando heapsort
    private void heapsort(ArrayList<Reserva> lista) {
        int n = lista.size();
        // transforma a lista numa árvore heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        // vai pegando o maior e jogando pro final
        for (int i = n - 1; i > 0; i--) {
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp); // joga o maior pro final
            heapify(lista, i, 0); // arruma o resto
        }
    }

    // arruma a árvore heap: pai tem que ser maior que os filhos
    private void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i;           // começa achando que o pai é o maior
        int l = 2 * i + 1;         // posição do filho esquerdo
        int r = 2 * i + 2;         // posição do filho direito
        // ve se o filho esquerdo é maior
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        // ve se o filho direito é maior
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;
        // se algum filho era maior, troca e continua arrumando
        if (largest != i) {
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap);
            heapify(lista, n, largest); // arruma lá embaixo também
        }
    }

    // coloca uma reserva nova na árvore
    public void insert(Reserva reserva) {
        root = insertRec(root, reserva);
    }

    // vai descendo na árvore até achar o lugar certo pra inserir
    private NodeABB insertRec(NodeABB root, Reserva reserva) {
        if (root == null) return new NodeABB(reserva); // achou o lugar vazio, cria aqui
        // se o nome é menor, vai pra esquerda
        if (reserva.compareTo(root.reserva) < 0) root.left = insertRec(root.left, reserva);
        // se o nome é maior, vai pra direita
        else if (reserva.compareTo(root.reserva) > 0) root.right = insertRec(root.right, reserva);
        return root;
    }

    // reorganiza a árvore pra ficar balanceada (não ficar torta)
    public void balance() {
        ArrayList<Reserva> list = new ArrayList<>();
        inorder(root, list); // pega todos os elementos em ordem
        root = buildBalanced(list, 0, list.size() - 1); // monta a árvore de novo
    }

    // percorre a árvore na ordem: esquerda -> meio -> direita (fica em ordem alfabética)
    private void inorder(NodeABB node, ArrayList<Reserva> list) {
        if (node != null) {
            inorder(node.left, list);      // primeiro visita filho esquerdo
            list.add(node.reserva);        // depois pega o do meio
            inorder(node.right, list);     // por último visita filho direito
        }
    }

    // monta uma árvore balanceada pegando sempre o elemento do meio como raiz
    private NodeABB buildBalanced(ArrayList<Reserva> list, int start, int end) {
        if (start > end) return null; // não tem mais elementos
        int mid = (start + end) / 2;  // pega o meio
        NodeABB node = new NodeABB(list.get(mid)); // meio vira raiz
        node.left = buildBalanced(list, start, mid - 1);  // esquerda fica com a primeira metade
        node.right = buildBalanced(list, mid + 1, end);   // direita fica com a segunda metade
        return node;
    }

    // procura todas as reservas de uma pessoa pelo nome
    public ArrayList<Reserva> search(String nome) {
        ArrayList<Reserva> result = new ArrayList<>();
        searchRec(root, nome, result);
        return result;
    }

    // vai descendo na árvore procurando o nome
    private void searchRec(NodeABB node, String nome, ArrayList<Reserva> result) {
        if (node == null) return; // chegou no fim, não tem nada aqui
        int cmp = nome.compareTo(node.reserva.getNome()); // compara o nome que tô buscando com o nome do nó
        // se o nome que eu quero é menor, vai pra esquerda
        if (cmp < 0) searchRec(node.left, nome, result);
        // se o nome que eu quero é maior, vai pra direita
        else if (cmp > 0) searchRec(node.right, nome, result);
        // se é igual, achou! mas pode ter mais com mesmo nome
        else {
            result.add(node.reserva); // adiciona essa reserva
            searchRec(node.left, nome, result);  // procura na esquerda se tem mais
            searchRec(node.right, nome, result); // procura na direita se tem mais
        }
    }
}
