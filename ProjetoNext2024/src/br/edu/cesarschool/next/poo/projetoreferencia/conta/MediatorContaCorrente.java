package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.DAOGenerico;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.StringUtils;


public class MediatorContaCorrente {
	
	private static final String CONTA_NAO_EXISTENTE = "Conta não existente";
	private static final String NUMERO_INVALIDO = "Número inválido";
	private DAOGenerico dao = new DAOGenerico(ContaCorrente.class);
	
	private String validarAgencia(int agencia) {
		if (agencia <= 0 || agencia > 999) {
			return "Agência inválida";
		}
		return null;
	}
	private String validarDadosCreditoDebito(double valor, int agencia, String numero) {
		if (valor < 0) {
			return "Valor inválido";
		}
		String msgAgencia = validarAgencia(agencia);
		if (msgAgencia != null) {
			return msgAgencia;
		}
		if (StringUtils.stringVazia(numero)) {
			return NUMERO_INVALIDO;
		}
		return null;
	}	
	private String validar(ContaCorrente conta) {		
		if (conta == null) {
			return "Conta corrente deve ser informada";
		}
		String msgAgencia = validarAgencia(conta.getAgencia());
		if (msgAgencia != null) {
			return msgAgencia;
		}
		if (StringUtils.stringVazia(conta.getNumero()) ||
				conta.getNumero().length() != 5 || 
				!StringUtils.stringEhNumeroInteiro(conta.getNumero())) {
			return NUMERO_INVALIDO;
		}
		if (conta.getSaldo() < 0) {
			return "Saldo inválido";
		}
		if (StringUtils.stringVazia(conta.getNomeCorrentista()) ||
				conta.getNomeCorrentista().length() > 60) {
			return "Nome do correntista inválido";
		}
		if (conta instanceof ContaPoupanca) {
			ContaPoupanca cp = (ContaPoupanca)conta;
			if (cp.getPercentualBonus() < 0) {
				return "Percentual bônus inválido";
			}
		}
		return null;		
	}
	String incluir(ContaCorrente conta) {
		String msg = validar(conta);
		if (msg == null) {
			boolean ret = dao.incluir(conta);
			if (!ret) {
				return "Conta corrente já existente";
			} else {
				return null;
			}
		} else {
			return msg;
		}
	}
	ContaCorrente buscar(int agencia, String numero) {
		return (ContaCorrente)dao.buscar(ContaCorrente.obterChave(agencia, numero));
	}
	String creditar(double valor, int agencia, String numero) {
		String msg = validarDadosCreditoDebito(valor, agencia, numero);
		if (msg != null) {
			return msg;
		}
		ContaCorrente conta = buscar(agencia, numero);
		if (conta == null) {
			return CONTA_NAO_EXISTENTE;
		}
		conta.creditar(valor);
		if (!dao.alterar(conta)) {
			return CONTA_NAO_EXISTENTE;
		}		
		return null; 
	}
	String debitar(double valor, int agencia, String numero) {
		String msg = validarDadosCreditoDebito(valor, agencia, numero);
		if (msg != null) {
			return msg;
		}
		ContaCorrente conta = buscar(agencia, numero);
		if (conta == null) {
			return CONTA_NAO_EXISTENTE;
		}
		if (conta.getSaldo() < valor) {
			return "Saldo insuficiente";
		}
		conta.debitar(valor);
		if (!dao.alterar(conta)) {
			return CONTA_NAO_EXISTENTE;
		}		
		return null; 
	}
	List<ContaCorrente> gerarRelatorioGeral() {
		List<ContaCorrente> contas = new ArrayList<ContaCorrente>();
		Registro[] regs = dao.buscarTodos();
		for (Registro registro : regs) {
			contas.add((ContaCorrente)registro);
		}
		Collections.sort(contas, new ComparadorContaCorrenteSaldo());
		return contas;
	}
	String alterar(ContaCorrente conta) {
		String msg = validar(conta);
		if (msg == null) {
			boolean ret = dao.alterar(conta);
			if (!ret) {
				return CONTA_NAO_EXISTENTE;
			} else {
				return null;
			}
		} else {
			return msg;
		}
	}
	String excluir(int agencia, String numero) {
		boolean ret = dao.excluir(ContaCorrente.obterChave(agencia, numero));
		if (!ret) {
			return CONTA_NAO_EXISTENTE;
		}
		return null;
	}
	
	String excluirContasSaldoZero() {
		int qtdContasExcluidas = 0;
		Registro[] regsArray = dao.buscarTodos();
		for (Registro registro : regsArray) {
			ContaCorrente conta = (ContaCorrente)registro;
			if (conta.getSaldo() == 0) {
				dao.excluir(ContaCorrente.obterChave(conta));
				qtdContasExcluidas++;
			}
		}
		return "Foram exclu�das " + qtdContasExcluidas;		
	}
}
