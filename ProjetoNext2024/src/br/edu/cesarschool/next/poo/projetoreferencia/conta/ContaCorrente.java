package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.StringUtils;

public class ContaCorrente extends Registro {

	private int agencia;
	private String numero;
	private double saldo;
	private String nomeDoCorrentista;

	public ContaCorrente() {
	}

	public ContaCorrente(int agencia, String numero, double saldo, String nomeDoCorrentista) {
		super();
		this.agencia = agencia;
		this.numero = numero;
		this.saldo = saldo;
		this.nomeDoCorrentista = nomeDoCorrentista;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNomeDoCorrentista() {
		return nomeDoCorrentista;
	}

	public void setNomeDoCorrentista(String nomeDoCorrentista) {
		this.nomeDoCorrentista = nomeDoCorrentista;
	}

	public double getSaldo() {
		return saldo;
	}

	void creditar(double valor) {
		saldo = saldo + valor;
	}

	void debitar(double valor) {
		saldo = saldo - valor;
	}

	public String getIdUnico() {
		return obterChave(agencia, numero);
	}

	public static String obterChave(int agencia, String numero) {
		return StringUtils.formatar(agencia, 3) + numero;
	}

	public double obterAliquotaCpmf() {
		return 0.3;
	}
}
