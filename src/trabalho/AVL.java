package trabalho;

import java.util.ArrayList;

class NodeAVL {
    Reserva reserva;
    NodeAVL left, right;
    int height;

    NodeAVL(Reserva reserva) {
        this.reserva = reserva;
        height = 1;
    }
}

public class AVL {
    private NodeAVL root;

    private int height(NodeAVL n) {
        return n == null ? 0 : n.height;
    }

    private NodeAVL rightRotate(NodeAVL y) { // Usada quando o lado esquerdo está muito 'pesado'. 
        NodeAVL x = y.left;                 // Move o nó atual para a direita e sobe o filho esquerdo               
        NodeAVL T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private NodeAVL leftRotate(NodeAVL x) { // Usada quando o lado direito está muito pesado. 
        NodeAVL y = x.right;                // Move o nó atual para a esquerda e o filho sobe para a direita.
        NodeAVL T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private int getBalance(NodeAVL n) {  // Calcula o fator balanceamento
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    public void insert(Reserva reserva) {
        root = insertRec(root, reserva);
    }

    private NodeAVL insertRec(NodeAVL node, Reserva reserva) { // Método recursivo
        // Insercao ABB
        if (node == null) return new NodeAVL(reserva); // Se chegar em uma folha nula, é criado um nó.

        // Se a nova reserva for menor, vai para a subàrvore esquerda.
        if (reserva.compareTo(node.reserva) < 0) node.left = insertRec(node.left, reserva);

        // Se a nova reserva for maior, vai para a subàrvore direita.
        else if (reserva.compareTo(node.reserva) > 0) node.right = insertRec(node.right, reserva);

        // Na volta da recursão, a altura da àrvore é atualizada.
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);
        if (balance > 1 && reserva.compareTo(node.left.reserva) < 0) return rightRotate(node);
        if (balance < -1 && reserva.compareTo(node.right.reserva) > 0) return leftRotate(node);
        if (balance > 1 && reserva.compareTo(node.left.reserva) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && reserva.compareTo(node.right.reserva) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public ArrayList<Reserva> search(String nome) {
        ArrayList<Reserva> result = new ArrayList<>();
        searchRec(root, nome, result);
        return result;
    }

    private void searchRec(NodeAVL node, String nome, ArrayList<Reserva> result) {
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
