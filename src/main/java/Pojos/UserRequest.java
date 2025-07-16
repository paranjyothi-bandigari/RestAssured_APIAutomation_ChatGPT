package Pojos;

import java.util.List;

public class UserRequest {

    private String name;
    private List<String> languages;
    private List<City> City;

    // Constructor
    public UserRequest() {}

    public UserRequest(String name, List<String> languages, List<City> City) {
        this.name = name;
        this.languages = languages;
        this.City = City;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<City> getCity() {
        return City;
    }

    public void setCity(List<City> city) {
        this.City = city;
    }
}

