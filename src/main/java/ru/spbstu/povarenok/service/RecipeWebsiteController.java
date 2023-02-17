package ru.spbstu.povarenok.service;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.LinkedList;

import org.springframework.web.server.ResponseStatusException;
import ru.spbstu.povarenok.repository.*;
import ru.spbstu.povarenok.model.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/povarenok")
public class RecipeWebsiteController
{
    private final RecipeWebsiteRepository repository;

    public RecipeWebsiteController(RecipeWebsiteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (user.getLogin().length() > 30 || !user.getLogin().matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Login must contain no more than 30 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        if (user.getPassword().length() < 8 || user.getPassword().length() > 12
                || !user.getPassword().matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password must contain at least 8 and no more 12 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        if (user.getEmail().length() > 30 ||
                !user.getEmail().matches("[a-zA-Zа-яА-Я0-9.]+@[a-zA-Zа-яА-Я0-9]+[.][a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mail must contain no more than 30 characters " +
                            "and match the template ***@***.*** (*** - any number of characters)!");
        }

        if(repository.getUser(user.getLogin()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this login already exists!");
        }

        if(repository.getUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with this email already exists!");
        }

        if (!repository.addUser(user)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to add user!");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/authorization")
    public ResponseEntity<User> getUser(@RequestParam String login, @RequestParam String password) {

        if (login.length() > 30 || !login.matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Login must contain no more than 30 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        if (password.length() < 8 || password.length() > 12
                || !password.matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password must contain at least 8 and no more 12 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        User user = repository.getUser(login, password);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<User> getUser(@PathVariable(name = "login") String login) {

        if (login.length() > 30 || !login.matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Login must contain no more than 30 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        User user = repository.getUser(login);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categories")
    public ResponseEntity<LinkedList<Category>> getCategories() {

        LinkedList<Category> categories = repository.getAllCategories();

        return categories != null
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cuisines")
    public ResponseEntity<LinkedList<Cuisine>> getCuisines() {

        LinkedList<Cuisine> cuisines = repository.getAllCuisines();

        return cuisines != null
                ? new ResponseEntity<>(cuisines, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/recipes/new")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe) {

        if (recipe.getName().length() > 100 || !recipe.getName().matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe name must contain no more than 100 characters " +
                            "and contain only numbers and Russian letters!");
        }

        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getName().length() > 100 || !ingredient.getName().matches("[а-яА-Я0-9 ]+$")) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The ingredient name must contain no more than 100 characters " +
                                "and contain only numbers and Russian letters!");
            }
            if (ingredient.getGrams().toString().length() > 5) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Grams must be a number with no more than 5 characters!");
            }
        }

        if (recipe.getDescription().length() > 500 || !recipe.getDescription().matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe description must contain no more than 500 characters " +
                            "and contain only numbers and Russian letters!");
        }

        if (recipe.getRecipe().length() > 5000 || !recipe.getRecipe().matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe must contain no more than 5000 characters " +
                            "and contain only numbers and Russian letters!");
        }

        if (repository.getRecipe(recipe.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Recipe with this name already exists!");
        }

        if (repository.getRecipeByUrl(recipe.getImageUrl()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Recipe with this url already exists!");
        }

        if (repository.getRecipeByDescription(recipe.getDescription()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Recipe with this description already exists!");
        }

        if (repository.getRecipeByStepByStepRecipe(recipe.getRecipe()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Recipe with this step-by-step recipe already exists!");
        }

        if (!repository.addRecipe(recipe)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to add recipe!");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/recipes/{name}")
    public ResponseEntity<?> getRecipe(@PathVariable(name = "name") String name) {

        if (name.length() > 100 || !name.matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe name must contain no more than 100 characters " +
                            "and contain only numbers and Russian letters!");
        }

        Recipe recipe = repository.getRecipe(name);

        return recipe != null
                ? new ResponseEntity<>(recipe, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/recipes/{login}/save/{name}")
    public ResponseEntity<?> saveRecipe(@PathVariable(name = "login") String login,
                                        @PathVariable(name = "name") String name) {

        if (login.length() > 30 || !login.matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Login must contain no more than 30 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        if (name.length() > 100 || !name.matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe name must contain no more than 100 characters " +
                            "and contain only numbers and Russian letters!");
        }

        if(repository.getUser(login) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no user with this login!");
        }

        if(repository.getRecipe(name) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no recipe with this name!");
        }

        if (!repository.saveRecipe(login, name)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save recipe!");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/recipes/last")
    public ResponseEntity<?> getRecipes(@RequestParam Integer count) {

        if (count <= 0) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The count of recipes can't be negative or equal to zero!");
        }

        LinkedList<Recipe> recipes = repository.getLastRecipes(count);

        return recipes != null
                ? new ResponseEntity<>(recipes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recipes/{category}/{cuisine}")
    public ResponseEntity<?> getRecipes(@PathVariable(name = "category") String category,
                                        @PathVariable(name = "cuisine") String cuisine) {

        LinkedList<Recipe> recipes = repository.getRecipes(category, cuisine);

         return recipes != null
                 ? new ResponseEntity<>(recipes, HttpStatus.OK)
                 : new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

    @GetMapping("/recipes/keywords/{keywords}")
    public ResponseEntity<?> getRecipeByKeywords(@PathVariable(name = "keywords") String keywords) {

        LinkedList<Recipe> recipes = repository.getRecipes(keywords);

        return recipes != null
                ? new ResponseEntity<>(recipes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/recipes/{login}/delete/{name}")
    public ResponseEntity<?> deleteRecipe(@PathVariable( name = "login") String login,
                                          @PathVariable( name = "name") String name) {

        if (login.length() > 30 || !login.matches("[a-zA-Zа-яА-Я0-9]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Login must contain no more than 30 characters " +
                            "and contain only numbers and Russian or English letters!");
        }

        if (name.length() > 100 || !name.matches("[а-яА-Я0-9 ]+$")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The recipe name must contain no more than 100 characters " +
                            "and contain only numbers and Russian letters!");
        }

        if(repository.getUser(login) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no user with this login!");
        }

        if(repository.getRecipe(name) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is no recipe with this name!");
        }

        if (!repository.deleteRecipe(login, name)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to save recipe!");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    }
