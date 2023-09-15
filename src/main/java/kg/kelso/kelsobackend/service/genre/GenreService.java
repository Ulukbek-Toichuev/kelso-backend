package kg.kelso.kelsobackend.service.genre;

import kg.kelso.kelsobackend.models.genre.GenreModel;
import kg.kelso.kelsobackend.util.exception.NotFoundException;

import java.util.List;

public interface GenreService {

    Long save(GenreModel model);

    GenreModel getByName(String name) throws NotFoundException;

    List<GenreModel> getAll();

    GenreModel getById(Long id) throws NotFoundException;

}
