import java.io.*;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;

public class ChatServer {

    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        int PORT= ReadSettings.readSettings("settings.txt");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter output;
        private BufferedReader input;

        public ClientHandler(Socket socket) {            this.socket = socket;
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(output);
                }
                String message;
                while ((message = input.readLine()) != null) {
                    logMessage(message);
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void logMessage(String message) {
            try (FileWriter fw = new FileWriter("file.log", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(new Date() + ": " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}