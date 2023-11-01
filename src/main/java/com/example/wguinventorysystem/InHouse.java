package com.example.wguinventorysystem;


/**This Class Inherits from the provided Parts class. This class holds the constructor for In House parts, which defines all its characteristics.  *
 * @author David Torres
 **/
public class InHouse extends Part{

    private int machineId;

    /**This is the Constructor for Inhouse parts **/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Setter for machine ID*
     * @param machineId
     **/
    public void setMachineId(int machineId) {
        this.machineId =machineId;
    }


    /**This method is used to get the return value of MachineID*
     * @return machineId
     **/
    public int getMachineId() {
        return machineId;
    }

}



