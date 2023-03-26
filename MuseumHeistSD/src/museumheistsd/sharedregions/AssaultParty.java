/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;

import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IAssaultParty;

import java.util.Arrays;
import java.util.concurrent.SynchronousQueue;

/**
 * @author Pedro1
 */
public class AssaultParty implements IAssaultParty {
    int id;
    int roomId;
    int roomDistance;
    SynchronousQueue<Thief> walkingThieves;
    int nThieves;
    boolean crawlingIn;

    int[] thievesDistance;
    Thief[] thieves;
    int intThieves = 0;
    // estado para a assault party blocquear thieves que queriam dar crawlin antes de a party ser dispatched

    public AssaultParty(int id, int roomId, int nThieves, boolean crawlingIn) {
        this.roomId = roomId;
        this.walkingThieves = new SynchronousQueue<>();
        this.nThieves = nThieves;
        this.crawlingIn = crawlingIn;
        thievesDistance = new int[nThieves];
        thieves = new Thief[nThieves];
    }

    @Override
    public int getID() throws Exception {
        return id;
    }

    @Override
    public int getTarget() throws Exception {
        return roomId;
    }

    @Override
    public int getState() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean partyFull() throws Exception {
        return nThieves == intThieves;
    }

    @Override
    public Integer[] getThieves() throws Exception {

        return (Integer[]) Arrays.stream(thieves).map(Thief::getId).toArray();
    }

    @Override
    public void prepareParty(Object room) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void addThief(Thief thief) throws Exception {
        thieves[intThieves] = thief;

        if (walkingThieves.size() < nThieves) {
            this.walkingThieves.add(thief);
        }

        intThieves++;
    }

    @Override
    public synchronized void removeThief(int id) throws Exception {
        walkingThieves.removeIf((Thief t) -> t.getId() == id);
        int pos = -1;
        for (int t = 0; t < thieves.length; t++) {
            if (thieves[t].getId() == id)
                pos = t;
        }
        if (pos < 0)
            return;

        thieves[pos] = null;
        intThieves--;
    }

    @Override
    public synchronized void sendParty() throws Exception {
        notifyAll();
    }

    @Override
    public int crawlIn(Thief thief) throws Exception {
        return 1;
    }

    @Override
    public boolean keepCrawling(Thief thief) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void reverseDirection(Thief thief) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int crawlOut(Thief thief) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getRoomId() {
        return roomId;
    }

    public int getThiefPos(int j) {
        return thievesDistance[j];
    }

    public boolean getThiefHasCanvas(int j) {
        return thieves[j].getHasCanvas();
    }


    enum APStatus {
        // Status of MasterThief
        /**
         * Status PLANNING_THE_HEIST.
         */
        WAITING(1000),
        /**
         * Status DECIDING_WHAT_TO_DO.
         */
        CRAWLING_IN(2000),
        /**
         * Status ASSEMBLING_A_GROUP.
         */
        CRAWLING_OUT(3000),
        /**
         * Status WAITING_FOR_GROUP_ARRIVAL.
         */
        DISMISSED(4000);

        private APStatus(int value) {
            this.value = value;
        }

        private final int value;

        public int getValue() {
            return value;
        }
    }

}
