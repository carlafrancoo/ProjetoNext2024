package br.edu.cesarschool.next.poo.projetoreferencia.produto;

import java.util.List;
import java.util.Scanner;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.DateUtils;

public class TelaProduto {
	private static final String SIM = "S";
	private Scanner entrada = new Scanner(System.in);
	private ProdutoMediator mediator = new ProdutoMediator();
	public void iniciar() {
		while(true) {
			System.out.println("1- Incluir");
			System.out.println("2- Atualizar estoque");
			System.out.println("3- Consultar preço total");
			System.out.println("4- Consultar produto");
			System.out.println("5- Relatório geral");
			System.out.println("6- Sair");
			System.out.println("Selecione a operação");
			int operacao = entrada.nextInt();
			if (operacao == 1) {
				rodarIncluir();
			} else if (operacao == 2) {
				rodarAtualizarEstoque();
			} else if (operacao == 3) {
				rodarConsultarPrecoTotal();
			} else if (operacao == 4) {
				rodarConsultarProduto();
			} else if (operacao == 5) {
				rodarRelatorioGeral();
			} else if (operacao == 6) {
				rodarSair();
			} else {
				System.out.println("Operação inválida");
			}
		} 
	}
	private void rodarIncluir() {
		Produto prod = null;
		System.out.println("Digite o código: ");
		long codigo = entrada.nextLong();
		System.out.println("Digite o nome: ");
		String nome = entrada.next();
		System.out.println("Digite o preço base: ");
		double precoBase = entrada.nextDouble();
		System.out.println("É produto cesta básica (S/N)?");
		String eCesta = entrada.next();		
		if (eCesta.equals(SIM)) {
			System.out.println("O produto é isento de imposto (S/N)?");
			String isentoImposto = entrada.next();
			System.out.println("Digite o desconto na alíquota:");
			double descontoAliquota = entrada.nextDouble();
			prod = new ProdutoCestaBasica(codigo, nome, precoBase, isentoImposto.equals(SIM), descontoAliquota);
		} else {
			prod = new Produto(codigo, nome, precoBase);
		}
		String mensagem = mediator.incluirProduto(prod);
		if (mensagem == null) {
			System.out.println("Produto incluído com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	private void rodarAtualizarEstoque() {
		System.out.println("Digite o código: ");
		long codigo = entrada.nextLong();
		System.out.println("Digite a quantidade: ");
		int quantidade = entrada.nextInt();
		System.out.println("É adição (S/N)?");
		String adicao = entrada.next();
		String mensagem = mediator.atualizarEstoque(codigo, quantidade, adicao.equals(SIM));
		if (mensagem == null) {
			System.out.println("Estoque atualizado com sucesso!");
		} else {
			System.out.println(mensagem);
		}		
	}
	private void rodarConsultarPrecoTotal() {
		System.out.println("Digite o código: ");
		long codigo = entrada.nextLong();
		System.out.println("Digite a alíquota de imposto: ");
		double aliquota = entrada.nextDouble();
		RetornoPrecoTotal ret = mediator.obterPrecoTotal(codigo, aliquota);
		if (ret.getMensagem() == null) {
			System.out.println("O preço total do produto " + codigo + " é " 
					+ ret.getPrecoTotal());
		} else {
			System.out.println(ret.getMensagem());
		}		
	}
	private void rodarConsultarProduto() {
		System.out.println("Digite o código: ");
		long codigo = entrada.nextLong();
		Produto prod = mediator.buscar(codigo);
		if (prod == null) {
			System.out.println("Produto não encontrado");
		} else {
			imprimirAtributosProduto(prod);
		}
	}
	private void rodarRelatorioGeral() {
		List<Produto> produtos = mediator.gerarRelatorio();
		System.out.println("######## INÍCIO RELATÓRIO ##########");
		for (Produto produto : produtos) {
			System.out.println("##### PRODUTO #####");
			imprimirAtributosProduto(produto);
		}
		System.out.println("######## FIM RELATÓRIO ##########");
	}
	private void imprimirAtributosProduto(Produto prod) {
		System.out.println("Código           : " + prod.getCodigo());
		System.out.println("Nome             : " + prod.getNome());
		System.out.println("Preço base       : " + prod.getPrecoBase());
		System.out.println("Estoque          : " + prod.getEstoque());
		if (prod instanceof ProdutoCestaBasica) {
			ProdutoCestaBasica prodCesta = (ProdutoCestaBasica)prod;
			System.out.println("Isento imposto   : " + prodCesta.isIsentoImposto());
			System.out.println("Desconto imposto : " + prodCesta.getDescontoImposto());
		}
		System.out.println("Incluído em      : " + 
				DateUtils.formatar(prod.getDhInclusao()));					
		System.out.println("Alterado em      : " + 
				DateUtils.formatar(prod.getDhUltimaAtualizacao()));
	}
	private void rodarSair() {
		System.out.println("Bye bye!");
		System.exit(0);
	}
}
