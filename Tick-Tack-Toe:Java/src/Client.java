import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class Client extends Server {
    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 5309);
            String playID = "HELLO";
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(playID);
            writer.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = in.readLine();
            System.out.println(msg);



                if (socket.isConnected()) {
                    String Game = in.readLine();
                    System.out.println(Game);
                    String game = in.readLine();
                    System.out.println(game);
                    for (int i = 1; i < 9; i++) {

                    }

                }


                BufferedReader out = new BufferedReader(new InputStreamReader(System.in));
                int move = Integer.parseInt(out.readLine());
                PrintWriter sendOut = new PrintWriter(socket.getOutputStream(), true);
                sendOut.println(move);
                sendOut.flush();


            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }





