package br.com.abce.advocacia.conexao;

import java.util.Date;

import java.sql.Blob;

/**
 * Created by CarlosEduardo on 19/12/2016.
 */

public interface ResultadoInterface {

	public boolean next() throws Exception;

	public boolean previous() throws Exception;

	public boolean first() throws Exception;

	public void close() throws Exception;

	public String getString(String key) throws Exception;

	public int getInt(String key) throws Exception;

	public long getLong(String key) throws Exception;

	public double getDouble(String key) throws Exception;

	public boolean getBoolean(String key) throws Exception;

	public Date getDate(String key) throws Exception;

	public byte[] getByte(String key) throws Exception;

}
