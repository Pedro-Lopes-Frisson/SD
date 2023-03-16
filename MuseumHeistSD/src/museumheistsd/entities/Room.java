package museumheistsd.entities;

import java.util.Random;

public class Room {
    int totalPaintings;
    int distance;

    int paintingsLeft;

    private Room(int paintings, int distance) {
        this.totalPaintings = paintings;
        this.paintingsLeft = paintings;
        this.distance = distance;
    }

    public static Room createRoom(int minPaintings, int maxPaintings, int minDistance, int maxDistance) {
        Random r = new Random();
        return new Room(r.nextInt(maxPaintings - minPaintings) + minPaintings, r.nextInt(maxDistance - minDistance) + minDistance);
    }

    ;

    public boolean decrementCanvas() {
        return --paintingsLeft > 0;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getCanvasLeft() {
        return this.paintingsLeft;
    }
}
