package kg.kelso.kelsobackend.services;


import kg.kelso.kelsobackend.dao.author.AuthorDao;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.models.author.AuthorModelResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorServiceImpl {

    AuthorDao dao;

    public String findById(Long id){
        Optional<Author> optionalAuthor = dao.findById(id);
        return optionalAuthor.isPresent() ? optionalAuthor.get().getAuthor_name() : "Author with id: " + id + " is not found!";
    }

    public void save(String authorName){

        dao.save(new Author(
                null,
                authorName,
                null,
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        ));

    }

    public String findByName(String name){
        return dao.findByName(name);
    }
}
