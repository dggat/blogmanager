package blog.manager.domain.model.album;

import blog.manager.domain.model.blogeintrag.Blogeintrag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Album extends Blogeintrag {

    private Integer albumid;
    private Integer blogeintragid;
    private Boolean sichtbarkeit;
    private String erstellungsdatum;


    public Album(Blogeintrag blogeintrag, Integer blogeintragid, Boolean privat) {
        this.setTitel(blogeintrag.getTitel());
        this.setText(blogeintrag.getText());
        this.setErstellungsdatum(blogeintrag.getErstellungsdatum());
        this.setAenderungsdatum(blogeintrag.getAenderungsdatum());
        this.setRedakteurEmail(blogeintrag.getRedakteurEmail());
        this.blogeintragid = blogeintragid;
        this.sichtbarkeit = privat;
        this.setErstellungsdatum(erstellungsdatum);
    }
}
