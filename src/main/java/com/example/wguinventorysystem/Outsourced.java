package com.example.wguinventorysystem;

/**This class inherits from the part class, this class contains the constructor and getter*
 * @author David Torres
 **/

public class Outsourced extends Part {

    private String companyName;

    /**Constructor for outsourced parts**/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Setter for company name *
     * @param companyName
     **/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**Getter for outsourced part name, returns name. *
     * @return companyName
     * */
    public String getCompanyName() {
        return companyName;
    }
}

