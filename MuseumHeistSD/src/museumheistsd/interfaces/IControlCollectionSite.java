package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

public interface IControlCollectionSite{
    
    void handACanvas();
    
    int amINeeded();
    
    void startOperations() throws Exception;
    
    int appraiseSit() throws Exception;
    
    int prepareNewParty(Object room) throws Exception;
    
    void getRoomToAttack() throws Exception;

    void takeARest() throws Exception;
    
    void handACanvas(Thief thief) throws Exception;
    
    void collectCanvas() throws Exception;
    
    void sumUpResults() throws Exception;
    
    int totalPaintingsStolen() throws Exception;
    
    default void end() throws Exception {}
}