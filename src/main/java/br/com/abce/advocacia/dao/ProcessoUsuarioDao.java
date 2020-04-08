package br.com.abce.advocacia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.ProcessoUsuario;

public class ProcessoUsuarioDao {

	ConexaoInterface conexao;

	public ProcessoUsuarioDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE PROCESSO_USUARIO (  processo " + conexao.typeINTEGER() + " , usuario "
				+ conexao.typeINTEGER() + ", PRIMARY KEY(processo,usuario) )");
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO_USUARIO ADD dataCadastro " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO_USUARIO ADD dataAtualizacao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO_USUARIO ADD dataExclusao " + conexao.typeDATE());

	}

	public void salvar(ProcessoUsuario item) throws Exception {
		if (get(item.processo, item.usuario) == null) {
			String sql = "INSERT INTO PROCESSO_USUARIO (processo,usuario,dataCadastro,dataAtualizacao) ";
			sql += " VALUES (";
			sql += conexao.str(item.processo) + ",";
			sql += conexao.str(item.usuario) + ",";
			sql += conexao.str(new Date()) + ",";
			sql += conexao.str(new Date()) + ")";
			conexao.executarSQL(sql);
		}
	}

	public List<ProcessoUsuario> get(int processo) throws Exception {
		String sql = "SELECT * FROM PROCESSO_USUARIO WHERE processo = " + conexao.str(processo);

		List<ProcessoUsuario> lista = new ArrayList<>();

		ResultadoInterface res = conexao.consultar(sql);
		while (res.next()) {
			lista.add(parse(res));
		}
		res.close();

		return lista;
	}

	public ProcessoUsuario get(int processo, int usuario) throws Exception {
		String sql = "SELECT * FROM PROCESSO_USUARIO ";
		sql += " WHERE processo = " + conexao.str(processo);
		sql += " AND   usuario  = " + conexao.str(usuario);

		ProcessoUsuario item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public ProcessoUsuario parse(ResultadoInterface res) throws Exception {
		ProcessoUsuario item = new ProcessoUsuario();
		item.processo = res.getInt("processo");
		item.usuario = res.getInt("usuario");
		item.dataCadastro = res.getDate("dataCadastro");
		item.dataAtualizacao = res.getDate("dataAtualizacao");
		item.dataExclusao = res.getDate("dataExclusao");
		return item;
	}
}
