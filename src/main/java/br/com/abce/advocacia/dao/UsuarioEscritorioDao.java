package br.com.abce.advocacia.dao;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.Usuario;

public class UsuarioEscritorioDao {

	ConexaoInterface conexao;

	public UsuarioEscritorioDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE USUARIO_ESCRITORIO (  usuario " + conexao.typeINTEGER() + " , escritorio "
				+ conexao.typeINTEGER() + " , PRIMARY KEY(usuario,escritorio) )");

		conexao.executarSQL_EX("ALTER TABLE  USUARIO_ESCRITORIO ADD dataCadastro " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  USUARIO_ESCRITORIO ADD dataAtualizacao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  USUARIO_ESCRITORIO ADD dataExclusao " + conexao.typeDATE());
	}

	public void salvar(Usuario item) throws Exception {

	}

	public Usuario get(int id) throws Exception {
		String sql = "SELECT * FROM USUARIO WHERE ID = " + conexao.str(id);

		Usuario item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Usuario get(String login) throws Exception {
		String sql = "SELECT * FROM USUARIO WHERE LOGIN = " + conexao.str(login);

		Usuario item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Usuario parse(ResultadoInterface res) throws Exception {
		Usuario item = new Usuario();
		item.id = res.getInt("id");
		item.login = res.getString("login");
		item.perfil = res.getString("perfil");
		item.nome = res.getString("nome");
		item.email = res.getString("email");
		item.senha = res.getString("senha");
		item.recuperarSenha = res.getBoolean("recuperarSenha");
		item.senhaTemporaria = res.getString("senhaTemporaria");
		item.ativo = res.getBoolean("ativo");
		item.dataCadastro = res.getDate("dataCadastro");
		item.dataAtualizacao = res.getDate("dataAtualizacao");
		item.dataExclusao = res.getDate("dataExclusao");
		return item;
	}
}
