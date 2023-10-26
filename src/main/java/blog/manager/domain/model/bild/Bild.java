package blog.manager.domain.model.bild;

//import blog.manager.domain.model.schalgwort.SchlagwortRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Bild {

    private Integer bildid;
    private String bezeichnung;
    private String aufnahmeort;
    private String pfad;

    public Bild(String bezeichnung, String aufnahmeort, String pfad) {
        this.bezeichnung = bezeichnung;
        this.aufnahmeort = aufnahmeort;
        this.pfad = pfad;
    }
}
