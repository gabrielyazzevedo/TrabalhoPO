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
