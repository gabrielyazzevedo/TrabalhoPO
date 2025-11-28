    package trabalho;

    import java.util.ArrayList;

    // cada bolinha da árvore AVL guarda uma reserva, filhos e altura
    class NodeAVL {
        Reserva reserva;
        NodeAVL left, right;
        int height; // altura do nó na árvore (pra saber se tá desbalanceada)

        NodeAVL(Reserva reserva) {
            this.reserva = reserva;
            height = 1; // novo nó sempre começa com altura 1
        }
    }

    // árvore AVL: se balanceia sozinha quando fica torta
    public class AVL {
        private NodeAVL root; // raiz da árvore (topo)

        // pega a altura do nó (se não existir retorna 0)
        private int height(NodeAVL n) {
            return n == null ? 0 : n.height;
        }

        // gira a árvore pra direita quando o lado esquerdo tá muito pesado
        // exemplo: se A tem filho B à esquerda muito alto, B vira raiz e A vira filho direito de B
        private NodeAVL rightRotate(NodeAVL y) {
            NodeAVL x = y.left;    // pega o filho esquerdo
            NodeAVL T2 = x.right;  // guarda a subárvore do meio
            x.right = y;           // x sobe e y vira filho direito
            y.left = T2;           // y recebe a subárvore que sobrou
            y.height = Math.max(height(y.left), height(y.right)) + 1; // recalcula altura de y
            x.height = Math.max(height(x.left), height(x.right)) + 1; // recalcula altura de x
            return x; // x é o novo topo desse pedaço
        }

        // gira a árvore pra esquerda quando o lado direito tá muito pesado
        // exemplo: se A tem filho B à direita muito alto, B vira raiz e A vira filho esquerdo de B
        private NodeAVL leftRotate(NodeAVL x) {
            NodeAVL y = x.right;   // pega o filho direito
            NodeAVL T2 = y.left;   // guarda a subárvore do meio
            y.left = x;            // y sobe e x vira filho esquerdo
            x.right = T2;          // x recebe a subárvore que sobrou
            x.height = Math.max(height(x.left), height(x.right)) + 1; // recalcula altura de x
            y.height = Math.max(height(y.left), height(y.right)) + 1; // recalcula altura de y
            return y; // y é o novo topo desse pedaço
        }

        // calcula o quanto a árvore tá desbalanceada (diferença entre altura esquerda e direita)
        // se der muito positivo, esquerda tá pesada; se der muito negativo, direita tá pesada
        private int getBalance(NodeAVL n) {
            return n == null ? 0 : height(n.left) - height(n.right);
        }

        // coloca uma reserva nova na árvore
        public void insert(Reserva reserva) {
            root = insertRec(root, reserva);
        }

        // insere a reserva e depois arruma a árvore se ficou torta
        private NodeAVL insertRec(NodeAVL node, Reserva reserva) {
            if (node == null) return new NodeAVL(reserva); // achou lugar vazio, cria aqui

            // decide pra que lado vai
            if (reserva.compareTo(node.reserva) < 0) node.left = insertRec(node.left, reserva); // menor vai esquerda
            else if (reserva.compareTo(node.reserva) > 0) node.right = insertRec(node.right, reserva); // maior vai direita

            node.height = 1 + Math.max(height(node.left), height(node.right)); // atualiza altura do nó

            int balance = getBalance(node); // ve se ficou torta

            // se ficou torta pra esquerda e inseriu na esquerda da esquerda: gira direita
            if (balance > 1 && reserva.compareTo(node.left.reserva) < 0) return rightRotate(node);
            
            // se ficou torta pra direita e inseriu na direita da direita: gira esquerda
            if (balance < -1 && reserva.compareTo(node.right.reserva) > 0) return leftRotate(node);
            
            // se ficou torta pra esquerda mas inseriu na direita da esquerda: gira duas vezes
            if (balance > 1 && reserva.compareTo(node.left.reserva) > 0) {
                node.left = leftRotate(node.left); // primeiro gira o filho esquerdo pra esquerda
                return rightRotate(node);          // depois gira o nó atual pra direita
            }
            
            // se ficou torta pra direita mas inseriu na esquerda da direita: gira duas vezes
            if (balance < -1 && reserva.compareTo(node.right.reserva) < 0) {
                node.right = rightRotate(node.right); // primeiro gira o filho direito pra direita
                return leftRotate(node);              // depois gira o nó atual pra esquerda
            }

            return node; // retorna o nó (já balanceado se precisou)
        }

        // procura todas as reservas de uma pessoa pelo nome
        public ArrayList<Reserva> search(String nome) {
            ArrayList<Reserva> result = new ArrayList<>();
            searchRec(root, nome, result);
            return result;
        }

        // vai descendo na árvore procurando o nome
        private void searchRec(NodeAVL node, String nome, ArrayList<Reserva> result) {
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