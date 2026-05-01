package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ApiClient apiClient = new ApiClient();
        RecipeService recipeService = new RecipeService();

        boolean running = true;

        while (running) {
            System.out.println("\n=== RECIPE FINDER ===");
            System.out.println("Type ingredients separated by comma");
            System.out.println("Example: chicken,rice");
            System.out.println("Type 'exit' to close app");
            System.out.print("Enter ingredients: ");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                continue;
            }

            List<Recipe> recipes = recipeService.findRecipesByIngredients(input, apiClient);

            if (recipes.isEmpty()) {
                System.out.println("No meals found.");
                continue;
            }

            printRecipeList(recipes);

            System.out.print("\nChoose meal number or 0 to search again: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                continue;
            }

            if (choice < 1 || choice > recipes.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            Recipe selectedRecipe = recipes.get(choice - 1);

            String detailsJson = apiClient.getMealDetailsById(selectedRecipe.getId());

            recipeService.printMealDetails(detailsJson);
        }

        scanner.close();
        System.out.println("Application closed.");
    }

    private static void printRecipeList(List<Recipe> recipes) {
        System.out.println("\nMeals:");

        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }
    }
}