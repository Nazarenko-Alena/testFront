package ru.spbstu.povarenok.model;

import java.util.LinkedList;

public class User {

    private Long id;
    private String login;
    private String password;
    private String email;
    private LinkedList<Recipe> addedRecipes;
    private LinkedList<Recipe> savedRecipes;

    public User(Long id, String login, String password, String email, LinkedList<Recipe> addedRecipes, LinkedList<Recipe> savedRecipes) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.addedRecipes = addedRecipes;
        this.savedRecipes = savedRecipes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<Recipe> getAddedRecipes() {
        return addedRecipes;
    }

    public void setAddedRecipes(LinkedList<Recipe> addedRecipes) {
        this.addedRecipes = addedRecipes;
    }

    public LinkedList<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    public void setSavedRecipes(LinkedList<Recipe> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }
}
