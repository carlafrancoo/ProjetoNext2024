package br.edu.cesarschool.next.poo.projetoreferencia.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class DAOGenerico {
	private CadastroObjetos cadastro;
	public DAOGenerico(Class<?> tipo) {
		cadastro = new CadastroObjetos(tipo);
	}
	private String obterIdUnico(Registro registro) {
		return registro.getIdUnico();
	}
	public Registro buscar(String id) {
		return (Registro)cadastro.buscar(id);
	}	
	/*
	 * Objetos são incluídos em arquivos físicos, gravados e
	 * no diretório Registro (o nome da classe de entidade), que fica
	 * no mesmo nível do diretório src, dentro do projeto JAVA.
	 * Cada objeto é incluído em um arquivo diferente, cujo nome 
	 * é o id único do objeto em questão, e tem a extensão dat.   
	 */
	public boolean incluir(Registro registro) {
		String idUnico = obterIdUnico(registro);
		Registro reg = buscar(idUnico);
		if (reg == null) {
			registro.setDhInclusao(LocalDateTime.now());
			cadastro.incluir(registro, idUnico);
			return true;
		} 
		return false; 
	}
	public boolean alterar(Registro registro) {
		String idUnico = obterIdUnico(registro);
		Registro reg = buscar(idUnico);
		if (reg != null) {
			registro.setDhUltimaAtualizacao(LocalDateTime.now());
			cadastro.alterar(registro, idUnico);
			return true;
		} 
		return false;
	}
	public boolean excluir(String id) {
		Registro reg = buscar(id);
		if (reg != null) {
			cadastro.excluir(id);
			return true;
		} 
		return false; 
	}	
	public Registro[] buscarTodos() {
		Serializable[] res = cadastro.buscarTodos();
		if (res == null) {
			return null;
		} else {
			Registro[] registros = new Registro[res.length];
			int i = 0;
			for (Serializable reg : res) {
				registros[i] = (Registro)reg;
				i++;
			}
			return registros;
		}
	}
}
