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

        // ordena pelo código da reserva (R000001, R000002...)
        resultado.sort((a, b) -> a.getCodigoReserva().compareTo(b.getCodigoReserva()));
        return resultado;
    }
}