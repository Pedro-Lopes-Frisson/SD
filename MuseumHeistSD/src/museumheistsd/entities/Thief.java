package museumheistsd.entities;

import museumheistsd.interfaces.IAssaultParty;
import museumheistsd.interfaces.IConcentrationSite;
import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IMuseum;
import museumheistsd.sharedregions.AssaultParty;

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


    private final int agility;
    private final int thiefID;
    private TStatus status;
    private int partyID;
    private int position;
    private boolean hasCanvas;

    private int maxDisplacement;
    private IConcentrationSite cs;
    private IControlCollectionSite ccs;
    private IMuseum museum;

    private AssaultParty assaultParty = null;

    public Thief(int agility, int thiefID, int maxDisplacement, IConcentrationSite cs, IControlCollectionSite ccs, IMuseum museum) {
        this.agility = agility;
        this.thiefID = thiefID;
        this.maxDisplacement = maxDisplacement;
        this.status = TStatus.CONTROL_COLLECTION;
        this.partyID = -1;
        this.position = 0;
        this.hasCanvas = false;
        this.cs = cs;
        this.ccs = ccs;
        this.museum = museum;
    }

    public static Thief createThief(int thiefMinAgility, int thiefMaxAgility, int thiefMaxDisplacement, int id, IConcentrationSite cs, IControlCollectionSite ccs, IMuseum museum) {
        Random r = new Random();
        return new Thief(r.nextInt(thiefMaxAgility - thiefMinAgility) + thiefMinAgility, id, thiefMaxDisplacement, cs, ccs, museum);
    }

    public boolean getHasCanvas() {
        return hasCanvas;
    }

    public int getAgility() {
        return agility;
    }

    public int getPartyID() {
        return partyID;
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

                if (this.status == TStatus.CONCENTRATION_SITE) {
                    this.amINeeded(); // block until he is needed
                    this.partyID = this.cs.prepareExcursion(this);
                }

                if (this.status == TStatus.CRAWLING_INWARDS) {
                    this.crawlIn();
                    this.keepCrawling();
                }

                if (this.status == TStatus.CRAWLING_OUTWARDS) {
                    this.crawlOut();
                    this.keepCrawling();
                }

                if (this.status == TStatus.AT_A_ROOM) {
                    this.museum.decremmentCanvas(this.assaultParty.getRoomId());
                    this.crawlOut();
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void keepCrawling() {
        try {
            this.assaultParty.keepCrawling(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void crawlIn() {
        if (this.assaultParty == null) {
            System.err.println("No assault party assault");
            Thread.currentThread().interrupt();
        }
        try {
            this.status = TStatus.CRAWLING_INWARDS;
            this.assaultParty.crawlIn(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void crawlOut() {
        if (this.assaultParty == null) {
            System.err.println("No assault party assault");
            Thread.currentThread().interrupt();
        }
        try {
            this.status = TStatus.CRAWLING_OUTWARDS;
            this.assaultParty.crawlOut(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void amINeeded() {
        this.status = TStatus.CONCENTRATION_SITE;
    }

    public int getStatus() {
        return this.status.value;
    }

    public String getSituation() {
        return this.partyID == -1 ? "W" : "P";
    }

    public int getMD() {
        return this.maxDisplacement;
    }

    public void clearHasCanvas() {
        this.hasCanvas = false;
    }

    public int getPartyPos() {
        return this.assaultParty.getThiefPos(this.thiefID);
    }

    public void setPosition(int position) {
        this.position = position;
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
