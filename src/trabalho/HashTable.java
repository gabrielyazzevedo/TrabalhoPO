package trabalho;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {

    private LinkedList<Reserva>[] tabela;
    private int primo;

    @SuppressWarnings("unchecked")
    public HashTable(int tamanhoArquivo) {
        switch (tamanhoArquivo) {
            case 1000:
                primo = 1009;
                break;
            case 5000:
                primo = 5003;
                break;
            case 10000:
                primo = 10007;
                break;
            case 50000:
                primo = 50021;
                break;
            default:
                primo = 50021;
        }

        tabela = new LinkedList[primo];
        for (int i = 0; i < primo; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += Character.getNumericValue(chave.charAt(i));
        }
        return Math.abs(soma % primo);
    }

    public void inserir(Reserva reserva) {
        int pos = hash(reserva.getNome());
        tabela[pos].add(reserva);
    }

    public ArrayList<Reserva> pesquisar(String nome) {
        ArrayList<Reserva> encontradas = new ArrayList<>();
        int pos = hash(nome);

        for (Reserva r : tabela[pos]) {
            if (r.getNome().equalsIgnoreCase(nome)) {
                encontradas.add(r);
            }
        }
        return encontradas;
    }
}