package trabalho;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    private static final int TAMANHO = 10007; // número primo para reduzir colisões
    private ArrayList<LinkedList<Reserva>> tabela; // array de listas encadeadas (tratamento de colisões)

    public HashTable() {
        tabela = new ArrayList<>(TAMANHO);
        for (int i = 0; i < TAMANHO; i++) {
            tabela.add(new LinkedList<>()); // cada posição começa com lista vazia
        }
    }

    // calcula índice da tabela usando hash do nome
    private int hash(String nome) {
        int h = nome.hashCode(); // hash padrão do Java
        h = Math.abs(h); // garante valor positivo
        return h % TAMANHO; // mapeia para índice válido
    }

    // adiciona reserva na posição calculada pelo hash
    public void insert(Reserva r) {
        int pos = hash(r.getNome());
        tabela.get(pos).add(r); // adiciona no final da lista encadeada
    }

    // busca todas as reservas com nome especificado
    public ArrayList<Reserva> search(String nome) {
        int pos = hash(nome); // calcula posição
        ArrayList<Reserva> resultado = new ArrayList<>();

        for (Reserva r : tabela.get(pos)) { // percorre lista encadeada na posição
            if (r.getNome().equals(nome)) {
                resultado.add(r); // adiciona se nome bater
            }
        }

        // ordena resultado por código da reserva (insertion sort)
        for (int i = 1; i < resultado.size(); i++) {
            Reserva chave = resultado.get(i);
            int j = i - 1;
            while (j >= 0 && resultado.get(j).getCodigoReserva().compareTo(chave.getCodigoReserva()) > 0) {
                resultado.set(j + 1, resultado.get(j)); // desloca maior para direita
                j--;
            }
            resultado.set(j + 1, chave); // insere na posição correta
        }
        return resultado;
    }
}