package blog.manager.domain.model.schalgwort;

import java.sql.SQLException;
import java.util.List;

public interface SchlagwortRepository {

    List<Schlagwort> getAll() throws SQLException;

    //List<Schlagwort> getSchalgwortByWort(String schlagwort) throws SQLException;

    //List<Schlagwort> getSchalgwortById(String schlagwotrId) throws SQLException;

}
