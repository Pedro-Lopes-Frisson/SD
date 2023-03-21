/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package museumheistsd.sharedregions;

import java.util.Arrays;

import museumheistsd.entities.MasterThief;
import museumheistsd.entities.Thief;
import museumheistsd.interfaces.IAssaultParty;
import museumheistsd.interfaces.IConcentrationSite;
import museumheistsd.interfaces.IControlCollectionSite;
import museumheistsd.interfaces.IGeneralRepository;
import museumheistsd.interfaces.ILogger;
import museumheistsd.interfaces.IMuseum;

/**
 * @author Pedro1
 */
public class GeneralInformationRepositorySystem implements IGeneralRepository {

    /**
     * Museum to be assaulted by AssaultParties.
     */
    private final IMuseum museum;

    /**
     * The control and collection site.
     */
    private final IControlCollectionSite controlCollection;

    /**
     * Concentration site where the OrdinaryThieves wait to be assigned to an
     * AssaultParty.
     */
    private final IConcentrationSite concentration;

    /**
     * AssaultParties to be used in the simulation.
     */
    private IAssaultParty[] parties;

    /**
     * MasterThieve that controls and assigns OrdinaryThieves to AssaultParties.
     */
    private final MasterThief master;

    /**
     * OrdinaryThieves array
     */
    private final Thief[] thieves;

    /**
     * Logger object used to log the state of this GeneralRepository
     */
    private final ILogger logger;

    public GeneralInformationRepositorySystem(int totalThieves, int totalRooms, int minPaintings, int maxPaintings, int minRooms, int maxRooms, int elemsPerParty, int thiefMaxAgility, int thiefMinAgility, int thiefMaxDisplacement) {
        this.museum = SharedMuseum.createMuseum(totalRooms, minPaintings, maxPaintings, minRooms, maxRooms);
        this.parties = new IAssaultParty[(int) (totalThieves / elemsPerParty)];
        this.concentration = new SharedConcentrationSite(totalThieves, (int) (totalThieves / elemsPerParty), this.parties);
        this.controlCollection = new SharedControlCollectionSite(totalThieves, museum,elemsPerParty);

        try {
            this.logger = SharedLogger.createLogger(totalThieves - 1, totalThieves / elemsPerParty, elemsPerParty, this.museum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.master = new MasterThief(this.controlCollection, this.concentration, getLogger(), 1);

        this.thieves = new Thief[totalThieves - 1];
        for (int i = 0; i < totalThieves - 1; i++) {
            thieves[i] = Thief.createThief(thiefMinAgility, thiefMaxAgility, thiefMaxDisplacement, i);
        }

    }

    @Override
    public synchronized ILogger getLogger() {
        return this.logger;
    }

    @Override
    public IMuseum getMuseum() {
        return this.museum;
    }

    @Override
    public IControlCollectionSite getControlCollectionSite() {
        return this.controlCollection;
    }

    @Override
    public IConcentrationSite getConcentrationSite() {
        return this.concentration;
    }

    @Override
    public MasterThief getMasterThief() {
        return this.master;
    }

    @Override
    public Thief[] getOrdinaryThieves() {
        return this.thieves;
    }

    public void startSimulation() {
        Arrays.asList(thieves).forEach(t -> t.start());
        master.start();
    }

}
