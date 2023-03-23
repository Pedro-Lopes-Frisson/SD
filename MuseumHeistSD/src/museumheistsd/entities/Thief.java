package museumheistsd.entities;

import museumheistsd.interfaces.IConcentrationSite;
import museumheistsd.interfaces.IControlCollectionSite;

import java.util.Objects;
import java.util.Random;

/**
 * Ordinary Thief
 * This thief will be the own who performs actions that lead to the stealing of a canvas.
 * He is assigned to an assault party which is in charge of clearing all canvas from a room
 *
 * @author Pedro1
 */
public class Thief extends Thread {

    public static Thief createThief(int thiefMinAgility, int thiefMaxAgility, int thiefMaxDisplacement, int id) {
        Random r = new Random();
        return new Thief(r.nextInt(thiefMaxAgility - thiefMinAgility) + thiefMinAgility, id, thiefMaxDisplacement);
    }

    private final int agility;
    private final int thiefID;
    private TStatus status;
    private int partyID;
    private int position;
    private boolean hasCanvas;

    private IConcentrationSite cs;
    private IControlCollectionSite ccs;

    public boolean getHasCanvas() {
        return hasCanvas;
    }

    public int getAgility() {
        return agility;
    }

    public int getPartyID() {
        return partyID;
    }

    public Thief(int agility, int thiefID, int maxDisplacement, IConcentrationSite cs, IControlCollectionSite ccs) {
        this.agility = agility;
        this.thiefID = thiefID;
        this.status = TStatus.OUTSIDE;
        this.partyID = -1;
        this.position = 0;
        this.hasCanvas = false;
        this.cs = cs;
        this.ccs = ccs;
    }

    @Override
    public long getId() {
        return this.thiefID; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (this.status == TStatus.CONTROL_COLLECTION) {
                    if (this.getHasCanvas()) {
                        this.ccs.handACanvas(this);
                    }
                }
                if(this.status == TStatus.CONCENTRATION_SITE){
                    this.amINeeded(); // block until he is needed
                    this.partyID = this.cs.prepareExcursion(this);
                }
                if(this.status == TStatus.CRAWLING_INWARDS){
                    this.crawlIn();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void crawlIn() {
        this.ccs.
    }

    private void amINeeded() {
        this.status = TStatus.CONCENTRATION_SITE;
    }

    public int getStatus() {
        return this.status.value;
    }

    public String getSituation() {
        return "p";
    }

    public int getMD() {
        return this.agility;
    }

    public void clearHasCanvas() {
        this.hasCanvas = false;
    }


    enum TStatus {
        // Status of MasterThief
        /**
         * Status PLANNING_THE_HEIST.
         */
        CONCENTRATION_SITE(1000),
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
        CRAWLING_OUTWARDS(4000),

        CONTROL_COLLECTION(5000);

        private TStatus(int value) {
            this.value = value;
        }

        private final int value;

        public int getValue() {
            return value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thief thief = (Thief) o;
        return thiefID == thief.thiefID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thiefID);
    }
}
