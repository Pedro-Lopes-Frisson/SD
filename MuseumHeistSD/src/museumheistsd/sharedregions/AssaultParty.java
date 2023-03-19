/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IAssaultParty;

import java.util.concurrent.SynchronousQueue;

/**
 *
 * @author Pedro1
 */
public class AssaultParty implements IAssaultParty{
    int roomId;
    SynchronousQueue<Thief> thieves;
    int nThieves;
    boolean crawlingIn;

    public AssaultParty(int roomId, int nThieves, boolean crawlingIn) {
        this.roomId = roomId;
        this.thieves = new SynchronousQueue<>();
        this.nThieves = nThieves;
        this.crawlingIn = crawlingIn;
    }

    @Override
    public int getID() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getTarget() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getState() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean partyFull() throws Exception {
        return this.thieves.size() == nThieves;
    }

    @Override
    public Integer[] getThieves() throws Exception {

        return (Integer []) thieves.stream().map(Thief::getId).toArray();
    }

    @Override
    public void prepareParty(Object room) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public synchronized void  addThief(Thief thief) throws Exception {
        if(thieves.size() < nThieves){
            this.thieves.add(thief);
        }
        else throw  new RuntimeException("Party already full");
    }

    @Override
    public synchronized void removeThief(int id) throws Exception {
        thieves.removeIf((Thief t) -> t.getId() == id);
    }

    @Override
    public synchronized void sendParty() throws Exception {
        notifyAll();
    }

    @Override
    public int crawlIn(Thief thief) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        return 1;
    }

    public int getThiefPos(int j) {
        return 1;
    }
    public int getThiefHasCanvas(int j) {
        return 1;
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
            public int getValue(){
                return value;
            }
    }

}
