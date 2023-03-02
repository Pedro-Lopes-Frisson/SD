/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sharedregions;

/**
 *
 * @author Pedro1
 */
public class GeneralInformationRepositorySystem {
    static GeneralInformationRepositorySystem singleton = null;
    
    public GeneralInformationRepositorySystem(){
    
    }
    
    /**
     *
     * @return
     */
    public static synchronized GeneralInformationRepositorySystem getInstance() {
        if(singleton == null){
            singleton = new GeneralInformationRepositorySystem();
            return singleton;
        }
        return singleton;
    }
    
}
