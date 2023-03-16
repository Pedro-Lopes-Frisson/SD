package museumheistsd.entities;

import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IConcentrationSite;
import museumheistsd.interfaces.ILogger;


public class MasterThief extends Thread {
    MTStatus status;
    ILogger logger;
    int id;
    int paintingsStolen;

    @Override
    public long getId() {
        return id;
    }

    public int getStatus() {
        return status.value;
    }

    @Override
    public void run() {
        try {

            this.startOperations();

            while (this.status != MTStatus.PRESENTING_THE_REPORT) {
                if(this.status == MTStatus.DECIDING_WHAT_TO_DO){
                    this.appraiseSit();
                }
                else if (this.status == MTStatus.WAITING_FOR_GROUP_ARRIVAL) {
                    this.takeARest(); // wait for thief to reach the concentration site
                    this.collectCanvas(); //collect canvas
                }
                else if(this.status == MTStatus.ASSEMBLING_A_GROUP){
                    this.prepareAssaultParties(); //  create assault parties
                    this.sendAssaultParties(); // send each assault party to a room until the room is cleared0
                }

            }

            this.sumUpResults();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void sendAssaultParties(){

    }
    private void prepareAssaultParties() {
    }

    private void collectCanvas() {
        this.paintingsStolen++;
    }

    private void takeARest() {

    }

    private void appraiseSit() {

    }

    private void sumUpResults() throws Exception {
        this.ccs.sumUpResults();
        this.logger.logResults();
    }

    private void startOperations() throws Exception {
        this.ccs.startOperations();
        this.status = MTStatus.DECIDING_WHAT_TO_DO;
        this.logger.setStatusMtThief(this);
        this.logger.log();
    }

    public MasterThief(IControlCollectionSite css, IConcentrationSite cs, ILogger logger, int id) {
        this.cs = cs;
        this.ccs = css;
        this.logger = logger;
        this.id = id;

        try {
            this.logger.setStatusMtThief(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        status = MTStatus.PLANNING_THE_HEIST;
    }

    private final IControlCollectionSite ccs;
    private final IConcentrationSite cs;

    public int getPaintingsStolen() {
        return paintingsStolen;
    }


    enum MTStatus {
        // Status of MasterThief
        /**
         * Status PLANNING_THE_HEIST.
         */
        PLANNING_THE_HEIST(1000),
        /**
         * Status DECIDING_WHAT_TO_DO.
         */
        DECIDING_WHAT_TO_DO(2000),
        /**
         * Status ASSEMBLING_A_GROUP.
         */
        ASSEMBLING_A_GROUP(3000),
        /**
         * Status WAITING_FOR_GROUP_ARRIVAL.
         */
        WAITING_FOR_GROUP_ARRIVAL(4000),
        /**
         * Status PRESENTING_THE_REPORT.
         */
        PRESENTING_THE_REPORT(5000);

        private MTStatus(int value) {
            this.value = value;
        }

        private final int value;

        public int getValue() {
            return value;
        }
    }


}
