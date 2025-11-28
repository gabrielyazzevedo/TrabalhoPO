package trabalho;

import java.util.ArrayList;

public class AVL {

    private class NoAvl {
        Reserva dado;
        NoAvl esq, dir;
        int fb; // Fator de Balanceamento

        public NoAvl(Reserva dado) {
            this.dado = dado;
        }
    }

    private NoAvl raiz;
    private boolean balancear;

    public void inserir(Reserva reserva) {
        this.raiz = inserirRec(reserva, this.raiz);
    }

    private NoAvl inserirRec(Reserva res, NoAvl no) {
        if (no == null) {
            this.balancear = true;
            return new NoAvl(res);
        }

        int cmp = res.compareTo(no.dado);
        if (cmp < 0) {
            no.esq = inserirRec(res, no.esq);
            if (this.balancear)
                no = balancearDir(no);
        } else { // >= 0 (inclui duplicatas indo para a direita)
            no.dir = inserirRec(res, no.dir);
            if (this.balancear)
                no = balancearEsq(no);
        }
        return no;
    }

    private NoAvl balancearDir(NoAvl no) {
        switch (no.fb) {
            case 1:
                no.fb = 0;
                this.balancear = false;
                break;
            case 0:
                no.fb = -1;
                break;
            case -1:
                no = rotDir(no);
                break;
        }
        return no;
    }

    private NoAvl balancearEsq(NoAvl no) {
        switch (no.fb) {
            case -1:
                no.fb = 0;
                this.balancear = false;
                break;
            case 0:
                no.fb = 1;
                break;
            case 1:
                no = rotEsq(no);
                break;
        }
        return no;
    }

    private NoAvl rotDir(NoAvl no) {
        NoAvl t1 = no.esq;
        if (t1.fb == -1) {
            no.esq = t1.dir;
            t1.dir = no;
            no.fb = 0;
            no = t1;
        } else { // Dupla
            NoAvl t2 = t1.dir;
            t1.dir = t2.esq;
            t2.esq = t1;
            no.esq = t2.dir;
            t2.dir = no;
            no.fb = (t2.fb == 1) ? -1 : 0;
            t1.fb = (t2.fb == -1) ? 1 : 0;
            no = t2;
        }
        no.fb = 0;
        this.balancear = false;
        return no;
    }

    private NoAvl rotEsq(NoAvl no) {
        NoAvl t1 = no.dir;
        if (t1.fb == 1) {
            no.dir = t1.esq;
            t1.esq = no;
            no.fb = 0;
            no = t1;
        } else {
            NoAvl t2 = t1.esq;
            t1.esq = t2.dir;
            t2.dir = t1;
            no.dir = t2.esq;
            t2.esq = no;
            no.fb = (t2.fb == -1) ? 1 : 0;
            t1.fb = (t2.fb == 1) ? -1 : 0;
            no = t2;
        }
        no.fb = 0;
        this.balancear = false;
        return no;
    }

    public ArrayList<Reserva> pesquisar(String nome) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        buscaRec(raiz, nome, resultado);
        return resultado;
    }

    private void buscaRec(NoAvl no, String nome, ArrayList<Reserva> resultado) {
        if (no == null)
            return;
        int cmp = nome.compareTo(no.dado.getNome());
        if (cmp < 0)
            buscaRec(no.esq, nome, resultado);
        else if (cmp > 0)
            buscaRec(no.dir, nome, resultado);
        else {
            buscaRec(no.esq, nome, resultado);
            resultado.add(no.dado);
            buscaRec(no.dir, nome, resultado);
        }
    }
}