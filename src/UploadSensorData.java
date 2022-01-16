import java.util.*;
import java.util.Timer;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.io.*;

public class UploadSensorData {
    
    UploadSensorData() {
        Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    File data = getLatestFile();

                    double sensor1 = 0;
                    double sensor2 = 0;
                    double sensor3 = 0;
                    double sensor4 = 0;

                    try {
                        sensor1 = getValue(data, 1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        sensor2 = getValue(data, 2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        sensor3 = getValue(data, 3);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        sensor4 = getValue(data, 4);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    
                    String url="jdbc:mysql://the-challenge-database.mysql.database.azure.com:3306/the-challenge?useSSL=false";

                    Connection conn = null;
                    Statement stmt = null;

                    try {
                        conn = DriverManager.getConnection(url, "stan", "zs!e3pqiFSxNSLM");

                        stmt = conn.createStatement();
                        stmt.executeUpdate("UPDATE resultaat SET sensor1 = " + sensor1 + ", sensor2 = " + sensor2 + ", sensor3 = "+ sensor3 + ", sensor4 = " + sensor4 + " WHERE idapparaat = 1;");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }, 0, 5000);
    }
    
    static File getLatestFile() {
        File dir = new File("sensordata");
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            return null;
        }
    
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
           if (lastModifiedFile.lastModified() < files[i].lastModified()) {
               lastModifiedFile = files[i];
           }
        }
        return lastModifiedFile;
    }

    static double getValue(File data, int sensor) throws FileNotFoundException {      
        try (Scanner scanner = new Scanner(data)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                if (Character.getNumericValue(line.charAt(3)) == sensor) {
                    System.out.printf("UPDATED %d TO %f%n", sensor, Double.parseDouble(line.substring(5)));
                    return Double.parseDouble(line.substring(5));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
