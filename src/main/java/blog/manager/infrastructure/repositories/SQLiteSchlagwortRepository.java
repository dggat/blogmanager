package blog.manager.infrastructure.repositories;

import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.sql.DataSource;

import blog.manager.domain.model.schalgwort.Schlagwort;
import blog.manager.domain.model.schalgwort.SchlagwortRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class SQLiteSchlagwortRepository implements SchlagwortRepository {
    @Inject
    DataSource dataSource;

    @Override
    public List<Schlagwort> getAll() throws SQLException {
        String sql = "select ROWID, * from Schlagwort;";
        List<Schlagwort> schlagworte;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            schlagworte = getSchlagworteList(preparedStatement);
        } catch (SQLException e) {
            throw e;
        }
        return schlagworte;
    }

    private List<Schlagwort> getSchlagworteList(PreparedStatement preparedStatement) throws SQLException {
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
}
