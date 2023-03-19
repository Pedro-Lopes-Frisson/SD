package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

/**
 *
 * @author Pedro1
 */
public interface IAssaultParty {

    int getID() throws Exception;
    
    int getTarget() throws Exception;

    int getState() throws Exception;
            
    boolean partyFull() throws Exception;
    
    Integer[] getThieves() throws Exception;
    
    void prepareParty(Object room) throws Exception;
    
    void addThief(Thief thief) throws Exception;
    
    void removeThief(int id) throws Exception;
            
    void sendParty() throws Exception;
    
    int crawlIn(Thief thief) throws Exception;
    
    boolean keepCrawling(Thief thief) throws Exception;
    
    void reverseDirection(Thief thief) throws Exception;

    int crawlOut(Thief thief) throws Exception;

    int getThiefHasCanvas(int i);
}
