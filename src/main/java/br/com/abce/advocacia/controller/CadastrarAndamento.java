package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.Andamento;
import br.com.abce.advocacia.bean.NotaAndamento;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CadastrarAndamento extends GenericController implements Serializable {

    private NotaAndamento andamentoBean;

    private List<ProcessoBean> listaProcessoBean;

    private Andamento[] andamentos = Andamento.values();

    @Inject
    private NotaService notaService;

    @Inject
    private Login login;

    @Inject
    private ProcessoService processoService;

    @PostConstruct
    public void init(){
        novo();
    }

    public String cadastrar(){

        novo();

        return "cadastrarAndamento";
    }

    public void salvar(){

        try {

            andamentoBean.setIdUsuario(login.getUsuarioBean().getId());

            notaService.salvarAndamento(andamentoBean);

            Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

        } catch (InfraestruturaException e) {
            LoggerUtil.error(e);
            Mensagem.erro(e.getMessage());
        } catch (ValidacaoException e) {
            Mensagem.info(Consts.NAO_POSSIVEL_DADOS_USUARIO);
        }

    }

    public void novo(){

        try {

            andamentoBean = new NotaAndamento();

            final String idProcesso = getProcessoIdFromRequest();

            if (StringUtils.isNotBlank(idProcesso))

                andamentoBean.setIdProcesso(Long.parseLong(idProcesso));

            listaProcessoBean = processoService.listar(login.getUsuarioBean().getId());

        } catch (InfraestruturaException e) {
            LoggerUtil.error(e);
            Mensagem.erro(e.getMessage());
        } catch (ValidacaoException e) {
            Mensagem.info(Consts.NAO_POSSIVEL_DADOS_USUARIO);
        } catch (RecursoNaoEncontradoException e) {
            Mensagem.info(e.getMessage());
        }
    }

    public NotaAndamento getAndamentoBean() {
        return andamentoBean;
    }

    public void setAndamentoBean(NotaAndamento andamentoBean) {
        this.andamentoBean = andamentoBean;
    }

    public List<ProcessoBean> getListaProcessoBean() {
        return listaProcessoBean;
    }

    public void setListaProcessoBean(List<ProcessoBean> listaProcessoBean) {
        this.listaProcessoBean = listaProcessoBean;
    }

    public Andamento[] getAndamentos() {
        return andamentos;
    }

    public NotaService getNotaService() {
        return notaService;
    }

    public void setNotaService(NotaService notaService) {
        this.notaService = notaService;
    }
}
