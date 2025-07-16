package Pojos;

public class City {

    private String Name;
    private String Temperature;

    // Constructor
    public City() {}

    public City(String name, String temperature) {
        this.Name = name;
        this.Temperature = temperature;
    }

    // Getters & Setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        this.Temperature = temperature;
    }
}
