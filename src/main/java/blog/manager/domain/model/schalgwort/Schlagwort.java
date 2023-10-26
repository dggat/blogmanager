package blog.manager.domain.model.schalgwort;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schlagwort {

    private Integer schlagwortid;
    private String bezeichnung;

    public Schlagwort(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
