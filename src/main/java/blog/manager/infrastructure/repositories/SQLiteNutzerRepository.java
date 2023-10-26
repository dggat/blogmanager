package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.nutzer.NutzerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
public class SQLiteNutzerRepository implements NutzerRepository {
    @Inject
    private DataSource dataSource;

    @Override
    public List<Nutzer> getAll() throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Nutzer;";
        List<Nutzer> nutzers;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            nutzers = getNutzerList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return nutzers;
    }

    @Override
    public Integer createNutzer(Nutzer nutzer) throws SQLException {
        String sql = "INSERT INTO Nutzer VALUES (?, ?, ?, ?, ?)";
        int key = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if ( !nutzerExists(nutzer.getEmail(), nutzer.getPasswort())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, nutzer.getEmail());
                    preparedStatement.setString(2, nutzer.getPasswort());
                    preparedStatement.setString(3, nutzer.getGeschlecht());
                    preparedStatement.setString(4, nutzer.getGeburtsdatum());
                    preparedStatement.setString(5, nutzer.getBenutzername());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Nutzer(Nutzer) konnte nicht eingefuegt werden.  " + e.getMessage());
            }
            connection.commit();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        }
        return key;
    }

    @Override
    public List<Nutzer> getNutzerByGeschlecht(String geschlecht) throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Nutzer WHERE Geschlecht = ?";
        List<Nutzer> nutzers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, geschlecht);
            nutzers = getNutzerList(preparedStatement);
        }catch (Exception e){
            throw  e;
        }

        return nutzers;
    }

    @Override
    public List<Nutzer> getNutzerByBenutzername(String benutzername) throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Nutzer where Nutzer.Benutzername like ? COLLATE NOCASE";
        List<Nutzer> nutzers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            benutzername = "%"+benutzername+"%";
            preparedStatement.setString(1, benutzername);
            nutzers = getNutzerList(preparedStatement);
        }catch (Exception e){
            throw  e;
        }

        return nutzers;

    }

    @Override
    public List<Nutzer> getNutzerByGeburtsdatum(String geburtsdatum) throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Nutzer where Nutzer.Geburtsdatum like ? COLLATE NOCASE";
        List<Nutzer> nutzers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            geburtsdatum = "%"+geburtsdatum+"%";
            preparedStatement.setString(1, geburtsdatum);
            nutzers = getNutzerList(preparedStatement);
        }catch (Exception e){
            throw  e;
        }

        return nutzers;
    }

    @Override
    public List<Nutzer> getNutzerByEmail(String email) throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Nutzer where Nutzer.Email like ? COLLATE NOCASE";
        List<Nutzer> nutzers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            email = "%"+email+"%";
            preparedStatement.setString(1, email);
            nutzers = getNutzerList(preparedStatement);
        }catch (Exception e){
            throw  e;
        }

        return nutzers;
    }

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

    private List<Nutzer> getNutzerList(PreparedStatement preparedStatement) throws SQLException{
        List<Nutzer> nutzers = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Nutzer nutzer = new Nutzer();
            nutzer.setNutzerid(resultSet.getInt("Rowid"));
            nutzer.setEmail(resultSet.getString("Email"));
            nutzer.setPasswort(resultSet.getString("Passwort"));
            nutzer.setGeschlecht(resultSet.getString("Geschlecht"));
            nutzer.setGeburtsdatum(resultSet.getString("Geburtsdatum"));
            nutzer.setBenutzername(resultSet.getString("Benutzername"));

            nutzers.add(nutzer);
        }
        return nutzers;
    }
}
