package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Data
@Table
public class Recipe implements Comparable<Recipe> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @UpdateTimestamp
    private LocalDateTime date;
    @NotBlank
    private String description;
    @Size(min = 1)
    private ArrayList<String> ingredients = new ArrayList<>();
    @Size(min = 1)
    private ArrayList<String> directions = new ArrayList<>();
    @JsonIgnore
    private String author;

    public Recipe() {
    }

    public Recipe(String name, String category, String description, ArrayList<String> ingredients, ArrayList<String> directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public void updateRecipe(Recipe otherRecipe){
        this.name = otherRecipe.name;
        this.category = otherRecipe.category;
        this.description = otherRecipe.description;
        this.ingredients = otherRecipe.ingredients;
        this.directions = otherRecipe.directions;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int compareTo(Recipe recipe) {
        return getDate().compareTo(recipe.getDate());
    }
}
