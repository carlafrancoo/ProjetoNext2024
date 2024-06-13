package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.StringUtils;

public class ContaCorrente extends Registro {

	private int agencia;
	private String numero;
	private double saldo;
	private String nomeCorrentista;

	public ContaCorrente() {
	}

	public ContaCorrente(int agencia, String numero, double saldo, String nomeCorrentista) {
		super();
		this.agencia = agencia;
		this.numero = numero;
		this.saldo = saldo;
		this.nomeCorrentista = nomeCorrentista;
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

	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
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

	public static String obterChave(ContaCorrente conta) {
		return obterChave(conta.getAgencia(), conta.getNumero());	
	}
	
	public double obterAliquotaCpmf() {
		return 0.3;
	}
}
