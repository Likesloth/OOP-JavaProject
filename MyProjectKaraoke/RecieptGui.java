package MyProjectKaraoke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RecieptGui extends JFrame implements ActionListener{

    private JPanel panel;
    private TextArea ReceiptArea;
    private JLabel titleLabel;

    public RecieptGui(){
        setTitle("Reciept");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 300);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        titleLabel = new JLabel("Karaoke Main Menu");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));
        
        panel = new JPanel(); // Initialize panel
        ReceiptArea = new TextArea(12,40);
        ReceiptArea.append(printRecieptInfo());

        panel.add(titleLabel);
        panel.add(ReceiptArea);

        setVisible(true);
        getContentPane().add(panel,BorderLayout.CENTER);
    }

    private String printRecieptInfo() {
        return("Room: "+RoomBookingGUI.RoomNumber+"\n"+"Name: "+RoomBookingGUI.nameReceipt+"\n"+"total: "+RoomBookingGUI.sumtotal + "\n" + "discount: " + RoomBookingGUI.discount
        + "\n" + "After discount: " + (RoomBookingGUI.sumtotal - RoomBookingGUI.discount));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public static void main(String[] args) {
        new RecieptGui();
    }

}
