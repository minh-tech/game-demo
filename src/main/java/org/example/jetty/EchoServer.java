package org.example.jetty;

import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.ee10.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.eclipse.jetty.server.Server;

import java.net.URL;
import java.util.Objects;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        Server server = EchoServer.newServer(8080);
        server.start();
        server.join();
    }
    public static Server newServer(int port) {
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        JettyWebSocketServletContainerInitializer.configure(context, null);
        ServletHolder wsHolder = new ServletHolder("echo", new EchoWebSocketServlet());
        context.addServlet(wsHolder, "/echo");

        URL urlStatics = Thread.currentThread().getContextClassLoader().getResource("echo-root/index.html");
        Objects.requireNonNull(urlStatics, "Unable to find index.html in classpath");
        String urlBase = urlStatics.toExternalForm().replaceFirst("/[^/]*$", "/");
        ServletHolder defaultHolder = new ServletHolder("default", new DefaultServlet());
        defaultHolder.setInitParameter("baseResource", urlBase);
        defaultHolder.setInitParameter("dirAllowed", "true");
        context.addServlet(defaultHolder, "/");

        return server;
    }
}
