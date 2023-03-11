/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package museumheistsd;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Pedro1
 */
public class MuseumHeistSD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Args: " + args.length);

        Properties prop = new Properties();
        int TotalThief  = 0;
        int totalThief  = 0;
        int totalRooms  = 0;
        int minPaintings= 0;
        int maxPaintings= 0;
        int minRooms    = 0;
        int maxRooms    = 0;


        try (InputStream input = new FileInputStream(args[0])) {

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            TotalThief   = Integer.parseInt(prop.getProperty("TotalThief", "10"));
            totalThief   = Integer.parseInt(prop.getProperty("totalThief","10"));
            totalRooms   = Integer.parseInt(prop.getProperty("totalRooms","10"));
            minPaintings = Integer.parseInt(prop.getProperty("minPaintings","11"));
            maxPaintings = Integer.parseInt(prop.getProperty("maxPaintings","1"));
            minRooms     = Integer.parseInt(prop.getProperty("minRooms","9"));
            maxRooms     = Integer.parseInt(prop.getProperty("maxRooms","12"));
            

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        System.out.println(TotalThief  );
        System.out.println(totalThief  );
        System.out.println(totalRooms  );
        System.out.println(minPaintings);
        System.out.println(maxPaintings);
        System.out.println(minRooms    );
        System.out.println(maxRooms    );


    }
    
}
