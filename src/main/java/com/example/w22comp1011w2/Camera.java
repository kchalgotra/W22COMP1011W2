package com.example.w22comp1011w2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Camera {
    private int cameraID;
    private int resolution;
    private String make, model;
    private boolean slr;
    private double price;
    private int unitsSold;

    public Camera(String make, String model, int res, boolean slr, double price) {
        if (res >=2 && res <=100){
            setResolution(res);
        }else
            throw new IllegalArgumentException("resolution should be between 2-100");
        List<String> validMakes = getManufacturers();
        if(validMakes.contains(make)){
            setMake(make);
        }else
            throw new IllegalArgumentException("Make should be in the list of "+ validMakes);

        if(!model.isBlank()){
            setModel(model);
        }else
            throw new IllegalArgumentException("Model can not be blank");
        setSlr(slr);
        setPrice(price);
        cameraID = -1;

    }

    /**
     *
     *This is an overloaded constructor
     */
    public Camera(int cameraID, String make, String model, int resolution, boolean slr, double price){
        this(make,model,resolution,slr,price);
        setCameraID(cameraID);
    }

    public Camera(int cameraID, String make, String model, int resolution, boolean slr, double price, int unitsSold){
        this(make,model,resolution,slr,price);
        setUnitsSold(unitsSold);
        setCameraID(cameraID);
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        if(unitsSold >= 0){
            this.unitsSold = unitsSold;
        }
        else throw new IllegalArgumentException("units sold should be >= 0");
    }

    public int getCameraID() {
        return cameraID;
    }

    public void setCameraID(int cameraID) {
        if(cameraID<0){
            throw new IllegalArgumentException("CameraID must be greater than 0");
        }else{
            this.cameraID = cameraID;
        }
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        if (resolution >= 2 && resolution <= 100)
            this.resolution = resolution;
        else
            throw new IllegalArgumentException("resolution should be between 2-100");
    }

    public String getMake() {
        return make;
    }

    /**
     * This method will validate that the manufacturer is either Canon, Nikon, Sony or FujiFilm
     * @param make - the manufacturer of camera
     */
    public void setMake(String make) {
        List<String> validMakes = getManufacturers();
        if (validMakes.contains(make))
            this.make = make;
        else
            throw new IllegalArgumentException("Make must be in the list of: " + validMakes);
    }

    /**
     * This method will return a list of all the valid camera manufacturers
     * @return
     */

    public static List<String> getManufacturers(){
        List<String> brands = Arrays.asList("Canon", "Nikon", "Sony", "Fujifilm", "Samsung");
        Collections.sort(brands);
        return brands;
    }

    public String getModel() {
        return model;
    }

    /**
     * Valid models are Canon, Nikon or Sony
     *
     * @param model
     */
    public void setModel(String model) {
        if(!model.isBlank())
        this.model = model;
        else
            throw new IllegalArgumentException("Model can not be blank");
    }

    public boolean isSlr() {
        return slr;
    }

    public void setSlr(boolean slr) {
        this.slr = slr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 10 && price <= 7000)
            this.price = price;
        else
            throw new IllegalArgumentException("price must be in the range 10-7000");
    }

    public String toString(){
        return String.format("%s-%s, %dMp, $%.2f", make, model, resolution, price);
    }
}

