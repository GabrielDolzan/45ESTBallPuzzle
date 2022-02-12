package exemplos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import busca.Aleatorio;
import busca.Estado;
import busca.Nodo;
import busca.SubidaMontanha;

/**
 * Problema do quadrado magico (ver getDescricao)
 *
 * Representa um estado do mundo: o nro que esta em cada
 * posicao do quadro.
 *
 *
 * Nesta solucao o estado inicial e o quadro com os numeros
 * distribuidos aleatoriamente e os sucessores sao gerados
 * por trocas de posicao entre numeros.
 */
public class QuadradoMagicoB extends QuadradoMagico implements Aleatorio {

    public String getDescricao() {
        return
        "Um quadrado magico de ordem n e um arranjo quadrado de n inteiros\n"+
        "distintos dispostos de tal maneira que os numeros de uma linha\n"+
        "qualquer, de uma coluna qualquer ou da diagonal principal tem mesma\n"+
        "soma, chamada constante magica do quadrado. O quadrado e normal se os\n"+
        "n numeros que o formam sao os primeiros n inteiros positivos.\n\n"+
        "A constante magica do quadrado e dada por: n (n + 1) / 2\n"+
        "Neste exemplo, n = 4 e a constante magica=34\n\n"+
        "Nesta versao (b), o tabuleiro inicia com os numeros\n"+
        "aleatoriamente posicionados e cada operacao\n"+
        "troca dois numeros de lugar\n"+
        "(tem heuristica e geracao de estados aleatorios implementado)\n";
    }

    /**
     *  cria um estado inicial (aleatorio)
     */
    public QuadradoMagicoB() {
        for (int i=1; i<=tam*tam; i++) {
            // tenta ate achar um posicao livre
            int l = Math.round( (float)(Math.random()*(tam-1)) );
            int c = Math.round( (float)(Math.random()*(tam-1)) );
            while (tabuleiro[l][c] != 0) {
                l = Math.round( (float)(Math.random()*(tam-1)) );
                c = Math.round( (float)(Math.random()*(tam-1)) );
            }
            tabuleiro[l][c] = i;
        }
        meuNro = tam*tam;
    }

    /**
     *  cria um estado inicial a partir de outro (copia)
     */
    QuadradoMagicoB(QuadradoMagico modelo) {
        super(modelo);
    }

    public Estado geraAleatorio() {
        return new QuadradoMagicoB();
    }

    /**
     * gera uma lista de sucessores do nodo.
     * (troca dois numeros de posicao no tabuleiro)
     */
    public List<Estado> sucessores() {
        List<Estado> suc = new ArrayList<Estado>(); // a lista de sucessores

        // coloca o seguinte em todas as posicaes livres
        for (int l=0;l<tam-1;l++) {
            for (int c=0;c<tam-1;c++) {
                // troca com o proximo na linha
                QuadradoMagicoB novo = new QuadradoMagicoB(this);
                int temp = novo.tabuleiro[l][c];
                novo.tabuleiro[l][c] = novo.tabuleiro[l+1][c];
                novo.tabuleiro[l+1][c] = temp;
                suc.add(novo);

                // troca com o debaixo
                novo = new QuadradoMagicoB(this);
                temp = novo.tabuleiro[l][c];
                novo.tabuleiro[l][c] = novo.tabuleiro[l][c+1];
                novo.tabuleiro[l][c+1] = temp;
                suc.add(novo);
            }
        }

        return suc;
    }

    public static void main(String[] a) {
        QuadradoMagicoB.tam = 4;

        //Estado inicial  = new EstadoSoma34a();
        Estado inicial  = new QuadradoMagicoB();
        Nodo n;

        System.out.println("Comecou em "+new Date());

        System.out.println("Estado inicial "+inicial);

        //n = Busca.buscaLargura(inicial, null); // (da "out of memory")
        //n = Busca.buscaProfRec(inicial, null, 18); // (demora muito)

        // chama busca em profundidade iterativo
        // (como se sabe que a solucao esta em 16 niveis, nao
        // faz sentido busca em prof. iterativo)
        //n = Busca.buscaProfIterativo(inicial, null);

        //n = Busca.buscaHeuristica(inicial, null);
        /*
        if (n == null) {
            System.out.println("sem solucao!");
        } else {
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
         */

        n = new SubidaMontanha().busca(inicial);
        System.out.println("solucao:\n" + n.getEstado() + "\n\n");
        System.out.println("Terminou em "+new Date());
    }
}
