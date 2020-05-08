package br.com.abce.advocacia.util;

import br.com.abce.advocacia.controller.Login;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class SecurityController implements PhaseListener {

    public static final String LOGIN_XHTML = "/login.xhtml";

    @Override
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();

		String paginaAcessada = context.getViewRoot().getViewId();
		if (LOGIN_XHTML.equals(paginaAcessada)
				|| paginaAcessada.contains("api")
				|| paginaAcessada.contains("rest")
				|| paginaAcessada.contains("esqueciSenha.xhtml")
				|| paginaAcessada.contains("alterarSenha.xhtml")) {
			return;
		}

		Login loginPage = context.getApplication().evaluateExpressionGet(context, "#{login}", Login.class);

		if (loginPage == null || loginPage.getSenha() == null) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, SecurityController.LOGIN_XHTML);

			// Pula para �ltima fase do ciclo de vida
			context.renderResponse();
			return;
		}

		// verifica se o usu�rio est� logado
		if (!loginPage.isLogado()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, SecurityController.LOGIN_XHTML);

			// Pula para �ltima fase do ciclo de vida
			context.renderResponse();
			return;
		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// faz nada
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}