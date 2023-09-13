package kg.kelso.kelsobackend.service.genre;

import kg.kelso.kelsobackend.models.Genre.GenreModel;

import java.util.List;

public interface GenreService {

    Long save(GenreModel model);

    void getByName(String name);

    List<GenreModel> getAll();

    GenreModel getById(Long id);

}
