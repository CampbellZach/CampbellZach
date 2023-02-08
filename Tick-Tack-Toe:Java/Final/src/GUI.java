import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {
    boolean ifIsXTurn = true;
    int turnCount = 0;
    JFrame frame = new JFrame();
    JPanel title = new JPanel();
    JPanel gridOfButtons = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] arrayOfButtons = new JButton[9];

    public void winConditions(){
        if((arrayOfButtons[0].getText().equals("X")) && (arrayOfButtons[1].getText().equals("X")) && (arrayOfButtons[2].getText().equals("X"))){
            xWinScreen(0,1,2);
        }
        else if((arrayOfButtons[3].getText().equals("X")) && (arrayOfButtons[4].getText().equals("X")) && (arrayOfButtons[5].getText().equals("X"))){
            xWinScreen(3,4,5);
        }
        else if((arrayOfButtons[6].getText().equals("X")) && (arrayOfButtons[7].getText().equals("X")) && (arrayOfButtons[8].getText().equals("X"))){
            xWinScreen(6,7,8);
        }
        else if((arrayOfButtons[0].getText().equals("X")) && (arrayOfButtons[3].getText().equals("X")) && (arrayOfButtons[6].getText().equals("X"))){
            xWinScreen(0,3,6);
        }
        else if((arrayOfButtons[1].getText().equals("X")) && (arrayOfButtons[4].getText().equals("X")) && (arrayOfButtons[7].getText().equals("X"))){
            xWinScreen(1,4,7);
        }
        else if((arrayOfButtons[2].getText().equals("X")) && (arrayOfButtons[5].getText().equals("X")) && (arrayOfButtons[8].getText().equals("X"))){
            xWinScreen(2,5,8);
        }
        else if((arrayOfButtons[0].getText().equals("X")) && (arrayOfButtons[4].getText().equals("X")) && (arrayOfButtons[8].getText().equals("X"))){
            xWinScreen(0,4,8);
        }
        else if((arrayOfButtons[2].getText().equals("X")) && (arrayOfButtons[4].getText().equals("X")) && (arrayOfButtons[6].getText().equals("X"))){
            xWinScreen(2,4,6);
        }
        else if((arrayOfButtons[0].getText().equals("O")) && (arrayOfButtons[1].getText().equals("O")) && (arrayOfButtons[2].getText().equals("O"))){
            oWinScreen(0,1,2);
        }
        else if((arrayOfButtons[3].getText().equals("O")) && (arrayOfButtons[4].getText().equals("O")) && (arrayOfButtons[5].getText().equals("O"))){
            oWinScreen(3,4,5);
        }
        else if((arrayOfButtons[6].getText().equals("O")) && (arrayOfButtons[7].getText().equals("O")) && (arrayOfButtons[8].getText().equals("O"))){
            oWinScreen(6,7,8);
        }
        else if((arrayOfButtons[0].getText().equals("O")) && (arrayOfButtons[3].getText().equals("O")) && (arrayOfButtons[6].getText().equals("O"))){
            oWinScreen(0,3,6);
        }
        else if((arrayOfButtons[1].getText().equals("O")) && (arrayOfButtons[4].getText().equals("O")) && (arrayOfButtons[7].getText().equals("O"))){
            oWinScreen(1,4,7);
        }
        else if((arrayOfButtons[2].getText().equals("O")) && (arrayOfButtons[5].getText().equals("O")) && (arrayOfButtons[8].getText().equals("O"))){
            oWinScreen(2,5,8);
        }
        else if((arrayOfButtons[0].getText().equals("O")) && (arrayOfButtons[4].getText().equals("O")) && (arrayOfButtons[8].getText().equals("O"))){
            oWinScreen(0,4,8);
        }
        else if((arrayOfButtons[2].getText().equals("O")) && (arrayOfButtons[4].getText().equals("O")) && (arrayOfButtons[6].getText().equals("O"))){
            oWinScreen(2,4,6);
        }
        else if(turnCount ==5){
            textfield.setText("You both Suck!");
        }
        else{
            if(ifIsXTurn){
                textfield.setText("X Make your Move");
            }
            else{
                textfield.setText("O Make your Move");
            }
        }
    }

    public void xWinScreen ( int a, int b, int c){
        arrayOfButtons[a].setBackground(Color.RED);
        arrayOfButtons[b].setBackground(Color.RED);
        arrayOfButtons[c].setBackground(Color.RED);

        for (int i = 0; i < 9; i++) {
            arrayOfButtons[i].setEnabled(false);
        }
        textfield.setText("X WINS");
    }
    public void oWinScreen ( int a, int b, int c){
        arrayOfButtons[a].setBackground(Color.RED);
        arrayOfButtons[b].setBackground(Color.RED);
        arrayOfButtons[c].setBackground(Color.RED);

        for (int i = 0; i < 9; i++) {
            arrayOfButtons[i].setEnabled(false);
        }
        textfield.setText("O WINS");
    }
    GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        textfield.setFont(new Font("Comic Sans", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setOpaque(true);
        title.setLayout(new BorderLayout());
        title.setBounds(0, 0, 800, 100);
        gridOfButtons.setLayout(new GridLayout(3, 3));
        gridOfButtons.setBackground(new Color(15, 150, 150));

        for (int i = 0; i < 9; i++) {
            arrayOfButtons[i] = new JButton();
            gridOfButtons.add(arrayOfButtons[i]);
            arrayOfButtons[i].setFont(new Font("Comic Sans", Font.BOLD, 120));
            arrayOfButtons[i].setFocusable(false);
            arrayOfButtons[i].addActionListener(this);
        }

        title.add(textfield);
        textfield.setText("X's turn");
        frame.add(title, BorderLayout.NORTH);
        frame.add(gridOfButtons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++){
            if(e.getSource()==arrayOfButtons[i]){
                if(ifIsXTurn){
                    textfield.setText("X turn");
                    if(arrayOfButtons[i].getText().equals("")){
                        arrayOfButtons[i].setForeground(new Color(100,0,100));
                        arrayOfButtons[i].setText("X");
                        turnCount++;
                        ifIsXTurn=false;
                        textfield.setText("O turn");
                        winConditions();

                    }
                }
                else{
                    textfield.setText("O turn");
                    if(arrayOfButtons[i].getText().equals("")){
                        arrayOfButtons[i].setForeground(new Color(0,0,0));
                        arrayOfButtons[i].setText("O");
                        ifIsXTurn=true;
                        textfield.setText("X turn");
                        winConditions();
                    }
                }
            }
        }
    }
}