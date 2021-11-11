
import org.apache.xpath.operations.Bool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.init();
    }

    private void init() throws IOException {
        System.out.println("Hello init");
        Excel excel = new Excel();
        String fileLocation = "src/main/demo.xlsx";
        Map<Integer, List<String>> data = excel.main(fileLocation);
        List<Object[]> result = new ArrayList<>();
        data.forEach((key, value) -> {
            if (key == 0) {
                Object[] newObj = {
                        "IP", "PORT", "STATUS"
                };
                result.add(newObj);
            } else {
                boolean isAlive = isSocketAlive(value.get(0), Integer.parseInt(value.get(1)));
//                log(isAlive);

                Object[] newObj = {
                        value.get(0), Integer.parseInt(value.get(1)), isAlive
                };
                result.add(newObj);
            }

        });
        try {
            excel.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isSocketAlive(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

//        log("hostName: " + hostName + ", port: " + port);
        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (SocketTimeoutException exception) {
            System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println(
                    "IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
        }
        return isAlive;
    }

    // Simple log utility
    private static void log(String string) {
        System.out.println(string);
    }

    // Simple log utility returns boolean result
    private static void log(boolean isAlive) {
        System.out.println("isAlive result: " + isAlive + "\n");
    }
}
