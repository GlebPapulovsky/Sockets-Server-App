import java.io.*;
import java.net.*;
import java.util.Date;

public class ChatClient2 {
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        int PORT= ReadSettings.readSettings("settings.txt");
        try (Socket socket = new Socket("localhost", PORT)) { // считывай порт из settings.txt
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String username = getUsername(console);
            final String[] message = new String[1];

            new Thread(() -> {
                try {
                    while ((message[0] = in.readLine()) != null) {
                        logMessage(message[0]);
                        System.out.println(message[0]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                message[0] = console.readLine();
                if ("/exit".equals(message[0])) {
                    break;
                }
                out.println(username + ": " + message[0]);
                logMessage(username + ": " + message[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUsername(BufferedReader console) throws IOException {
        System.out.print("Введите имя пользователя: ");
        return console.readLine();
    }

    private static void logMessage(String message) {
        try (FileWriter fw = new FileWriter("file.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new Date() + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}