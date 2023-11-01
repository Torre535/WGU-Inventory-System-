package com.example.wguinventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This product class is similar to part class in that it defines attributes and characteristics *
 * @author David Torres
 **/
public class Product {


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This is the constructor for Product
     **/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**Method gets list containing all associated parts*
     * @return associatedParts
     **/
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


    /**Method us used to set associated parts**/
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;

    }

    /**
     * Method is used to set ID for product
     * @param id
     **/
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method is used to set name for product
     * @param name
     **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method is used to set price for product
     * @param price
     *
     **/
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method is used to set min value for Product
     * @param min
     **/
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method is used to set Max value for Product
     * @param max
     **/
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method is used to set Stock Inv for Product
     **/
    public void setStock(int parseInt) {
        this.stock = stock;
    }

    /**
     * Method is used to get ID for product
     * @return id
     **/
    public int getId() {
        return id;
    }

    /**
     * method is used to get name for product
     * @return name
     **/
    public String getName() {
        return name;
    }

    /**
     * method is used to get price for product
     * @return price
     **/
    public double getPrice() {
        return price;
    }

    /**
     * Method is used to get stock Inv for product
     * @return stock
     **/
    public int getStock() {
        return stock;
    }

    /**
     * Method is used to get min value for product
     * @return min
     **/
    public int getMin() {
        return min;
    }

    /**
     * Method is used to get max value for product
     * @return max
     **/
    public int getMax() {
        return max;
    }
}






