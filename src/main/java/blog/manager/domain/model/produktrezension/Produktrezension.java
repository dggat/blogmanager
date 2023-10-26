package blog.manager.domain.model.produktrezension;

import com.fasterxml.jackson.annotation.JsonIgnore;

import blog.manager.domain.model.blogeintrag.Blogeintrag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Produktrezension extends Blogeintrag {


    private Integer blogeintragid;
    private Integer produktrezensionsid;
    private String beschreibung;
    private String fazit;

    @JsonIgnore
    private String redakteurEmail;
    @JsonIgnore
    private String titel;
    @JsonIgnore
    private String text;
    @JsonIgnore
    private String erstellungsdatum;
    @JsonIgnore
    private String aenderungsdatum;

    public Produktrezension(Blogeintrag blogeintrag, String beschreibung, String fazit) {
        this.setBlogeintragid(blogeintrag.getBlogeintragid());
        this.setErstellungsdatum(blogeintrag.getErstellungsdatum());
        this.setText(blogeintrag.getText());
        this.setTitel(blogeintrag.getTitel());
        this.setRedakteurEmail(blogeintrag.getRedakteurEmail());
        this.beschreibung = beschreibung;
        this.fazit = fazit;
        this.blogeintragid = blogeintrag.getBlogeintragid();
        this.erstellungsdatum = blogeintrag.getErstellungsdatum();
        this.aenderungsdatum = blogeintrag.getErstellungsdatum();
        this.titel = blogeintrag.getTitel();
        this.text = blogeintrag.getText();
    }

}
