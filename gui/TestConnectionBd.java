package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class TestConnectionBd {
    TestConnectionBd() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Replace the connection URL, username, and password with your MySQL details
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "53064271")) {
                JOptionPane.showMessageDialog (null,"Connection established","Database Status",JOptionPane.PLAIN_MESSAGE );
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog (null,"Error connecting to the database","Database Status", ERROR_MESSAGE );
            e.printStackTrace();
        }
    }

}
