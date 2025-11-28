package trabalho;

// guarda os dados de uma reserva de voo
public class Reserva implements Comparable<Reserva> {
    private String codigoReserva;
    private String nome;
    private String voo;
    private String data;
    private String assento;

    // cria uma nova reserva com todos os dados
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
        // primeiro compara os nomes em ordem alfabética
        int cmpNome = this.nome.compareTo(outra.nome);
        // se os nomes são diferentes, já decide quem vem primeiro
        if (cmpNome != 0)
            return cmpNome; // negativo = eu venho antes, positivo = eu venho depois
        
        // se os nomes são iguais, desempata pelo código da reserva
        return this.codigoReserva.compareTo(outra.codigoReserva);
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
