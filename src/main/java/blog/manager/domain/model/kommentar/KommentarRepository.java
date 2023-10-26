package blog.manager.domain.model.kommentar;

import java.sql.SQLException;
import java.util.List;

public interface KommentarRepository {

    List<Kommentar> getAllByNutzerEmail(String email) throws SQLException;

    List<Kommentar> getAlleKommentareByText(String email, String text) throws SQLException;

    Integer createKommenatr(String text, String name, Integer blogeintragid) throws SQLException;

    Integer editKommentar(String text, String email, Integer blogeintragId) throws SQLException;


}
