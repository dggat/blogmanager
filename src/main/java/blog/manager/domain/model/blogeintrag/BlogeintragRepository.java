package blog.manager.domain.model.blogeintrag;

import java.sql.SQLException;
import java.util.List;

//import blog.manager.domain.model.kommentar.Kommentar;
import blog.manager.domain.model.kommentar.KommentarForAnwender;
import blog.manager.domain.model.schalgwort.Schlagwort;

public interface BlogeintragRepository {

    List<Blogeintrag> getAll() throws SQLException;

    List<Schlagwort> getSchalworteByBlogeintragId(Integer blogeintragId) throws SQLException;

    Blogeintrag getByBlogeintragId(Integer blogeintragId) throws SQLException;

    Integer createBlogeintrag(Blogeintrag blogeintrag) throws SQLException;

    List<KommentarForAnwender> getAllKommentarByBlogeintragId(Integer blogeintragId) throws SQLException;

    Integer setSchlagwort(Integer blogeintragId, String bezeichnung) throws SQLException;

    Integer setBewertung(String email, Integer blogeintragId, Integer bewertung) throws SQLException;

    Integer setKommentar(String email, Integer blogeintragId, String text) throws SQLException;

    void deleteBlogeintrag(Integer blogeintrag) throws SQLException;

    boolean blogeintragExists(Integer blogeintragid) throws SQLException;
}
