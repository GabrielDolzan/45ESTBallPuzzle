package exemplos;

import java.util.LinkedList;
import java.util.List;

import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.Nodo;

public class NossaTorreHanoi implements Estado{

	// 1) qual a estrutura do estado?

	private static class Pino {

		private int p1;
		private int p2;
		private int p3;

		Pino() {}

		Pino(int p1, int p2, int p3) {
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}

		public boolean igual(int p1, int p2, int p3) {
			return this.p1 == p1 & this.p2 == p2 && this.p3 == p3;
		}

		@Override
		public String toString() {
			return p1 + " " + p2 + " " + p3;
		}

		@Override
		public boolean equals(Object obj) {
			Pino outro = (Pino)obj;
			return p1 == outro.p1 &&
					p2 == outro.p2 &&
					p3 == outro.p3;
		}

		public boolean temDisco(int disco) {

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

		public Pino copiarSemDisco(int disco) {

			Pino copia = new Pino(p1, p2, p3);

			if (copia.p1 == disco)
				copia.p1 = 0;
			else
				if (copia.p2 == disco)
					copia.p2 = 0;
				else
					copia.p3 = 0;

			return copia;
		}

		public Pino copiarComDisco(int disco, int posPino) {
			Pino copia = new Pino(p1, p2, p3);

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

		public Pino copiar() {
			return new Pino(p1, p2, p3);
		}

	}

	private Pino pino1;
	private Pino pino2;
	private Pino pino3;

	public NossaTorreHanoi(Pino pino1, Pino pino2, Pino pino3) {
		this.pino1 = pino1;
		this.pino2 = pino2;
		this.pino3 = pino3;
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ehMeta() {
		// 4) definir o estado objetivo

		return this.pino3.igual(1, 2, 3);
	}

	@Override
	public int custo() {
		return 1;
	}

	@Override
	public List<NossaTorreHanoi> sucessores() {

		List<NossaTorreHanoi> suc = new LinkedList<>();

		for (int disco = 1; disco <= 3; disco++) {

			if (pino1.temDisco(disco)) { // o disco estah disponivel para sair do pino1?

				// o disco do pino1 pode ser empilhado no pino2?
				int posPino = pino2.podeEmpilhar(disco);
				if (posPino > 0) {
					NossaTorreHanoi novo = new NossaTorreHanoi(
							pino1.copiarSemDisco(disco),
							pino2.copiarComDisco(disco, posPino),
							pino3.copiar());
					suc.add(novo);
				}

				posPino = pino3.podeEmpilhar(disco);
				if (posPino > 0) {
					NossaTorreHanoi novo = new NossaTorreHanoi(
							pino1.copiarSemDisco(disco),
							pino2.copiar(),
							pino3.copiarComDisco(disco, posPino));
					suc.add(novo);
				}
			} else {
				if (pino2.temDisco(disco)) { // o disco estah disponivel para sair do pino2?

					int posPino = pino1.podeEmpilhar(disco);
					if (posPino > 0) {
						NossaTorreHanoi novo = new NossaTorreHanoi(
								pino1.copiarComDisco(disco, posPino),
								pino2.copiarSemDisco(disco),
								pino3.copiar());
						suc.add(novo);
					}

					posPino = pino3.podeEmpilhar(disco);
					if (posPino > 0) {
						NossaTorreHanoi novo = new NossaTorreHanoi(
								pino1.copiar(),
								pino2.copiarSemDisco(disco),
								pino3.copiarComDisco(disco, posPino));
						suc.add(novo);
					}
				} else {
					if (pino3.temDisco(disco)) {

						int posPino = pino1.podeEmpilhar(disco);
						if (posPino > 0) {
							NossaTorreHanoi novo = new NossaTorreHanoi(
									pino1.copiarComDisco(disco, posPino),
									pino2.copiar(),
									pino3.copiarSemDisco(disco));
							suc.add(novo);
						}

						posPino = pino2.podeEmpilhar(disco);
						if (posPino > 0) {
							NossaTorreHanoi novo = new NossaTorreHanoi(
									pino1.copiar(),
									pino2.copiarComDisco(disco, posPino),
									pino3.copiarSemDisco(disco));
							suc.add(novo);
						}
					}

				}

			}

		}

		return suc;
	}

	@Override
	public String toString() {
		return "\n"+ pino1.toString() + "\n" + pino2.toString() + "\n" + pino3.toString();
	}

	@Override
	public boolean equals(Object obj) {
		NossaTorreHanoi outro = (NossaTorreHanoi) obj;
		return this.pino1.equals(outro.pino1) &&
				this.pino2.equals(outro.pino2) &&
				this.pino3.equals(outro.pino3);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public static void main(String[] args) {

		// 2) definir o estado inicial
        NossaTorreHanoi inicial = new NossaTorreHanoi(new Pino(1, 2, 3), new Pino(), new Pino());



        // chama busca em largura
        System.out.println("busca em largura");
        BuscaLargura<NossaTorreHanoi> bLarg = new BuscaLargura<NossaTorreHanoi>();
		Nodo n = bLarg.busca(inicial);

        if (n == null) {
            System.out.println("sem solucao!");
        } else {
        	System.out.println(n.getProfundidade());
        	Nodo w = n;
        	while (w != null) {
                        NossaTorreHanoi th = (NossaTorreHanoi)w.getEstado();
                        System.out.println(th.pino1);
        		w = w.getPai();
        	}
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
        /*
        // chama busca em profundidade
        System.out.println("busca em profundidade");
        n = new BuscaProfundidade<NossaTorreHanoi>().busca(inicial);
        if (n == null) {
            System.out.println("sem solucao!");
        } else {
        	System.out.println(n.getProfundidade());
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
        */

	}

}