package br.com.abce.advocacia.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.primefaces.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.model.Usuario;

@Path("/v1_0_0")
public class V1_0_0 {

	static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
	public static String versao = "v1_0_0";

	@GET
	@Path("/testeConexao")
	public Response testeConexao() {
		return Response.status(Status.OK).entity("SERVIDOR ONLINE").build();
	}

	@POST
	@Path("/getUsuario")
	public Response getUsuario(InputStream incomingData) {
		try {
			String s = convertStreamToString(incomingData);

			JSONObject json = new JSONObject(s);
			String login = json.getString("login");

			Usuario usuario = new UsuarioDao().get(login);

			return Response.status(Status.OK).entity(gson.toJson(usuario)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(403).type("text/plain").entity("ERRO BUSCAR PRODUTOS. " + e.getMessage()).build();
		}
	}

	// FUN��O QUE BUSCA AS INFORMA��E CONTIDAS NO POST
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(sb.toString());
		return sb.toString();
	}

}
