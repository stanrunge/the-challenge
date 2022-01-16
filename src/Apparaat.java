import java.util.*;
import java.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Apparaat {
    int apparaatID;
    String apparaatNaam;
    double temperatuur;
    double ph;
    double geleidbaarheid;
    double troebelheid;
    String status = "";
    
    Apparaat(int apparaatID, JFrame frame) {
        this.apparaatID = apparaatID;

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBounds(0, 100, 350, 250);

        if (status.equals("OK")) {
            panel.setBackground(Color.decode("#9dff73"));
        } else {
            panel.setBackground(Color.decode("#ff5757"));
        }

        JLabel lbl_device = new JLabel(apparaatNaam);
        panel.add(lbl_device);

        JLabel lbl_status = new JLabel(status);
        panel.add(lbl_status);

        JLabel lbl_reading1_desc = new JLabel("Temperatuur:");
        panel.add(lbl_reading1_desc);
        
        JLabel lbl_reading1 = new JLabel(Double.toString(temperatuur));
        panel.add(lbl_reading1);

        JLabel lbl_reading2_desc = new JLabel("pH:");
        panel.add(lbl_reading2_desc);

        JLabel lbl_reading2 = new JLabel(Double.toString(ph));
        panel.add(lbl_reading2);

        JLabel lbl_reading3_desc = new JLabel("Geleidbaarheid:");
        panel.add(lbl_reading3_desc);

        JLabel lbl_reading3 = new JLabel(Double.toString(geleidbaarheid));
        panel.add(lbl_reading3);

        JLabel lbl_reading4_desc = new JLabel("Troebelheid:");
        panel.add(lbl_reading4_desc);

        JLabel lbl_reading4 = new JLabel(Double.toString(troebelheid));
        panel.add(lbl_reading4);
        
        frame.getContentPane().add(panel);
        
        Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                    String url="jdbc:mysql://the-challenge-database.mysql.database.azure.com:3306/the-challenge?useSSL=false";

                    Connection conn = null;
                    Statement stmt = null;

                    try {
                        conn = DriverManager.getConnection(url, "stan", "zs!e3pqiFSxNSLM");

                        stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM gebruiker JOIN apparaat ON gebruiker.idgebruiker = apparaat.idgebruiker JOIN resultaat ON apparaat.idapparaat = resultaat.idapparaat WHERE apparaat.idapparaat = " + apparaatID);

                        rs.next();

                        lbl_device.setText(rs.getString("apparaat.naam"));;

                        lbl_reading1.setText(Double.toString(Double.parseDouble(rs.getString("sensor1")) * 0.1));
                        lbl_reading2.setText(Double.toString(Double.parseDouble(rs.getString("sensor2")) * 0.01));
                        lbl_reading3.setText(Double.toString(Double.parseDouble(rs.getString("sensor3")) * 0.005));
                        lbl_reading4.setText(Double.toString(Double.parseDouble(rs.getString("sensor4")) * 0.001));

                        temperatuur = Double.parseDouble(rs.getString("sensor1")) * 0.1;
                        ph = Double.parseDouble(rs.getString("sensor2")) * 0.01;
                        geleidbaarheid = Double.parseDouble(rs.getString("sensor3")) * 0.005;
                        troebelheid = Double.parseDouble(rs.getString("sensor4")) * 0.001;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                
                    if(checkOK()) {
                        status = "OK";
                        lbl_status.setText("OK");
                        panel.setBackground(Color.decode("#9dff73"));
                    } else {
                        status = "ERROR!";
                        lbl_status.setText("ERROR!");
                        panel.setBackground(Color.decode("#ff5757"));
                    }

                }}, 0, 5000);


    }
    
    boolean checkOK() {
        if (temperatuur <= 32 && 6.5 <= ph && ph <= 8.5 && 0.5 <= geleidbaarheid && geleidbaarheid <= 3 && troebelheid <= 1) {
            return true;
        } else {
            return false;
        }
    }

    void updateInfo() {
        String url="jdbc:mysql://the-challenge-database.mysql.database.azure.com:3306/the-challenge?useSSL=false";

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url, "stan", "zs!e3pqiFSxNSLM");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM gebruiker JOIN apparaat ON gebruiker.idgebruiker = apparaat.idgebruiker JOIN resultaat ON apparaat.idapparaat = resultaat.idapparaat WHERE apparaat.idapparaat = " + apparaatID);

            rs.next();

            temperatuur = Double.parseDouble(rs.getString("sensor1")) * 0.1;
            ph = Double.parseDouble(rs.getString("sensor2")) * 0.01;
            geleidbaarheid = Double.parseDouble(rs.getString("sensor3")) * 0.005;
            troebelheid = Double.parseDouble(rs.getString("sensor4")) * 0.001;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(checkOK()) {
            status = "OK";
        } else {
            status = "ERROR!";
        }

        System.out.printf("ApparaatID: %d Temperatuur: %f pH: %f Geleidbaarheid: %f Troebelheid: %f %n", apparaatID, temperatuur, ph, geleidbaarheid, troebelheid);
    }
}
