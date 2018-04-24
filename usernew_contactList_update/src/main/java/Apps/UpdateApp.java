package Apps;


import java.io.IOException;

public class UpdateApp {
    public static String path;
    public static void main(String [] args) throws IOException {
//        path=args[0];
        path="updateFile.properties";
        try {
           Task.excuteTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
