package fr.thivard.jsonparser.models;

//Car class
public class Car {

    private String brand;

    private String model;

    private Long power;

    public Car(String brand, String model, Long power) {
        this.brand = brand;
        this.model = model;
        this.power = power;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
