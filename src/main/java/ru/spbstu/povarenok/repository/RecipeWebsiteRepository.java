package ru.spbstu.povarenok.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.LinkedList;
import java.sql.*;

import ru.spbstu.povarenok.model.*;

@Repository
public class RecipeWebsiteRepository
{
    @Value("${database.url}")
    public String DB_URL;

    @Value("${database.user}")
    public String DB_USER;

    @Value("${database.password}")
    public String DB_PASSWORD;

    public String DOWNLOADS_FOLDER = "С:\\Users\\Никита\\Downloads";

    public String IMAGES_FOLDER = "D:\\Сем 7\\Маслаков\\Povarenok\\Frontend\\downloads";

    public Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public boolean addUser(User user) {

        String query = "INSERT INTO users (login, password, email) VALUES (\'" + user.getLogin() + "\', \'" +
                user.getPassword() + "\', \'" + user.getEmail() + "\')";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public User getUser(String login, String password) {

        User user = null;
        Long idUser = null;

        String query = "SELECT * FROM users WHERE login = \'" +  login + "\' AND password = \'" + password + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
               idUser = result.getLong("id_user");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        LinkedList<Long> idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();
        LinkedList<Recipe> addedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    addedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM saved_recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        ingredients = new LinkedList<>();
        LinkedList<Recipe> savedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    savedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        query = "SELECT * FROM users WHERE login = \'" +  login + "\' AND password = \'" + password + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                user = new User(result.getLong("id_user"), result.getString("login"),
                        result.getString("password"), result.getString("email"), addedRecipes, savedRecipes);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User getUser(String login) {

        User user = null;
        Long idUser = null;

        String query = "SELECT * FROM users WHERE login = \'" +  login + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                idUser = result.getLong("id_user");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        LinkedList<Long> idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();
        LinkedList<Recipe> addedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    addedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM saved_recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        ingredients = new LinkedList<>();
        LinkedList<Recipe> savedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    savedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        query = "SELECT * FROM users WHERE login = \'" +  login + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                user = new User(result.getLong("id_user"), result.getString("login"),
                        result.getString("password"), result.getString("email"), addedRecipes, savedRecipes);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public User getUserByEmail(String email) {

        User user = null;
        Long idUser = null;

        String query = "SELECT * FROM users WHERE email = \'" +  email + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                idUser = result.getLong("id_user");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        LinkedList<Long> idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();
        LinkedList<Recipe> addedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    addedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        idRecipes = new LinkedList<>();

        query = "SELECT id_recipe FROM saved_recipes WHERE id_user = " + idUser;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return user;
        }

        ingredients = new LinkedList<>();
        LinkedList<Recipe> savedRecipes = new LinkedList<>();

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    savedRecipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return user;
            }
        }

        query = "SELECT * FROM users WHERE email = \'" +  email + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                user = new User(result.getLong("id_user"), result.getString("login"),
                        result.getString("password"), result.getString("email"), addedRecipes, savedRecipes);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public LinkedList<Category> getAllCategories() {

        LinkedList<Category> categories = new LinkedList<>();

        String query = "SELECT * FROM categories";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query);) {

            while (result.next()) {
                categories.add(new Category(result.getLong("id_category"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }

    public Category getCategory(String name) {

        Category category = null;

        String query = "SELECT * FROM categories WHERE name = \'" +  name + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                category = new Category(result.getLong("id_category"), result.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return category;
    }

    public LinkedList<Cuisine> getAllCuisines() {

        LinkedList<Cuisine> cuisines = new LinkedList<>();

        String query = "SELECT * FROM cuisines";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                cuisines.add(new Cuisine(result.getLong("id_cuisine"), result.getString("name")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cuisines;
    }

    public Cuisine getCuisine(String name) {

        Cuisine cuisine = null;

        String query = "SELECT * FROM cuisines WHERE name = \'" +  name + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query);) {

            if (result.next()) {
                cuisine = new Cuisine(result.getLong("id_cuisine"), result.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cuisine;
    }

    public boolean addIngredient(Ingredient ingredient) {

        String query = "INSERT INTO ingredients (id_recipe, name, grams) VALUES (" + ingredient.getIdRecipe() +
                ", \'" + ingredient.getName() + "\', " + ingredient.getGrams() + ")";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addRecipe(Recipe recipe) {

        Long idUser = getUser(recipe.getUserLogin()).getId();
        Long idCuisine = getCuisine(recipe.getCuisine()).getId();
        Long idCategory = getCategory(recipe.getCategory()).getId();

        String query = "INSERT INTO recipes (id_user, name, image_url, date_added, cuisine, category, cooking_time, " +
                "description, recipe) VALUES (" + idUser + ", \'" + recipe.getName() + "\', \'" + recipe.getImageUrl() +
                "\', \'" + recipe.getDateAdded() + "\', " + idCuisine + ", " + idCategory + ", " + recipe.getCookingTime() +
                ", \'" + recipe.getDescription() + "\', \'" + recipe.getRecipe() + "\')";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(query);

            Long idRecipe = getRecipe(recipe.getName()).getId();

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.setIdRecipe(idRecipe);
                addIngredient(ingredient);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public Recipe getRecipe(String name) {

        Recipe recipe = null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();

        String query = "SELECT * FROM ingredients " +
                "JOIN recipes ON ingredients.id_recipe = recipes.id_recipe " +
                "WHERE recipes.name = \'" + name + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                ingredients.add(new Ingredient(result.getLong("id_ingredient"),
                        result.getLong("id_recipe"), result.getString("name"),
                        result.getDouble("grams")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipe;
        }

        query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                "categories.name, cooking_time, description, recipe FROM recipes " +
                "JOIN users ON recipes.id_user = users.id_user " +
                "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                "JOIN categories ON recipes.category = categories.id_category " +
                "WHERE recipes.name = \'" + name + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                recipe = new Recipe(result.getLong("id_recipe"), result.getString("login"),
                        result.getString("name"), result.getString("image_url"),
                        result.getString("date_added"), result.getString(6),
                        result.getString(7), result.getInt("cooking_time"), ingredients,
                        result.getString("description"),  result.getString("recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recipe;
    }

    public Recipe getRecipeByUrl(String url) {

        Recipe recipe = null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();

        String query = "SELECT * FROM ingredients " +
                "JOIN recipes ON ingredients.id_recipe = recipes.id_recipe " +
                "WHERE recipes.image_url = \'" + url + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                ingredients.add(new Ingredient(result.getLong("id_ingredient"),
                        result.getLong("id_recipe"), result.getString("name"),
                        result.getDouble("grams")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipe;
        }

        query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                "categories.name, cooking_time, description, recipe FROM recipes " +
                "JOIN users ON recipes.id_user = users.id_user " +
                "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                "JOIN categories ON recipes.category = categories.id_category " +
                "WHERE recipes.image_url = \'" + url + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                recipe = new Recipe(result.getLong("id_recipe"), result.getString("login"),
                        result.getString("name"), result.getString("image_url"),
                        result.getString("date_added"), result.getString(6),
                        result.getString(7), result.getInt("cooking_time"), ingredients,
                        result.getString("description"),  result.getString("recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recipe;
    }

    public Recipe getRecipeByDescription(String description) {

        Recipe recipe = null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();

        String query = "SELECT * FROM ingredients " +
                "JOIN recipes ON ingredients.id_recipe = recipes.id_recipe " +
                "WHERE recipes.description = \'" + description + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                ingredients.add(new Ingredient(result.getLong("id_ingredient"),
                        result.getLong("id_recipe"), result.getString("name"),
                        result.getDouble("grams")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipe;
        }

        query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                "categories.name, cooking_time, description, recipe FROM recipes " +
                "JOIN users ON recipes.id_user = users.id_user " +
                "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                "JOIN categories ON recipes.category = categories.id_category " +
                "WHERE recipes.description = \'" + description + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                recipe = new Recipe(result.getLong("id_recipe"), result.getString("login"),
                        result.getString("name"), result.getString("image_url"),
                        result.getString("date_added"), result.getString(6),
                        result.getString(7), result.getInt("cooking_time"), ingredients,
                        result.getString("description"),  result.getString("recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recipe;
    }

    public Recipe getRecipeByStepByStepRecipe(String stepByStepRecipe) {

        Recipe recipe = null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();

        String query = "SELECT * FROM ingredients " +
                "JOIN recipes ON ingredients.id_recipe = recipes.id_recipe " +
                "WHERE recipes.recipe = \'" + stepByStepRecipe + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                ingredients.add(new Ingredient(result.getLong("id_ingredient"),
                        result.getLong("id_recipe"), result.getString("name"),
                        result.getDouble("grams")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipe;
        }

        query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                "categories.name, cooking_time, description, recipe FROM recipes " +
                "JOIN users ON recipes.id_user = users.id_user " +
                "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                "JOIN categories ON recipes.category = categories.id_category " +
                "WHERE recipes.recipe = \'" + stepByStepRecipe + "\'";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            if (result.next()) {
                recipe = new Recipe(result.getLong("id_recipe"), result.getString("login"),
                        result.getString("name"), result.getString("image_url"),
                        result.getString("date_added"), result.getString(6),
                        result.getString(7), result.getInt("cooking_time"), ingredients,
                        result.getString("description"),  result.getString("recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recipe;
    }

    public boolean saveRecipe(String login, String name) {

        Long idUser = getUser(login).getId();
        Long idRecipe = getRecipe(name).getId();

        String query = "INSERT INTO saved_recipes (id_user, id_recipe) VALUES (" + idUser + ", " + idRecipe + ")";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public LinkedList<Recipe> getLastRecipes(Integer count) {

        LinkedList<Long> idRecipes = new LinkedList<>();
        LinkedList<Recipe> recipes = new LinkedList<>();
        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();

        String query = "SELECT id_recipe FROM recipes ORDER BY date_added DESC LIMIT " + count;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipes;
        }

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    recipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }
        }

        return recipes;
    }

    public LinkedList<Recipe> getRecipes(String category, String cuisine) {

        LinkedList<Long> idRecipes = new LinkedList<>();
        LinkedList<Recipe> recipes = new LinkedList<>();
        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();

        String query = "SELECT id_recipe FROM recipes " +
                "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                "JOIN categories ON recipes.category = categories.id_category " +
                "WHERE cuisines.name = \'" + cuisine + "\' AND categories.name = \'" + category + "\' " +
                "ORDER BY date_added DESC";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipes;
        }

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    recipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }
        }

        return recipes;
    }

    public LinkedList<Recipe> getRecipes(String keywords) {

        LinkedList<Long> idRecipes = new LinkedList<>();
        LinkedList<Recipe> recipes = new LinkedList<>();
        LinkedList<LinkedList<Ingredient>> ingredients = new LinkedList<>();

        String query = "SELECT id_recipe FROM recipes " +
                "WHERE name LIKE \'%" + keywords + "%\' " +
                "ORDER BY date_added DESC";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                idRecipes.add(result.getLong("id_recipe"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return recipes;
        }

        for (Long id : idRecipes) {

            query = "SELECT * FROM ingredients WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                ingredients.add(new LinkedList<>());
                while (result.next()) {
                    ingredients.getLast().add(new Ingredient(result.getLong("id_ingredient"),
                            result.getLong("id_recipe"), result.getString("name"),
                            result.getDouble("grams")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }

            query = "SELECT id_recipe, users.login, recipes.name, image_url, date_added, cuisines.name, " +
                    "categories.name, cooking_time, description, recipe FROM recipes " +
                    "JOIN users ON recipes.id_user = users.id_user " +
                    "JOIN cuisines ON recipes.cuisine = cuisines.id_cuisine " +
                    "JOIN categories ON recipes.category = categories.id_category " +
                    "WHERE id_recipe = " + id;

            try (Connection connection = getConnection(); Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    recipes.add(new Recipe(result.getLong("id_recipe"), result.getString("login"),
                            result.getString("name"), result.getString("image_url"),
                            result.getString("date_added"), result.getString(6),
                            result.getString(7), result.getInt("cooking_time"), ingredients.getLast(),
                            result.getString("description"), result.getString("recipe")));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return recipes;
            }
        }

        return recipes;
    }

    public boolean deleteRecipe(String login, String name) {

        Long idUser = getUser(login).getId();
        Long idRecipe = getRecipe(name).getId();

        String query = "DELETE FROM saved_recipes WHERE id_user = " + idUser + " AND id_recipe = " + idRecipe;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            statement.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}