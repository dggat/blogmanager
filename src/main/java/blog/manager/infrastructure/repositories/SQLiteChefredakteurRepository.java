package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

import blog.manager.domain.model.chefredakteur.Chefredakteur;
import blog.manager.domain.model.chefredakteur.ChefredakteurRepository;
import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.redakteur.Redakteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;

@NoArgsConstructor
public class SQLiteChefredakteurRepository implements ChefredakteurRepository {

    @Inject
    private DataSource dataSource;

    @Override
    public Integer createChefredakteur(Nutzer nutzer, Redakteur redakteur, Chefredakteur chefredakteur) throws SQLException {
        String sql = "INSERT INTO Nutzer(Email, Passwort, Geschlecht, Benutzername, Geburtsdatum) VALUES (?, ?, ?, ?, ?);";
        int generatedKey = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if (!nutzerExists(nutzer.getEmail(), nutzer.getPasswort())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, nutzer.getEmail());
                    preparedStatement.setString(2, nutzer.getPasswort());
                    preparedStatement.setString(3, nutzer.getGeschlecht());
                    preparedStatement.setString(4, nutzer.getBenutzername());
                    preparedStatement.setString(5, nutzer.getGeburtsdatum());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Nutzer nicht eingefuegt. " + e.getMessage());
            }
            try {
                sql = "INSERT INTO Redakteur VALUES (?, ?, ?, ?)";
                if ( !nutzerExists(nutzer.getEmail(), nutzer.getPasswort())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, nutzer.getEmail());
                    preparedStatement.setString(2, redakteur.getVorname());
                    preparedStatement.setString(3, redakteur.getNachname());
                    preparedStatement.setString(4, redakteur.getBiographie());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("        Redakteur nicht eingefuegt.         " + e.getMessage());
            }

            try {
                sql = "INSERT INTO Chefredakteur(Email, Telefonnummer) VALUES (?, ?)";
                if ( !nutzerExists(nutzer.getEmail(), nutzer.getPasswort())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, nutzer.getEmail());
                    preparedStatement.setString(2, chefredakteur.getTelefonnummer());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("        Chefredakteur nicht eingefuegt      . " + e.getMessage());
            }

            connection.commit();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        }

        return generatedKey;
    }

    private boolean nutzerExists(String email, String passwort) throws SQLException {
        boolean found = false;
        String sql = "SELECT Passwort FROM Nutzer WHERE Email=?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesPasswort = null;

            if (resultSet.next()) {
                found = true;
                altesPasswort = resultSet.getString("Passwort");
            }

            if (altesPasswort != null && !altesPasswort.equals(passwort))
                throw new SQLException("        Passwort stimmt nicht ueberein.     ");

        } catch (SQLException e) {
            throw e;
        }

        return found;
    }


}
