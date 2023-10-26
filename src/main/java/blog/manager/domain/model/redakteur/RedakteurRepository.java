package blog.manager.domain.model.redakteur;

import java.sql.SQLException;
import java.util.List;

import blog.manager.domain.model.nutzer.Nutzer;

public interface RedakteurRepository {

    List<Redakteur> getAll() throws SQLException;

    Integer createRedakteur(Nutzer nutzer, Redakteur redakteur) throws SQLException;

    List<Redakteur> getRedakteurByNachmaeLike(String nachname) throws SQLException;

}
