package resolucao;

import busca.Estado;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;

/**
 * NAO USAR ACENTUACAO
 */
public class EstadoPuzzle implements Estado {

    private static class Coluna {

        private int cores[];

        public int[] getCores() {
            return cores;
        }

        //Coluna() {}

        Coluna(int[] cores) {
            this.cores = cores;
        }

        public boolean igual(int[] cores) {
            for (int i = 0; i < cores.length; i++) {
                if (this.cores[i] != cores[i]) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public String toString() {
            return cores.toString();
        }

        @Override
        public boolean equals(Object obj) {
            Coluna outro = (Coluna)obj;

            return igual(outro.getCores());
        }

        /*public boolean temDisco(int disco) {
            return (p1 == disco) ||
                       (p2 == disco && p1 == 0) ||
                       (p3 == disco && p2 == 0);
        }

        public int podeEmpilhar(int disco) {
            if (p3 == 0)
                return 3;
            else
                if (p2 == 0) {
                    if (p3 > disco)
                        return 2;
                    else
                        return 0;
                }

                else
                    if (p2 > disco)
                        return 1;

            return 0;
        }

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
        }*/

        public Coluna copiar() {
            return new Coluna(cores);
        }

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
        // Verificar se para cada tubo as cores sao iguais
        // Feito antes do sucessores

        // Eh meta se percorrer todas as colunas e todas as colunas sao iguais
        for (int i = 0; i < colunas.length; i++) {
            //Verificar se na coluna tem cores
            if (colunas[i].getCores().length != 0) {
                //Percorrer as cores para ver se eh tudo igual
                //Percorre da segunda ate a ultima verificando se eh igual a primeira
                for (int x = 1; x < colunas[i].getCores().length; x++) {
                    // Verifica se a linha cor atual eh igual a primeira
                    if (colunas[i].getCores()[x] != colunas[i].getCores()[0]) {
                        return false;
                    }
                }
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

        // Cuidar para nao alterar da propria classe

        return suc;
    }

    /**
     * verifica se um estado e igual a outro
     * (usado para poda)
     */
    public boolean equals(Object o) {
        return false;
    }

    /**
     * retorna o hashCode desse estado
     * (usado para poda, conjunto de fechados)
     */
    public int hashCode() {
        // Implementar
        return 0;
    }

}