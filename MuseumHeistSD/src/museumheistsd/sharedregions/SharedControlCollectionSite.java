/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

import datastructures.MemFIFO;
import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Room;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IMuseum;

/**
 * @author Pedro1
 */
public class SharedControlCollectionSite implements IControlCollectionSite {

    int nThieves;
    SynchronousQueue<Thief> thieves;
    ArrayList<AssaultParty> assaultParties;

    MemFIFO<Thief> teamlessThieves;
    IMuseum museum;
    int elemsPerAParty;
    int partyID = 0;

    int collectedCanvas = 0;

    MemFIFO<Thief> thievesArrived;

    boolean simulationEnd = false;
    boolean simulationStarted = false;

    public SharedControlCollectionSite(int nThieves, IMuseum museum, int elemsPerAParty) {
        this.nThieves = nThieves;
        this.thieves = new SynchronousQueue<>();
        this.assaultParties = new ArrayList<>();
        this.teamlessThieves = new MemFIFO<>(Thief.class, nThieves / elemsPerAParty);
        this.museum = museum;
        this.elemsPerAParty = elemsPerAParty;
        this.thievesArrived = new MemFIFO<>(Thief.class, nThieves / elemsPerAParty);
    }

    @Override
    public synchronized void handACanvas() {
        this.thievesArrived.write((Thief) Thread.currentThread());
        notifyAll(); //wake mt

        while (thievesArrived.contains((Thief) Thread.currentThread())) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int amINeeded() {
        this.teamlessThieves.write((Thief) Thread.currentThread());
        this.notifyAll();
        while (this.teamlessThieves.contains((Thief) Thread.currentThread())) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return 1;

    }

    @Override
    public void startOperations() throws Exception {
        this.simulationStarted = true;
    }

    @Override
    public int appraiseSit() throws Exception {
        if (this.museum.getRoomToAttack() == null) {
            while (this.teamlessThieves.size() < this.nThieves) {
                this.wait();
            }
            this.notifyAll();

            return MasterThief.MTStatus.PRESENTING_THE_REPORT.getValue();
        } else if (this.museum.getRoomToAttack() != null && this.teamlessThieves.size() >= this.elemsPerAParty && this.assaultParties.stream().anyMatch((AssaultParty a) -> {
            try {
                return a.partyFull();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })) {
            for (int i = 0; i < elemsPerAParty; i++) {
                this.teamlessThieves.read();
            }
            this.notifyAll();

            return MasterThief.MTStatus.ASSEMBLING_A_GROUP.getValue();
        } else if (Arrays.stream(this.museum.getRooms()).anyMatch(Room::isBeingAttacked)) {
            return MasterThief.MTStatus.WAITING_FOR_GROUP_ARRIVAL.getValue();
        }

        this.wait();
        return MasterThief.MTStatus.DECIDING_WHAT_TO_DO.getValue();
    }

    @Override
    public int prepareNewParty(Object room) throws Exception {
        // create new Assault party and notify thieves
        AssaultParty a = new AssaultParty(partyID, ((Room) room).getId(), elemsPerAParty, true);
        partyID++;
        return a.getID();
    }

    @Override
    public Room getRoomToAttack() throws Exception {
        return museum.getRoomToAttack();
    }

    @Override
    public void takeARest() throws Exception {
        while (!thievesArrived.isFull())
            wait();
    }

    @Override
    public void handACanvas(Thief thief) throws Exception {
        this.thievesArrived.write(thief);
        notifyAll();
        while (this.thievesArrived.contains(thief))
            wait();
    }

    @Override
    public void collectCanvas() throws Exception {
        if (!thievesArrived.isEmpty()) {
            Thief t = thievesArrived.read();
            AssaultParty a = (AssaultParty) this.assaultParties.stream().filter(party -> {
                try {
                    return party.getID() == t.getPartyID();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).findFirst().orElse(null);

            if (a == null) {
                System.err.println("Thief had invalid party id");
                Thread.currentThread().interrupt();
            }

            if (t.getHasCanvas()) {
                this.museum.decremmentCanvas(a.getRoomId());
                t.clearHasCanvas();
                this.collectedCanvas++;
            } else {
                this.museum.setClear(a.getRoomId());
            }
        }
    }

    @Override
    public void sumUpResults() throws Exception {
        this.simulationEnd = true;
        this.teamlessThieves = new MemFIFO<>(Thief.class, 0);
        this.notifyAll(); //wake waiting thieves
    }

    @Override
    public int totalPaintingsStolen() throws Exception {
        return collectedCanvas;
    }


}
