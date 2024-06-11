package br.edu.cesarschool.next.poo.projetoreferencia.produto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.DAOGenerico;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.StringUtils;

public class ProdutoMediator {
	private static final String CODIGO_DO_PRODUTO_INEXISTENTE = "Código do produto inexistente";
	private DAOGenerico dao = new DAOGenerico(Produto.class);
	
	// Dois métodos privados auxiliares - o código fica mais reusável e organizado.
	private String validar(Produto produto) {
		if (produto == null) {
			return "Produto não informado";
		}		
		if (produto.getCodigo() <= 0) {
			return "Código inválido";
		}		
		if (StringUtils.stringVazia(produto.getNome())) {
			return "Nome não informado";
		}
		if (produto.getPrecoBase() <= 0) {
			return "Preço base menor ou igual a zero";
		}
		if (produto.getEstoque() < 0) {
			return "Estoque negativo";
		}
		if (produto instanceof ProdutoCestaBasica) {
			ProdutoCestaBasica produtoCesta = (ProdutoCestaBasica)produto;
			return validarProdutoCesta(produtoCesta);
			
		}
		return null;
	}
	private String validarProdutoCesta(ProdutoCestaBasica produtoCesta) {
		if (produtoCesta.isIsentoImposto()) { 
			if (produtoCesta.getDescontoImposto() != 0) {
				return "Produto isento mas com desconto de imposto diferente de zero";
			} 
		} else {
			if (produtoCesta.getDescontoImposto() < 0) {
				return "Desconto de imposto menor que zero";
			} 
		}
		return null;
	}
	// As três operações da tela de produto
	public String incluirProduto(Produto produto) {
		String mensagem = validar(produto);
		if (mensagem != null) {
			return mensagem;
		}
		if (!dao.incluir(produto)) {
			return "Código do produto já existente";
		}
		return null;
	}
	public String atualizarEstoque(long codigo, int quantidade, boolean adicionar) {
		if (quantidade < 0) {
			return "Quantidade de estoque menor que zero";
		}
		Produto prod = buscar(codigo);
		if (prod == null) {
			return CODIGO_DO_PRODUTO_INEXISTENTE;
		}
		if (adicionar) {
			prod.adicionarEstoque(quantidade);
		} else {
			if (prod.getEstoque() < quantidade) {
				return "Quantidade a ser subtraída é menor do que o estoque atual";
			} else {
				prod.subtrairEstoque(quantidade);
			}
		}
		dao.alterar(prod);
		return null;
	}
	public RetornoPrecoTotal obterPrecoTotal(long codigo, double aliquotaImposto) {
		if (aliquotaImposto < 0 || aliquotaImposto > 100.0) {
			return new RetornoPrecoTotal(0.0, "Alíquota inválida");
		}
		Produto prod = buscar(codigo);
		if (prod == null) {
			return new RetornoPrecoTotal(0.0, CODIGO_DO_PRODUTO_INEXISTENTE);
		} else {
			// A sobrescrita garante que este método seja chamado em Produto 
			// ou em ProdutoCestaBasica, a depender do tipo de objeto que
			// prod referencia, a dependere de produto buscado 
			double precoTotal = prod.calcularPrecoFinal(aliquotaImposto);
			return new RetornoPrecoTotal(precoTotal, null);
		}
	}
	public Produto buscar(long codigo) {
		return (Produto)dao.buscar("" + codigo);		
	}
	// Daqui para frente não há operação na tela de produto 
	public String alterarProduto(Produto produto) {
		String mensagem = validar(produto);
		if (mensagem != null) {
			return mensagem;
		}
		if (!dao.alterar(produto)) {
			return CODIGO_DO_PRODUTO_INEXISTENTE;
		}
		return null;
	}
	public String excluir(long codigo) {		
		if (!dao.excluir("" + codigo)) {
			return CODIGO_DO_PRODUTO_INEXISTENTE;
		}
		return null;
	}
	public List<Produto> gerarRelatorio() {
		List<Produto> produtos = new ArrayList<Produto>();
		Registro[] arrayRegs = dao.buscarTodos();
		if (arrayRegs != null) {
			for (Registro registro : arrayRegs) {
				produtos.add((Produto)registro);
			}
			Collections.sort(produtos, new ComparadorProdutoNome());
		}
		return produtos;
	}
}
