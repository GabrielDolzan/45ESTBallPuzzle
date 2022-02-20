package resolucao;

import busca.Estado;
import java.util.LinkedList;
import java.util.List;

/**
 * NAO USAR ACENTUACAO
 */
public class EstadoPuzzle implements Estado {

    public static class Coluna {

        private String cores[] = new String[4];

        public String[] getCores() {
            return cores;
        }

        Coluna() {}

        Coluna(String[] cores) {
            this.cores = cores;
        }

        @Override
        public String toString() {
            return String.join(" ", cores);
        }

        @Override
        public boolean equals(Object obj) {
            Coluna outro = (Coluna)obj;

            return toString().equals(outro.toString());
        }

        public boolean possuiBola() {
            return cores[0] != null;
        }

        public boolean cheia() {
            for (String cor : cores) {
                if (cor == null) {
                    return false;
                }
            }

            return true;
        }

        public int tamanho() {
            int tamanho = 0;

            for (String cor : cores) {
                if (cor != null) {
                    tamanho++;
                }
            }

            return tamanho;
        }

        public boolean tudoMesmaCor() {
            if (!possuiBola()) {
                return true;
            }

            for (int iCor = 1; iCor < 4; iCor++) {
                // Verifica se esta cheio somente de uma cor
                if (getCores()[iCor] == null || !getCores()[iCor].equalsIgnoreCase(getCores()[0])) {
                    return false;
                }
            }

            return true;
        }

        public boolean somenteMesmaCor() {
            if (!possuiBola()) {
                return true;
            }

            for (int iCor = 1; iCor < getCores().length; iCor++) {
                // Verifica se possui somente a mesma cor na coluna
                if (getCores()[iCor] != null && !getCores()[iCor].equalsIgnoreCase(getCores()[0])) {
                    return false;
                }
            }

            return true;
        }

        public String getBolaTopo() {
            String corTopo = null;

            for (String cor : cores) {
                if (cor != null) {
                    corTopo = cor;
                }
            }

            return corTopo;
        }

        public boolean podeEmpilhar(String cor) {
            // Se possui bola
            if (possuiBola()) {
                // Se possui lugar para empilhar
                if (cores[3] == null) {
                    // E a bola do topo eh igual
                    return getBolaTopo().equalsIgnoreCase(cor);
                }

                return false;
            }

            return true;
        }

        public void removeBola() {
            // Percorre de cima para baixo e retira a primeira que possuir
            for (int i = 3; i >= 0; i--) {
                if (cores[i] != null) {
                    cores[i] = null;
                    break;
                }
            }
        }

        public void adicionaBola(String cor) {
            // Adiciona na primeira posição que estiver vazia
            for (int i = 0; i < cores.length; i++) {
                if (cores[i] == null) {
                    cores[i] = cor;
                    break;
                }
            }
        }

        public String[] getCopia() {
            String[] copia = new String[4];

            for (int i = 0; i < cores.length; i++) {
                copia[i] = cores[i];
            }

            return copia;
        }
/*
        public Coluna copiarSemDisco(int disco) {
            Coluna copia = new Coluna(p1, p2, p3);

            if (copia.p1 == disco)
                copia.p1 = 0;
            else
                if (copia.p2 == disco)
                    copia.p2 = 0;
                else
                    copia.p3 = 0;

            return copia;
        }

        public Coluna copiarComDisco(int disco, int posPino) {
            Coluna copia = new Coluna(p1, p2, p3);

            if (posPino == 1) {
                copia.p1 = disco;
            } else {
                if (posPino == 2) {
                    copia.p2 = disco;
                } else {
                    copia.p3 = disco;
                }
            }

            return copia;
        }

        public Coluna copiar() {
            return new Coluna(cores);
        }*/

    }

    private Coluna colunas[];

    public EstadoPuzzle(Coluna[] colunas) {
        this.colunas = colunas;
    }

    @Override
    public String getDescricao() {
        return "Trabalho de 45EST";
    }

    @Override
    public boolean ehMeta() {
        // Eh meta se percorrer todas as colunas e todas as colunas sao iguais
        for (Coluna coluna : colunas) {
            if (!coluna.tudoMesmaCor()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<EstadoPuzzle> sucessores() {
        List<EstadoPuzzle> suc = new LinkedList<>();

        // Definir as possibilidades para o proximo estado

        // Percorre todas as colunas e verifica se pode colocar em cima
        // ou adicionar numa coluna vazia
        for (int i = 0; i < colunas.length; i++) {
            String corTopo = colunas[i].getBolaTopo();

            for (int j = 0; j < colunas.length; j++) {
                // Se a coluna de origem possui bola
                // Se a coluna de origem nao esta pronta
                // Se a coluna de destino nao esta cheia
                // Se a coluna de destino nao possui bola ou pode empilhar
                if (colunas[i].possuiBola()
                    && !colunas[i].tudoMesmaCor()
                    && !colunas[j].cheia()
                    && colunas[j].podeEmpilhar(corTopo)) {

                    // Nao pode ser a mesma coluna
                    if (i != j) {
                        boolean origemIgual = colunas[i].somenteMesmaCor();
                        boolean destinoIgual = colunas[j].somenteMesmaCor();

                        // Nao se a origem esta tudo mesma cor e destino esta vazio
                        if (!(origemIgual && !colunas[j].possuiBola())) {

                            if (!(origemIgual && destinoIgual && colunas[i].tamanho() > colunas[j].tamanho())) {
                                // Executa a copia das colunas
                                Coluna[] colunaSucessor = new Coluna[colunas.length];
                                for (int k = 0; k < colunas.length; k++) {
                                    colunaSucessor[k] = new Coluna(colunas[k].getCopia());
                                }

                                // Executa as alteracoes
                                colunaSucessor[i].removeBola();
                                colunaSucessor[j].adicionaBola(corTopo);

                                //Cria um novo estado
                                EstadoPuzzle sucessor = new EstadoPuzzle(colunaSucessor);

                                //Adiciona sucessor

                                // TA ADICIONANDO MAIS CORES

                                suc.add(sucessor);
                            }
                        }
                    }
                }
            }
        }

        // Cuidar para nao alterar da propria classe

        return suc;
    }

    /**
     * verifica se um estado e igual a outro
     * (usado para poda)
     */
    public boolean equals(Object obj) {
        EstadoPuzzle outro = (EstadoPuzzle) obj;

        for (int i = 0; i < outro.colunas.length; i++) {
            if (!colunas[i].equals(outro.colunas[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * retorna o hashCode desse estado
     * (usado para poda, conjunto de fechados)
     */
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        String[] string = new String[colunas.length];

        for (int i = 0; i < colunas.length; i++) {
            string[i] = colunas[i].toString();
        }

        return String.join("\n", string);
    }



}