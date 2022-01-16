import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    static boolean login(String gebruiker, String geprobeerdeWachtwoord) {
        String url="jdbc:mysql://the-challenge-database.mysql.database.azure.com:3306/the-challenge?useSSL=false";

        Connection conn = null;
        Statement stmt = null;

        String password = null;

        try {
            conn = DriverManager.getConnection(url, "stan", "zs!e3pqiFSxNSLM");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM gebruiker WHERE naam = '" + gebruiker + "'");

            rs.next();

            password = rs.getString("wachtwoord");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (geprobeerdeWachtwoord.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public static void dashboardScherm (String gebruiker) {        
        JFrame frame = new JFrame("AquaPure Dashboard");

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setSize(400, 100);
        
        JLabel lbl_title = new JLabel("AquaPure Dashboard");
        panel.add(lbl_title);

        JLabel lbl_user = new JLabel("Welkom " + gebruiker);
        panel.add(lbl_user);

        frame.add(panel);
        Apparaat apparaat1 = new Apparaat(1, frame);

        frame.setSize(350, 390);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/assets/logo.png"));
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main (String[] args) {         
        UploadSensorData upload = new UploadSensorData();
        
        JFrame frame = new JFrame("AquaPure Login");

        JLabel wrongCombination = new JLabel("Verkeerde email en/of wachtwoord!");
        wrongCombination.setBounds(10, 20, 300, 25);
        wrongCombination.setForeground(Color.RED);;
        wrongCombination.setVisible(false);
        frame.add(wrongCombination);
        
        JLabel lbl_email = new JLabel("Gebruikersnaam:");
        lbl_email.setBounds(10, 50, 200, 25);
        frame.add(lbl_email);

        JTextField usernameEntry = new JTextField();
        usernameEntry.setBounds(10, 80, 80, 25);
        frame.add(usernameEntry);

        JLabel lbl_password = new JLabel("Wachtwoord:");
        lbl_password.setBounds(10, 110, 80, 25);
        lbl_password.setVisible(true);
        frame.add(lbl_password);

        JPasswordField passwordEntry = new JPasswordField();
        passwordEntry.setBounds(10, 140, 80, 25);
        frame.add(passwordEntry);
        
        JButton btn_submit = new JButton("Log In");
        btn_submit.setBounds(10,170,91,26);         
        btn_submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (login(usernameEntry.getText(), new String(passwordEntry.getPassword()))) {
                    frame.dispose();
                    dashboardScherm(usernameEntry.getText());
                } else {
                    wrongCombination.setVisible(true);
                }
            }
        });
        frame.add(btn_submit);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/assets/logo.png"));
        frame.setLayout(null);
        frame.setVisible(true);        
    }
}