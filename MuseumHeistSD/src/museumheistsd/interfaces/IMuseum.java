package museumheistsd.interfaces;

import museumheistsd.entities.Room;

public interface IMuseum{

    boolean rollACanvas(int id) throws Exception;
    Room[] getRooms() throws Exception;

    Room getRoomToAttack();
    default void end() throws Exception {}

    void setClear(int roomId);

    void decremmentCanvas(int roomId);
}