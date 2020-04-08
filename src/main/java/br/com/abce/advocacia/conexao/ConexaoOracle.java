package br.com.abce.advocacia.conexao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import br.com.abce.advocacia.util.BuscarPath;

/**
 * Created by CarlosEduardo on 09/12/2016.
 */

public class ConexaoOracle extends ConexaoInterface {
	public static ConexaoOracle oracleDB;

	private Connection connection;

	private ConexaoOracle() {
		try {

			// pega apenas o nome do arquivo
			String path = new BuscarPath().getPath();

			int idx = path.lastIndexOf(File.separator);
			if (idx < 0)
				idx = 0;
			else
				idx++;

			String contexto = path.substring(idx, path.length());

			Properties props = new Properties();
			File f = new File(contexto + ".properties");
			System.out.println(f.getAbsolutePath());
			InputStream file = new FileInputStream(f.getAbsolutePath());

			props.load(file);

			String ip = props.getProperty("prop.banco.ip");
			String porta = props.getProperty("prop.banco.porta");
			String sid = props.getProperty("prop.banco.sid");
			String usuario = props.getProperty("prop.banco.usuario");
			String senha = props.getProperty("prop.banco.senha");

			Class.forName("oracle.jdbc.OracleDriver");

			String strConexao = "jdbc:oracle:thin:@" + ip + ":" + porta + ":" + sid;
			System.out.println(strConexao);

			System.out.println("user:" + usuario);
			System.out.println("pwd :" + senha);

			this.connection = DriverManager.getConnection(strConexao, usuario, senha);

		} catch (Exception e) {
			System.err.println("Exception AO CRIAR CONEXAO: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	public boolean isRemoto() {
		return false;
	}

	public static ConexaoOracle getInstance() {
		try {
			int i = 0;
			// ENQUANDO NÃO TIVER CONEXÃO TENTA CRIAR UMA NOVA CONEXÃO
			while (oracleDB == null || oracleDB.connection.isValid(1) == false) {
				i++;
				oracleDB = new ConexaoOracle();
				if (i > 50)
					break;
			}
		} catch (Exception e) {
			oracleDB = null;
		}
		return oracleDB;
	}

	@Override
	public void fecharConexao() {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			connection = null;
		}
	}

	@Override
	public long executarSQL(List<String> lista) throws Exception {
		Statement statement = null;
		long resultado = 0;

		String sql = "";

		try {
			connection.setAutoCommit(false);
			for (String s : lista) {
				sql = s;
				System.out.println(sql);

				statement = connection.createStatement();
				resultado = statement.executeUpdate(sql);
			}

		} catch (Exception exception) {
			if (connection != null) {
				connection.rollback();
			}
			exception.printStackTrace();
			throw new Exception("Exception ao executar (" + sql + "), " + exception.getMessage(), exception);

		} finally {
			if (connection != null) {
				connection.commit();
			}
			if (statement != null) {
				statement.close();
			}
			connection.setAutoCommit(true);
		}

		return resultado;
	}

	@Override
	public long executarSQL(String sql) throws Exception {
		Statement statement = null;
		long resultado = 0;

		System.out.println(sql);

		try {
			connection.setAutoCommit(true);

			statement = connection.createStatement();
			resultado = statement.executeUpdate(sql);

		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("Exception ao executar (" + sql + "), " + exception.getMessage(), exception);

		} finally {
			if (statement != null) {
				statement.close();
			}
		}

		return resultado;
	}

	@Override
	public long executarSQL_EX(String sql) {
		Statement statement = null;
		long resultado = 0;

		System.out.println(sql);
		try {
			connection.setAutoCommit(true);

			statement = connection.createStatement();
			resultado = statement.executeUpdate(sql);

		} catch (Exception exception) {
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}

		return resultado;
	}

	public Resultado consultar(String sql) throws Exception {
		Statement statement = null;
		ResultSet resultset = null;

		try {
			System.out.println(sql);
			statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultset = statement.executeQuery(sql);

			Resultado rs = new Resultado(this, statement, resultset);

			return rs;

		} catch (Exception exception) {

			if (resultset != null) {
				resultset.close();
			}

			if (statement != null) {
				statement.close();
			}

			exception.printStackTrace();
			// throw new Exception("Exception ao executar (" + sql + "), " +
			// exception.getMessage(), exception);
			throw new Exception("Exception ao executar (" + sql + "), ");
		}
	}

	@Override
	public String str(String s) {
		return s == null ? "null" : "'" + s + "'";
	}

	@Override
	public String str(int s) {
		return String.valueOf(s);
	}

	@Override
	public String str(long s) {
		return String.valueOf(s);
	}

	@Override
	public String str(boolean s) {
		return s == false ? "0" : "1";
	}

	@Override
	public String str(double s) {
		return String.valueOf(s);
	}

	@Override
	public String str(Date s) {
		// return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s) +
		// "'";
		if (s == null)
			return "null";
		return "to_date('" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(s) + "','yyyy/MM/dd hh24:mi:ss')";
	}

	@Override
	public void criarTabela(String tabela, String campoChave, CampoTipo campoTipo, boolean primaryKey) {
		executarSQL_EX("CREATE TABLE " + tabela + "(" + campoChave + " " + campoTipo + " "
				+ (primaryKey == true ? "PRIMARY KEY" : "") + ")");
	}

	@Override
	public void addColuna(String tabela, String campo, CampoTipo campoTipo) {
		executarSQL_EX("ALTER  TABLE " + tabela + " ADD " + campo + " " + campoTipo);
	}

	@Override
	public void excluirColuna(String tabela, String campo) {

	}

	@Override
	public void excluirTabela(String tabela) {

	}

	@Override
	public CampoTipo typeSTRING(int tamanho) {
		return new CampoTipo("VARCHAR(" + tamanho + ")");
	}

	@Override
	public CampoTipo typeINTEGER() {
		return new CampoTipo("INTEGER");
	}

	@Override
	public CampoTipo typeLONG() {
		return new CampoTipo("LONG");
	}

	@Override
	public CampoTipo typeBOOLEAN() {
		return new CampoTipo("INTEGER");
	}

	@Override
	public CampoTipo typeDOUBLE() {
		return new CampoTipo("DECIMAL(12,4)");
	}

	@Override
	public CampoTipo typeDATE() {
		return new CampoTipo("DATE");
	}

	@Override
	public CampoTipo typeBLOB() {
		return new CampoTipo("BLOB");
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

}
