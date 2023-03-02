package museumheistsd.entities;


public interface IThief {
    /**
     * This method will simulate that the thief has successfully stolen a Canvas
     * @param roomNumber the room number the thief has stolen the Canvas from
     */
    public void rollACanvas(int roomNumber);
    
    /**
     * Take steps having in account that there are other thief's that might be more or less agile
     */
    public void crawlIn();
    
    /**
     * Take steps having in account that there are other thief's that might be more or less agile
     */
    public void crawlOut();
} 

