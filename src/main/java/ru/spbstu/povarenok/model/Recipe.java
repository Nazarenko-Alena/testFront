package ru.spbstu.povarenok.model;

import java.util.LinkedList;

public class Recipe {

    private Long id;
    private String userLogin;
    private String name;
    private String imageUrl;
    private String dateAdded;
    private String cuisine;
    private String category;
    private Integer cookingTime;
    private LinkedList<Ingredient> ingredients;
    private String description;
    private String recipe;

    public Recipe(Long id, String userLogin, String name, String imageUrl, String dateAdded, String cuisine, String category,
                  Integer cookingTime, LinkedList<Ingredient> ingredients, String description, String recipe) {
        this.id = id;
        this.userLogin = userLogin;
        this.name = name;
        this.imageUrl = imageUrl;
        this.dateAdded = dateAdded;
        this.cuisine = cuisine;
        this.category = category;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.description = description;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
