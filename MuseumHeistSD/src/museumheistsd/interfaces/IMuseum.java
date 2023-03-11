package museumheistsd.interfaces;

import museumheistsd.entities.Room;

public interface IMuseum{

    public boolean rollACanvas(int id) throws Exception;
    public Room[] getRooms() throws Exception;
    public default void end() throws Exception {}

}