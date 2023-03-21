/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import museumheistsd.entities.Room;
import museumheistsd.interfaces.IMuseum;

/**
 *
 * @author Pedro1
 */
public class SharedMuseum implements IMuseum {

    private Room[] rooms;
    private ReentrantLock rl = new ReentrantLock();

    public static IMuseum createMuseum(int totalRooms, int minPaintings, int maxPaintings, int minDistance, int maxDistance) {
        Room[] rooms = new Room[totalRooms];
        for (int i = 0; i < totalRooms; i++) {
            rooms[i] = Room.createRoom(i,minPaintings, maxPaintings, minDistance, maxDistance);
        }

        return new SharedMuseum(rooms);
    }

    private SharedMuseum(Room[] rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean rollACanvas(int id) throws Exception {
        rl.lock();
        boolean rt = false;
        
        try {
            rt = this.rooms[id].decrementCanvas();
        } finally {
            rl.unlock();
        }
        
        return rt;
    }

    @Override
    public Room[] getRooms() throws Exception {
        return rooms;
    }

    @Override
    public Room getRoomToAttack() {
        return Arrays.stream(rooms).filter((Room r)-> ! r.isBeingAttacked() && r.getCanvasLeft() > 0 ).findFirst().orElse(null);
    }

    @Override
    public void end() throws Exception {
        IMuseum.super.end();
    }

    @Override
    public void setClear(int roomId) {
        this.rooms[roomId].setClear();
    }

    @Override
    public void decremmentCanvas(int roomId) {
        this.rooms[roomId].decrementCanvas();
    }

}
