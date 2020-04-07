package br.com.abce.advocacia.conexao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import br.com.abce.advocacia.conexao.ConexaoInterface;
import br.com.abce.advocacia.conexao.ResultadoInterface;

public class Resultado implements ResultadoInterface {

	private ConexaoInterface conexao;
	private Statement statement;
	private ResultSet resultset;

	public Resultado(ConexaoInterface conexao, Statement statement, ResultSet resultset) {
		this.conexao = conexao;
		this.statement = statement;
		this.resultset = resultset;
	}

	public ConexaoInterface getConexao() {
		return conexao;
	}

	public String getString(String nome) throws Exception {
		return resultset.getString(nome);
	}

	public String getString(int posicao) throws Exception {
		return resultset.getString(posicao);
	}

	public int getInt(int indice) throws Exception {
		return resultset.getInt(indice);
	}

	public int getInt(String nome) throws Exception {
		return resultset.getInt(nome);
	}

	public void updateInt(String nome, int x) throws SQLException {
		resultset.updateInt(nome, x);
	}

	public long getLong(String nome) throws Exception {
		return resultset.getLong(nome);
	}

	public Date getDate(String nome) throws Exception {
		// String s = resultset.getString(nome);
		// if (s != null && s.equals("") == false)
		// return resultset.getDate(nome);
		// return null;
		Timestamp timestamp = resultset.getTimestamp(nome);
		return timestamp == null ? null : new Date(timestamp.getTime());
	}

	public Date getDate(int indice) throws Exception {
		Timestamp timestamp = resultset.getTimestamp(indice);
		return timestamp == null ? null : new Date(timestamp.getTime());
	}

	public double getDouble(String nome) throws Exception {
		return resultset.getDouble(nome) + 0.00000001d;
	}

	@Override
	public boolean getBoolean(String key) throws Exception {
		return resultset.getInt(key) == 0 ? false : true;
	}

	@Override
	public byte[] getByte(String key) throws Exception {
		InputStream is = resultset.getBinaryStream(key);
		return toByteArrayUsingJava(is);
	}

	public static byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (is == null)
			return null;
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	public boolean next() throws Exception {
		return resultset.next();
	}

	public boolean previous() throws Exception {
		return resultset.previous();
	}

	public boolean first() throws Exception {
		return resultset.first();
	}

	public void updateRow() throws SQLException {
		resultset.updateRow();
	}

	public void close() throws Exception {
		if (conexao != null) {
			// conexao.fecharConexao();

			if (resultset != null) {
				resultset.close();
			}

			if (statement != null) {
				statement.close();
			}

			// conexao = null;
			resultset = null;
		}
	}

}