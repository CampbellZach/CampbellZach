import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Controller {

        static final int PORT = 1978;

        public static void main(String args[]) {

            ServerSocket serverSocket = null;
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();

            }
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println(e);
                }
                // new thread for a client

            }
        }
    }


