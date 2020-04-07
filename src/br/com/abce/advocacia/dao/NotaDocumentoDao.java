package br.com.abce.advocacia.dao;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.NotaDocumento;

public class NotaDocumentoDao {

	ConexaoInterface conexao;

	public NotaDocumentoDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE NOTA_DOCUMENTO (  nota " + conexao.typeINTEGER() + " PRIMARY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  NOTA_DOCUMENTO ADD nome " + conexao.typeSTRING(100));
		conexao.executarSQL_EX("ALTER TABLE  NOTA_DOCUMENTO ADD descricao " + conexao.typeSTRING(100));
		conexao.executarSQL_EX("ALTER TABLE  NOTA_DOCUMENTO ADD arquivo " + conexao.typeBLOB());
		conexao.executarSQL_EX("ALTER TABLE  NOTA_DOCUMENTO ADD formato " + conexao.typeSTRING(50));

	}

	public void salvar(NotaDocumento item) throws Exception {

	}

	public NotaDocumento get(int id) throws Exception {
		String sql = "SELECT * FROM NOTA_DOCUMENTO WHERE NOTA = " + conexao.str(id);

		NotaDocumento item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public NotaDocumento parse(ResultadoInterface res) throws Exception {
		NotaDocumento item = new NotaDocumento();
		item.nota = res.getInt("nota");
		item.nome = res.getString("nome");
		item.descricao = res.getString("descricao");
		item.arquivo = res.getByte("arquivo");
		item.formato = res.getString("formato");
		return item;
	}
}
