package br.com.abce.advocacia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;
import br.com.abce.advocacia.model.Escritorio;
import br.com.abce.advocacia.model.Processo;

public class ProcessoDao {

	ConexaoInterface conexao;

	public ProcessoDao() throws Exception {
		conexao = ConexaoInterface.getConexao();
	}

	public static void createTable(ConexaoInterface conexao) {
		// CRIA O BANCO DE DADOS
		conexao.executarSQL_EX("CREATE TABLE PROCESSO (  id " + conexao.typeINTEGER() + " PRIMARY KEY)");
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD dataCadastro " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD dataAtualizacao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD dataExclusao " + conexao.typeDATE());
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD numero " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD area " + conexao.typeSTRING(20));
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD situacao " + conexao.typeSTRING(50));
		conexao.executarSQL_EX("ALTER TABLE  PROCESSO ADD dataInicio " + conexao.typeDATE());
	}

	public void salvar(Processo item) throws Exception {
		List<String> sqls = new ArrayList<>();

		if (item.id == 0) {
			String sql = "SELECT MAX(ID) id FROM PROCESSO";
			ResultadoInterface res = conexao.consultar(sql);
			if (res.next()) {
				item.id = res.getInt("id");
			}
			item.id++;

			sql = "INSERT INTO PROCESSO (ID,dataCadastro) ";
			sql += " VALUES (" + conexao.str(item.id) + "," + conexao.str(new Date()) + ")";
			sqls.add(sql);
		}

		String sql = "UPDATE PROCESSO SET ";
		sql += " numero             = " + conexao.str(item.numero);
		sql += ",area               = " + conexao.str(item.area);
		sql += ",situacao           = " + conexao.str(item.situacao);
		sql += ",dataInicio         = " + conexao.str(item.dataInicio);
		sql += ",dataAtualizacao    = " + conexao.str(new Date());
		sql += " WHERE ID = " + conexao.str(item.id);

		sqls.add(sql);
		conexao.executarSQL(sqls);
	}

	public List<Processo> get() throws Exception {
		String sql = "SELECT * FROM PROCESSO ";

		List<Processo> lista = new ArrayList<>();

		ResultadoInterface res = conexao.consultar(sql);
		while (res.next()) {
			lista.add(parse(res));
		}
		res.close();

		return lista;
	}

	public Processo get(int id) throws Exception {
		String sql = "SELECT * FROM PROCESSO WHERE ID = " + conexao.str(id);

		Processo item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Processo get(String numero) throws Exception {
		String sql = "SELECT * FROM PROCESSO WHERE numero = " + conexao.str(numero);

		Processo item = null;

		ResultadoInterface res = conexao.consultar(sql);
		if (res.next()) {
			item = parse(res);
		}
		res.close();

		return item;
	}

	public Processo parse(ResultadoInterface res) throws Exception {
		Processo item = new Processo();
		item.id = res.getInt("id");
		item.numero = res.getString("numero");
		item.area = res.getString("area");
		item.situacao = res.getString("situacao");
		item.dataInicio = res.getDate("dataInicio");
		item.dataCadastro = res.getDate("dataCadastro");
		item.dataAtualizacao = res.getDate("dataAtualizacao");
		item.dataExclusao = res.getDate("dataExclusao");
		return item;
	}
}
