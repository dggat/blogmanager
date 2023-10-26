package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;
import blog.manager.domain.model.produktrezension.Produktrezension;
import blog.manager.domain.model.produktrezension.ProduktrezensionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteProduktrezensionRepository implements ProduktrezensionRepository {

    @Inject
    DataSource dataSource;
    @Inject
    BlogeintragRepository blogeintragRepository;


    @Override
    public List<Produktrezension> getAll() throws SQLException {
        String sql = "SELECT * FROM Produktrezension INNER JOIN Blogeintrag ON Produktrezension.Id=Blogeintrag.Id;";
        List<Produktrezension> produktrezensionList;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            produktrezensionList = getProdukrezensionList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return produktrezensionList;
    }

    private List<Produktrezension> getProdukrezensionList(PreparedStatement preparedStatement) throws SQLException {

        List<Produktrezension> produktrezensionList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Produktrezension produktrezension = new Produktrezension();
            produktrezension.setBlogeintragid(resultSet.getInt("Id"));
            produktrezension.setProduktrezensionsid(resultSet.getInt("Id"));
            //produktrezension.setTitel(resultSet.getString("Titel"));
            //produktrezension.setText(resultSet.getString("Text"));
            produktrezension.setBeschreibung(resultSet.getString("Produktbezeichnung"));
            //produktrezension.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            //produktrezension.setAenderungsdatum(resultSet.getString("Aenderungsdatum"));
            produktrezension.setFazit(resultSet.getString("Fazit"));

            produktrezensionList.add(produktrezension);
        }
        return produktrezensionList;

    }

    @Override
    public Integer craeteProduktrezension(Blogeintrag blogeintrag, Produktrezension produktrezension) throws SQLException {

        String sql = "INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail)VALUES (?, ?, ?, ?, ?)";
        int generatedKey = 0;
        List<Blogeintrag> blogeintragList = blogeintragRepository.getAll();
        int letztesBlogeintragId = 0;

        for (Blogeintrag blogeintrag1 : blogeintragList) {
            if(blogeintrag1.getBlogeintragid() > letztesBlogeintragId)
                letztesBlogeintragId = blogeintrag1.getBlogeintragid();
        }
        letztesBlogeintragId = letztesBlogeintragId + 1;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if ( !produktrezensionExists(letztesBlogeintragId)) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, blogeintrag.getTitel());
                    preparedStatement.setString(2, blogeintrag.getText());
                    preparedStatement.setString(3, blogeintrag.getErstellungsdatum());
                    preparedStatement.setString(3, blogeintrag.getAenderungsdatum());
                    preparedStatement.setString(5, blogeintrag.getRedakteurEmail());
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Blogeintrag konnte nicht gespeichert werden. " + e.getMessage());
            }
            try {
                sql = "INSERT INTO Produktrezension(Id, Produktbezeichnung, Fazit) VALUES(?, ?, ?);"; //Id, Produktbezeichnung, Fazit
                if ( !produktrezensionExists(letztesBlogeintragId)) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setInt(1, letztesBlogeintragId);
                    preparedStatement.setString(2, produktrezension.getText());
                    preparedStatement.setString(3, produktrezension.getFazit());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Produktrezension konnte nicht gespeichert werden.   " + e.getMessage());
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

    private boolean produktrezensionExists(Integer letztesBlogeintragId) throws SQLException {
        boolean found = false;
        String sql = "SELECT Id=? FROM Produktrezension WHERE Id=?;";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, letztesBlogeintragId);
            preparedStatement.setInt(2, letztesBlogeintragId);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesid = "-1";

            if (resultSet.next()) {
                found = true;
                altesid = resultSet.getString("Id");
            }

            if (altesid.equals("1"))
                throw new SQLException("    Rezension existiert.    ");
            if(altesid.equals("-1")) {
                found = false;
            }

        } catch (SQLException e) {
            throw e;
        }
        return found;
    }

    @Override
    public List<Produktrezension> getAllProduktrezensionByFazitLike(String fazitLike) throws SQLException {

        String sql = "select * from Produktrezension where Fazit like ? COLLATE NOCASE;";
        List<Produktrezension> produktrezensionList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+fazitLike+"%");
            produktrezensionList = getProdukrezensionList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return produktrezensionList;

    }

    @Override
    public List<Produktrezension> getAllProduktrezensionByBeschreibungLike(String berschleibungLike) throws SQLException {

        String sql = "select * from Produktrezension where Produktbezeichnung like ? COLLATE NOCASE;";
        List<Produktrezension> produktrezensionList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+berschleibungLike+"%");
            produktrezensionList = getProdukrezensionList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return produktrezensionList;
    }
}
