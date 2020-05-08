package br.com.abce.advocacia.controller;


import br.com.abce.advocacia.bean.NotaAndamento;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class Chat {

    @Inject
    private NotaService notaService;

    @Inject
    private ProcessoService processoService;

    @Inject
    private Login login;

    private ProcessoBean processoBean;

    private UsuarioBean usuarioBean;

    private List<NotaAndamento> lista;

    private Long idProcesso;

    @PostConstruct
    public void init() {
        usuarioBean = login.getUsuarioBean();
    }

    public String conversa() {

        if (processoBean == null)

            if (idProcesso != null) {

                try {

                    processoBean = processoService.buscar(idProcesso);

                } catch (RecursoNaoEncontradoException e) {
                    Mensagem.info(e.getMessage());
                } catch (ValidacaoException e) {
                    Mensagem.info(Consts.NAO_POSSIVEL_DADOS_PROCESSO);
                }
            }

        carregarHistorico();

        return "chat";
    }

    public void carregarHistorico() {

        try {

            if (processoBean == null)

                throw new ValidacaoException(Consts.NAO_POSSIVEL_DADOS_PROCESSO);

            lista = notaService.listaMensagens(processoBean.getId());

        } catch (ValidacaoException | RecursoNaoEncontradoException e) {
            Mensagem.info(e.getMessage());
        } catch (Exception e){
            LoggerUtil.error(e.getMessage(), e);
            Mensagem.erro(e.getMessage());
        }
    }

    public ProcessoBean getProcessoBean() {
        return processoBean;
    }

    public void setProcessoBean(ProcessoBean processoBean) {
        this.processoBean = processoBean;
    }

    public List<NotaAndamento> getLista() {
        return lista;
    }

    public void setLista(List<NotaAndamento> lista) {
        this.lista = lista;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }
}
