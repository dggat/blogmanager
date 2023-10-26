package blog.manager.domain.model.chefredakteur;

import java.sql.SQLException;
//import java.util.List;

import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.redakteur.Redakteur;

public interface ChefredakteurRepository {

    Integer createChefredakteur(Nutzer nutzer, Redakteur redakteur, Chefredakteur chefredakteur) throws SQLException;
}
