package br.com.abce.advocacia.dao;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.NotaMensagem;

public class NotaMensagemDao {

	ConexaoInterface conexao;

	public NotaMensagemDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE NOTA_MENSAGEM (  nota " + conexao.typeINTEGER() + " PRIMAY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  NOTA_MENSAGEM ADD mensagem " + conexao.typeSTRING(500));

	}

	public void salvar(NotaMensagem item) throws Exception {

	}

	public NotaMensagem get(int nota) throws Exception {
		String sql = "SELECT * FROM NOTA_MENSAGEM WHERE NOTA = " + conexao.str(nota);

		NotaMensagem item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public NotaMensagem parse(ResultadoInterface res) throws Exception {
		NotaMensagem item = new NotaMensagem();
		item.nota = res.getInt("nota");
		item.mensagem = res.getString("mensagem");
		return item;
	}
}
