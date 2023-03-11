package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

/**
 *
 * @author Pedro1
 */
public interface IAssaultParty {
    
    
    public int getID() throws Exception;
    
    public int getTarget() throws Exception;

    public int getState() throws Exception;
            
    public boolean partyFull() throws Exception;
    
    public int[] getThieves() throws Exception;
    
    public void prepareParty(Object room) throws Exception;
    
    public void addThief(Thief thief) throws Exception;
    
    public void removeThief(int id) throws Exception;
            
    public void sendParty() throws Exception;
    
    public int crawlIn(Thief thief) throws Exception;
    
    public boolean keepCrawling(Thief thief) throws Exception;
    
    public void reverseDirection(Thief thief) throws Exception;

    public int crawlOut(Thief thief) throws Exception;
}
