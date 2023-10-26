package blog.manager.domain.model.produktrezension;

import java.sql.SQLException;
import java.util.List;

import blog.manager.domain.model.blogeintrag.Blogeintrag;

public interface ProduktrezensionRepository {

    List<Produktrezension> getAll() throws SQLException;

    Integer craeteProduktrezension(Blogeintrag blogeintrag, Produktrezension produktrezension) throws  SQLException;

    List<Produktrezension> getAllProduktrezensionByFazitLike(String fazitLike) throws SQLException;

    List<Produktrezension> getAllProduktrezensionByBeschreibungLike(String berschleibungLike) throws SQLException;


}
