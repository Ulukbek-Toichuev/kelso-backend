package kg.kelso.kelsobackend.dao;

import kg.kelso.kelsobackend.entities.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.rdt is null and b.is_available = true ")
    List<Book> getAll();

    @Modifying
    @Query("update Book b set b.price = :price, b.mdt = :mdt where b.book_id = :id")
    void updatePrice(@Param("price") BigInteger price, @Param("id") Long id, @Param("mdt") Timestamp mdt);

    @Query("select b from Book b where b.author.id = :authorId")
    List<Book> getByAuthorId(@Param("authorId") Long authorId);

    @Query(value = "SELECT " +
            "b.book_id AS book_id, b.isbn AS isbn, b.title AS title, " +
            "b.price AS price, b.page_count AS page_count, b.cover_type AS cover_type, " +
            "b.is_available AS is_available, b.available_count AS available_count, " +
            "b.cdt AS cdt, b.mdt AS mdt, b.rdt AS rdt, " +
            "a.id AS author_id, a.name AS author_name, " +
            "a.cdt AS author_cdt, a.mdt AS author_mdt, a.rdt AS author_rdt " +
            "FROM books b " +
            "JOIN books_genre bg ON b.book_id = bg.book_id " +
            "JOIN authors a ON b.author_id = a.id " +
            "WHERE bg.genre_id = :genreId", nativeQuery = true)
    List<Book> getByGenreId(@Param("genreId") Long genreId);

}
