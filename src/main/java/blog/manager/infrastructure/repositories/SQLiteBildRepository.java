package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.NotFoundException;
//import javax.ws.rs.core.Response;

//import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.bild.Bild;
import blog.manager.domain.model.bild.BildRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteBildRepository implements BildRepository {

    @Inject
    DataSource dataSource;

    @Override
    public List<Bild> getAll() throws SQLException {
        String sql = "SELECT * FROM Bild";
        List<Bild> bildList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            bildList = getBildList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return bildList;
    }

    @Override
    public List<Bild> getBilderByAufnahmeort(String aufnahmeort) throws SQLException {
        String sql = "SELECT * FROM Bild WHERE Aufnahmeort like ? COLLATE NOCASE";
        List<Bild> bildList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+aufnahmeort+"%" );
            bildList = getBildList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return bildList;
    }

    @Override
    public List<Bild> getBilderBezeichnung(String bezeichnung) throws SQLException {
        String sql = "SELECT * FROM Bild WHERE Bezeichnung like ? COLLATE NOCASE";
        List<Bild> bildList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%"+bezeichnung+"%");
            bildList = getBildList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return bildList;
    }

    @Override
    public Bild getBildById(Integer bildId) throws SQLException {
        String sql = "SELECT * FROM Bild WHERE rowid=? ";
        List<Bild> bildList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bildId);
            bildList = getBildList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        /*
        if(bildList.size() == 0)
            throw new SQLException("Bild mit Id: "+bildId + "existiert nicht");

         */
        return bildList.get(0);
    }

    @Override
    public Integer createBild(Bild bild) throws SQLException {
        String sql = "INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES (?, ?, ?)"; //(Bezeichnung, Aufnahmeort, Pfad)
        int key = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if ( !bildExists(bild.getPfad())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, bild.getBezeichnung());
                    preparedStatement.setString(2, bild.getAufnahmeort());
                    preparedStatement.setString(3, bild.getPfad());

                    //int i = preparedStatement.executeUpdate();

                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Bild nicht eingefuegt. " + e.getMessage());
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
    public void deleteBild(Integer bildId) throws SQLException {
        String sql = "DELETE FROM Bild WHERE rowid=?;";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, bildId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0)
                throw new NotFoundException("   Bild mit Id: "+ bildId+" existiert nicht!    ");
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean bildExists(Integer id) throws SQLException {
        List<Bild> all = getAll();
        List<Integer> ids = new ArrayList<>();
        for (Bild bild : all) {
            ids.add(bild.getBildid());
        }

        if(ids.contains(id))
            return true;
        else
            return false;
    }

    @Override
    public boolean bildExists(String pfad) throws SQLException {
        boolean found = false;
        String sql = "SELECT Pfad FROM Bild WHERE Pfad=?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setString(1, pfad);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesPfad = null;

            if (resultSet.next()) {
                found = true;
                altesPfad = resultSet.getString("Pfad");
            }
            if (altesPfad != null && pfad.equals(altesPfad))
                throw new SQLException("        Bild existiert.     ");

        } catch (SQLException e) {
            throw e;
        }

        return found;
    }


    private List<Bild> getBildList(PreparedStatement preparedStatement) throws SQLException {

        List<Bild> bildList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Bild bild = new Bild();
            bild.setBezeichnung(resultSet.getString("Bezeichnung"));
            bild.setAufnahmeort(resultSet.getString("Aufnahmeort"));
            bild.setPfad(resultSet.getString("Pfad"));
            bild.setBildid(resultSet.getInt("Id"));
            bildList.add(bild);
        }
        return bildList;
    }
}
