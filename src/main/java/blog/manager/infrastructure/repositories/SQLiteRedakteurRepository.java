package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.redakteur.Redakteur;
import blog.manager.domain.model.redakteur.RedakteurRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

@NoArgsConstructor
public class SQLiteRedakteurRepository implements RedakteurRepository {

    @Inject
    private DataSource dataSource;

    @Override
    public List<Redakteur> getAll() throws SQLException {
        String sql = "SELECT Redakteur.rowid, Nutzer.rowid, * FROM Redakteur INNER JOIN Nutzer ON Redakteur.Email=Nutzer.Email where Redakteur.Email not in (select Email from Chefredakteur);";
        List<Redakteur> redakteure;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            redakteure = getRedakteureList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return redakteure;
    }

    @Override
    public Integer createRedakteur(Nutzer nutzer, Redakteur redakteur) throws SQLException {
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
                throw new SQLException("    Nutzer nicht eingefuegt.    " + e.getMessage());
            }
            try {
                sql = "INSERT INTO Redakteur VALUES (?, ?, ?, ?)";
                if ( !nutzerExists(nutzer.getEmail(), nutzer.getPasswort())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, redakteur.getEmail());
                    preparedStatement.setString(2, redakteur.getVorname());
                    preparedStatement.setString(3, redakteur.getNachname());
                    preparedStatement.setString(4, redakteur.getBiographie());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Redakteur nicht eingefuegt.  " + e.getMessage());
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

    @Override
    public List<Redakteur> getRedakteurByNachmaeLike(String nachname) throws SQLException {
        String sql = "SELECT Nutzer.rowid, Redakteur.rowid, * FROM Redakteur INNER JOIN Nutzer ON Redakteur.Email = Nutzer.Email AND Redakteur.Nachname LIKE ? COLLATE NOCASE;";
        List<Redakteur> redakteure;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+nachname+ "%");
            redakteure = getRedakteureList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return redakteure;

    }


    private List<Redakteur> getRedakteureList(PreparedStatement preparedStatement) throws SQLException {
        List<Redakteur> redakteurs = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();
        //Map<String, Integer> redakteurRowidsFromNutzer = getRedakteurRowidsFromNutzer();
        while (resultSet.next()) {
            Redakteur redakteur = new Redakteur();
            redakteur.setRedakteurid(resultSet.getInt(2));
            redakteur.setNutzerid(resultSet.getInt(1));
            redakteur.setEmail(resultSet.getString("Email"));
            redakteur.setPasswort(resultSet.getString("Passwort"));
            redakteur.setGeschlecht(resultSet.getString("Geschlecht"));
            redakteur.setGeburtsdatum(resultSet.getString("Geburtsdatum"));
            redakteur.setBenutzername(resultSet.getString("Benutzername"));
            redakteur.setVorname(resultSet.getString("Vorname"));
            redakteur.setNachname(resultSet.getString("Nachname"));
            redakteur.setBiographie(resultSet.getString("Biographie"));
            redakteurs.add(redakteur);
        }
        return redakteurs;
    }


    /*private Map<String, Integer> getRedakteurRowidsFromNutzer() throws SQLException{
        String sql = "SELECT Nutzer.rowid, Redakteur.Email FROM Redakteur INNER JOIN Nutzer ON Redakteur.Email=Nutzer.Email where Redakteur.Email not in (select Email from Chefredakteur);";
        Map<String, Integer> redakteureIds;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            redakteureIds = getRedakteureIds(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return redakteureIds;
    }*/

    /*private Map<String, Integer> getRedakteureIds(PreparedStatement preparedStatement) throws SQLException {
        Map<String, Integer> ids = new HashMap<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            ids.put(resultSet.getString("Email"), resultSet.getInt("Rowid"));
        }

        return ids;
    }*/

    private boolean nutzerExists(String email, String passwort) throws SQLException{
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
                throw new SQLException("    Passwort stimmt nicht ueberein.     ");

        } catch (SQLException e) {
            throw e;
        }

        return found;
    }
}
