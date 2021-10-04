
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
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
        String fileLocation = "/Users/tamzid/Work/practice/socket-test/src/main/demo.xlsx";
        Map<Integer, List<String>> data = excel.main(fileLocation);
        data.forEach((key, value) -> {
            if (key > 0) {
                log(isSocketAliveUitlitybyCrunchify(value.get(0), Integer.parseInt(value.get(1))));
            }
        });
    }

    public void checkConnections() {
        // Connection to pro.crunchify.com on port 81
        log(isSocketAliveUitlitybyCrunchify("pro.crunchify.com", 81));

        // Connection to pro.crunchify.com on port 80
        log(isSocketAliveUitlitybyCrunchify("pro.crunchify.com", 80));
    }

    public static boolean isSocketAliveUitlitybyCrunchify(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        log("hostName: " + hostName + ", port: " + port);
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
