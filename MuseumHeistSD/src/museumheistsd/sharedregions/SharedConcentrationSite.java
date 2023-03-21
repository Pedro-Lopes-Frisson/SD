/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;

import datastructures.MemFIFO;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IAssaultParty;
import museumheistsd.interfaces.IConcentrationSite;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Pedro1
 */
public class SharedConcentrationSite implements IConcentrationSite {

    /**
     * AssaultParties
     */
    private final IAssaultParty[] parties;


    /**
     * List of thieves waiting to enter a team.
     */
    private final MemFIFO<Thief> waitingThieves;

    /**
     * List of parties waiting to be filled with thieves.
     */
    private final Queue<Integer> waitingParties;


    public SharedConcentrationSite(int nThieves, int nParties, IAssaultParty[] parties) {
        this.parties = parties;
        this.waitingThieves = new MemFIFO<>(Thief.class, nThieves);
        this.waitingParties = new ArrayDeque<>(nParties);
    }
    @Override
    public synchronized void fillAssaultParty(int party) throws Exception {
        if(!this.waitingParties.contains(party))
            throw new RuntimeException("That is no party with such id;");
        this.waitingParties.add(party);
        this.notifyAll();
        while (!this.parties[party].partyFull()){
            wait();
        }
        this.waitingParties.remove(party);
    }

    @Override
    public int prepareExcursion(Thief thief) throws Exception {
        this.waitingThieves.write(thief);
        while (this.waitingParties.isEmpty())
            wait();
        int partyID = this.waitingParties.peek();
        Thief t = this.waitingThieves.read();
        this.parties[partyID].addThief(t);

        if(this.parties[partyID].partyFull()){
           this.notifyAll();
        }
        return partyID;
    }

    @Override
    public void sendParty(int partyId) {
        try {
            this.parties[partyId].sendParty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
