package trabalho;

import java.util.ArrayList;

public class ABB {

    private class NoABB {
        Reserva dado;
        NoABB esq, dir;

        public NoABB(Reserva dado) {
            this.dado = dado;
            this.esq = null;
            this.dir = null;
        }
    }

    private NoABB raiz;

    public ABB() {
        this.raiz = null;
    }

    // Constrói árvore balanceada a partir de uma lista
    public void construirDeLista(ArrayList<Reserva> lista) {
        heapsort(lista); // Ordena o vetor antes

        this.raiz = construirBalanceada(lista, 0, lista.size() - 1);
    }

    // Pega o meio do vetor e transforma em raiz recursivamente
    private NoABB construirBalanceada(ArrayList<Reserva> lista, int inicio, int fim) {
        if (inicio > fim) {
            return null;
        }

        int meio = (inicio + fim) / 2;
        NoABB no = new NoABB(lista.get(meio)); // Cria o nó com o elemento do meio

        // Constrói as subárvores esquerda e direita recursivamente
        no.esq = construirBalanceada(lista, inicio, meio - 1);
        no.dir = construirBalanceada(lista, meio + 1, fim);

        return no;
    }

    // Rebalanceia uma árvore já existente
    public void balancear() {
        ArrayList<Reserva> lista = new ArrayList<>();
        caminhamentoCentral(this.raiz, lista); // Gera o Vetor Ordenado
        this.raiz = construirBalanceada(lista, 0, lista.size() - 1); // Reconstrói balanceada
    }

    // Caminhamento Central
    private void caminhamentoCentral(NoABB no, ArrayList<Reserva> lista) {
        if (no != null) {
            caminhamentoCentral(no.esq, lista);
            lista.add(no.dado); // Visita a raiz
            caminhamentoCentral(no.dir, lista);
        }
    }

    public void inserir(Reserva reserva) {
        this.raiz = inserirRec(reserva, this.raiz);
    }

    private NoABB inserirRec(Reserva reserva, NoABB no) {
        if (no == null) {
            return new NoABB(reserva);
        }

        int cmp = reserva.compareTo(no.dado);

        if (cmp < 0) {
            no.esq = inserirRec(reserva, no.esq);
        } else if (cmp > 0) {
            no.dir = inserirRec(reserva, no.dir);
        } else {
            // Tratamento de colisão (nomes iguais): insere à direita (regra do projeto)
            no.dir = inserirRec(reserva, no.dir);
        }
        return no;
    }

    public ArrayList<Reserva> pesquisar(String nome) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        pesquisarRec(this.raiz, nome, resultado);
        return resultado;
    }

    private void pesquisarRec(NoABB no, String nome, ArrayList<Reserva> resultado) {
        if (no == null) {
            return;
        }

        int cmp = nome.compareTo(no.dado.getNome());

        if (cmp < 0) {
            pesquisarRec(no.esq, nome, resultado);
        } else if (cmp > 0) {
            pesquisarRec(no.dir, nome, resultado);
        } else {
            // Encontrou (cmp == 0)
            resultado.add(no.dado);

            // Como há duplicatas, continua procurando nas duas subárvores
            // para garantir que pegamos todos
            pesquisarRec(no.esq, nome, resultado);
            pesquisarRec(no.dir, nome, resultado);
        }
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
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n && lista.get(esq).compareTo(lista.get(maior)) > 0)
            maior = esq;
        if (dir < n && lista.get(dir).compareTo(lista.get(maior)) > 0)
            maior = dir;

        if (maior != i) {
            Reserva troca = lista.get(i);
            lista.set(i, lista.get(maior));
            lista.set(maior, troca);
            heapify(lista, n, maior);
        }
    }
}