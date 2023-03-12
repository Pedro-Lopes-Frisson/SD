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
import museumheistsd.sharedregions.GeneralInformationRepositorySystem;

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
        int totalThief = 0, minPaintings = 0,
                maxPaintings = 0, nRooms = 0, nParties = 0, thiefMaxAgility = 0, thiefMinAgility = 0, thiefMaxDisplacement = 0, minDistanceRooms = 0, maxDistanceRooms = 0, assaultPartySize = 0;

        try (InputStream input = new FileInputStream(args[0])) {

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            totalThief = Integer.parseInt(prop.getProperty("totalThief", "7"));
            thiefMinAgility = Integer.parseInt(prop.getProperty("thiefMinAgility", "2"));
            thiefMaxAgility = Integer.parseInt(prop.getProperty("thiefMaxAgility", "6"));

            nRooms = Integer.parseInt(prop.getProperty("nRooms", "5"));
            minPaintings = Integer.parseInt(prop.getProperty("minPaintings", "8"));
            maxPaintings = Integer.parseInt(prop.getProperty("maxPaintings", "16"));
            minDistanceRooms = Integer.parseInt(prop.getProperty("minDistanceRooms", "15"));
            maxDistanceRooms = Integer.parseInt(prop.getProperty("maxDistanceRooms", "30"));

            assaultPartySize = Integer.parseInt(prop.getProperty("assaultPartySize", "3"));
            thiefMaxDisplacement = Integer.parseInt(prop.getProperty("thiefMaxDisplacement", "3"));

        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.exit(-1);
        }

        GeneralInformationRepositorySystem grs = new GeneralInformationRepositorySystem(totalThief, nRooms, minPaintings, maxPaintings, minDistanceRooms, maxDistanceRooms, nParties, thiefMaxAgility, thiefMinAgility, thiefMaxDisplacement);
        grs.startSimulation();
    }

}
