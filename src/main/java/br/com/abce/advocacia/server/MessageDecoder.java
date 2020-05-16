package br.com.abce.advocacia.server;

import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    private static Gson gson = new Gson();

    @Override
    public Message decode(String message) {
        return gson.fromJson(message, Message.class);
    }

    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
