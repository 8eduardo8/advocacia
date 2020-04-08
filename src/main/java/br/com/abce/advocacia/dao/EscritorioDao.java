package br.com.abce.advocacia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.Escritorio;
import br.com.abce.advocacia.model.Usuario;

public class EscritorioDao {

	ConexaoInterface conexao;

	public EscritorioDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE ESCRITORIO (  id " + conexao.typeINTEGER() + " PRIMARY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD dataCadastro      " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD dataAtualizacao   " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD dataExclusao      " + conexao.typeDATE());

		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD cnpj              " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD razao             " + conexao.typeSTRING(100));
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD fantasia          " + conexao.typeSTRING(100));
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD endereco          " + conexao.typeSTRING(100));
		conexao.executarSQL_EX("ALTER TABLE  ESCRITORIO ADD ativo             " + conexao.typeBOOLEAN());

	}

	public void salvar(Escritorio item) throws Exception {

		List<String> sqls = new ArrayList<>();

		if (item.id == 0) {
			String sql = "SELECT MAX(ID) id FROM ESCRITORIO";
			ResultadoInterface res = conexao.consultar(sql);
			if (res.next()) {
				item.id = res.getInt("id");
			}
			res.close();
			item.id++;

			sql = "INSERT INTO ESCRITORIO (ID,dataCadastro) ";
			sql += " VALUES (" + conexao.str(item.id) + "," + conexao.str(new Date()) + ")";
			sqls.add(sql);
		}

		String sql = "UPDATE ESCRITORIO SET ";
		sql += " cnpj             = " + conexao.str(item.cnpj);
		sql += ",razao            = " + conexao.str(item.razao);
		sql += ",fantasia         = " + conexao.str(item.fantasia);
		sql += ",endereco         = " + conexao.str(item.endereco);
		sql += ",ativo            = " + conexao.str(item.ativo);
		sql += ",dataAtualizacao  = " + conexao.str(new Date());
		sql += " WHERE ID = " + conexao.str(item.id);

		sqls.add(sql);
		conexao.executarSQL(sqls);

	}

	public List<Escritorio> get() throws Exception {
		String sql = "SELECT * FROM ESCRITORIO ";

		List<Escritorio> lista = new ArrayList<>();

		ResultadoInterface res = conexao.consultar(sql);
		while (res.next()) {
			lista.add(parse(res));
		}
		res.close();

		return lista;
	}

	public Escritorio get(int id) throws Exception {
		String sql = "SELECT * FROM ESCRITORIO WHERE ID = " + conexao.str(id);

		Escritorio item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Escritorio get(String cnpj) throws Exception {
		String sql = "SELECT * FROM ESCRITORIO WHERE cnpj = " + conexao.str(cnpj);

		Escritorio item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Escritorio parse(ResultadoInterface res) throws Exception {
		Escritorio item = new Escritorio();
		item.id = res.getInt("id");
		item.cnpj = res.getString("cnpj");
		item.razao = res.getString("razao");
		item.fantasia = res.getString("fantasia");
		item.endereco = res.getString("endereco");
		item.ativo = res.getBoolean("ativo");
		item.dataCadastro = res.getDate("dataCadastro");
		item.dataAtualizacao = res.getDate("dataAtualizacao");
		item.dataExclusao = res.getDate("dataExclusao");
		return item;
	}
}
