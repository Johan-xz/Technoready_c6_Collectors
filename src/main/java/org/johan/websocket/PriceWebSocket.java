package org.johan.websocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class PriceWebSocket {

    // (C2) Usamos una cola "thread-safe" para guardar las sesiones
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("Cliente conectado al WebSocket: " + user.getRemoteAddress());
        sessions.add(user); // Añade al nuevo usuario a la lista
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Cliente desconectado: " + reason);
        sessions.remove(user); // Lo quita de la lista
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        // Asumimos que el mensaje es una puja: "ITEM_ID:NUEVO_PRECIO"
        // Ej: "uuid-1234-abcd:150.99"
        System.out.println("Puja recibida: " + message);

        // (C2) Estrategia: Re-transmitir (broadcast) el mensaje
        // a TODOS los clientes conectados (incluido el que lo envió)
        PriceWebSocket.broadcast(message);
    }

    // Método estático para enviar el mensaje a todos los clientes conectados
    // Esto permite que PriceUpdateService pueda llamarlo sin necesidad de una instancia
    public static void broadcast(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getRemote().sendString(message);
                } catch (IOException e) {
                    System.err.println("Error enviando mensaje: " + e.getMessage());
                }
            }
        }
    }
}