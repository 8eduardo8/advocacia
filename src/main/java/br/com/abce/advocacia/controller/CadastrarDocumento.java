package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.NotaDocumento;
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
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CadastrarDocumento extends GenericController implements Serializable {

    private NotaDocumento documentoBean;

    private List<ProcessoBean> listaProcessoBean;

    @Inject
    private NotaService notaService;

    @Inject
    private Login login;

    @Inject
    private ProcessoService processoService;

    private UploadedFile file;

    @PostConstruct
    public void init(){
        novo();
    }

    public String cadastrar(){

        novo();

        return "cadastrarDocumento";
    }

    public void salvar(){

        try {

            if (file == null) {
                throw new ValidacaoException("Favor efetuar o upload do arquivo novamente.");
            }

            documentoBean.setIdUsuario(login.getUsuarioBean().getId());
            documentoBean.setArquivo(file.getContents());
            documentoBean.setFormato(file.getContentType());
            documentoBean.setNome(file.getFileName());

            notaService.salvarDocumento(documentoBean);

            Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

        } catch (InfraestruturaException e) {
            LoggerUtil.error(e);
            Mensagem.erro(e.getMessage());
        } catch (ValidacaoException e) {
            Mensagem.info(e.getMessage());
        }

    }

    public void novo(){

        try {

            documentoBean = new NotaDocumento();

            final String idProcesso = getProcessoIdFromRequest();

            if (StringUtils.isNotBlank(idProcesso))

                documentoBean.setIdProcesso(Long.parseLong(idProcesso));

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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<ProcessoBean> getListaProcessoBean() {
        return listaProcessoBean;
    }

    public void setListaProcessoBean(List<ProcessoBean> listaProcessoBean) {
        this.listaProcessoBean = listaProcessoBean;
    }

    public NotaDocumento getDocumentoBean() {
        return documentoBean;
    }

    public void setDocumentoBean(NotaDocumento documentoBean) {
        this.documentoBean = documentoBean;
    }
}
