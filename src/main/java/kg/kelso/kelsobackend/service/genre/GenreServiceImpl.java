package kg.kelso.kelsobackend.service.genre;

import kg.kelso.kelsobackend.dao.genre.GenreDao;
import kg.kelso.kelsobackend.entities.genre.Genre;
import kg.kelso.kelsobackend.models.Genre.GenreModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GenreServiceImpl implements GenreService{
    GenreDao dao;

    @Override
    public Long save(GenreModel model){
        return dao.save(new Genre(
                null,
                model.getName(),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        )).getId();
    }

    @Override
    public void getByName(String name){
        Optional<Genre> optionalGenre = dao.findByNameWithCDT(name);
        optionalGenre.ifPresent(genre -> log.info(genre.toString()));
    }

    @Override
    public List<GenreModel> getAll(){
        return dao.findAll().stream().map(e -> new GenreModel(e.getId(), e.getName())).collect(Collectors.toList());
    }

    @Override
    public GenreModel getById(Long id){
        Optional<Genre> optionalGenre = dao.findById(id);
        if (optionalGenre.isPresent()){
            return new GenreModel(optionalGenre.get().getId(), optionalGenre.get().getName());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre with id: " + id + " not found");
        }
    }
}
