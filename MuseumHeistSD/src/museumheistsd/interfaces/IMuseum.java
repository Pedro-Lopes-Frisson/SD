package museumheistsd.interfaces;

import museumheistsd.entities.Room;

public interface IMuseum{

    boolean rollACanvas(int id) throws Exception;
    Room[] getRooms() throws Exception;
    default void end() throws Exception {}

}