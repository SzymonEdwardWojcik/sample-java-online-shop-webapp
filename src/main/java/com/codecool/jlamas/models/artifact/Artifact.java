package com.codecool.jlamas.models.artifact;

public class Artifact {

    private String name;
    private Integer price;
    private String description;

    public Artifact() {

    }

    public Artifact(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String asString;
        asString = this.name + "  |  " + this.description + "  |  " + this.price;
        asString += "\n================================\n";

        return asString;
    }
}