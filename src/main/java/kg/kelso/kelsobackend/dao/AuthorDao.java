package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    Author findByName(String name);
}
