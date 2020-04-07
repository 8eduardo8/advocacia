package br.com.abce.advocacia.dao;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.NotaAndamento;;

public class NotaAndamentoDao {

	ConexaoInterface conexao;

	public NotaAndamentoDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE NOTA_ANDAMENTO (  nota " + conexao.typeINTEGER() + " PRIMAY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  NOTA_ANDAMENTO ADD descricao " + conexao.typeSTRING(100));

	}

	public void salvar(NotaAndamento item) throws Exception {

	}

	public NotaAndamento get(int nota) throws Exception {
		String sql = "SELECT * FROM NOTA_ANDAMENTO WHERE NOTA = " + conexao.str(nota);

		NotaAndamento item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public NotaAndamento parse(ResultadoInterface res) throws Exception {
		NotaAndamento item = new NotaAndamento();
		item.nota = res.getInt("nota");
		item.descricao = res.getString("descricao");
		return item;
	}
}
