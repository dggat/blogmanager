package blog.manager.domain.model.bild;

import java.sql.SQLException;
import java.util.List;

public interface BildRepository {

    List<Bild> getAll() throws SQLException;

    List<Bild> getBilderByAufnahmeort(String aufnahmeort) throws SQLException;

    List<Bild> getBilderBezeichnung(String bezeichnung) throws SQLException;

    Bild getBildById(Integer bildId) throws SQLException;

    Integer createBild(Bild bild) throws SQLException;

    void deleteBild(Integer bildId) throws SQLException;

    boolean bildExists(Integer id) throws SQLException;

    boolean bildExists(String pfad) throws SQLException;
}
