package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

public interface IConcentrationSite{


    
    
    public void fillAssaultParty(int party) throws Exception;
    
   
    public int prepareExcursion(Thief thief) throws Exception;
    
  
    public default void end() throws Exception {}


}