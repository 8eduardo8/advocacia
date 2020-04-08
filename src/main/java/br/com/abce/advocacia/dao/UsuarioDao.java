package br.com.abce.advocacia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.Usuario;

public class UsuarioDao {

	ConexaoInterface conexao;

	public UsuarioDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE USUARIO (  id " + conexao.typeINTEGER() + " PRIMARY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD dataCadastro " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD dataAtualizacao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD dataExclusao " + conexao.typeDATE());

		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD login " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD cpf " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD perfil " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD nome " + conexao.typeSTRING(50));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD sobreNome " + conexao.typeSTRING(50));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD sexo " + conexao.typeSTRING(50));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD email " + conexao.typeSTRING(50));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD senha " + conexao.typeSTRING(200));
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD recuperarSenha " + conexao.typeBOOLEAN());
		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD senhaTemporaria " + conexao.typeSTRING(200));

		conexao.executarSQL_EX("ALTER TABLE  USUARIO ADD ativo " + conexao.typeBOOLEAN());

	}

	public void salvar(Usuario item) throws Exception {

		List<String> sqls = new ArrayList<>();

		if (item.id == 0) {
			if (get(item.login) != null) {
				throw new Exception("Esse Login já foi cadastrado!");
			}

			String sql = "SELECT MAX(ID) id FROM USUARIO";
			ResultadoInterface res = conexao.consultar(sql);
			if (res.next()) {
				item.id = res.getInt("id");
			}res.close();
			item.id++;

			sql = "INSERT INTO USUARIO (ID,dataCadastro) ";
			sql += " VALUES (" + conexao.str(item.id) + "," + conexao.str(new Date()) + ")";
			sqls.add(sql);
		}

		String sql = "UPDATE USUARIO SET ";
		sql += " login            = " + conexao.str(item.login);
		sql += ",cpf              = " + conexao.str(item.cpf);
		sql += ",perfil           = " + conexao.str(item.perfil);
		sql += ",nome             = " + conexao.str(item.nome);
		sql += ",sobreNome        = " + conexao.str(item.sobrenome);
		sql += ",sexo             = " + conexao.str(item.sexo);
		sql += ",email            = " + conexao.str(item.email);
		sql += ",senha            = " + conexao.str(item.senha);
		sql += ",ativo            = " + conexao.str(item.ativo);
		sql += ",recuperarSenha   = " + conexao.str(item.recuperarSenha);
		sql += ",senhaTemporaria  = " + conexao.str(item.senhaTemporaria);
		sql += ",dataAtualizacao  = " + conexao.str(new Date());
		sql += " WHERE ID = " + conexao.str(item.id);

		sqls.add(sql);
		conexao.executarSQL(sqls);

	}

	public List<Usuario> get() throws Exception {
		String sql = "SELECT * FROM USUARIO ";

		List<Usuario> lista = new ArrayList<>();

		ResultadoInterface res = conexao.consultar(sql);
		while (res.next()) {
			lista.add(parse(res));
		}
		res.close();

		return lista;
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
		item.cpf = res.getString("cpf");
		item.perfil = res.getString("perfil");
		item.nome = res.getString("nome");
		item.sobrenome = res.getString("sobreNome");
		item.sexo = res.getString("sexo");
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
