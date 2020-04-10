package br.com.abce.advocacia.util;

import br.com.abce.advocacia.exceptions.InfraestruturaException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OperacoesArquivos {

	private OperacoesArquivos() {
		super();
	}

	public static synchronized void downloadFile(String filename, String fileLocation, String mimeType,
												 FacesContext facesContext) throws InfraestruturaException {

		ExternalContext context = facesContext.getExternalContext(); // Context
		String fullFileName = fileLocation;
		File file = new File(fullFileName); // Objeto arquivo mesmo :)

		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
		response.setContentLength((int) file.length()); // O tamanho do arquivo
		response.setContentType(mimeType); // e obviamente o tipo

		try (FileInputStream in = new FileInputStream(file)) {
			try(OutputStream out = response.getOutputStream()) {

				byte[] buf = new byte[(int) file.length()];
				int count;
				while ((count = in.read(buf)) >= 0) {
					out.write(buf, 0, count);
				}
				out.flush();
			}
			facesContext.responseComplete();
		} catch (IOException ex) {
			throw new InfraestruturaException(ex.getMessage());
		}
	}
}// By Caio Rodrigo Paulucci