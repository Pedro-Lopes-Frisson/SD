package museumheistsd.interfaces;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;

public interface IGeneralRepository{

    ILogger getLogger();
    
    IMuseum getMuseum();
    
    IControlCollectionSite getControlCollectionSite();
    
    IConcentrationSite getConcentrationSite();
    
    MasterThief getMasterThief();
    
    Thief[] getOrdinaryThieves();
    
}