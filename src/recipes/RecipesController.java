package recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService){
        this.recipesService = recipesService;
    }

    @PostMapping("/api/recipe/new")
    @ResponseBody
    public IdResponse postRecipe(@Valid @RequestBody Recipe newRecipe){
        return recipesService.addNewRecipe(newRecipe);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id){
        return recipesService.getRecipe(id);
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipe(@RequestParam (required = false) String category, @RequestParam (required = false) String name){
        if((category == null && name == null) || (category != null && name != null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(category != null) return  recipesService.searchByCategory(category);
        else return recipesService.searchByName(name);
    }

    @DeleteMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id){
        recipesService.deleteRecipe(id);
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe updateRecipe){
        recipesService.putRecipe(id, updateRecipe);
    }

}

