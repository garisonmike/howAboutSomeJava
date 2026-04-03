/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatServer;

/**
 *
 * @author spiderman
 */

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 5123;
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("--------________Java Chat Server v1.0________--------");
        System.out.println("Server Starting on port " + PORT + "...\n");
        System.out.println("I had no reason to choose this port, it just crossed my mind :) ");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server Ready and listening for connections.");
            System.out.println("Server Press Ctrl+C to stop. Or just shutdown your pc :) \n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }


    // sends to everyone except the person who sent it
    static void broadcast(String message, ClientHandler sender) {
        String timestamp = TIME_FORMAT.format(new Date());
        String formatted = "[" + timestamp + "] " + message;
        System.out.println("Broadcast " + formatted);

        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(formatted);
            }
        }
    }

    // sends to everyone including the sender
    static void broadcastAll(String message) {
        String timestamp = TIME_FORMAT.format(new Date());
        String formatted = "[" + timestamp + "] " + message;
        System.out.println("Broadcast " + formatted);

        for (ClientHandler client : clients) {
            client.sendMessage(formatted);
        }
    }

    // pushes the updated user list to all clients
    static void broadcastUserList() {
        StringBuilder list = new StringBuilder("/userlist ");
        for (ClientHandler c : clients) {
            if (c.getUsername() != null) {
                list.append(c.getUsername()).append(",");
            }
        }
        for (ClientHandler c : clients) {
            c.sendMessage(list.toString().replaceAll(",$", ""));
        }
    }

    static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    static int getClientCount() {
        return clients.size();
    }


    // each connected client gets its own thread through this class
    static class ClientHandler implements Runnable {

        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // first thing the client sends is their username
                username = in.readLine();
                if (username == null || username.isBlank()) {
                    username = "Anonymous";
                }

                System.out.println("[Server] ✚ " + username + " connected from "
                        + socket.getInetAddress().getHostAddress()
                        + " | Online: " + getClientCount());

                broadcastAll("★ " + username + " has joined the chat! (" + getClientCount() + " online)");
                broadcastUserList();
                sendMessage("/welcome Welcome to the chat, " + username + "!");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    System.out.println("[" + username + "]: " + message);
                    broadcast(username + ": " + message, this);
                    String timestamp = TIME_FORMAT.format(new Date());
                    sendMessage("[" + timestamp + "] You: " + message);
                }

            } catch (IOException e) {
                System.out.println("Server Connection reset for: "
                        + (username != null ? username : "unknown"));
            } finally {
                disconnect();
            }
        }

        void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }

        String getUsername() {
            return username;
        }

        private void disconnect() {
            removeClient(this);
            try {
                if (socket != null) socket.close();
            } catch (IOException ignored) {}

            if (username != null) {
                System.out.println("Server  " + username + " disconnected | Online: " + getClientCount());
                broadcastAll("✖ " + username + " has left the chat. (" + getClientCount() + " online)");
                broadcastUserList();
            }
        }
    }
}