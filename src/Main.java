import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final int RODADAS = 5;
    private static final String ARQUIVO_NOMES = "nome.txt";

    private static final String[] ARQUIVOS = {
        "Reserva1000ord.txt", "Reserva1000inv.txt", "Reserva1000alea.txt",
        "Reserva5000ord.txt", "Reserva5000inv.txt", "Reserva5000alea.txt",
        "Reserva10000ord.txt","Reserva10000inv.txt","Reserva10000alea.txt",
        "Reserva50000ord.txt","Reserva50000inv.txt","Reserva50000alea.txt"
    };

    public static void main(String[] args) throws IOException {
        ArrayList<String> nomesParaBuscar = Util.carregarNomeBusca(ARQUIVO_NOMES);

        for (String arquivo : ARQUIVOS) {
            String nomeBase = arquivo.replace("Reserva", "").replace(".txt", "");

            // ORDENAÇÕES
            long tempoHeap = testarHeapsort(arquivo, "heap" + nomeBase + ".txt");
            long tempoQuick = testarQuicksort(arquivo, "quick" + nomeBase + ".txt");
            long tempoQuickIns = testarQuickInsertion(arquivo, "QuickIns" + nomeBase + ".txt");

            // PESQUISAS
            long tempoABB = testarABB(arquivo, "ABB" + nomeBase + ".txt", nomesParaBuscar);
            long tempoAVL = testarAVL(arquivo, "AVL" + nomeBase + ".txt", nomesParaBuscar);
            long tempoHash = testarHash(arquivo, "Hash" + nomeBase + ".txt", nomesParaBuscar);

            // Mostra no console
            System.out.println("=== " + arquivo + " ===");
            System.out.println("Heapsort:      " + (tempoHeap / 1_000_000.0) + " ms");
            System.out.println("Quicksort:     " + (tempoQuick / 1_000_000.0) + " ms");
            System.out.println("Quick+Insert:  " + (tempoQuickIns / 1_000_000.0) + " ms");
            System.out.println("ABB:           " + (tempoABB / 1_000_000.0) + " ms");
            System.out.println("AVL:           " + (tempoAVL / 1_000_000.0) + " ms");
            System.out.println("Hash:          " + (tempoHash / 1_000_000.0) + " ms");
            System.out.println("-------------------------------");
        }
    }

    // ORDENAÇÕES
    private static long testarHeapsort(String entrada, String saida) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);
            Heapsort.sort(lista);
            Util.gravarOrdenacao(saida, lista);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    private static long testarQuicksort(String entrada, String saida) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);
            Quicksort.sort(lista);
            Util.gravarOrdenacao(saida, lista);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    private static long testarQuickInsertion(String entrada, String saida) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);
            QuickInsertion.sort(lista);
            Util.gravarOrdenacao(saida, lista);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    // PESQUISAS
    private static long testarABB(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

            BST arvore = new BST();
            for (Reserva r : lista) arvore.insert(r);
            arvore.balance();

            gerarArquivoPesquisa(arvore, nomes, saida);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    private static long testarAVL(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

            AVL arvore = new AVL();
            for (Reserva r : lista) arvore.insert(r);

            gerarArquivoPesquisa(arvore, nomes, saida);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    private static long testarHash(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        long total = 0;
        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

            HashTable tabela = new HashTable();
            for (Reserva r : lista) tabela.insert(r);

            gerarArquivoPesquisa(tabela, nomes, saida);
            total += System.nanoTime() - ini;
        }
        return total / RODADAS;
    }

    // Método auxiliar que serve pros 3 tipos de estrutura
    private static void gerarArquivoPesquisa(Object estrutura, ArrayList<String> nomes, String arquivo) throws IOException {
        ArrayList<String> linhas = new ArrayList<>();

        for (String nome : nomes) {
            linhas.add("NOME " + nome + ":");

            ArrayList<Reserva> encontradas;
            if (estrutura instanceof BST)    encontradas = ((BST) estrutura).search(nome);
            else if (estrutura instanceof AVL) encontradas = ((AVL) estrutura).search(nome);
            else                              encontradas = ((HashTable) estrutura).search(nome);

            if (encontradas.isEmpty()) {
                linhas.add("NÃO TEM RESERVA");
            } else {
                for (Reserva r : encontradas) {
                    linhas.add("Reserva: " + r.getCodigoReserva() + 
                              "    Voo: " + r.getVoo() + 
                              "     Data: " + r.getData() + 
                              "    Assento: " + r.getAssento());
                }
                linhas.add("TOTAL: " + encontradas.size() + " reservas");
            }
            linhas.add(""); // linha em branco entre nomes
        }
        Util.gravarPesquisa(arquivo, linhas);
    }
}