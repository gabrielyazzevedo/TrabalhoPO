package trabalho;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    private static final int TAMANHO = 10007; // tamanho da tabela (número primo pra não dar colisão)
    private ArrayList<LinkedList<Reserva>> tabela; // cada posição guarda uma lista de reservas

    public HashTable() {
        tabela = new ArrayList<>(TAMANHO);
        // cria todas as posições vazias
        for (int i = 0; i < TAMANHO; i++) {
            tabela.add(new LinkedList<>());
        }
    }

    // transforma o nome em um número pra saber em qual posição guardar
    private int hash(String nome) {
        int h = nome.hashCode(); // java transforma texto em número
        h = Math.abs(h);         // garante que não vai dar número negativo
        return h % TAMANHO;      // pega o resto da divisão pra ficar dentro do tamanho da tabela
    }

    // guarda uma reserva na tabela
    public void insert(Reserva r) {
        int pos = hash(r.getNome()); // calcula em qual posição guardar
        tabela.get(pos).add(r);      // adiciona na lista daquela posição
    }

    // procura todas as reservas de uma pessoa pelo nome
    public ArrayList<Reserva> search(String nome) {
        int pos = hash(nome); // calcula em qual posição procurar
        ArrayList<Reserva> resultado = new ArrayList<>();

        // olha todas as reservas daquela posição
        for (Reserva r : tabela.get(pos)) {
            if (r.getNome().equals(nome)) {
                resultado.add(r); // se o nome bater, adiciona
            }
        }

        // ordena as reservas encontradas pelo código (insertion sort simples)
        for (int i = 1; i < resultado.size(); i++) {
            Reserva chave = resultado.get(i); // pega a reserva pra inserir
            int j = i - 1;
            // vai empurrando as maiores pra direita
            while (j >= 0 && resultado.get(j).getCodigoReserva().compareTo(chave.getCodigoReserva()) > 0) {
                resultado.set(j + 1, resultado.get(j));
                j--;
            }
            resultado.set(j + 1, chave); // coloca a reserva no lugar certo
        }
        return resultado;
    }
}