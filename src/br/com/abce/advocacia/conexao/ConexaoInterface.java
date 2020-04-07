package br.com.abce.advocacia.conexao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import br.com.abce.advocacia.dao.EscritorioDao;
import br.com.abce.advocacia.dao.NotaAndamentoDao;
import br.com.abce.advocacia.dao.NotaDao;
import br.com.abce.advocacia.dao.NotaDocumentoDao;
import br.com.abce.advocacia.dao.NotaMensagemDao;
import br.com.abce.advocacia.dao.ProcessoDao;
import br.com.abce.advocacia.dao.ProcessoUsuarioDao;
import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.dao.UsuarioEscritorioDao;
import br.com.abce.advocacia.util.BuscarPath;

/**
 * Created by CarlosEduardo on 19/12/2016.
 */

public abstract class ConexaoInterface {

	private static String bancoTipo = "ORACLE";
	private static ConexaoInterface conexao = null;

	public static ConexaoInterface getConexao() throws Exception {

		if (conexao != null)
			return conexao;

		if (bancoTipo == null) {
			Properties props = new Properties();

			// pega apenas o nome do arquivo
			String path = new BuscarPath().getPath();

			int idx = path.lastIndexOf(File.separator);
			if (idx < 0)
				idx = 0;
			else
				idx++;

			String contexto = path.substring(idx, path.length());

			Properties properties = new Properties();
			File fileConfig = new File(contexto + ".properties");

			System.out.println("**>>ARQUIVO DE CONFIGURAÇÕES:: " + fileConfig.getAbsolutePath());

			if (fileConfig.isFile() == false) {
				BufferedWriter fw = new BufferedWriter(new FileWriter(fileConfig));
				fw.write("banco.tipo = ORACLE");
				fw.newLine();
				fw.write("prop.banco.ip = X");
				fw.newLine();
				fw.write("prop.banco.porta = X");
				fw.newLine();
				fw.write("prop.banco.sid = X");
				fw.newLine();
				fw.write("prop.banco.usuario = X");
				fw.newLine();
				fw.write("prop.banco.senha = X");
				fw.newLine();
				fw.flush();
				fw.close();
			}

			InputStream file = new FileInputStream(fileConfig.getAbsolutePath());
			props.load(file);

			bancoTipo = props.getProperty("banco.tipo");
		}

		if (bancoTipo != null) {
			if (bancoTipo.equals("ORACLE")) {
				conexao = ConexaoOracle.getInstance();
			} else {
				conexao = ConexaoSQLite.getInstance();
			}
		}

		{
			EscritorioDao.createTable(conexao);
			NotaAndamentoDao.createTable(conexao);
			NotaDao.createTable(conexao);
			NotaDocumentoDao.createTable(conexao);
			NotaMensagemDao.createTable(conexao);
			ProcessoDao.createTable(conexao);
			ProcessoUsuarioDao.createTable(conexao);
			UsuarioDao.createTable(conexao);
			UsuarioEscritorioDao.createTable(conexao);
		}

		return conexao;
	}

	public abstract Connection getConnection();

	public abstract PreparedStatement preparedStatement(String sql) throws SQLException;

	public abstract boolean isRemoto();

	public abstract void fecharConexao() throws Exception;

	public abstract long executarSQL(String sql) throws Exception;

	public abstract long executarSQL(List<String> sql) throws Exception;

	public abstract long executarSQL_EX(String sql);

	public abstract ResultadoInterface consultar(String sql) throws Exception;

	public abstract void criarTabela(String tabela, String campoChave, CampoTipo campoTipo, boolean primaryKey);

	public abstract void addColuna(String tabela, String campo, CampoTipo campoTipo);

	public abstract void excluirColuna(String tabela, String campo);

	public abstract void excluirTabela(String tabela);

	public abstract String str(String s);

	public abstract String str(int s);

	public abstract String str(long s);

	public abstract String str(boolean s);

	public abstract String str(double s);

	public abstract String str(Date s);

	public abstract CampoTipo typeSTRING(int tamanho);

	public abstract CampoTipo typeINTEGER();

	public abstract CampoTipo typeLONG();

	public abstract CampoTipo typeBOOLEAN();

	public abstract CampoTipo typeDOUBLE();

	public abstract CampoTipo typeDATE();

	public abstract CampoTipo typeBLOB();

	public class CampoTipo {
		private String tipo;

		public CampoTipo(String tipo) {
			this.tipo = tipo;
		}

		@Override
		public String toString() {
			return tipo;
		}

	}

}
