package trabalho;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final int RODADAS = 5;
    private static final String ARQUIVO_NOMES = "dados/nome.txt";

    private static final String[] ARQUIVOS = {
            "dados/Reserva1000ord.txt", "dados/Reserva1000inv.txt", "dados/Reserva1000alea.txt",
            "dados/Reserva5000ord.txt", "dados/Reserva5000inv.txt", "dados/Reserva5000alea.txt",
            "dados/Reserva10000ord.txt", "dados/Reserva10000inv.txt", "dados/Reserva10000alea.txt",
            "dados/Reserva50000ord.txt", "dados/Reserva50000inv.txt", "dados/Reserva50000alea.txt"
    };

    public static void main(String[] args) throws IOException {
        // carrega nomes para pesquisa
        ArrayList<String> nomesParaBuscar = Util.carregarNomeBusca(ARQUIVO_NOMES);

        // processa cada arquivo de entrada
        for (String arquivo : ARQUIVOS) {
            String nomeBase = arquivo.replace("dados/Reserva", "").replace(".txt", "");

            // TESTES DE ORDENAÇÃO
            long tempoHeap = testarHeapsort(arquivo, "saida/heap" + nomeBase + ".txt");
            long tempoQuick = testarQuicksort(arquivo, "saida/quick" + nomeBase + ".txt");
            long tempoQuickIns = testarQuickInsertion(arquivo, "saida/QuickIns" + nomeBase + ".txt");

            // TESTES DE PESQUISA
            long tempoABB = testarABB(arquivo, "saida/ABB" + nomeBase + ".txt", nomesParaBuscar);
            long tempoAVL = testarAVL(arquivo, "saida/AVL" + nomeBase + ".txt", nomesParaBuscar);
            long tempoHash = testarHash(arquivo, "saida/Hash" + nomeBase + ".txt", nomesParaBuscar);

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

    private static long testarHeapsort(String entrada, String saida) throws IOException {
        // carrega arquivo uma vez
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        // executa múltiplas rodadas
        for (int i = 0; i < RODADAS; i++) {
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            long ini = System.nanoTime();
            Heapsort.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista; // guarda última para gravar
        }

        // grava resultado uma vez
        Util.gravarOrdenacao(saida, listaFinal);

        return total / RODADAS; // retorna média
    }

    private static long testarQuicksort(String entrada, String saida) throws IOException {
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        for (int i = 0; i < RODADAS; i++) {
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            long ini = System.nanoTime();
            Quicksort.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista;
        }

        Util.gravarOrdenacao(saida, listaFinal);
        return total / RODADAS;
    }

    private static long testarQuickInsertion(String entrada, String saida) throws IOException {
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        for (int i = 0; i < RODADAS; i++) {
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            long ini = System.nanoTime();
            QuickInsertion.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista;
        }

        Util.gravarOrdenacao(saida, listaFinal);
        return total / RODADAS;
    }

    private static long testarABB(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

        long total = 0;
        ABB arvoreUltima = null;

        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            ABB arvore = new ABB();

            arvore.construirDeLista(lista);

            for (String nome : nomes) {
                arvore.pesquisar(nome);
            }
            total += System.nanoTime() - ini;

            arvoreUltima = arvore;
        }

        gerarArquivoPesquisa(arvoreUltima, nomes, saida);
        return total / RODADAS;
    }

    private static long testarAVL(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

        long total = 0;
        AVL arvoreUltima = null;

        for (int i = 0; i < RODADAS; i++) {
            long ini = System.nanoTime();
            AVL arvore = new AVL();
            for (Reserva r : lista)
                arvore.inserir(r);

            for (String nome : nomes) {
                arvore.pesquisar(nome);
            }
            total += System.nanoTime() - ini;

            arvoreUltima = arvore;
        }

        gerarArquivoPesquisa(arvoreUltima, nomes, saida);
        return total / RODADAS;
    }

    private static long testarHash(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);
        int tamanhoArquivo = lista.size();

        long total = 0;
        HashTable tabelaUltima = null;

        for (int i = 0; i < RODADAS; i++) {
            HashTable tabela = new HashTable(tamanhoArquivo);

            long ini = System.nanoTime();
            for (Reserva r : lista)
                tabela.inserir(r);
            for (String nome : nomes)
                tabela.pesquisar(nome);

            total += System.nanoTime() - ini;

            tabelaUltima = tabela;
        }

        gerarArquivoPesquisa(tabelaUltima, nomes, saida);
        return total / RODADAS;
    }

    private static void gerarArquivoPesquisa(Object estrutura, ArrayList<String> nomes, String arquivo)
            throws IOException {
        ArrayList<String> linhas = new ArrayList<>();

        for (String nome : nomes) {
            linhas.add("NOME " + nome + ":");

            ArrayList<Reserva> encontradas = new ArrayList<>();

            if (estrutura instanceof ABB) {
                encontradas = ((ABB) estrutura).pesquisar(nome);
            } else if (estrutura instanceof AVL) {
                encontradas = ((AVL) estrutura).pesquisar(nome);
            } else if (estrutura instanceof HashTable) {
                encontradas = ((HashTable) estrutura).pesquisar(nome);
            }

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