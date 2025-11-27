package trabalho;

import java.util.ArrayList;

// nó da árvore binária de busca
class NodeABB {
    Reserva reserva;
    NodeABB left, right;

    NodeABB(Reserva reserva) {
        this.reserva = reserva;
    }
}

// árvore binária de busca
public class ABB {
    private NodeABB root;

    // constrói árvore balanceada a partir de lista
    public void buildFromList(ArrayList<Reserva> lista) {
        heapsort(lista); // ordena para garantir balanceamento
        root = buildBalanced(lista, 0, lista.size() - 1); // começa a monta árvore com elemento do meio como raiz
    }

    // ordena lista usando heapsort
    private void heapsort(ArrayList<Reserva> lista) {
        int n = lista.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i); // transforma em heap máximo
        }
        for (int i = n - 1; i > 0; i--) {
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp); // move maior para final
            heapify(lista, i, 0); // reconstrói heap com tamanho reduzido
        }
    }

    // mantém propriedade do heap (pai maior que filhos)
    private void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l; // compara com filho esquerdo
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r; // compara com filho direito
        if (largest != i) {
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap); // troca raiz com maior filho
            heapify(lista, n, largest); // propaga troca para baixo
        }
    }

    // insere reserva na árvore
    public void insert(Reserva reserva) {
        root = insertRec(root, reserva);
    }

    // insere recursivamente comparando valores
    private NodeABB insertRec(NodeABB root, Reserva reserva) {
        if (root == null) return new NodeABB(reserva); // cria nó folha
        if (reserva.compareTo(root.reserva) < 0) root.left = insertRec(root.left, reserva); // vai esquerda
        else if (reserva.compareTo(root.reserva) > 0) root.right = insertRec(root.right, reserva); // vai direita
        return root;
    }

    // rebalanceia árvore existente
    public void balance() {
        ArrayList<Reserva> list = new ArrayList<>();
        inorder(root, list); // extrai elementos em ordem
        root = buildBalanced(list, 0, list.size() - 1); // reconstrói balanceada
    }

    // percorre árvore em ordem (esquerda-raiz-direita)
    private void inorder(NodeABB node, ArrayList<Reserva> list) {
        if (node != null) {
            inorder(node.left, list);
            list.add(node.reserva); // visita raiz
            inorder(node.right, list);
        }
    }

    // constrói árvore balanceada recursivamente
    private NodeABB buildBalanced(ArrayList<Reserva> list, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        NodeABB node = new NodeABB(list.get(mid)); // meio vira raiz
        node.left = buildBalanced(list, start, mid - 1); // metade esquerda vira subárvore esquerda
        node.right = buildBalanced(list, mid + 1, end); // metade direita vira subárvore direita
        return node;
    }

    // busca todas as reservas com nome especificado
    public ArrayList<Reserva> search(String nome) {
        ArrayList<Reserva> result = new ArrayList<>();
        searchRec(root, nome, result);
        return result;
    }

    // busca recursivamente percorrendo árvore
    private void searchRec(NodeABB node, String nome, ArrayList<Reserva> result) {
        if (node == null) return;
        int cmp = nome.compareTo(node.reserva.getNome());
        if (cmp < 0) searchRec(node.left, nome, result); // nome menor, vai esquerda
        else if (cmp > 0) searchRec(node.right, nome, result); // nome maior, vai direita
        else {
            result.add(node.reserva); // encontrou, adiciona
            searchRec(node.left, nome, result); // verifica duplicatas à esquerda
            searchRec(node.right, nome, result); // verifica duplicatas à direita
        }
    }
}
