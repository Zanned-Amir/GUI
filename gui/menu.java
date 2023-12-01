package gui;

import gui.list;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu extends JFrame implements ActionListener{
    JButton Admin;
    JButton User;
    menu() {
        // setting the  panel1 :  grey

        JPanel panel1 =new JPanel();
        panel1.setBackground(new Color(174, 182, 191));
        panel1.setBounds(250,150,500,281);

        // setting the panel2 : blue

        JPanel panel2 =new JPanel();
        panel2.setBackground(new Color(52, 152, 219));
        panel2.setBounds(0,0,1000,100);

        //setting the panel3 : accueil label

        JPanel panel3 =new JPanel();
        panel3.setBackground(new Color(212, 244, 255));
        panel3.setBounds(430,20,100,50);

        //setting the panel4 : button

        JPanel panel4 =new JPanel();
        panel4.setBackground(new Color(174, 182, 191));
        panel4.setBounds(380,250,250,50);


        // adding button to lable 4 :

         Admin = new JButton("Admin");
        Admin.setBounds(50, 5, 100, 40);

         User = new JButton("User");
        User.setBounds(150, 5, 100, 40);

        Admin.addActionListener(this);
        User.addActionListener(this);


        // setting the label

        JLabel label = new JLabel();
        label.setText("Accueil" );
        label.setForeground(new Color(228, 157, 34));
        label.setFont(new Font("TimesRoman",Font.PLAIN,20));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER );

        // setting icon image

        ImageIcon image = new ImageIcon("C:\\Users\\amir\\IdeaProjects\\ds\\src\\doggo.jpg");
        this.setIconImage(image.getImage());

        //setting Frame

        this.setTitle("gui.menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000,600);
        this.getContentPane().setBackground(new Color(33,113,163));
        this.setLayout(null);
        this.setVisible(true);

        this.add(panel4);
        panel4.add(Admin);
        panel4.add(User);
        this.add(panel1);
        this.add(panel3);
        this.add(panel2);
        panel3.add(label);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Admin){
            this.dispose();
            list l=new list();
            System.out.println("Admin button clicked");}
        else if (e.getSource() == User) {
            this.dispose();
            user u = new user();
            System.out.println("User button clicked");
        }
    }


}
