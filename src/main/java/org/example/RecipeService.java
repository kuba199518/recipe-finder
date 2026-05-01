package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RecipeService {

    public List<Recipe> getMeals(String json) {
        List<Recipe> recipes = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        if (jsonObject.isNull("meals")) {
            return recipes;
        }

        JSONArray meals = jsonObject.getJSONArray("meals");

        for (int i = 0; i < meals.length(); i++) {
            JSONObject meal = meals.getJSONObject(i);

            String id = meal.getString("idMeal");
            String name = meal.getString("strMeal");
            String image = meal.getString("strMealThumb");

            recipes.add(new Recipe(id, name, image));
        }

        return recipes;
    }

    public List<Recipe> findRecipesByIngredients(String input, ApiClient apiClient) {
        String[] ingredients = input.split(",");

        if (ingredients.length == 1) {
            return findRecipesBySingleIngredient(ingredients[0].trim(), apiClient);
        }

        return findRecipesByMultipleIngredients(ingredients, apiClient);
    }

    private List<Recipe> findRecipesBySingleIngredient(String ingredient, ApiClient apiClient) {
        String json = apiClient.getMealsByIngredient(ingredient);
        return getMeals(json);
    }

    private List<Recipe> findRecipesByMultipleIngredients(String[] ingredients, ApiClient apiClient) {
        Set<String> commonIds = null;
        Map<String, Recipe> recipeMap = new HashMap<>();

        for (String ingredient : ingredients) {
            String json = apiClient.getMealsByIngredient(ingredient.trim());
            List<Recipe> recipes = getMeals(json);

            Set<String> currentIds = new HashSet<>();

            for (Recipe recipe : recipes) {
                currentIds.add(recipe.getId());
                recipeMap.put(recipe.getId(), recipe);
            }

            if (commonIds == null) {
                commonIds = currentIds;
            } else {
                commonIds.retainAll(currentIds);
            }
        }

        List<Recipe> result = new ArrayList<>();

        if (commonIds != null) {
            for (String id : commonIds) {
                result.add(recipeMap.get(id));
            }
        }

        return result;
    }

    public void printMealDetails(String json) {
        JSONObject jsonObject = new JSONObject(json);

        if (jsonObject.isNull("meals")) {
            System.out.println("Meal details not found.");
            return;
        }

        JSONArray meals = jsonObject.getJSONArray("meals");
        JSONObject meal = meals.getJSONObject(0);

        System.out.println("\n=== MEAL DETAILS ===");
        System.out.println("Name: " + meal.getString("strMeal"));
        System.out.println("Category: " + meal.optString("strCategory"));
        System.out.println("Area: " + meal.optString("strArea"));

        System.out.println("\nIngredients:");

        for (int i = 1; i <= 20; i++) {
            String ingredient = meal.optString("strIngredient" + i);
            String measure = meal.optString("strMeasure" + i);

            if (ingredient != null && !ingredient.isBlank()) {
                System.out.println("- " + ingredient + " - " + measure);
            }
        }

        System.out.println("\nInstructions:");
        System.out.println(meal.optString("strInstructions"));

        String youtube = meal.optString("strYoutube");

        if (!youtube.isBlank()) {
            System.out.println("\nVideo: " + youtube);
        }
    }
}