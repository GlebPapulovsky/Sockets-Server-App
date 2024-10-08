import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadSettings {
    public static int readSettings(String filepath){
        String filePath = "settings.txt"; // Укажите путь к вашему файлу

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();
            String port=line.substring(line.indexOf("=")+1);
            return Integer.valueOf(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;


    }
    public static void main(String args[]){
        int port=readSettings("settings.txt");
        System.out.println(port);
    }

}