import java.sql.*;
import java.util.*;
import java.awt.*;

public class Main {
    
    boolean Login(String gebruiker, String geprobeerdeWachtwoord) {
        String url="jdbc:mysql://the-challenge-database.mysql.database.azure.com:3306/the-challenge?useSSL=false";

        Connection conn = null;
        Statement stmt = null;

        String password = null;

        try {
            conn = DriverManager.getConnection(url, "stan", "zs!e3pqiFSxNSLM");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM gebruiker WHERE naam = " + gebruiker);

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

    public static void main (String[] args) {         
        Apparaat apparaat1 = new Apparaat(1);
        apparaat1.main(args);

        
    }
}

class Apparaat {
    int apparaatID;
    String apparaatNaam;
    double temperatuur;
    double ph;
    double geleidbaarheid;
    double troebelheid;
    String status;

    boolean checkOK() {
        if (0 <= temperatuur && temperatuur <= 30 && 0 <= ph && ph <= 30 && 0 <= geleidbaarheid && geleidbaarheid <= 30 && 0 <= troebelheid && troebelheid <= 30) {
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
            ph = Double.parseDouble(rs.getString("sensor2")) * 0.015;
            geleidbaarheid = Double.parseDouble(rs.getString("sensor3"));
            troebelheid = Double.parseDouble(rs.getString("sensor4"));

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
    
    Apparaat(int apparaatID) {
        this.apparaatID = apparaatID;
    }

    void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               updateInfo();
            }}, 0, 5000);
    }

}