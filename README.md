# Recipe Finder (Java CLI)

Simple backend-style console application that allows users to search for recipes based on one or multiple ingredients using an external API.

## Features

- Search recipes by a single ingredient
- Search recipes by multiple ingredients (intersection logic)
- Fetch data from external REST API (TheMealDB)
- Display list of matching recipes
- View detailed recipe information (instructions, ingredients, video)
- Handle cases where no recipes are found
- Interactive CLI (no need to restart application)

## Tech Stack

- Java 21
- Maven
- HTTP Client (java.net.http)
- JSON parsing (org.json)
- External API: TheMealDB

## How It Works

1. User enters one or more ingredients (e.g. chicken,rice)
2. Application sends requests to external API
3. Results are combined and filtered based on recipe ID
4. User selects a recipe
5. Application fetches and displays full details

## Project Structure

- Main – application flow and user interaction
- RecipeService – business logic and data processing
- ApiClient – external API communication
- Recipe – data model

## Example Usage

Enter ingredients: chicken,rice

Meals:
1. Chicken & chorizo rice pot
2. ...

Choose meal number: 1

=== MEAL DETAILS ===
Name: Chicken & chorizo rice pot
Category: ...
Instructions: ...

## What I Learned

- Working with external REST APIs
- Handling JSON data in Java
- Structuring backend applications (separation of concerns)
- Implementing business logic (data filtering and merging)
- Building interactive console applications

## Future Improvements

- Save favorite recipes
- Add pagination
- Build REST API using Spring Boot
- Add web frontend (HTML/JS or React)