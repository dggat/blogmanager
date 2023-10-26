package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;
//import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;
//import blog.manager.domain.model.kommentar.Kommentar;
import blog.manager.domain.model.kommentar.KommentarForAnwender;
import blog.manager.domain.model.schalgwort.Schlagwort;
import blog.manager.domain.model.schalgwort.SchlagwortRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteBlogeintragRepository  implements BlogeintragRepository {

    @Inject
    DataSource dataSource;

    @Inject
    SchlagwortRepository schlagwortRepository;

    @Override
    public List<Blogeintrag> getAll() throws SQLException {
        String sql="SELECT Rowid, * FROM Blogeintrag;";
        List<Blogeintrag> blogeintragList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            blogeintragList = getBlogeintraege(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return blogeintragList;
    }

    private List<Blogeintrag> getBlogeintragList(PreparedStatement preparedStatement) throws SQLException {
        List<Blogeintrag> blogeintragList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Blogeintrag blogeintrag = new Blogeintrag();
            blogeintrag.setBlogeintragid(resultSet.getInt("Id"));
            blogeintrag.setTitel(resultSet.getString("Titel"));
            blogeintrag.setText(resultSet.getString("Text"));
            blogeintrag.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            blogeintrag.setAenderungsdatum(resultSet.getString("Aenderungsdatum"));
            blogeintrag.setRedakteurEmail(resultSet.getString("RedakteurEMail"));
            blogeintragList.add(blogeintrag);
        }
        return blogeintragList;
    }

    private List<Blogeintrag> getBlogeintraege(PreparedStatement preparedStatement) throws SQLException {
        List<Blogeintrag> blogeintragList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Blogeintrag blogeintrag = new Blogeintrag();
            blogeintrag.setBlogeintragid(resultSet.getInt(1));
            blogeintrag.setTitel(resultSet.getString("Titel"));
            blogeintrag.setText(resultSet.getString("Text"));
            blogeintrag.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            blogeintrag.setAenderungsdatum(resultSet.getString("Aenderungsdatum"));
            //String aenderungsdatum = resultSet.getString("Aenderungsdatum");
            blogeintrag.setRedakteurEmail(resultSet.getString("RedakteurEMail"));
            blogeintragList.add(blogeintrag);
        }
        return blogeintragList;
    }

    @Override
    public List<Schlagwort> getSchalworteByBlogeintragId(Integer blogeintragId) throws SQLException {

        String sql = "SELECT s.ROWID , * FROM Gehoert_zu INNER JOIN Blogeintrag B on Gehoert_zu.BlogeintragId = ?\n" +
                "INNER JOIN Schlagwort S on Gehoert_zu.Wort = S.Wort GROUP BY Gehoert_zu.Wort;";
        List<Schlagwort> schlagworte;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogeintragId);
            schlagworte = getWorteByBlogeintragId(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return schlagworte;
    }

    @Override
    public Blogeintrag getByBlogeintragId(Integer blogeintragId) throws SQLException {
        String sql = "SELECT * FROM Blogeintrag WHERE rowid = ?;";
        List<Blogeintrag> blogeintragList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogeintragId);
            blogeintragList = getBlogeintragList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        if(blogeintragList.size() == 0)
            throw new SQLException("        Blogeintrag mit id: "+blogeintragId+" existiert nicht!      ");
        return blogeintragList.get(0);
    }

    @Override
    public Integer createBlogeintrag(Blogeintrag blogeintrag) throws SQLException {
        String sql = "INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail)" +
                "VALUES (?, ?, ?, ?, ?)";
        int key = 0;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.closeOnCompletion();
                preparedStatement.setString(1, blogeintrag.getTitel());
                preparedStatement.setString(2, blogeintrag.getText());
                preparedStatement.setString(3, blogeintrag.getErstellungsdatum());
                preparedStatement.setString(3, blogeintrag.getAenderungsdatum());
                preparedStatement.setString(5, blogeintrag.getRedakteurEmail());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Blogeintrag konnte nicht eingefuegt werden. " + e.getMessage());
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
    public List<KommentarForAnwender> getAllKommentarByBlogeintragId(Integer blogeintragId) throws SQLException {
        String sql ="SELECT n.ROWID, * FROM Kommentar INNER JOIN Nutzer N on Kommentar.BlogeintragId=? GROUP BY Kommentar.Id;";
        List<KommentarForAnwender> kommentarList;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogeintragId);
            kommentarList = getKommentarForAnwenderListByBlogeintragId(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return kommentarList;
    }

    @Override
    public Integer setSchlagwort(Integer blogeintragRowId, String bezeichnung) throws SQLException {
        String sql = "insert into Gehoert_zu values (?, ?);"; //Wort, BlogeintragId
        int key = 0;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sql);
                if ( !gehoert_zuExists(blogeintragRowId, bezeichnung)) {
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, bezeichnung);
                    preparedStatement.setInt(2, blogeintragRowId);

                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("            Gehoert_zu konnte nicht eingefuegt werden!             " + e.getMessage());
            }
            connection.commit();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        }
        if(key == 0)
            throw new SQLException("    Schllagwort wurde nicht gespeichert!");
        return key;
    }

    private boolean gehoert_zuExists(int blogeintrgId, String bezeichnung) throws SQLException {

        String sql = "select Wort From Gehoert_zu where BlogeintragId=? and Wort=?;";
        boolean found = false;
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, blogeintrgId);
            preparedStatement.setString(2, bezeichnung);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesWort = null;

            if (resultSet.next()) {
                found = true;
                altesWort = resultSet.getString("Wort");
            }

            if (altesWort !=null && !altesWort.equals(bezeichnung))
                throw new SQLException("Wort ist bereits zum gegebenen Blogeintrag gespeichert.");


        } catch (SQLException e) {
            throw e;
        }
        return found;
    }


    @Override
    public Integer setBewertung(String email, Integer blogeintragRowId, Integer bewertung) throws SQLException {
        String sql = "INSERT INTO Bewertet VALUES(?, ?, ?);";
        int key = 0;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.closeOnCompletion();
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, blogeintragRowId);
                preparedStatement.setInt(3, bewertung);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Blogeintrag konnte nicht eingefuegt werden. " + e.getMessage());
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
    public Integer setKommentar(String email, Integer rowId, String text) throws SQLException {

        String sql = "INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(?,?,?,?);";
        int key = 0;
        Blogeintrag byBlogeintragId = getByBlogeintragId(rowId);
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
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4, byBlogeintragId.getBlogeintragid());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Kommentar konnte nicht eingefuegt werden. " + e.getMessage());
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
    public void deleteBlogeintrag(Integer blogeintragIRowId) throws SQLException {
        String sql = "DELETE FROM Blogeintrag WHERE rowid = ?;";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, blogeintragIRowId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows <= 0)
                throw new NotFoundException("       Album wurde nicht gefunden oder existiert nicht!        ");
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean blogeintragExists(Integer blogeintragid) throws SQLException {
        List<Blogeintrag> blogeintragList = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        try{
            blogeintragList = getAll();
            for (Blogeintrag blogeintrag :blogeintragList) {
                ids.add(blogeintrag.getBlogeintragid());
            }
        }catch (SQLException e){
            throw e;
        }

        if(ids.contains(blogeintragid))
            return true;
        else
            return false;
    }



    /*private int getBlogeintrgIdByRowId(Integer blogeintragId) throws SQLException {
        String sql = "SELECT Id FROM Blogeintrag WHERE Rowid=?;";

        int id;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, blogeintragId);
                preparedStatement.closeOnCompletion();
                ResultSet resultSet = preparedStatement.executeQuery();
                id = resultSet.getInt(1);
                preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    connection.rollback();
                    throw new SQLException("        Blogeintrag mit id: "+blogeintragId+"existiert nicht gefunden.         " + e.getMessage());
                }
                connection.commit();

        } catch (SQLException e) {
            throw e;
        }
            return id;
    }*/



    private List<Schlagwort> getWorteByBlogeintragId(PreparedStatement preparedStatement) throws SQLException {
        List<Schlagwort> schlagworte = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Schlagwort schlagwort = new Schlagwort();
            schlagwort.setSchlagwortid(resultSet.getInt("ROWID"));
            schlagwort.setBezeichnung(resultSet.getString("Wort"));
            schlagworte.add(schlagwort);
        }
        return schlagworte;

    }

    /*private List<Kommentar> getKommentarListByBlogeintragId(PreparedStatement preparedStatement) throws SQLException {
        List<Kommentar> kommentarList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Kommentar kommentar = new Kommentar();
            kommentar.setNutzerid(resultSet.getInt("ROWID"));
            kommentar.setBlogeintragid(resultSet.getInt("BlogeintragId"));
            kommentar.setKommenatrid(resultSet.getInt("Id"));
            kommentar.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            kommentar.setText(resultSet.getString("Text"));
            kommentar.setEmail(resultSet.getString("Email"));
            kommentarList.add(kommentar);
        }
        return kommentarList;

    }*/

    private List<KommentarForAnwender> getKommentarForAnwenderListByBlogeintragId(PreparedStatement preparedStatement) throws SQLException {
        List<KommentarForAnwender> kommentarList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            KommentarForAnwender kommentar = new KommentarForAnwender();
            kommentar.setNutzerid(resultSet.getInt("ROWID"));
            kommentar.setBlogeintragid(resultSet.getInt("BlogeintragId"));
            kommentar.setKommenatrid(resultSet.getInt("Id"));
            kommentar.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            kommentar.setText(resultSet.getString("Text"));
            kommentar.setEmail(resultSet.getString("Email"));
            kommentarList.add(kommentar);
        }
        return kommentarList;

    }


}
