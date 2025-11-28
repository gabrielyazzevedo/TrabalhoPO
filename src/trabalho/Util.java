package trabalho;

import java.io.*;
import java.util.ArrayList;

// utilitários para leitura e escrita de arquivos
public class Util {
    public static ArrayList<Reserva> carregarArquivo(String arquivo) throws IOException {
        ArrayList<Reserva> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;
        // lê cada linha do arquivo
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";"); // separa campos por ponto e vírgula
            if (partes.length == 5) { // verifica se tem todos os campos
                lista.add(new Reserva(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                        partes[4].trim()));
            }
        }
        br.close();
        return lista;
    }

    // grava lista ordenada em arquivo
    public static void gravarOrdenacao(String arquivo, ArrayList<Reserva> lista) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        // escreve cada reserva
        for (Reserva r : lista) {
            bw.write(r.toString()); // usa toString da classe Reserva
            bw.newLine();
        }
        bw.close();
    }

    public static ArrayList<String> carregarNomeBusca(String arquivoNomes) throws IOException {
        ArrayList<String> nomes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivoNomes));
        String linha;
        // lê cada nome
        while ((linha = br.readLine()) != null) {
            nomes.add(linha.trim());
        }
        br.close();
        return nomes;
    }

    public static void gravarPesquisa(String arquivo, ArrayList<String> resultados) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        // escreve cada linha de resultado
        for (String res : resultados) {
            bw.write(res);
            bw.newLine();
        }
        bw.close();
    }
}
