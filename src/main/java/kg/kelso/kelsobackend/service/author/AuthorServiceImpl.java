package kg.kelso.kelsobackend.service.author;

import kg.kelso.kelsobackend.dao.author.AuthorDao;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.models.author.AuthorModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorServiceImpl implements AuthorService {

    AuthorDao dao;

    @Override
    public String getById(Long id){
        Optional<Author> optionalAuthor = dao.findById(id);
        return optionalAuthor.isPresent() ? optionalAuthor.get().getName() : "Author with id: " + id + " is not found!";
    }

    @Override
    public Long save(String authorName){
        return dao.save(new Author(null, authorName, new Timestamp(System.currentTimeMillis()), null, null)).getId();
    }

    @Override
    public Author getByName(String name){
        return dao.findByName(name);
    }

    @Override
    public List<AuthorModel> getAll(){
        return dao.findAll().stream().map(e -> new AuthorModel(e.getName())).collect(Collectors.toList());
    }
}
