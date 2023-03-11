package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

public interface IControlCollectionSite{
    
    public void handACanvas();
    
    public int amINeeded();
    
    public void startOperations() throws Exception;
    
    public int appraiseSit() throws Exception;
    
    public int prepareNewParty(Object room) throws Exception;
    
    public void getRoomToAttack() throws Exception;

    public void takeARest() throws Exception;
    
    public void handACanvas(Thief thief) throws Exception;
    
    public void collectCanvas() throws Exception;
    
    public void sumUpResults() throws Exception;
    
    public int totalPaintingsStolen() throws Exception;
    
    public default void end() throws Exception {}
}