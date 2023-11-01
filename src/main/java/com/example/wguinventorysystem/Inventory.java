package com.example.wguinventorysystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**This class allows us to manage our Inventory for Parts and Products. it has functions within it as well as Arraylists where data is stored*
 * @author David Torres
 **/
public class Inventory {
    static ObservableList<Part> allParts = FXCollections.observableArrayList();

    static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static int productIndex = 2000;
    public static int partIndex = 1;


    /**This allows us to add a new part *
     * @param newPart
     **/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
        partIndex++;
    }

    /**This allows us to add a new Product *
     * @param newProduct
     **/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        productIndex++;
    }

    /**Looking up parts. For loop checks to see if there is a match in the observable array.*
     * @return part
     **/
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**Looking up Products. For loop checks to see if there is a match in the observable array.*
     * @return product
     **/
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** Looking up Part Name in String form using a for loop*
     **/
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> samePart = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part != null && part.getName() != null && part.getName().equals(partName)) {
                samePart.add(part);
            }
        }
        return samePart;
    }

    /** Looking up Product Name in a String Form using a for loop *
     * @param productName
     **/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> sameProduct = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if ((product != null && product.getName() != null && product.getName().equals(productName))) {
                sameProduct.add(product);
            }
        }
        return sameProduct;
    }

    /**Updating Part that is already within Inventory *
     * @param selectedPart
     **/
    public static void updatePart(int index, Part selectedPart) {
        for (Part part : allParts) {
            if (part.getId() == index) {
                allParts.set(allParts.indexOf(part), selectedPart);
                return;
            }
        }
    }
    /** Updating Product that is already within Inventory *
     * @param newProduct
     **/
    public static void updateProduct(int index, Product newProduct) {
        for (Product product : allProducts) {
            if (product.getId() == index) {
                allProducts.set(allProducts.indexOf(product), newProduct);
                return;

            }
        }
    }
    /**Removal of selected part from Inventory.*
     * @return patsSelected
     **/
    public static boolean deletePart (Part partSelected) {
        boolean removed = allParts.remove(partSelected);
        return removed;
    }
    /**Removal of selected product from inventory.*
     * @return removed
     */

    public static boolean deleteProduct (Product productSelected) {
        boolean removed = allProducts.remove(productSelected);
        return removed;
    }
    /**Getting all parts from Inventory list*
     * @return allParts
     **/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**Getting all products within list *
     * @return allProducts
     **/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }



}