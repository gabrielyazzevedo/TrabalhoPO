package trabalho;

// classe que representa uma reserva de voo
public class Reserva implements Comparable<Reserva> {
    private String codigoReserva;
    private String nome;
    private String voo;
    private String data;
    private String assento;

    // construtor que inicializa todos os campos
    public Reserva(String codigoReserva, String nome, String voo, String data, String assento) {
        this.codigoReserva = codigoReserva;
        this.nome = nome;
        this.voo = voo;
        this.data = data;
        this.assento = assento;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public String getNome() {
        return nome;
    }

    public String getVoo() {
        return voo;
    }

    public String getData() {
        return data;
    }

    public String getAssento() {
        return assento;
    }

    @Override
    public int compareTo(Reserva outra) {
        int cmpNome = this.nome.compareTo(outra.nome); // Compara o 'nome' com 'outro.nome'. O resultado fica guardado
                                                       // no 'cmpNome'. Se der negativo o 'nome' vem antes, se der zero
                                                       // os nomes são idênticos, se der positivo o 'nome' vem depois.
        if (cmpNome != 0)
            return cmpNome;
        return this.codigoReserva.compareTo(outra.codigoReserva); // Caso haja nomes iguais, partimos para a comparação
                                                                  // da chave secundária que é o codigoReserva. Quem
                                                                  // tiver o menor codigoReserva vem antes.
    }

    @Override
    public String toString() {
        return "Reserva: " + codigoReserva + "\n" +
                "Nome:    " + nome + "\n" +
                "Voo:     " + voo + "\n" +
                "Data:    " + data + "\n" +
                "Assento: " + assento + "\n";
    }
}
