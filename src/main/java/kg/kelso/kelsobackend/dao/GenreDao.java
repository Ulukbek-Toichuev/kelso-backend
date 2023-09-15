package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.rdt is null and g.name = :name")
    Optional<Genre> findByNameWithCDT(@Param("name") String name);

}
