package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;
//import javax.ws.rs.NotFoundException;

import blog.manager.domain.model.album.Album;
import blog.manager.domain.model.album.AlbumRepository;
import blog.manager.domain.model.bild.Bild;
import blog.manager.domain.model.bild.BildRepository;
import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteAlbumRepository implements AlbumRepository {

    @Inject
    DataSource dataSource;

    @Inject
    BildRepository bildRepository;
    @Inject
    BlogeintragRepository blogeintragRepository;

    @Override
    public List<Album> getAll() throws SQLException {
        String sql = "SELECT Blogeintrag.Id, * FROM Album INNER JOIN Blogeintrag ON Album.Id=Blogeintrag.Id;";
        List<Album> alben;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public Integer createAlbum(Blogeintrag blogeintrag, Album album, Integer bildRowId) throws SQLException {

        String sql = "INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail)VALUES (?, ?, ?, ?, ?)";
        int generatedKey = 0;

        Integer letztesBlogeintragId = 0;
        List<Blogeintrag> blogeintragList = blogeintragRepository.getAll();
        for (Blogeintrag blogeintrag1 : blogeintragList) {
            if(blogeintrag1.getBlogeintragid() > letztesBlogeintragId)
                letztesBlogeintragId = blogeintrag1.getBlogeintragid();
        }


        Bild bild = bildRepository.getBildById(bildRowId);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {

                if (!albumUndBildExist((letztesBlogeintragId +1), bild.getBildid())) {
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
                throw new SQLException("        Blogeintrag mit gegebenem Bild bereits vorhanden.       " + e.getMessage());
            }
            try {
                sql = "INSERT INTO Album VALUES(?, ?)"; //ID, Private
                if ( !albumUndBildExist(letztesBlogeintragId+1, bild.getBildid())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setInt(1, letztesBlogeintragId+1);
                    preparedStatement.setBoolean(2, album.getSichtbarkeit());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("        Blogeintrag(Album) mit gegebenem Bild bereits vorhanden. " + e.getMessage());
            }

            try {
                sql = "INSERT INTO Enthaelt VALUES(?, ?);";//BlogeintragId, BildId
                if ( !albumUndBildExist(letztesBlogeintragId+1, bild.getBildid())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setInt(1, letztesBlogeintragId+1);
                    preparedStatement.setInt(2, bild.getBildid());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Blogeintrag(Enthaelt) mit gegebenem Bild bereits vorhanden. " + e.getMessage());
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

    private boolean blogeintragExists(String email, Integer blogeintragid) throws SQLException {
        boolean found = false;
        String sql = "SELECT Id FROM Blogeintrag WHERE rowid=?";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, blogeintragid);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesId = null;

            if (resultSet.next()) {
                found = true;
                altesId = resultSet.getString("Id");
            }

            if (altesId != null && altesId.equals(blogeintragid))
                throw new SQLException("        Blogeintrag stimmt nicht ueberein.      ");

        } catch (SQLException e) {
            throw e;
        }

        return found;
    }



    private boolean albumUndBildExist(Integer blogeintragId, Integer bildId) throws SQLException {
        boolean found = false;
        String sql = "SELECT BildId=? FROM Enthaelt WHERE BlogeintragId=? LIMIT 1;";
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.closeOnCompletion();
            preparedStatement.setInt(1, bildId);
            preparedStatement.setInt(2, blogeintragId);

            ResultSet resultSet = preparedStatement.executeQuery();

            String altesid = "-1";

            if (resultSet.next()) {
                found = true;
                altesid = resultSet.getString("BildId");
            }

            if (altesid.equals("1"))
                throw new SQLException("        Bild gehoert bereits zum Blogeintrag.       ");
            if(altesid.equals("-1")) {
                found = false;
            }

        } catch (SQLException e) {
            throw e;
        }
        return found;
    }

    @Override
    public Boolean albumExists(Integer albumId) throws SQLException {
        List<Album> albumList = getAll();
        List<Integer> ids = new ArrayList<>();
        for (Album album : albumList) {
            ids.add(album.getAlbumid());
        }

        if (ids.contains(albumId))
            return true;
        else
            return false;

    }

    @Override
    public List<Album> getByAllFilter(String titel, String schlagwort, Double durchschnittsbewertung, String erstellungsdatum) throws SQLException {
        String sql = "SELECT * FROM Album A" +
                " inner join Blogeintrag B on A.Id = B.Id" +
                " inner join Gehoert_zu Gz on B.Id = Gz.BlogeintragId" +
                " inner join Bewertet Bw on B.Id = Bw.BlogeintragId" +
                " where 1=1";

        List<Object> params = new ArrayList<>();
        if (titel != null) {
            sql += " AND B.Titel LIKE ?";
            params.add("%" + titel + "%");
        }
        if (schlagwort != null) {
            sql += " AND Gz.Wort LIKE ?";
            params.add("%" + schlagwort + "%");
        }
        if (durchschnittsbewertung != null) {
            sql += " AND Bw.Bewertung>=?";
            params.add(durchschnittsbewertung);
        }
        if (erstellungsdatum != null) {
            sql += " AND strftime('%s', B.Erstellungsdatum >=?)";
            params.add(erstellungsdatum);
        }
        sql += " COLLATE NOCASE GROUP BY B.Id;";
        List<Album> alben = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public List<Album> getAlbenBySchlagwort(String schlagwort) throws SQLException {
        String sql = "SELECT Blogeintrag.rowid, Blogeintrag.Aenderungsdatum, * FROM Album INNER JOIN Blogeintrag ON Album.rowid=Blogeintrag.rowid INNER JOIN Gehoert_zu ON Gehoert_zu.BlogeintragId=Blogeintrag.rowid WHERE Gehoert_zu.Wort LIKE ? COLLATE NOCASE;";
        List<Album> alben;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+schlagwort+"%");
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public List<Album> getAlbenByTitel(String titel) throws SQLException {
        String sql = "SELECT * FROM Blogeintrag INNER JOIN Album ON Blogeintrag.rowid=Album.rowid WHERE Blogeintrag.rowid in(SELECT Id FROM Album) AND Titel like ? COLLATE NOCASE GROUP BY Album.Id;";
        List<Album> alben;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+titel+"%");
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public List<Album> getAlbenByDurchschnittsbewertung(Double durchschnittsbewertung) throws SQLException {
        String sql = "SELECT * FROM Album inner join Blogeintrag on Blogeintrag.rowid=Album.rowid AND Album.Id in (select BlogeintragId from Bewertet where Bewertung >= ?);";
        List<Album> alben;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,durchschnittsbewertung);
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public List<Album> getAlbenByErstellungsdatum(String erstellungsdatum) throws SQLException {
        String sql = "SELECT * FROM Blogeintrag \n" +
                "\tINNER JOIN Album ON Blogeintrag.rowid=Album.rowid \n" +
                "\t\tWHERE Blogeintrag.Id in(SELECT Id FROM Album) \n" +
                "\t\t\tAND Erstellungsdatum >= ?;";
        List<Album> alben;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+erstellungsdatum+"%");
            alben = getAlbenList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return alben;
    }

    @Override
    public Integer editAlbum(Integer rowId, Boolean sichtbarkeit, String titel, String text) throws SQLException {
        String sql ="UPDATE Blogeintrag SET Titel=?, Text=? WHERE Id=? AND RedakteurEMail=?"; //UPDATE Kommentar SET Text=? WHERE Id=?;
        int key = 0;

        List<Album> albumList = getAll();
        Album album = new Album();
        for (Album album1 : albumList) {
            if(rowId == album1.getAlbumid())
                album = album1;
        }

        List<Blogeintrag> blogeintragList = blogeintragRepository.getAll();
        Blogeintrag blogeintrag = new Blogeintrag();
        for (Blogeintrag blogeintrag1 : blogeintragList) {
            if(rowId == blogeintrag1.getBlogeintragid())
                blogeintrag = blogeintrag1;
        }

        String newText = null;
        if(text != null)
            newText = text;
        else
            newText = blogeintrag.getText();

        String newTitel = null;
        if (titel != null)
            newTitel = titel;
        else
            newTitel = blogeintrag.getTitel();

        Boolean newSichtbarkeit = null;
        if(sichtbarkeit != null)
            newSichtbarkeit = sichtbarkeit;
        else
            newSichtbarkeit = album.getSichtbarkeit();

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            try {
                if ( blogeintragExists(blogeintrag.getRedakteurEmail(), blogeintrag.getBlogeintragid())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setString(1, newTitel);
                    preparedStatement.setString(2, newText);
                    preparedStatement.setInt(3, rowId);
                    preparedStatement.setString(4, blogeintrag.getRedakteurEmail());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Blogeintrag nicht geaendert!      " + e.getMessage());
            }

            sql = "UPDATE Album SET Private=? WHERE Id=?";
            try {
                if ( blogeintragExists(blogeintrag.getRedakteurEmail(), blogeintrag.getBlogeintragid())) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.closeOnCompletion();
                    preparedStatement.setBoolean(1, newSichtbarkeit);
                    preparedStatement.setInt(2, rowId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("    Album nicht geaendert!      " + e.getMessage());
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
    public Album getAlbumById(Integer albumid) throws SQLException {
        List<Album> all = getAll();
        Album album = null;
        for (Album album1 : all) {
            if(album1.getAlbumid() == albumid)
                album = album1;
        }
        return album;
    }


    private List<Album> getAlbenList(PreparedStatement preparedStatement) throws SQLException {
        List<Album> albenList = new ArrayList<>();
        preparedStatement.closeOnCompletion();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Album album = new Album();
            album.setTitel(resultSet.getString("Titel"));
            album.setText(resultSet.getString("Text"));
            album.setErstellungsdatum(resultSet.getString("Erstellungsdatum"));
            album.setRedakteurEmail(resultSet.getString("RedakteurEMail"));
            album.setAlbumid(resultSet.getInt("Id"));
            album.setBlogeintragid(resultSet.getInt("Id"));
            album.setAenderungsdatum(resultSet.getString("Aenderungsdatum"));
            if(resultSet.getBoolean("Private"))
                album.setSichtbarkeit(true);
            else
                album.setSichtbarkeit(false);
            albenList.add(album);
        }
        return albenList;
    }

}
