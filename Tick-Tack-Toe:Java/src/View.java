import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

public class View implements ActionListener{

    static JFrame jFrame = new JFrame();
    static JButton[] buttonArray = new JButton[9];
    static boolean ifIsXTurn;
    static JTextField textfield = new JTextField();
    static int turnCount = 0;
    static JLabel grid = new JLabel();
    static JPanel top = new JPanel();


    View() {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800, 800);
        jFrame.getContentPane().setBackground(new Color(50,50,50));
        jFrame.setLayout(new BorderLayout());
        jFrame.setVisible(true);
        textfield.setFont(new Font("Comic Sans", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setOpaque(true);
        top.setLayout(new BorderLayout());
        top.setBounds(0, 0, 800, 100);
        grid.setLayout(new GridLayout(3, 3));
        grid.setBackground(new Color(15, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttonArray[i] = new JButton();
            grid.add(buttonArray[i]);
            buttonArray[i].setFont(new Font("Comic Sans", Font.BOLD, 120));
            buttonArray[i].setFocusable(false);
            buttonArray[i].addActionListener(this);
        }

        top.add(textfield);
        textfield.setText("X's turn");
        jFrame.add(top, BorderLayout.NORTH);
        jFrame.add(grid);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++){
            if(e.getSource()==buttonArray[i]){
                if(ifIsXTurn){
                    textfield.setText("X turn");
                    if(buttonArray[i].getText().equals("")){
                        buttonArray[i].setForeground(new Color(100,0,100));
                        buttonArray[i].setText("X");
                        turnCount++;
                        ifIsXTurn=false;
                        textfield.setText("O turn");
                        //winConditions();

                    }
                }
                else{
                    textfield.setText("O turn");
                    if(buttonArray[i].getText().equals("")){
                        buttonArray[i].setForeground(new Color(0,0,0));
                        buttonArray[i].setText("O");
                        ifIsXTurn=true;
                        textfield.setText("X turn");
                        //winConditions();
                    }
                }
            }
        }
    }

}
