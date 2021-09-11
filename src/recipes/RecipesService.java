package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipesService {
    private final RecipesRepository recipesRepository;

    @Autowired
    public RecipesService(RecipesRepository recipesRepository){
        this.recipesRepository = recipesRepository;
    }

    public IdResponse addNewRecipe(Recipe newRecipe){
        newRecipe.setAuthor(getCurrentAuthUser());
        // Save the new recipe to the repository
        Recipe savedRecipe = recipesRepository.save(newRecipe);
        return new IdResponse(savedRecipe.getId());
    }

    public Recipe getRecipe(Long id){
        Optional<Recipe> recipe = recipesRepository.findById(id);
        if(!recipe.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipe.get();
    }

    public void deleteRecipe(Long recipeId){
        boolean exists = recipesRepository.existsById(recipeId);
        if(!exists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<Recipe> recipe = recipesRepository.findById(recipeId);
        if(!getCurrentAuthUser().equals(recipe.get().getAuthor())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipesRepository.deleteById(recipeId);
    }

    public void putRecipe(Long id, Recipe updateRecipe){
        Optional<Recipe> recipe = recipesRepository.findById(id);
        if(!recipe.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(!getCurrentAuthUser().equals(recipe.get().getAuthor())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.get().updateRecipe(updateRecipe);
        recipesRepository.save(recipe.get());
    }

    public List<Recipe> searchByCategory(String category){
        List<Recipe> recipeList = recipesRepository.findByCategoryIgnoreCase(category);
        if(!recipeList.isEmpty()) {
            Collections.sort(recipeList);
            Collections.reverse(recipeList);
        }
        return recipeList;
    }

    public List<Recipe> searchByName(String name){
        List<Recipe> recipeList = recipesRepository.findByNameContainingIgnoreCase(name);
        if(!recipeList.isEmpty()) {
            Collections.sort(recipeList);
            Collections.reverse(recipeList);
        }
        return recipeList;
    }

    public String getCurrentAuthUser(){
        // Get current authenticated user name and set is the author for the new Recipe
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
