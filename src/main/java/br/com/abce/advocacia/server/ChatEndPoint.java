package br.com.abce.advocacia.server;

import br.com.abce.advocacia.bean.*;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value="/chat/{id-processo}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class )
public class ChatEndPoint {

    private Session session;
    private static Set<ChatEndPoint> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @Inject
    private NotaService notaService;

    @Inject
    private ProcessoService processoService;

    @Inject
    private UsuarioService usuarioService;

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam(value = "id-processo") String idProcesso) throws IOException {

        LoggerUtil.info(String.format("Chat - %s", idProcesso));

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), idProcesso);

        LoggerUtil.info(String.format("Nova Sessão %s", session.getId()));

        Message message = new Message();
        message.setNomeUsuario("Servidor");
        message.setContent("Connected!");
        message.setData(getDataFormatada());

        broadcast(message, this.session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, ValidacaoException, InfraestruturaException, RecursoNaoEncontradoException {

        final String idProcesso = users.get(session.getId());

        if (StringUtils.isNotBlank(message.getContent()) || message.getFile() != null) {

            NotaBean notaBean = new NotaBean();

            final ProcessoBean processoBean = processoService.buscar(Long.valueOf(idProcesso));

            notaBean.setIdProcesso(processoBean.getId());

            final UsuarioBean usuarioBean = usuarioService.buscar(Long.valueOf(message.getIdUsuario()));

            notaBean.setIdUsuario(usuarioBean.getId());
            notaBean.setDataCadastro(new Date());
            notaBean.setTipo(Consts.TIPO_MENSAGEM);

            if (StringUtils.isNotBlank(message.getContent())) {

                notaBean.setNotaMensagem(new NotaMensagem());
                notaBean.getNotaMensagem().setMensagem(message.getContent());
                notaBean.getNotaMensagem().setTipo(Consts.TIPO_MENSAGEM);

            }

            if (StringUtils.isNotBlank(message.getFile())) {

                notaBean.setNotaDocumento(new NotaDocumento());
                notaBean.getNotaDocumento().setDescricao(Consts.DOCUMENTO_ANEXADO_MENSAGEM);
                notaBean.getNotaDocumento().setArquivo(Base64.getDecoder().decode(message.getFile()));
                notaBean.getNotaDocumento().setFormato(message.getFormatoFile());
                notaBean.getNotaDocumento().setNome(message.getNomeFile());

            }

            notaBean = notaService.salvarNota(notaBean);

            message.setIdNota(notaBean.getId());
        }

        message.setIdProcesso(idProcesso);
        message.setData(getDataFormatada());
        message.setFile(null);

        broadcast(message, this.session.getId());
    }

    @OnClose
    public void onClose(Session session) throws IOException {

        chatEndpoints.remove(this);

        LoggerUtil.info(String.format("Close session - %s", session.getId()));
        Message message = new Message();
        String idProcesso = users.get(session.getId());
        message.setIdProcesso(idProcesso);
        message.setData(getDataFormatada());
        message.setContent("Disconnected!");
        message.setNomeUsuario("Servidor");
        broadcast(message, this.session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LoggerUtil.error(String.format("Falha na sessão %s", session.getId()), throwable);
    }

    private static void broadcast(Message message, String idSession) {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {

                    endpoint.session.getBasicRemote().
                            sendObject(message);

                    LoggerUtil.info(String.format("Message username from %s to %s - Session %s - Message %s ",
                            message.getIdUsuario(), message.getContent(), idSession, message.getContent()));
                } catch (IOException | EncodeException e) {
                    LoggerUtil.error(e.getMessage(), e);
                }
            }
        });
    }

    private String getDataFormatada() {
        return DateFormatUtils.format(new Date(), "dd/MM/yyyy HH:mm:ss");
    }
}
