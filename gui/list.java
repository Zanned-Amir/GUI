package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class list extends JFrame implements ActionListener {
    JButton menu, valide;
    JTable dataTable ;
    JComboBox sp, cl;

    public list() {
        ImageIcon image = new ImageIcon("C:\\Users\\amir\\IdeaProjects\\ds\\src\\gui\\doggo.jpg");
        this.setIconImage(image.getImage());

        // Right container of data grabbed from the database

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(174, 182, 191));
        panel1.setBounds(250, 100, 700, 400);

        // Create a DefaultTableModel with column names

        String[] columnNames = {"ID", "Nom", "Age", "Spécialité", "Club"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    // Create the JTable using the DefaultTableModel
        dataTable = new JTable(tableModel);

    // Set up any preferences for the JTable (e.g., scrolling)
        JScrollPane scrollPane = new JScrollPane(dataTable);
        panel1.add(scrollPane);

    // Add the panel to the frame
        this.add(panel1);

    // Call the fetchDataFromDatabase method to populate the table
        fetchDataFromDatabase();



        // Left container of options to choose:
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(73, 136, 176));
        panel2.setBounds(20, 100, 200, 400);
        panel2.setLayout(new GridLayout(9, 1));

        // Setting spécialité:
        String[] s = {"NaN", "Im", "Bd", "cm", "av", "jv"};
        JPanel panel3 = new JPanel();
        panel2.add(panel3);
        panel3.setBackground(new Color(174, 182, 191));
        panel3.setBounds(380, 310, 200, 50);
        JLabel spec = new JLabel();
        spec.setText("Spécialité:");
        spec.setHorizontalAlignment(JLabel.LEFT);
        sp = new JComboBox(s);
        panel3.add(spec);
        panel3.add(sp);

        // Setting club
        String[] c = {"NaN", "log", "robotique", "microsoft", "orenda", "chkoba", "jeune ingénieur"};
        JPanel panel4 = new JPanel();
        panel2.add(panel4);
        panel4.setBackground(new Color(174, 182, 191));
        panel4.setBounds(380, 310, 200, 50);
        JLabel cc = new JLabel();
        cc.setText("Club:");
        cl = new JComboBox(c);
        panel4.add(cc);
        panel4.add(cl);

        JPanel panel5 = new JPanel();
        panel5.setBackground(new Color(174, 182, 191));
        panel5.setBounds(250, 100, 700, 400);

        // Setting buttons
        menu = new JButton("menu");
        menu.setBounds(50, 5, 100, 40);
        menu.addActionListener(this);
        panel5.add(menu);
        panel2.add(panel5);

        valide = new JButton("valide");
        valide.setBounds(50, 5, 100, 40);
        valide.addActionListener(this);
        panel5.add(valide);
        panel2.add(panel5);

        valide.addActionListener(this);

        // Setting Frame
        this.setTitle("List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 600);
        this.getContentPane().setBackground(new Color(3, 23, 26));
        this.setLayout(null);
        this.add(panel2);
        this.setVisible(true);
        fetchDataFromDatabase();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            this.dispose();
            new menu();
            System.out.println("Menu button clicked");
        } else if (e.getSource() == valide && !sp.getSelectedItem().equals("NaN") && !cl.getSelectedItem().equals("NaN")) {
            updateTableOp();
        }
    }
   // fetching all data
    private void fetchDataFromDatabase() {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "53064271";

        try {
            // Establishing a connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Creating a statement
            Statement statement = connection.createStatement();

            // Executing a query to retrieve data (replace "info" with your actual table name)
            String query = "SELECT id, nom, age, specialite, club FROM info";
            ResultSet resultSet = statement.executeQuery(query);

            // Get the table model to add rows
            DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Populate the table with data
            while (resultSet.next()) {
                // Replace column names with actual column names
                Object[] rowData = {
                        resultSet.getString("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("age"),
                        resultSet.getString("specialite"),
                        resultSet.getString("club")
                };
                tableModel.addRow(rowData);
            }

            // Closing resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // fetching data that  have been chosen by gui.list
    public void updateTableOp() {
        // Fetch data from the database based on selected values in JComboBox components
        String selectedSpecialite = (String) sp.getSelectedItem();
        String selectedClub = (String) cl.getSelectedItem();

        // Replace these placeholder values with your actual database information
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "amir";
        String password = "53064271";

        String query = "SELECT id, nom, age, specialite, club FROM info WHERE specialite = ? AND club = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement stm = connection.prepareStatement(query)) {

            stm.setString(1, selectedSpecialite);
            stm.setString(2, selectedClub);

            try (ResultSet resultSet = stm.executeQuery()) {
                // Populate the JTable with data from the ResultSet
                DefaultTableModel tableModel = (DefaultTableModel) dataTable.getModel();
                tableModel.setRowCount(0); // Clear existing rows
                Object[] rowData = new Object[0];
                while (resultSet.next()) {
                    rowData = new Object[]{
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getInt("age"),
                            resultSet.getString("specialite"),
                            resultSet.getString("club")
                    };
                    tableModel.addRow(rowData);
                }
                System.out.println("Data updated successfully");
            }

        } catch (SQLException exception) {
            System.err.println("Error executing SQL query: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}