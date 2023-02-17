package ru.spbstu.povarenok.model;

public class Ingredient {

    private Long id;
    private Long idRecipe;
    private String name;
    private Double grams;

    public Ingredient(Long id, Long idRecipe, String name, Double grams) {
        this.id = id;
        this.idRecipe = idRecipe;
        this.name = name;
        this.grams = grams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Long idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGrams() {
        return grams;
    }

    public void setGrams(Double grams) {
        this.grams = grams;
    }
}
