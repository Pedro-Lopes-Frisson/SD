package museumheistsd.entities;
import java.util.Random;
public class Room {
    int totalPaintings;
    int distance;

    private Room(int paintings, int distance){
        this.totalPaintings = paintings;
        this.distance = distance;
    }
    
    public Room createRoom(int minPaintings, int maxPaintings, int minDistance, int maxDistance){
        Random r = new Random();
        return new Room(r.nextInt(maxPaintings - minPaintings) + minPaintings, r.nextInt(maxDistance - minDistance) + minDistance);
    };
}
