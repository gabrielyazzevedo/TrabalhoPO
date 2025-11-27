package trabalho;

import java.util.ArrayList;

// nó da árvore AVL
class NodeAVL {
    Reserva reserva;
    NodeAVL left, right;
    int height;

    NodeAVL(Reserva reserva) {
        this.reserva = reserva;
        height = 1;
    }
}

// árvore AVL auto-balanceada
public class AVL {
    private NodeAVL root;

    // retorna altura do nó (0 se nulo)
    private int height(NodeAVL n) {
        return n == null ? 0 : n.height;
    }

    // rotação à direita para balancear lado esquerdo pesado
    private NodeAVL rightRotate(NodeAVL y) {
        NodeAVL x = y.left;
        NodeAVL T2 = x.right;
        x.right = y; // x sobe, y desce para direita
        y.left = T2; // subárvore T2 vira filho esquerdo de y
        y.height = Math.max(height(y.left), height(y.right)) + 1; // recalcula altura de y
        x.height = Math.max(height(x.left), height(x.right)) + 1; // recalcula altura de x
        return x; // x é a nova raiz
    }

    // rotação à esquerda para balancear lado direito pesado
    private NodeAVL leftRotate(NodeAVL x) {
        NodeAVL y = x.right;
        NodeAVL T2 = y.left;
        y.left = x; // y sobe, x desce para esquerda
        x.right = T2; // subárvore T2 vira filho direito de x
        x.height = Math.max(height(x.left), height(x.right)) + 1; // recalcula altura de x
        y.height = Math.max(height(y.left), height(y.right)) + 1; // recalcula altura de y
        return y; // y é a nova raiz
    }

    // calcula fator de balanceamento (diferença entre altura esquerda e direita)
    private int getBalance(NodeAVL n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // insere reserva na árvore
    public void insert(Reserva reserva) {
        root = insertRec(root, reserva);
    }

    // insere recursivamente e balanceia automaticamente
    private NodeAVL insertRec(NodeAVL node, Reserva reserva) {
        if (node == null) return new NodeAVL(reserva); // cria novo nó

        if (reserva.compareTo(node.reserva) < 0) node.left = insertRec(node.left, reserva); // menor, vai esquerda
        else if (reserva.compareTo(node.reserva) > 0) node.right = insertRec(node.right, reserva); // maior, vai direita

        node.height = 1 + Math.max(height(node.left), height(node.right)); // atualiza altura após inserção

        int balance = getBalance(node); // calcula desbalanceamento

        if (balance > 1 && reserva.compareTo(node.left.reserva) < 0) return rightRotate(node); // esquerda-esquerda: rotação direita
        if (balance < -1 && reserva.compareTo(node.right.reserva) > 0) return leftRotate(node); // direita-direita: rotação esquerda
        if (balance > 1 && reserva.compareTo(node.left.reserva) > 0) { // esquerda-direita: dupla rotação
            node.left = leftRotate(node.left); // rotaciona filho esquerdo à esquerda
            return rightRotate(node); // depois rotaciona raiz à direita
        }
        if (balance < -1 && reserva.compareTo(node.right.reserva) < 0) { // direita-esquerda: dupla rotação
            node.right = rightRotate(node.right); // rotaciona filho direito à direita
            return leftRotate(node); // depois rotaciona raiz à esquerda
        }

        return node; // retorna nó (possivelmente balanceado)
    }

    // busca todas as reservas com nome especificado
    public ArrayList<Reserva> search(String nome) {
        ArrayList<Reserva> result = new ArrayList<>();
        searchRec(root, nome, result);
        return result;
    }

    // busca recursivamente percorrendo árvore
    private void searchRec(NodeAVL node, String nome, ArrayList<Reserva> result) {
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
