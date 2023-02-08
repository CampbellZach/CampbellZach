import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Copy_Client extends Server {
    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 5310);
            String playID = "HELLO";
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(playID);
            writer.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = in.readLine();
            System.out.println(msg);


            for(int i = 0; i < 9; i++) {
                if (socket.isConnected()) {
                    String Game = in.readLine();
                    System.out.println(Game);
                }
            }
            if(socket.isConnected()){
                BufferedReader output = new BufferedReader(new InputStreamReader(System.in));
                writer.println(output);
                writer.flush();
                System.out.println("It is " + PlayerTurn + "'s turn!");
            }
            while (true) {

            }
        }
            catch (IOException e) {
            e.printStackTrace();
        }

    }
}

