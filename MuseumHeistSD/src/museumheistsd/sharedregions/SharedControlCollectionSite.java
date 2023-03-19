/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;
import java.util.concurrent.SynchronousQueue;

import museumheistsd.entities.Room;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IMuseum;

/**
 *
 * @author Pedro1
 */
public class SharedControlCollectionSite implements IControlCollectionSite{

    SynchronousQueue<Thief> thieves;
    SynchronousQueue<AssaultParty> assaultParties;

    SynchronousQueue<Thief> teamlessThieves;
    IMuseum museum;

    @Override
    public void handACanvas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int amINeeded() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void startOperations() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int appraiseSit() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int prepareNewParty(Object room) throws Exception {
        // create new Assault party and notify thieves
        AssaultParty a = new AssaultParty();

    }

    @Override
    public Room getRoomToAttack() throws Exception {
        return museum.getRoomToAttack();
    }

    @Override
    public void takeARest() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void handACanvas(Thief thief) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void collectCanvas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void sumUpResults() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int totalPaintingsStolen() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
