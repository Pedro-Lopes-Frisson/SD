package museumheistsd.interfaces;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;

public interface IGeneralRepository{

    public ILogger getLogger();
    
    public IMuseum getMuseum();
    
    public IControlCollectionSite getControlCollectionSite();
    
    public IConcentrationSite getConcentrationSite();
    
    public MasterThief getMasterThief();
    
    public Thief[] getOrdinaryThieves();
    
}