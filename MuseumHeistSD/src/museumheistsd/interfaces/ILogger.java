package museumheistsd.interfaces;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;
import museumheistsd.sharedregions.AssaultParty;

public interface ILogger{

    void beginLog() throws Exception;

    void setStatusThief(Thread t) throws Exception;

    void setStatusMtThief(Thread t) throws Exception;

    void setAssaultParty(AssaultParty a) throws Exception;

    public void log() throws Exception;

    void logResults();
}