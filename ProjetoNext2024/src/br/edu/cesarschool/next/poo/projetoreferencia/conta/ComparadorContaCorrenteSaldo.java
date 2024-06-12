package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import java.util.Comparator;

public class ComparadorContaCorrenteSaldo implements Comparator<ContaCorrente> {
	
	@Override
	public int compare(ContaCorrente o1, ContaCorrente o2) {
		double saldo1 = o1.getSaldo();
		double saldo2 = o2.getSaldo();
		if (saldo1 > saldo2) {
			return 1;
		} else if (saldo1 < saldo2) {
			return -1;
		} 
		return 0;
	}

}
