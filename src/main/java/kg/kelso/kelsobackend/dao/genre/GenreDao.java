package kg.kelso.kelsobackend.dao.genre;

import kg.kelso.kelsobackend.entities.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {

    @Query("select g.id, g.name from Genre g where g.rdt is null")
    Optional<Genre> findByNameWithCDT(String name);

}
