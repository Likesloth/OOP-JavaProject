package MyProjectKaraoke;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomBookingGUI extends JFrame implements ActionListener {
    private JTable bookingTable;
    private JButton bookButton;
    private JButton cancelButton;
    private JButton backButton;
    private JButton receiptButton;
    private JTextField nameField;
    private List<Integer> selectedRows = new ArrayList<>();
    private JLabel timeLabel;
    public static int RoomNumber;
    public static String nameReceipt;
    public static Double sumtotal;
    public static Double discount;
    String lastedName = "";
    public static String name;
    public String[] timeSlots = { "13.00-14.00", "14.00-15.00", "15.00-16.00", "16.00-17.00", "17.00-18.00",
            "18.00-19.00", "19.00-20.00", "20.00-21.00", "21.00-22.00", "22.00-23.00" };

    public RoomBookingGUI() {
        super("Room Booking Schedule");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);

        RoomNumber = SelectRoom.roomCheck;

        // Create table model and add columns
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Time Slot");
        tableModel.addColumn("Booked by");

        for (String timeSlot : timeSlots) {
            tableModel.addRow(new Object[] { timeSlot, FindName(RoomNumber, timeSlot), "" });

        }

        // Create table and add to frame
        bookingTable = new JTable(tableModel);
        bookingTable.getSelectionModel().addListSelectionListener(e -> {
            selectedRows.clear();
            int[] selected = bookingTable.getSelectedRows();
            for (int i : selected) {
                selectedRows.add(i);

            }
        });

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create booking button and add to frame
        bookButton = new JButton("Book Room");
        bookButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create cancel button and add to frame
        cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        // Create back button and add to frame
        backButton = new JButton("Back to Select Room");
        backButton.addActionListener(e -> {
            // setToAlist();
            this.dispose(); // close the current frame
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        buttonPanel.add(backButtonPanel, BorderLayout.NORTH);

        // Add realtime
        timeLabel = new JLabel();
        buttonPanel.add(timeLabel, BorderLayout.WEST);

        // Create name field and add to frame
        nameField = new JTextField(10);
        nameField.setLayout(new GridLayout(2, 1));

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name: "));
        namePanel.add(nameField);
        // add(namePanel, BorderLayout.NORTH);

        receiptButton = new JButton("Receipt");
        receiptButton.addActionListener(this);

        JPanel receiptPanel = new JPanel();
        receiptPanel.add(namePanel);
        receiptPanel.add(receiptButton);
        add(receiptPanel, BorderLayout.NORTH);

        setVisible(true);

        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {
        updateTime();
        if (e.getSource() == bookButton && selectedRows.size() > 0) {
            // Check if any of the selected time slots are already booked
            for (int selectedRow : selectedRows) {
                String bookedBy = (String) bookingTable.getValueAt(selectedRow, 1);
                if (!bookedBy.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "This time slot is already booked.");
                    return;
                }
            }
        }

        if (e.getSource() == bookButton && selectedRows.size() == 1) {
            // book selected time slot
            int selectedRow = selectedRows.get(0);
            name = nameField.getText();
            bookingTable.setValueAt(name, selectedRow, 1);
            setToAlist();
            bookingTable.repaint();
            JOptionPane.showMessageDialog(this, "Room booked successfully!");

        }

        if (e.getSource() == bookButton && selectedRows.size() > 1) {
            name = nameField.getText();
            for (int selectedRow : selectedRows) {
                bookingTable.setValueAt(name, selectedRow, 1);
            }
            setToAlist();
            JOptionPane.showMessageDialog(this, "Room booked successfully!");
        }

        if (selectedRows.size() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one time slot.");
        }

        if (e.getSource() == cancelButton) {
            deleteBooking();
        }

        if (e.getSource() == receiptButton) {
            nameReceipt = JOptionPane.showInputDialog(this, "Enter name: ", "");

            if (nameReceipt == null) { // user clicked "Cancel"
                return; // exit the method
            }
            sumtotal = 0.0;
            discount = 0.0;
            for (AllRoom re : MainPage.Alist) {
                if (re.name.equals(nameReceipt) && re.Room == RoomNumber) {
                    if (re.Room == 1 || re.Room == 2) {
                        sumtotal += 500.0;
                    }

                    else if (re.Room == 3 || re.Room == 4) {
                        sumtotal += 1000.0;
                    }

                }

            }
            for (register member : MainPage.Rlist) {
                if (member.name.equals(nameReceipt)) {

                    discount = sumtotal * (0.1);
                }

            }
           new RecieptGui();
        }
        
    }

    
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        timeLabel.setText(time);
    }

    //set name at booked by colum to Alist
    public void setToAlist() {
        for (int i = 0; i < 10; i++) {
            String timeSlot = (String) bookingTable.getValueAt(i, 0);
            String bookedBy = (String) bookingTable.getValueAt(i, 1);
            if (bookedBy.equals(name)) {

                MainPage.Alist.add(new AllRoom(RoomNumber, timeSlot, bookedBy));
            }
        }
    }


    // Define a method to delete a booking
    public void deleteBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            // No row is selected
            JOptionPane.showMessageDialog(this, "Please select a row to cancel.");
        } else {
            // Get the selected booking's time and customer name
            String time = bookingTable.getValueAt(selectedRow, 0).toString();
            String name = bookingTable.getValueAt(selectedRow, 1).toString();
            // Show a confirmation message
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel the booking for " + name + " at " + time + "?",
                    "Confirm Cancel Booking", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Delete only the customer name from the selected row
                bookingTable.setValueAt("", selectedRow, 1);
                // Optionally, you can clear the selection after deleting the booking
                bookingTable.clearSelection();
                // Remove the corresponding data from the ArrayList
                for (int i = 0; i < MainPage.Alist.size(); i++) {
                    if (MainPage.Alist.get(i).Room == RoomNumber && MainPage.Alist.get(i).time.equals(time)
                            && MainPage.Alist.get(i).name.equals(name)) {
                        MainPage.Alist.remove(i);
                        break;
                    }
                }
            }
        }
    }

    //Find name in Alist 
    public String FindName(int num, String time) {
        for (AllRoom usename : MainPage.Alist) {
            if (usename.Room == num) {
                if (usename.time.equals(time)) {
                    return usename.name;
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        new RoomBookingGUI();
    }
}
