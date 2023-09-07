package kg.kelso.kelsobackend.dao.author;

import kg.kelso.kelsobackend.entities.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    @Query("SELECT a.author_name FROM author a WHERE a.author_name = :author_name")
    String findByName(@Param("author_name") String author_name);
}
