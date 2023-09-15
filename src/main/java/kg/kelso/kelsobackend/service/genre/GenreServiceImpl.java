package kg.kelso.kelsobackend.service.genre;

import kg.kelso.kelsobackend.dao.GenreDao;
import kg.kelso.kelsobackend.entities.genre.Genre;
import kg.kelso.kelsobackend.models.genre.GenreModel;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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
        )).getGenre_id();
    }

    @Override
    public GenreModel getByName(String name) throws NotFoundException {
        return dao.findByNameWithCDT(name).map(genre -> new GenreModel(genre.getGenre_id(), genre.getName()))
                .orElseThrow(() -> new NotFoundException("Genre with name: " + name + " not found"));
    }

    @Override
    public List<GenreModel> getAll(){
        return dao.findAll().stream().map(e -> new GenreModel(e.getGenre_id(), e.getName())).collect(Collectors.toList());
    }

    @Override
    public GenreModel getById(Long id) throws NotFoundException {
        Genre genre = dao.findById(id).orElseThrow(() -> new NotFoundException("Genre with id: " + id + " not found"));
        return new GenreModel(genre.getGenre_id(), genre.getName());
    }
}
