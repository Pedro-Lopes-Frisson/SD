package museumheistsd.entities;
import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IConcentrationSite;


public class MasterThief extends Thread {
    MTStatus status;
    
    @Override
    public long getId() {
        return super.getId(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void run() {
        while(true){
            System.out.println("ola");
        }
    }

    public MasterThief(IControlCollectionSite css, IConcentrationSite cs){
        status = MTStatus.PLANNING_THE_HEIST;
        this.cs = cs;
        this.ccs = css;
    }

    private final IControlCollectionSite ccs;
    private final IConcentrationSite cs;


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
            public int getValue(){
                return value;
            }
    }
    
    
}
