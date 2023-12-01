package gui;

import gui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class user extends JFrame implements ActionListener {
    JButton menu, inscrit, Annuler;
    JTextField In1, In2;
    JComboBox cl;
    JComboBox sp;

    user() {
        //setting name field

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(174, 182, 191));
        panel1.setBounds(380, 250, 200, 50);
        JLabel name = new JLabel();
        name.setText("Nom:");
        In1 = new JTextField();
        In1.setPreferredSize(new Dimension(240, 40));
        panel1.add(name);
        panel1.add(In1);

        //setting Age field

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(174, 182, 191));
        panel2.setBounds(380, 310, 200, 50);
        JLabel age = new JLabel();
        age.setText("Age:");
        In2 = new JTextField();
        In2.setPreferredSize(new Dimension(240, 40));
        panel2.add(age);
        panel2.add(In2);

        //setting  spécialité

        String[] s = {"NaN", "Im", "Bd", "cm", "av", "jv"};
        JPanel panel3 = new JPanel();
        panel3.setBackground(new Color(174, 182, 191));
        panel3.setBounds(380, 310, 200, 50);
        JLabel spec = new JLabel();
        spec.setText("Spécialité:");
        sp = new JComboBox(s);

        panel3.add(spec);
        panel3.add(sp);

        //setting club

        String[] c = {"NaN", "log", "robotique", "microsoft", "orenda", "chkoba", "jeune ingénieur"};
        JPanel panel4 = new JPanel();
        panel4.setBackground(new Color(174, 182, 191));
        panel4.setBounds(380, 310, 200, 50);
        JLabel cc = new JLabel();
        cc.setText("Club:");
        cl = new JComboBox(c);

        panel3.add(cc);
        panel3.add(cl);

        // setting button
        menu = new JButton("gui.menu");
        menu.setBounds(50, 5, 100, 40);

        inscrit = new JButton("Inscrit");
        inscrit.setBounds(50, 5, 100, 40);

        Annuler = new JButton("Annuler");
        Annuler.setBounds(150, 5, 100, 40);

        Annuler.addActionListener(this);
        inscrit.addActionListener(this);

        panel4.add(inscrit);
        panel4.add(Annuler);

        JPanel panel5 = new JPanel();
        panel5.setBackground(new Color(174, 182, 191));
        panel5.setBounds(380, 310, 200, 50);

        panel5.add(menu);
        menu.addActionListener(this);

        //setting container  for all content

        JPanel panelf = new JPanel();
        panelf.setBackground(new Color(174, 182, 191));
        panelf.setBounds(300, 0, 400, 1000);
        panelf.setLayout(new GridLayout(9, 1));
        panelf.add(panel1);
        panelf.add(panel2);
        panelf.add(panel3);
        panelf.add(panel4);
        panelf.add(panel5);
        ImageIcon image = new ImageIcon("C:\\Users\\amir\\IdeaProjects\\ds\\src\\doggo.jpg");
        this.setIconImage(image.getImage());

        //setting Frame
        this.setTitle("Inscription");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 600);
        this.getContentPane().setBackground(new Color(33, 113, 163));
        this.setLayout(null);
        this.setVisible(true);
        this.add(panelf);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            this.dispose();
            new menu();
            System.out.println("gui.menu button clicked");

        }
        else if (e.getSource() == inscrit) {
            if (In1.getText().isEmpty() || In2.getText().isEmpty() || !containsOnlyNumbers(In2.getText()) || cl.getSelectedItem().equals("NaN") || sp.getSelectedItem().equals("NaN")) {
                JOptionPane.showMessageDialog(null, "Input invalid", "Inscrit Status", ERROR_MESSAGE);
            } else if (Integer.parseInt(In2.getText()) > 100 || Integer.parseInt(In2.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Invalid age", "Inscrit Status", ERROR_MESSAGE);
            } else {
                insertData(In1.getText(), Integer.parseInt(In2.getText()), sp.getSelectedItem().toString(), cl.getSelectedItem().toString());
            }
        }

        if (e.getSource() == Annuler)
        {
            System.out.println("Annuler Selected");
            cl.setSelectedIndex(0);
            sp.setSelectedIndex(0);
            In1.setText("");
            In2.setText("");

        }

    }

    public void insertData(String nom, int age, String specialite, String club) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "53064271");

            String q = "INSERT INTO info (nom, age, specialite, club) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(q);
            stm.setString(1, nom);
            stm.setInt(2, age);
            stm.setString(3, specialite);
            stm.setString(4, club);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data inserted successfully", "Database Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting data into the database", "Database Status", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }


    }

    public static boolean containsOnlyNumbers(String text) {
        // Define a regular expression pattern for numbers
        String numberPattern = "\\d+";

        // Check if the input string matches the number pattern
        return Pattern.matches(numberPattern, text);
    }
}
