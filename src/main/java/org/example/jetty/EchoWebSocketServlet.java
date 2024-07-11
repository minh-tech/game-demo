package org.example.jetty;

import org.eclipse.jetty.ee10.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.ee10.websocket.server.JettyWebSocketServletFactory;

public class EchoWebSocketServlet extends JettyWebSocketServlet {
    @Override
    protected void configure(JettyWebSocketServletFactory factory) {
        factory.register(EchoWebSocket.class);
    }
}
