package trabalho;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    private static final int TAMANHO = 10007; // número primo grande
    private ArrayList<LinkedList<Reserva>> tabela;

    public HashTable() {
        tabela = new ArrayList<>(TAMANHO);
        for (int i = 0; i < TAMANHO; i++) {
            tabela.add(new LinkedList<>());
        }
    }

    private int hash(String nome) {
        int h = nome.hashCode();
        h = Math.abs(h);            // evita negativo
        return h % TAMANHO;
    }

    public void insert(Reserva r) {
        int pos = hash(r.getNome());
        tabela.get(pos).add(r);
    }

    public ArrayList<Reserva> search(String nome) {
        int pos = hash(nome);
        ArrayList<Reserva> resultado = new ArrayList<>();

        for (Reserva r : tabela.get(pos)) {
            if (r.getNome().equals(nome)) {
                resultado.add(r);
            }
        }

        // ordena pelo código da reserva usando insertion sort
        for (int i = 1; i < resultado.size(); i++) {
            Reserva chave = resultado.get(i);
            int j = i - 1;
            while (j >= 0 && resultado.get(j).getCodigoReserva().compareTo(chave.getCodigoReserva()) > 0) {
                resultado.set(j + 1, resultado.get(j));
                j--;
            }
            resultado.set(j + 1, chave);
        }
        return resultado;
    }
}