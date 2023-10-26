package blog.manager.domain.model.album;

import java.sql.SQLException;
import java.util.List;

import blog.manager.domain.model.bild.Bild;
import blog.manager.domain.model.blogeintrag.Blogeintrag;

public interface AlbumRepository {

    List<Album>  getAll() throws SQLException;

    Integer createAlbum(Blogeintrag blogeintrag, Album album, Integer bildRowId) throws SQLException;

    List<Album>  getAlbenBySchlagwort(String schlagwort) throws SQLException;

    List<Album>  getAlbenByTitel(String titel) throws SQLException;

    List<Album>  getAlbenByDurchschnittsbewertung(Double durchschnittsbewertung) throws SQLException;

    List<Album> getAlbenByErstellungsdatum(String erstellungsdatum) throws SQLException;

    Integer editAlbum(Integer rowId, Boolean sichtbarkeit, String text, String titel) throws SQLException;

    Album getAlbumById(Integer albumid) throws SQLException;

    Boolean albumExists(Integer albumId) throws SQLException;

    List<Album> getByAllFilter(String titel, String schlagwort, Double durchschnittsbewertung, String erstellungsdatum) throws SQLException;
}
