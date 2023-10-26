package blog.manager.domain.model.nutzer;

import java.sql.SQLException;
import java.util.List;

public interface NutzerRepository {

    List<Nutzer> getAll() throws SQLException;

    Integer createNutzer(Nutzer nutzer) throws SQLException;

    List<Nutzer> getNutzerByGeschlecht(String geschlecht) throws SQLException;

    List<Nutzer> getNutzerByBenutzername(String benutzername) throws SQLException;

    List<Nutzer> getNutzerByGeburtsdatum(String geburtsdatum) throws SQLException;

    List<Nutzer> getNutzerByEmail(String email) throws SQLException;
}
