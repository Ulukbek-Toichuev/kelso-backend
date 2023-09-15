package kg.kelso.kelsobackend.dao.book;

import kg.kelso.kelsobackend.entities.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.rdt is null and b.is_available = true ")
    List<Book> getAllPreview();

}
