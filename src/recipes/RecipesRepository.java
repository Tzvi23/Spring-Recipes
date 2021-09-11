package recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategoryIgnoreCase(String category);

    List<Recipe> findByNameContainingIgnoreCase(String name);
}
