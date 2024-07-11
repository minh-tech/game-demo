package org.example.jetty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Callback;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.example.model.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket
public class EchoWebSocket {
    private static final Logger LOG = LoggerFactory.getLogger(EchoWebSocket.class);
    private Session session;
    private int score = 0;
    ObjectMapper objectMapper = new ObjectMapper();

    @OnWebSocketOpen
    public void onWebSocketOpen(Session session) {
        this.session = session;
        LOG.info("WebSocket Open: {}", session);
        this.session.sendText("You are now connected to " + this.getClass().getName(), Callback.NOOP);
    }

    @OnWebSocketMessage
    public void onWebSocketMessage(String message) {
        if (message.equalsIgnoreCase("HighestScore")) {
            this.session.sendText("Highest Score is " + score, Callback.NOOP);
        } else {
            try {
                Score obj = objectMapper.readValue(message, Score.class);
                score = Math.max(obj.getScore(), score);
                this.session.sendText("Save score " + obj.getScore(), Callback.NOOP);
            } catch (JsonProcessingException e) {
                this.session.sendText("Request is not supported", Callback.NOOP);
            }
        }
        LOG.info("Echoing back message [{}]", message);
    }

    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason) {
        this.session = null;
        LOG.info("WebSocket Close: {} - {}", statusCode, reason);
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable cause) {
        LOG.warn("WebSocket Error", cause);
    }
}
