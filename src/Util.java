import java.io.*;
import java.util.ArrayList;

public class Util {
    public static ArrayList<Reserva> carregarArquivo(String arquivo) throws IOException { // throws IOException: Caso
                                                                                          // haja erro de entrada/saída,
                                                                                          // o Java avisará
        ArrayList<Reserva> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivo)); //
        String linha;
        while ((linha = br.readLine()) != null) { // Loop que roda enquanto tiver linha no arquivo
            String[] partes = linha.split(":"); // Corta a linha em 5 pedaços para os 5 campos
            if (partes.length == 5) { // Só cria a reserva se realmente tiver 5 campos
                lista.add(new Reserva(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(), partes[4].trim()));
            }
        }
        br.close();
        return lista;
    }

    public static void gravarOrdenacao(String arquivo, ArrayList<Reserva> lista) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo)); // Sobrescreve o arquivo
        for (Reserva r : lista) { // Pega uma reserva por vez da lista já ordenada
            bw.write(r.toString()); // Escreve oque o toString da clase Reserva devolve
            bw.newLine();
        }
        bw.close();
    }

    public static ArrayList<String> carregarNomeBusca(String arquivoNomes) throws IOException {
        ArrayList<String> nomes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivoNomes));
        String linha;
        while ((linha = br.readLine()) != null) {
            nomes.add(linha.trim());

        }
        br.close();
        return nomes;
    }

    public static void gravarPesquisa(String arquivo, ArrayList<String> resultados) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        for (String res : resultados) {
            bw.write(res);
            bw.newLine();
        }
        bw.close();
    }
}
