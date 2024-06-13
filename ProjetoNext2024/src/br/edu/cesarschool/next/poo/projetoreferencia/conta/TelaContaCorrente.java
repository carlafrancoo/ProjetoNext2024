package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import java.util.List;
import java.util.Scanner;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.DateUtils;

public class TelaContaCorrente {
	
	private static final String SIM = "S";
	private Scanner entrada = new Scanner(System.in);
	private MediatorContaCorrente mediator = new MediatorContaCorrente();
	public void iniciarTela() {
		while(true) {
			System.out.println("1- Incluir");
			System.out.println("2- Creditar");
			System.out.println("3- Debitar");
			System.out.println("4- Consultar conta");
			System.out.println("5- Relatório geral");
			System.out.println("6- Alterar");
			System.out.println("7- Excluir");
			System.out.println("8- Excluir contas com saldo zero");
			System.out.println("9- Sair");
			System.out.println("Selecione a operação");
			int operacao = entrada.nextInt();
			if (operacao == 1) {
				incluir();
			} else if (operacao == 2) {
				creditar();
			} else if (operacao == 3) {
				debitar();
			} else if (operacao == 4) {
				buscar();
			} else if (operacao == 5) {
				gerarRelatorioGeral();
			} else if (operacao == 6) {
				alterar();
			} else if (operacao == 7) {
				excluir();
			} else if (operacao == 8) {
				excluirContasSaldoZero();
			} else if (operacao == 9) {
				rodarSair();
			} else {
				System.out.println("Operação inválida");
			}
		} 
	}
	private ContaCorrente obterDadosContaDoConsole() {
		ContaCorrente conta = null;
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o número: ");
		String numero = entrada.next();
		System.out.println("Digite o saldo: ");
		double saldo = entrada.nextDouble();
		System.out.println("Digite o nome do correntista: ");
		String nomeCorrentista = entrada.next();
		System.out.println("É conta poupança (S/N)?");
		String eCp = entrada.next();		
		if (eCp.equals(SIM)) {
			System.out.println("Digite o percentual de bônus");
			double percentualBonus = entrada.nextDouble();
			conta = new ContaPoupanca(agencia, numero, saldo, 
					nomeCorrentista, percentualBonus);				
		} else {
			conta = new ContaCorrente(agencia, numero, saldo, 
					nomeCorrentista);
		}
		return conta;
	}
	private void incluir() {
		ContaCorrente conta = obterDadosContaDoConsole();
		String mensagem = mediator.incluir(conta);
		if (mensagem == null) {
			System.out.println("Conta incluída com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	private void creditar() {
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o número: ");
		String numero = entrada.next();
		System.out.println("Digite o valor: ");
		double valor = entrada.nextDouble();
		String mensagem = mediator.creditar(valor, agencia, numero);
		if (mensagem == null) {
			System.out.println("Crédito realizado com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	private void debitar() {
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o número: ");
		String numero = entrada.next();
		System.out.println("Digite o valor: ");
		double valor = entrada.nextDouble();
		String mensagem = mediator.debitar(valor, agencia, numero);
		if (mensagem == null) {
			System.out.println("Débito realizado com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	private void buscar() {
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o número: ");
		String numero = entrada.next();
		ContaCorrente conta = mediator.buscar(agencia, numero);
		if (conta == null) {
			System.out.println("Conta não encontrada!");
		} else {
			imprimirAtributosConta(conta); 
		}
	}
	private void gerarRelatorioGeral() {
		List<ContaCorrente> contas = mediator.gerarRelatorioGeral();
		System.out.println("######## INÍCIO RELATÓRIO ##########");
		for (ContaCorrente conta : contas) {
			System.out.println("##### CONTA #####");
			imprimirAtributosConta(conta); 
		}
		System.out.println("######## FIM RELATÓRIO ##########");
	}
	private void imprimirAtributosConta(ContaCorrente conta) {
		System.out.println("Agência          : " + conta.getAgencia());
		System.out.println("Número           : " + conta.getNumero());
		System.out.println("Saldo            : " + conta.getSaldo());
		System.out.println("Nome correntista : " + conta.getNomeCorrentista());
		if (conta instanceof ContaPoupanca) {
			ContaPoupanca cp = (ContaPoupanca)conta;
			System.out.println("Percenutal bônus : " + cp.getPercentualBonus());
		}		
		System.out.println("Incluído em      : " + 
				DateUtils.formatar(conta.getDhInclusao()));					
		System.out.println("Alterado em      : " + 
				DateUtils.formatar(conta.getDhUltimaAtualizacao()));
	}
	private void alterar() {
		ContaCorrente conta = obterDadosContaDoConsole();
		String mensagem = mediator.alterar(conta);
		if (mensagem == null) {
			System.out.println("Conta alterada com sucesso!");
		} else {
			System.out.println(mensagem);
		}		
	}
	private void excluir() {
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o número: ");
		String numero = entrada.next();
		String mensagem = mediator.excluir(agencia, numero);
		if (mensagem == null) {
			System.out.println("Conta excluída com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	private void excluirContasSaldoZero() {
		String mensagem = mediator.excluirContasSaldoZero();
		System.out.println(mensagem);
	}
	private void rodarSair() {
		System.out.println("Até logo!");
		System.exit(0);
	}
}
