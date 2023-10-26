package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

//import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;
import blog.manager.domain.model.kommentar.Kommentar;
import blog.manager.domain.model.kommentar.KommentarRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteKommentarRepository implements KommentarRepository {

    @Inject
    DataSource dataSource;
    @Inject
    BlogeintragRepository blogeintragRepository;

    @Override
    public List<Kommentar> getAllByNutzerEmail(String email) throws SQLException {
        String sql = "SELECT N.ROWID, * from Kommentar K, Nutzer N where K.Email =N.Email AND K.Email=?;";
        List<Kommentar> kommentarList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            kommentarList = getKommentarList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return kommentarList;
    }

    @Override
    public List<Kommentar> getAlleKommentareByText(String email, String text) throws SQLException {
        String sql = "SELECT Nutzer.rowid, * FROM Kommentar INNER JOIN Nutzer on Kommentar.Email = Nutzer.Email AND Kommentar.Email=? AND Kommentar.Text LIKE ? COLLATE NOCASE ORDER BY Nutzer.Email=?;";
        List<Kommentar> kommentarList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, "%"+text+"%");
            preparedStatement.setString(3, email);
            kommentarList = getKommentarList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return kommentarList;
    }

    @Override
    public Integer createKommenatr(String text, String name, Integer blogeintragid) throws SQLException {
        String sql = "INSERT INTO Kommentar(Erstellungsdatum, Text, Email, BlogeintragId) VALUES (?, ?, ?, ?)";
        int key = 0;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.closeOnCompletion();
                LocalDateTime now = LocalDateTime.now();
                String dateStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
                preparedStatement.setString(1, dateStr);
                preparedStatement.setString(2, text);
                preparedStatement.setString(3, name);
                preparedStatement.setInt(4, blogeintragid);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Kommentar nicht eingefuegt.     " + e.getMessage());
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
    public Integer editKommentar(String text, String email, Integer kommentarId) throws SQLException {


        String sql = "UPDATE Kommentar SET Text=? WHERE Id=?;";
        int key = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if ( kommenatrExists(kommentarId, email)) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, text);
                    preparedStatement.setInt(2, kommentarId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Kommentar nicht geaendert!      " + e.getMessage());
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


    /*private List<Kommentar> getAll() throws SQLException {
        String sql = "SELECT Rowid, Id, Erstellungsdatum,  Email, Text, BlogeintragId FROM Kommentar WHERE Id=Id;";
        List<Kommentar> kommentarList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            kommentarList = getKommentarList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return kommentarList;
    }*/

    private boolean kommenatrExists(Integer kommenatrid, String email) throws SQLException {
        boolean found = false;
        String sql = "SELECT Id, Email FROM Kommentar WHERE Id=?;";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, kommenatrid);
            ResultSet resultSet = preparedStatement.executeQuery();

            String altesEmail = null;

            if (resultSet.next()) {
                found = true;
                altesEmail = resultSet.getString("Email");
            }

            if (!altesEmail.equals(email))
                throw new SQLException("    Fremder Kommentar.  ");

        } catch (SQLException e) {
            throw e;

        }

        return found;
    }

    private List<Kommentar> getKommentarList(PreparedStatement preparedStatement) throws SQLException {
        List<Kommentar> kommentarList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Kommentar kommentar = new Kommentar();
            kommentar.setKommenatrid(resultSet.getInt("Id"));
            kommentar.setNutzerid(resultSet.getInt("rowid"));
            kommentar.setBlogeintragid(resultSet.getInt("BlogeintragId"));
            kommentar.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            kommentar.setText(resultSet.getString("Text"));
            kommentar.setEmail(resultSet.getString("Email"));

            kommentarList.add(kommentar);
        }
        return kommentarList;
    }

    /*private Kommentar getKommenatrById(Integer kommentarId) throws SQLException {
        List<Kommentar> kommentarList = new ArrayList<>();
        String sql = "  SELECT * FROM Kommentar HWERE rowid=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, kommentarId);
            preparedStatement.closeOnCompletion();
            kommentarList = getKommentarList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        if (kommentarList.size() == 0)
            throw new SQLException("        Kommentar mit Id: "+kommentarId+" existiert nicht       ");
        return kommentarList.get(0);
    }*/
}
