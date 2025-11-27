package trabalho;

import java.util.ArrayList;

class NodeABB {
    Reserva reserva;
    NodeABB left, right;

    NodeABB(Reserva reserva) {
        this.reserva = reserva;
    }
}

public class ABB {
    private NodeABB root;

    public void buildFromList(ArrayList<Reserva> lista) {
        // Ordena a lista primeiro usando heapsort
        heapsort(lista);
        root = buildBalanced(lista, 0, lista.size() - 1);
    }

    private void heapsort(ArrayList<Reserva> lista) {
        int n = lista.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            Reserva temp = lista.get(0);
            lista.set(0, lista.get(i));
            lista.set(i, temp);
            heapify(lista, i, 0);
        }
    }

    private void heapify(ArrayList<Reserva> lista, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && lista.get(l).compareTo(lista.get(largest)) > 0) largest = l;
        if (r < n && lista.get(r).compareTo(lista.get(largest)) > 0) largest = r;
        if (largest != i) {
            Reserva swap = lista.get(i);
            lista.set(i, lista.get(largest));
            lista.set(largest, swap);
            heapify(lista, n, largest);
        }
    }

    public void insert(Reserva reserva) {
        root = insertRec(root, reserva);
    }

    private NodeABB insertRec(NodeABB root, Reserva reserva) {
        if (root == null) return new NodeABB(reserva);
        if (reserva.compareTo(root.reserva) < 0) root.left = insertRec(root.left, reserva);
        else if (reserva.compareTo(root.reserva) > 0) root.right = insertRec(root.right, reserva);
        return root;
    }

    public void balance() {
        ArrayList<Reserva> list = new ArrayList<>();
        inorder(root, list);
        root = buildBalanced(list, 0, list.size() - 1);
    }

    private void inorder(NodeABB node, ArrayList<Reserva> list) {
        if (node != null) {
            inorder(node.left, list);
            list.add(node.reserva);
            inorder(node.right, list);
        }
    }

    private NodeABB buildBalanced(ArrayList<Reserva> list, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        NodeABB node = new NodeABB(list.get(mid));
        node.left = buildBalanced(list, start, mid - 1);
        node.right = buildBalanced(list, mid + 1, end);
        return node;
    }

    public ArrayList<Reserva> search(String nome) {
        ArrayList<Reserva> result = new ArrayList<>();
        searchRec(root, nome, result);
        return result;
    }

    private void searchRec(NodeABB node, String nome, ArrayList<Reserva> result) {
        if (node == null) return;
        int cmp = nome.compareTo(node.reserva.getNome());
        if (cmp < 0) searchRec(node.left, nome, result);
        else if (cmp > 0) searchRec(node.right, nome, result);
        else {
            result.add(node.reserva);
            searchRec(node.left, nome, result);
            searchRec(node.right, nome, result);
        }
    }
}
