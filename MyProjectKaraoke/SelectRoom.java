package MyProjectKaraoke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectRoom extends JFrame {

    JPanel northPanel;
    JLabel selectLabel;
    JPanel cenPanel;
    private JButton[][] roomButtons;
    JButton backToMainBtn;
    JPanel btnPanel;
    public static int roomCheck;
    

    public SelectRoom() {
        roomCheck = -1;
        setTitle("Room Booking");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLayout(new GridLayout(2, 3));

        northPanel = new JPanel();

        selectLabel = new JLabel("Select Room System");
        selectLabel.setForeground(Color.BLUE);
        selectLabel.setFont(new Font("", Font.BOLD, 20));

        northPanel.add(selectLabel);

        cenPanel = new JPanel();
        cenPanel.setLayout(new GridLayout(2, 3));

        //create Roombutton 2*2
        roomButtons = new JButton[2][2];
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                roomButtons[row][col] = new JButton("Room " + (row * 2 + col + 1));
                roomButtons[row][col].addActionListener(new RoomButtonListener(row, col));
                cenPanel.add(roomButtons[row][col]);
            }
        }

        btnPanel = new JPanel();

        backToMainBtn = new JButton("Back to Main Page");
        backToMainBtn.setLayout(new FlowLayout());
        backToMainBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });

        btnPanel.add(backToMainBtn);
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(cenPanel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private class RoomButtonListener implements ActionListener {
        private int row, col;

        public RoomButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            // Check if the source of the event is the button that was clicked
            // if (e.getSource() == roomButtons[row][col]) {
            // RoomBookingGUI bookingGUI = new RoomBookingGUI();
            // bookingGUI.setVisible(true);
            // }
            if (e.getActionCommand().equals("Room 1")) {
                roomCheck = 1;
                RoomBookingGUI bookingGUI = new RoomBookingGUI();
                bookingGUI.setVisible(true);

            }
            if (e.getActionCommand().equals("Room 2")) {
                roomCheck = 2;
                RoomBookingGUI bookingGUI = new RoomBookingGUI();
                bookingGUI.setVisible(true);
            }
            if (e.getActionCommand().equals("Room 3")) {
                roomCheck = 3;
                RoomBookingGUI bookingGUI = new RoomBookingGUI();
                bookingGUI.setVisible(true);

            }
            if (e.getActionCommand().equals("Room 4")) {
                roomCheck = 4;
                RoomBookingGUI bookingGUI = new RoomBookingGUI();
                bookingGUI.setVisible(true);
            }
        }

    }

    public static void main(String[] args) {
        new SelectRoom();
    }
}
