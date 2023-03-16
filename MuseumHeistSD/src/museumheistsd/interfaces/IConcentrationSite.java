package museumheistsd.interfaces;

import museumheistsd.entities.Thief;

public interface IConcentrationSite{


    
    
    void fillAssaultParty(int party) throws Exception;
    
   
    int prepareExcursion(Thief thief) throws Exception;
    
  
    default void end() throws Exception {}


}