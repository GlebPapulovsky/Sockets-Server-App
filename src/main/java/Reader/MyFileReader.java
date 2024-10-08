package Reader;

import java.io.BufferedReader;
import java.io.IOException;

public class MyFileReader {
    public static void main(String args[]) {
        //int port = readSettings("settings.txt");
        //System.out.println(port);
        System.out.println(MyFileReader.readAllFromLog("file.log"));
    }

    public static int readSettings(String filePath) {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            line = br.readLine();
            String port = line.substring(line.indexOf("=") + 1);
            return Integer.valueOf(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static String readAllFromLog(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}



