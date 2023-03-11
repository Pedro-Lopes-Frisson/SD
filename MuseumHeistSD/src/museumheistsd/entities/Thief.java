package museumheistsd.entities;

/**
 * Ordinary Thief
 * This thief will be the own who performs actions that lead to the stealing of a canvas.
 * He is assigned to an assault party which is in charge of clearing all canvas from a room
 * 
 * @author Pedro1
 */
public class Thief extends Thread {
   
    private final int agility;
    private final int thiefID;
    private TStatus status;
    private int partyID;
    private int position;
    private int hasCanvas;

    public Thief(int agility, int id, int thiefID, TStatus status, int maxDisplacement, int partyID, int position, int hasCanvas) {
        this.agility = agility;
        this.thiefID = thiefID;
        this.status = status;
        this.partyID = partyID;
        this.position = position;
        this.hasCanvas = hasCanvas;
    }

    @Override
    public long getId() {
        return this.thiefID; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void run() {
        while (true) {
            // do something
        }
    }
    
    
    
    enum TStatus {
            // Status of MasterThief
            /**
            * Status PLANNING_THE_HEIST.
             */
            OUTSIDE(1000),
            /**
             * Status DECIDING_WHAT_TO_DO.
             */
            CRAWLING_INWARDS(2000),
            /**
             * Status ASSEMBLING_A_GROUP.
             */
            AT_A_ROOM(3000),
            /**
             * Status WAITING_FOR_GROUP_ARRIVAL.
             */
            CRAWLING_OUTWARDS(4000);
            
            private TStatus(int value) {
                this.value = value;
            }
            
            private final int value;
            public int getValue(){
                return value;
            }
    }

}
