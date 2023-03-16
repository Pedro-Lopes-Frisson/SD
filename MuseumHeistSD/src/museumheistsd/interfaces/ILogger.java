package museumheistsd.interfaces;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;

public interface ILogger{

    public void log(MasterThief mt);
    public void log(Thief t);

    void logResults();
}