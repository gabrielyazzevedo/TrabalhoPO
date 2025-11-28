package trabalho;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static final int RODADAS = 5; // quantas vezes vai rodar cada teste pra tirar a média
    private static final String ARQUIVO_NOMES = "dados/nome.txt";

    // todos os arquivos que vão ser testados
    private static final String[] ARQUIVOS = {
        "dados/Reserva1000ord.txt", "dados/Reserva1000inv.txt", "dados/Reserva1000alea.txt",
        "dados/Reserva5000ord.txt", "dados/Reserva5000inv.txt", "dados/Reserva5000alea.txt",
        "dados/Reserva10000ord.txt","dados/Reserva10000inv.txt","dados/Reserva10000alea.txt",
        "dados/Reserva50000ord.txt","dados/Reserva50000inv.txt","dados/Reserva50000alea.txt"
    };

    public static void main(String[] args) throws IOException {
        // carrega os nomes que vão ser buscados depois
        ArrayList<String> nomesParaBuscar = Util.carregarNomeBusca(ARQUIVO_NOMES);

        // testa cada arquivo da lista
        for (String arquivo : ARQUIVOS) {
            String nomeBase = arquivo.replace("dados/Reserva", "").replace(".txt", "");

            // testa os 3 algoritmos de ordenação
            long tempoHeap = testarHeapsort(arquivo, "saida/heap" + nomeBase + ".txt");
            long tempoQuick = testarQuicksort(arquivo, "saida/quick" + nomeBase + ".txt");
            long tempoQuickIns = testarQuickInsertion(arquivo, "saida/QuickIns" + nomeBase + ".txt");

            // testa as 3 estruturas de busca
            long tempoABB = testarABB(arquivo, "saida/ABB" + nomeBase + ".txt", nomesParaBuscar);
            long tempoAVL = testarAVL(arquivo, "saida/AVL" + nomeBase + ".txt", nomesParaBuscar);
            long tempoHash = testarHash(arquivo, "saida/Hash" + nomeBase + ".txt", nomesParaBuscar);

            // mostra os tempos na tela (em milissegundos)
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

    // testa o heapsort e retorna o tempo médio
    private static long testarHeapsort(String entrada, String saida) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // faz uma cópia da lista original (pra não ordenar lista já ordenada)
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            // cronometra só a ordenação
            long ini = System.nanoTime();
            Heapsort.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista; // guarda a última lista ordenada
        }

        // salva o resultado em arquivo
        Util.gravarOrdenacao(saida, listaFinal);

        return total / RODADAS; // retorna o tempo médio
    }

    // testa o quicksort e retorna o tempo médio
    private static long testarQuicksort(String entrada, String saida) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // faz uma cópia da lista original
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            // cronometra só a ordenação
            long ini = System.nanoTime();
            Quicksort.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista;
        }

        // salva o resultado em arquivo
        Util.gravarOrdenacao(saida, listaFinal);

        return total / RODADAS; // retorna o tempo médio
    }

    // testa o quicksort híbrido (com insertion) e retorna o tempo médio
    private static long testarQuickInsertion(String entrada, String saida) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> listaOriginal = Util.carregarArquivo(entrada);

        long total = 0;
        ArrayList<Reserva> listaFinal = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // faz uma cópia da lista original
            ArrayList<Reserva> lista = new ArrayList<>(listaOriginal);

            // cronometra só a ordenação
            long ini = System.nanoTime();
            QuickInsertion.sort(lista);
            total += System.nanoTime() - ini;

            listaFinal = lista;
        }

        // salva o resultado em arquivo
        Util.gravarOrdenacao(saida, listaFinal);

        return total / RODADAS; // retorna o tempo médio
    }

    // testa a árvore ABB (monta e busca) e retorna o tempo médio
    private static long testarABB(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

        long total = 0;
        ABB arvoreUltima = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // cronometra montar a árvore e fazer todas as buscas
            long ini = System.nanoTime();
            ABB arvore = new ABB();
            arvore.buildFromList(lista); // monta a árvore com as reservas

            // busca cada nome na árvore
            for (String nome : nomes) {
                arvore.search(nome);
            }
            total += System.nanoTime() - ini;

            arvoreUltima = arvore; // guarda a última árvore
        }

        // salva os resultados das buscas em arquivo
        gerarArquivoPesquisa(arvoreUltima, nomes, saida);

        return total / RODADAS; // retorna o tempo médio
    }

    // testa a árvore AVL (monta e busca) e retorna o tempo médio
    private static long testarAVL(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

        long total = 0;
        AVL arvoreUltima = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // cronometra montar a árvore e fazer todas as buscas
            long ini = System.nanoTime();
            AVL arvore = new AVL();
            // insere cada reserva na árvore (ela se balanceia sozinha)
            for (Reserva r : lista) arvore.insert(r);

            // busca cada nome na árvore
            for (String nome : nomes) {
                arvore.search(nome);
            }
            total += System.nanoTime() - ini;

            arvoreUltima = arvore;
        }

        // salva os resultados das buscas em arquivo
        gerarArquivoPesquisa(arvoreUltima, nomes, saida);

        return total / RODADAS; // retorna o tempo médio
    }

    // testa a tabela hash (monta e busca) e retorna o tempo médio
    private static long testarHash(String entrada, String saida, ArrayList<String> nomes) throws IOException {
        // carrega as reservas do arquivo
        ArrayList<Reserva> lista = Util.carregarArquivo(entrada);

        long total = 0;
        HashTable tabelaUltima = null;

        // roda várias vezes pra tirar a média
        for (int i = 0; i < RODADAS; i++) {
            // cronometra montar a tabela e fazer todas as buscas
            long ini = System.nanoTime();
            HashTable tabela = new HashTable();
            // insere cada reserva na tabela hash
            for (Reserva r : lista) tabela.insert(r);

            // busca cada nome na tabela
            for (String nome : nomes) {
                tabela.search(nome);
            }
            total += System.nanoTime() - ini;

            tabelaUltima = tabela;
        }

        // salva os resultados das buscas em arquivo
        gerarArquivoPesquisa(tabelaUltima, nomes, saida);

        return total / RODADAS; // retorna o tempo médio
    }

    // monta o arquivo de saída com os resultados das buscas
    private static void gerarArquivoPesquisa(Object estrutura, ArrayList<String> nomes, String arquivo) throws IOException {
        ArrayList<String> linhas = new ArrayList<>();

        // busca cada nome e formata o resultado
        for (String nome : nomes) {
            linhas.add("NOME " + nome + ":");

            // busca na estrutura (pode ser ABB, AVL ou Hash)
            ArrayList<Reserva> encontradas;
            if (estrutura instanceof ABB)    encontradas = ((ABB) estrutura).search(nome);
            else if (estrutura instanceof AVL) encontradas = ((AVL) estrutura).search(nome);
            else                              encontradas = ((HashTable) estrutura).search(nome);

            // escreve o que encontrou
            if (encontradas.isEmpty()) {
                linhas.add("NÃO TEM RESERVA");
            } else {
                // escreve cada reserva encontrada
                for (Reserva r : encontradas) {
                    linhas.add("Reserva: " + r.getCodigoReserva() +
                              "    Voo: " + r.getVoo() +
                              "     Data: " + r.getData() +
                              "    Assento: " + r.getAssento());
                }
                linhas.add("TOTAL: " + encontradas.size() + " reservas");
            }
            linhas.add(""); // deixa uma linha em branco entre os nomes
        }
        Util.gravarPesquisa(arquivo, linhas);
    }
}