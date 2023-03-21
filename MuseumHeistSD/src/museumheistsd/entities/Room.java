package museumheistsd.entities;

import java.util.Random;

public class Room {
    int totalPaintings;
    int distance;
    int id;

    boolean isBeingAttacked = false;
    int paintingsLeft;

    private Room(int id, int paintings, int distance) {
        this.id = id;
        this.totalPaintings = paintings;
        this.paintingsLeft = paintings;
        this.distance = distance;
    }

    public static Room createRoom(int id ,int minPaintings, int maxPaintings, int minDistance, int maxDistance) {
        Random r = new Random();
        return new Room(id, r.nextInt(maxPaintings - minPaintings) + minPaintings, r.nextInt(maxDistance - minDistance) + minDistance);
    }

    public boolean isBeingAttacked() {
        return isBeingAttacked;
    }

    public int getId() {
        return id;
    }

    public boolean decrementCanvas() {
        return --paintingsLeft > 0;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getCanvasLeft() {
        return this.paintingsLeft;
    }

    public void setClear() {
        this.isBeingAttacked = false;
    }
}
