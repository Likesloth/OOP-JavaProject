package MyProjectKaraoke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainPage extends JFrame implements ActionListener {
    public static ArrayList<register> Rlist = new ArrayList<>();
    public static ArrayList<AllRoom> Alist = new ArrayList<>();
    private JPanel panel;
    private JLabel titleLabel;
    private JButton BookingRoomButton;
    private JButton RegisterSystem;
    private JButton exitButton;
    private JMenuBar menuBar;
    private JMenu menu1;
    private JMenuItem selectRoomMenu, RegisterMenu, ExitMenu;

    public MainPage() {
        setTitle("Karaoke Information System");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // test clicking the close button on window
        setSize(300, 300);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // frame = new JFrame("Karaoke Information System");
        menuBar = new JMenuBar();

        //create menu object
        menu1 = new JMenu("Menu");

        //add menu to menubar
        menuBar.add(menu1);

        selectRoomMenu = new JMenuItem("Booking Room");
        selectRoomMenu.addActionListener(this);
        RegisterMenu = new JMenuItem("Register");
        RegisterMenu.addActionListener(this);
        ExitMenu = new JMenuItem("Exit");
        ExitMenu.addActionListener(this);

        menu1.add(selectRoomMenu);
        menu1.add(RegisterMenu);
        menu1.add(ExitMenu);


        titleLabel = new JLabel("Karaoke Main Menu");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        RegisterSystem = new JButton("Register System");
        RegisterSystem.addActionListener(this);

        BookingRoomButton = new JButton("Booking Room");

        BookingRoomButton.addActionListener(this);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this); // register event handler to exitButton

        setJMenuBar(menuBar);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(BookingRoomButton);
        panel.add(RegisterSystem);
        panel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);


        MainPage.Rlist = AddCustomerList();

        setVisible(true);

    }

    public ArrayList<register> AddCustomerList() {
        MainPage.Rlist.add(new register("Thana", "0936666666", "100"));
        MainPage.Rlist.add(new register("Peter", "0891243422", "101"));
        MainPage.Rlist.add(new register("Phoenix", "0945129112", "102"));
        AddRoom();
        return MainPage.Rlist;
    }

    public void AddRoom() {
        Alist.add(new AllRoom(1, "13.00-14.00", "Peter"));
        Alist.add(new AllRoom(1, "14.00-15.00", "Peter"));
        Alist.add(new AllRoom(1, "15.00-16.00", "Peter"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (e.getSource() == BookingRoomButton) {
            new SelectRoom();
        } else if (e.getSource() == RegisterSystem) {
            new registerPage();
        }

        else if (actionCommand.equals("Exit")) {
            JOptionPane.showConfirmDialog(this, "Do you want to exit the program?", "Member Information System",
                    JOptionPane.OK_CANCEL_OPTION);
            System.exit(0);
        }

        if (e.getSource() == selectRoomMenu) {
            new SelectRoom();
        } else if (e.getSource() == RegisterMenu) {
            new registerPage();
        }


    }
    public static void addINfile() throws IOException {
        FileWriter writer = new FileWriter("Member.txt");
         for (register member : Rlist) {
            writer.write("  Name :"+member.name +"  Phone Number :" + member.phonenum +" Member ID : " + member.id + "\n");
         }
         writer.close();
    }
    public static void addtobinary() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("filename.bin"))) {
            out.writeObject(Rlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void saveArrayListToBinary(ArrayList<register> list, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        }
    }

    public static void main(String[] args) throws IOException {
        new MainPage();
        addINfile();
        addtobinary();
    }
    
}