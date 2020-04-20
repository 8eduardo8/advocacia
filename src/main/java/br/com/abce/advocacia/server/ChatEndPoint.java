package br.com.abce.advocacia.server;

import br.com.abce.advocacia.util.LoggerUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value="/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class )
public class ChatEndPoint {

    private Session session;
    private static Set<ChatEndPoint> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam(value = "username") String username) throws IOException, EncodeException {

        LoggerUtil.info(String.format("Chat - %s", username));

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        LoggerUtil.info(String.format("Nova Sessão %s", session.getId()));

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");

        broadcast(message, this.session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message message)
            throws IOException, EncodeException {

        String from = users.get(session.getId());
        message.setFrom(from);

        broadcast(message, this.session.getId());
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        chatEndpoints.remove(this);

        LoggerUtil.info(String.format("Close session - %s", session.getId()));
        Message message = new Message();
        String from = users.get(session.getId());
        message.setFrom(from);
        message.setContent("Disconnected!");
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
                            message.getFrom(), message.getTo(), idSession, message.getContent()));
                } catch (IOException | EncodeException e) {
                    LoggerUtil.error(e.getMessage(), e);
                }
            }
        });
    }
}
