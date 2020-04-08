package br.com.abce.advocacia.dao;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.Nota;

public class NotaDao {

	ConexaoInterface conexao;

	public NotaDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE NOTA (  id " + conexao.typeINTEGER() + " PRIMARY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  NOTA ADD dataCadastro " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  NOTA ADD dataAtualizacao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  NOTA ADD dataExclusao " + conexao.typeDATE());

		conexao.executarSQL_EX("ALTER TABLE  NOTA ADD tipo " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  NOTA ADD usuario " + conexao.typeINTEGER());

	}

	public void salvar(Nota item) throws Exception {

	}

	public Nota get(int id) throws Exception {
		String sql = "SELECT * FROM NOTA WHERE ID = " + conexao.str(id);

		Nota item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Nota parse(ResultadoInterface res) throws Exception {
		Nota item = new Nota();
		item.id = res.getInt("id");
		item.usuario = res.getInt("usuario");
		item.tipo = res.getString("tipo");
		item.dataCadastro = res.getDate("dataCadastro");
		item.dataAtualizacao = res.getDate("dataAtualizacao");
		item.dataExclusao = res.getDate("dataExclusao");
		return item;
	}
}
