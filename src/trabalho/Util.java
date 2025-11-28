package trabalho;

import java.io.*;
import java.util.ArrayList;

// funções auxiliares para ler e gravar arquivos
public class Util {
    // lê o arquivo de reservas e transforma em lista
    public static ArrayList<Reserva> carregarArquivo(String arquivo) throws IOException {
        ArrayList<Reserva> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;
        // lê linha por linha do arquivo
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";"); // divide onde tem ponto e vírgula
            if (partes.length == 5) { // se tem os 5 campos (código, nome, voo, data, assento)
                // cria uma reserva nova e adiciona na lista
                lista.add(new Reserva(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(), partes[4].trim()));
            }
        }
        br.close();
        return lista;
    }

    // salva a lista de reservas ordenadas em um arquivo
    public static void gravarOrdenacao(String arquivo, ArrayList<Reserva> lista) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        // escreve cada reserva no arquivo
        for (Reserva r : lista) {
            bw.write(r.toString()); // converte a reserva em texto formatado
            bw.newLine();
        }
        bw.close();
    }

    // lê o arquivo com os nomes que vão ser buscados
    public static ArrayList<String> carregarNomeBusca(String arquivoNomes) throws IOException {
        ArrayList<String> nomes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(arquivoNomes));
        String linha;
        // lê cada nome do arquivo
        while ((linha = br.readLine()) != null) {
            nomes.add(linha.trim()); // remove espaços em branco e adiciona
        }
        br.close();
        return nomes;
    }

    // salva os resultados das buscas em um arquivo
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
