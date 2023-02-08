import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Server extends View {
    public static void main(String[] args) {


        View GUI = new View();
        GUI.jFrame.setVisible(true);
        final boolean[] player1Turn = {true};
        boolean player2Turn = false;

        public static void Server() {
            if ((buttonArray[0].getText().equals("X")) && (buttonArray[1].getText().equals("X")) && (buttonArray[2].getText().equals("X"))) {
                xWinScreen(0, 1, 2);
            } else if ((buttonArray[3].getText().equals("X")) && (buttonArray[4].getText().equals("X")) && (buttonArray[5].getText().equals("X"))) {
                xWinScreen(3, 4, 5);
            } else if ((buttonArray[6].getText().equals("X")) && (buttonArray[7].getText().equals("X")) && (buttonArray[8].getText().equals("X"))) {
                xWinScreen(6, 7, 8);
            } else if ((buttonArray[0].getText().equals("X")) && (buttonArray[3].getText().equals("X")) && (buttonArray[6].getText().equals("X"))) {
                xWinScreen(0, 3, 6);
            } else if ((buttonArray[1].getText().equals("X")) && (buttonArray[4].getText().equals("X")) && (buttonArray[7].getText().equals("X"))) {
                xWinScreen(1, 4, 7);
            } else if ((buttonArray[2].getText().equals("X")) && (buttonArray[5].getText().equals("X")) && (buttonArray[8].getText().equals("X"))) {
                xWinScreen(2, 5, 8);
            } else if ((buttonArray[0].getText().equals("X")) && (buttonArray[4].getText().equals("X")) && (buttonArray[8].getText().equals("X"))) {
                xWinScreen(0, 4, 8);
            } else if ((buttonArray[2].getText().equals("X")) && (buttonArray[4].getText().equals("X")) && (buttonArray[6].getText().equals("X"))) {
                xWinScreen(2, 4, 6);
            } else if ((buttonArray[0].getText().equals("O")) && (buttonArray[1].getText().equals("O")) && (buttonArray[2].getText().equals("O"))) {
                oWinScreen(0, 1, 2);
            } else if ((buttonArray[3].getText().equals("O")) && (buttonArray[4].getText().equals("O")) && (buttonArray[5].getText().equals("O"))) {
                oWinScreen(3, 4, 5);
            } else if ((buttonArray[6].getText().equals("O")) && (buttonArray[7].getText().equals("O")) && (buttonArray[8].getText().equals("O"))) {
                oWinScreen(6, 7, 8);
            } else if ((buttonArray[0].getText().equals("O")) && (buttonArray[3].getText().equals("O")) && (buttonArray[6].getText().equals("O"))) {
                oWinScreen(0, 3, 6);
            } else if ((buttonArray[1].getText().equals("O")) && (buttonArray[4].getText().equals("O")) && (buttonArray[7].getText().equals("O"))) {
                oWinScreen(1, 4, 7);
            } else if ((buttonArray[2].getText().equals("O")) && (buttonArray[5].getText().equals("O")) && (buttonArray[8].getText().equals("O"))) {
                oWinScreen(2, 5, 8);
            } else if ((buttonArray[0].getText().equals("O")) && (buttonArray[4].getText().equals("O")) && (buttonArray[8].getText().equals("O"))) {
                oWinScreen(0, 4, 8);
            } else if ((buttonArray[2].getText().equals("O")) && (buttonArray[4].getText().equals("O")) && (buttonArray[6].getText().equals("O"))) {
                oWinScreen(2, 4, 6);
            } else if (turnCount == 5) {
                textfield.setText("It's a draw!");
            } else {
                if (ifIsXTurn) {
                    textfield.setText("X's turn");
                } else {
                    textfield.setText("O's turn");
                }
            }
        }
    }
        public static void xWinScreen ( int a, int b, int c){
            buttonArray[a].setBackground(Color.GREEN);
            buttonArray[b].setBackground(Color.GREEN);
            buttonArray[c].setBackground(Color.GREEN);

            for (int i = 0; i < 9; i++) {
                buttonArray[i].setEnabled(false);
            }
            textfield.setText("X WINS");
        }
        public static void oWinScreen ( int a, int b, int c){
            buttonArray[a].setBackground(Color.GREEN);
            buttonArray[b].setBackground(Color.GREEN);
            buttonArray[c].setBackground(Color.GREEN);

            for (int i = 0; i < 9; i++) {
                buttonArray[i].setEnabled(false);
            }
            textfield.setText("O WINS");
        }

    }
}
