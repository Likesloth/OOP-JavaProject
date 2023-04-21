package MyProjectKaraoke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;

public class registerPage extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel titleLabel;

    private JTextField nameTxt;
    private JTextField phoneNumberText;
    private JTextField idTxt;

    private JTextArea RlistTxtArea;

    private JButton addMember;
    private JButton backMainBtn;

    public registerPage() {
        super("Register System"); // call JFrame(String title) constructor

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        titleLabel = new JLabel("Customer Register System");
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        JLabel nameLbl = new JLabel("Name");
        JLabel phoneLbl = new JLabel("Phone Number");
        JLabel idLbl = new JLabel("ID");

        nameTxt = new JTextField(30);
        phoneNumberText = new JTextField(30);
        idTxt = new JTextField(30);
        RlistTxtArea = new JTextArea(10, 30);

        JPanel titlePanel = new JPanel();
        JLabel lbl = new JLabel("Add new Member");
        titlePanel.add(lbl);

        addMember = new JButton("Add Member");
        addMember.addActionListener(this);

        backMainBtn = new JButton("Back to Main Page");
        backMainBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });

        // add event handler code to close this form
        panel = new JPanel();

        panel.setBackground(new Color(213, 227, 189));
        panel.setLayout(new GridLayout(5, 1));

        panel.add(titlePanel);
        JPanel stdPanel = new JPanel();
        stdPanel.setLayout(new GridLayout(3, 2));
        stdPanel.add(nameLbl);
        stdPanel.add(nameTxt);
        stdPanel.add(phoneLbl);
        stdPanel.add(phoneNumberText);
        stdPanel.add(idLbl);
        stdPanel.add(idTxt);
        panel.add(stdPanel);

        JPanel btnPanel = new JPanel();
        btnPanel.add(addMember);
        btnPanel.add(backMainBtn);

        panel.add(btnPanel);

        RlistTxtArea.append("Member List\n");

        // add code to create student array list and show in TextArea
        // MainPage.Rlist = AddCustomerList();
        for (register s : MainPage.Rlist) {
            RlistTxtArea.append(s.printinfo());
        }

        panel.add(RlistTxtArea);

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // add code to insert new student
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Add Member")) {
            String name = nameTxt.getText().trim();
            String phoneNumber = phoneNumberText.getText().trim();
            String id = idTxt.getText().trim();

            if (name.isEmpty() || phoneNumber.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter values for all fields.");
                return;
            }

            try {
                register s = new register(name, phoneNumber, id);
                MainPage.Rlist.add(s);

                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("filename.bin"))) {
                    out.writeObject(MainPage.Rlist);
                } catch (IOException e3) {
                    e3.printStackTrace();
                }

                RlistTxtArea.append(s.printinfo());
                JOptionPane.showMessageDialog(this, "Add Member successfully.");
                clearTextField();

            } catch (InputMismatchException ex) {
                String message = "Wrong input. Please check data type. \nError: " + ex;
                JOptionPane.showMessageDialog(this, message);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Some error occurred. Please contact the admin.\n" + ex);
            }

            try {
                MainPage.addINfile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void clearTextField() {
        idTxt.setText("");
        nameTxt.setText("");
        phoneNumberText.setText("");
    }

    public static void main(String[] args) {
        new registerPage();
    }
}
